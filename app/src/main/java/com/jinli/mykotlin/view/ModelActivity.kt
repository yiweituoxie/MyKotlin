package com.jinli.mykotlin.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.jinli.mykotlin.R
import com.jinli.mykotlin.viewModel.model.ModelViewModel
import kotterknife.bindView
import org.jetbrains.anko.toast

/**
 * Created by Jin on 10/15/2018
 */
class ModelActivity : BaseActivity() {

    private val mCoordinatorLayout by bindView<CoordinatorLayout>(R.id.coordinator)
    private val mLayoutSwipe by bindView<SwipeRefreshLayout>(R.id.layout_swipe)
    private val mRvModels by bindView<RecyclerView>(R.id.rv_list)

    private var mAdapter: ModelAdapter? = null

    private val mModelViewModel: ModelViewModel by lazy {
        ViewModelProviders.of(this)
                .get(ModelViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model)
        initView()
        initData()
    }

    private fun initView() {
        mLayoutSwipe.setOnRefreshListener {
            mModelViewModel.loadData()
        }
        mRvModels.layoutManager = LinearLayoutManager(this)

        mAdapter = ModelAdapter(this)
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            toast("click")
        }
        mRvModels.adapter = mAdapter
    }

    fun initData() {
        mModelViewModel.toastStringMessage.observe(this, Observer {
            //报错信息
            it?.let {
                snack(mCoordinatorLayout, it, R.string.retry, View.OnClickListener {
                    //重试事件
                    mModelViewModel.loadData()
                })
            }
        })
        //监听加载状态
        mModelViewModel.loadingMessage.observe(this, Observer {
            mLayoutSwipe.isRefreshing = it ?: false
            if (it == true) {
                dismissSnack()
            }
        })
        mModelViewModel.model.observe(this, Observer { modelList ->
            mAdapter?.setNewData(modelList?.toMutableList())
        })
    }


}