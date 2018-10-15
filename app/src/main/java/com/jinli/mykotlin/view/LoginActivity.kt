package com.jinli.mykotlin.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.jinli.mykotlin.R
import com.jinli.mykotlin.application.App
import com.jinli.mykotlin.ext.enable
import com.jinli.mykotlin.ext.onClick
import com.jinli.mykotlin.viewModel.login.LoginViewModel
import com.jinli.mykotlin.witgets.HeaderBar
import kotterknife.bindView
import org.jetbrains.anko.toast

/**
 * Created by Jin on 10/15/2018
 */
class LoginActivity : BaseActivity(), View.OnClickListener {

    private val mLoginBtn: Button by bindView(R.id.btn_login)
    private val mMobileEt: EditText by bindView(R.id.et_mobile)
    private val mPwdEt: EditText by bindView(R.id.et_pwd)
    private val mHeaderBar: HeaderBar by bindView(R.id.header)
    private val mForgetPwdTv: TextView by bindView(R.id.tv_forget_pwd)

    private val mLoginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this)
                .get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        mLoginBtn.enable(mMobileEt, { isBtnEnable() })
        mLoginBtn.enable(mPwdEt, { isBtnEnable() })

        mLoginBtn.onClick(this)
        mHeaderBar.getRightView().onClick(this)
        mForgetPwdTv.onClick(this)

        mLoginViewModel.toastStringMessage.observe(this, Observer {
            it?.let {
                toast(App.Instance.getString(R.string.retry))
            }
        })
        //监听加载状态
        mLoginViewModel.loadingMessage.observe(this, Observer {
            toast(App.Instance.getString(R.string.loading))
        })
        mLoginViewModel.userInfo.observe(this, Observer { userInfo ->
            if (userInfo != null) {
                toast(App.Instance.getString(R.string.login_success))
            }
        })
    }

    /*
        点击事件
     */
    override fun onClick(view: View) {
        when (view.id) {

            R.id.btn_login -> {
                mLoginViewModel.login()
            }
        }
    }

    /*
        判断按钮是否可用
     */
    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not()
    }
}