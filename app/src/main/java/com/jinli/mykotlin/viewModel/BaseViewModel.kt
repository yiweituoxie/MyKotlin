package com.jinli.mykotlin.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.ericsson.ecf.utils.LoadingMessage
import com.ericsson.ecf.utils.ToastStringMessage

/**
 * Created by Jin on 10/15/2018
 * 提供加载状态、错误处理LiveData
 */
abstract class BaseViewModel(ctx: Application) : AndroidViewModel(ctx) {
    val loadingMessage by lazy { LoadingMessage() }
    val toastStringMessage by lazy { ToastStringMessage() }

}
