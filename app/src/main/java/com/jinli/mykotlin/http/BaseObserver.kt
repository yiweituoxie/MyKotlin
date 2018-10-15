package com.jinli.mykotlin.http

import io.reactivex.observers.DisposableObserver

/**
 * Created by Jin on 10/15/2018
 */
abstract class BaseObserver<T> : DisposableObserver<T>(){
    override fun onComplete() {
    }
}