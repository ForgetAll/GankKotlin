package com.xiasuhuei321.gankkotlin.network

import com.xiasuhuei321.gankkotlin.data.Data
import com.xiasuhuei321.gankkotlin.data.DataManager

object GankDataStore {
    private val dataManager = DataManager()

    fun initStore() {
        dataManager.initStore()
    }

    suspend fun requestWelfareData(pageIndex: Int): List<Data>? {
        gankService { getWelfare(pageIndex) }.await().apply {
            return if (isSuccessful) {
                this.body().results?.apply { dataManager.addOrUpdateData(this) }
            } else {
                val start = pageIndex * 10 - 10
                dataManager.queryData(start, start + 10)
            }
        }

        return mutableListOf()
    }
}