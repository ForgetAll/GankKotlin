package com.xiasuhuei321.gankkotlin.network

import com.google.gson.GsonBuilder
import com.xiasuhuei321.gankkotlin.data.Daily
import com.xiasuhuei321.gankkotlin.data.GankData
import com.xiasuhuei321.gankkotlin.util.XLog
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by xiasuhuei321 on 2018/8/22.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */
object GankService : ApiService by GankServer.service {
    const val TIMEOUT_ERROR = 20001
    const val NETWORK_ERROR = 20002
    const val UNKNOWN_ERROR = 20003

    var welfarePageIndex = 0

    fun getWelfare(pageIndex: Int, count: Int = 10) = getData("福利", count.toString(), pageIndex.toString())

    /**
     * 时间格式 2018-08-01
     */
    fun getDaily(time: String): Call<GankData<Daily>> {
        val timestr = time.split("-")
        return getDaily(timestr[0], timestr[1], timestr[2])
    }
}

object GankServer {
    private val DEFAULT_TIMEOUT = 10

    private val DOMAIN = "http://gank.io/"
    private val BASE_URL = "${DOMAIN}api/"

    private val retrofit: Retrofit

    val service: ApiService

    init {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        clientBuilder.writeTimeout(0, TimeUnit.SECONDS)
        clientBuilder.readTimeout(0, TimeUnit.SECONDS)

        // 添加日志
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> XLog.i("server", message) })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(interceptor)

        // 添加 Token
        clientBuilder.addInterceptor { chain ->
            val original = chain.request()

            return@addInterceptor chain.proceed(
                    original.newBuilder()
                            .headers(original.headers())
                            .method(original.method(), original.body()).build())
        }

        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()

        service = retrofit.create(ApiService::class.java)
    }


}