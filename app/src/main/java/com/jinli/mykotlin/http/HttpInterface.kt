package com.jinli.mykotlin.http

import com.jinli.mykotlin.model.response.LoginResponse
import com.jinli.mykotlin.model.response.ModelResponse
import io.reactivex.Observable
import retrofit2.http.POST

/**
 * Created by Jin on 10/15/2018
 */
interface HttpInterface {

    @POST("login")
    fun login(): Observable<LoginResponse>
    @POST("modelList")
    fun modelList(): Observable<ModelResponse>
}