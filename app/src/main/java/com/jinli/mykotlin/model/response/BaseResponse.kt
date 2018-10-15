package com.jinli.mykotlin.model.response

/**
 * Created by Jin on 10/15/2018
 */
open class BaseResponse<T> {
    var isSuccess: Boolean? = null
    var message: String? = null
    var data: T? = null
}

