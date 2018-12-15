package com.xiasuhuei321.gankkotlin.data

import com.xiasuhuei321.gankkotlin.Global
import com.xiasuhuei321.gankkotlin.MyObjectBox
import io.objectbox.Box
import io.objectbox.BoxStore

object DataManager {
    lateinit var boxStore: BoxStore
    val storeMap = HashMap<String, Box<Any>>()

    fun initStore() {
        boxStore = MyObjectBox.builder().androidContext(Global.context).build()
    }

    fun <T> getBoxFor(clz: Class<T>): Box<T> {
        return boxStore.boxFor(clz)
    }

    fun updateOrAddData(datas: List<Data>) {
        boxStore.runInTxAsync({
            val dataStore = boxStore.boxFor(Data::class.java)
            for (data in datas) {
                val d = dataStore.query().equal(Data_._id, data._id).build().findFirst()
                d?.let {
                    data.id = it.id
                }
            }

            dataStore.put(datas)
        }, { r, e ->

        })
    }

    fun getData(): List<Data> {
        return boxStore.boxFor(Data::class.java).query().build().find()
    }
}