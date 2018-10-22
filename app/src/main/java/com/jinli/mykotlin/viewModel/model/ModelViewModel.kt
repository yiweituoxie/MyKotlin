package com.jinli.mykotlin.viewModel.model

import android.app.Application
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.jinli.mykotlin.http.BaseObserver
import com.jinli.mykotlin.http.HttpManager
import com.jinli.mykotlin.model.response.LoginResponse
import com.jinli.mykotlin.model.response.ModelResponse
import com.jinli.mykotlin.model.vo.Model
import com.jinli.mykotlin.model.vo.UserInfo
import com.jinli.mykotlin.viewModel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Jin on 10/15/2018
 */
class ModelViewModel (val ctx: Application) : BaseViewModel(ctx){
    val model: MediatorLiveData<List<Model>> = MediatorLiveData()

    private var mDisposable: Disposable? = null

    init {
        loadData()
    }

    fun loadData() {
        mDisposable = HttpManager.modelList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        object : BaseObserver<ModelResponse>() {
                            override fun onNext(rsp: ModelResponse) {
                                loadingMessage.value = false
                                if (rsp.message != null) {
                                    toastStringMessage.postValue(rsp.message)
                                } else{
                                    model.value = rsp.data
                                }
                            }

                            override fun onError(e: Throwable) {
                                loadingMessage.value = false
                                toastStringMessage.value = e.message
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