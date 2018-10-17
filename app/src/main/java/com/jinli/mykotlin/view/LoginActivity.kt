package com.jinli.mykotlin.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.*
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

    private val mLinearLayout: LinearLayout by bindView(R.id.linearLayout)
    private val mLoginBtn: Button by bindView(R.id.btn_login)
    private val mMobileEt: EditText by bindView(R.id.et_mobile)
    private val mPwdEt: EditText by bindView(R.id.et_pwd)
    private val mHeaderBar: HeaderBar by bindView(R.id.header)
    private val mForgetPwdTv: TextView by bindView(R.id.tv_forget_pwd)
    private val mProgressBar: ProgressBar by bindView(R.id.progress_bar)

    private val mLoginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this)
                .get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        initData()
    }

    private fun initView() {
        mLoginBtn.enable(mMobileEt, { isBtnEnable() })
        mLoginBtn.enable(mPwdEt, { isBtnEnable() })
        mLoginBtn.onClick(this)
        mHeaderBar.getRightView().onClick(this)
        mForgetPwdTv.onClick(this)
    }

    fun initData() {
        mLoginViewModel.toastStringMessage.observe(this, Observer {
            //报错信息
            it?.let {
                mProgressBar.visibility = View.GONE
                snack(mLinearLayout, it, R.string.retry, View.OnClickListener {
                    //重试事件
                })
            }
        })
        //监听加载状态
        mLoginViewModel.loadingMessage.observe(this, Observer {
            if (it == true)
                mProgressBar.visibility = View.VISIBLE
        })
        mLoginViewModel.userInfo.observe(this, Observer { userInfo ->
            //保存用户信息，并跳转界面
            mProgressBar.visibility = View.GONE
            toast(App.Instance.getString(R.string.login_success))
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