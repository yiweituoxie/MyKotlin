package com.jinli.mykotlin.http

import com.jinli.mykotlin.model.response.LoginResponse
import com.jinli.mykotlin.model.response.ModelResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Jin on 10/15/2018
 */
object HttpManager {

    private fun getMockClient(): OkHttpClient {
        val mockClient = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS).build()
        return mockClient
    }

    var serviceMock: HttpInterface =
            Retrofit.Builder()
                    .client(getMockClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("http://yapi.demo.qunar.com/mock/23756/")
//                    .baseUrl(" https://easy-mock.com/mock/5b90c4a3c5ecb256d33e4bc9/ljj/mock/")
                    .build()
                    .create(HttpInterface::class.java)
        get() {
            return field
        }

    fun login(): Observable<LoginResponse> = serviceMock.login()

    fun modelList(): Observable<ModelResponse> = serviceMock.modelList()
}