package com.xiasuhuei321.gankkotlin.data

import com.xiasuhuei321.gankkotlin.Global
import com.xiasuhuei321.gankkotlin.MyObjectBox
import io.objectbox.Box
import io.objectbox.BoxStore

class DataManager {
    lateinit var boxStore: BoxStore
    val storeMap = HashMap<String, Box<Any>>()

    fun initStore() {
        boxStore = MyObjectBox.builder().androidContext(Global.context).build()
    }

    fun <T> getBoxFor(clz: Class<T>): Box<T> {
        return boxStore.boxFor(clz)
    }

    fun addOrUpdateData(datas: List<Data>) {
        boxStore.runInTxAsync({
            val dataStore = boxStore.boxFor(Data::class.java)
            for (data in datas) {
                val d = dataStore.query().equal(Data_._id, data._id).build().findFirst()
                d?.let {
                    data.id = it.id
                }
            }

            dataStore.put(datas)
        }, { _, _ ->

        })
    }

    fun queryDataByType(start: Int, end: Int, dataType: DataType) = queryData(start, end, dataType)


    /**
     * 查询 [start] 到 [end] 之间的数据，左边界 [start] 包含在内，右边界 [end] 不包含在内
     */
    private fun queryData(start: Int, end: Int, dataType: DataType): List<Data> {
        val q = query(Data::class.java).equal(Data_.type, dataType.type).build()
        if (start > q.count()) return mutableListOf()
        val result = q.find()
        if (end > result.size) return result.subList(start, result.size)
        return result.subList(start, end)
    }

    private fun <T> query(clz: Class<T>) = boxStore.boxFor(clz).query()


}

enum class DataType(val type: String) {
    Welfare("福利"),
    Android("Android"),
    iOS("iOS"),
    WebFront("前端"),
    XiaJBTui("瞎推荐"),
    App("App"),
    Video("视频"),
    Extra("拓展资源"),
    OneDay("OneDay"),
    Other("其他")
}