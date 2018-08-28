package com.xiasuhuei321.gankkotlin.network

import com.xiasuhuei321.gankkotlin.data.Daily
import com.xiasuhuei321.gankkotlin.data.Data
import com.xiasuhuei321.gankkotlin.data.GankData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

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

    /**
     * 获取数据
     */
    @GET("data/{type}/{count}/{welfarePageIndex}")
    fun getData(@Path("type") type: String,
                @Path("count") count: String,
                @Path("welfarePageIndex") pageIndex: String): GankCall<List<Data>>

    @GET("day/{year}/{month}/{day}")
    fun getDaily(@Path("year") year: String,
                 @Path("month") month: String,
                 @Path("day") day: String): GankCall<Daily>
}