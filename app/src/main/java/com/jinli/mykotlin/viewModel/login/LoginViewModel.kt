package com.jinli.mykotlin.viewModel.login

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jinli.mykotlin.http.BaseObserver
import com.jinli.mykotlin.http.HttpManager
import com.jinli.mykotlin.model.response.LoginResponse
import com.jinli.mykotlin.model.vo.UserInfo
import com.jinli.mykotlin.viewModel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Jin on 10/15/2018
 */
class LoginViewModel (val ctx: Application) : BaseViewModel(ctx){
    val userInfo: MutableLiveData<UserInfo> = MutableLiveData()

    private var mDisposable: Disposable? = null

    init {
    }


    fun login() {
        mDisposable = HttpManager.login()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        object : BaseObserver<LoginResponse>() {
                            override fun onNext(rsp: LoginResponse) {
                                loadingMessage.value = false
                                if (rsp.message != null) {
                                    toastStringMessage.postValue(rsp.message)
                                } else{
                                    userInfo.value = rsp.data
                                }

                            }

                            override fun onError(e: Throwable) {
                                loadingMessage.value = false
                                toastStringMessage.postValue(e.message)
                            }

                            override fun onStart() {
                                loadingMessage.value = true
                                super.onStart()
                            }
                        }

                )


    }

    override fun onCleared() {
        mDisposable?.dispose()
        super.onCleared()
    }
}