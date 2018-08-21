package com.xiasuhuei321.gankkotlin.network

import com.xiasuhuei321.gankkotlin.data.GankData
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by xiasuhuei321 on 2018/8/16.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */
typealias GankCall<T> = Call<GankData<T>>

interface ApiService {
    /**
     * 获取发过干货日期
     */
    @GET("day/history")
    fun getHistory(): GankCall<List<String>>

}