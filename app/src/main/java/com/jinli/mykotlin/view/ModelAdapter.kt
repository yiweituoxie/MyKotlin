package com.jinli.mykotlin.view

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jinli.mykotlin.R
import com.jinli.mykotlin.model.vo.Model


/**
 * Created by Jin on 10/17/2018
 */
class ModelAdapter(
        val context: Context, data: List<Model> = arrayListOf()
) : BaseQuickAdapter<Model, BaseViewHolder>(R.layout.item_model, data) {
    override fun convert(helper: BaseViewHolder?, item: Model?) {
        helper ?: return
        Glide.with(context).load(item?.modelIcon).into(helper.getView(R.id.iv_icon) as ImageView)
        helper.setText(R.id.tv_name, item?.modelName)
        helper.setText(R.id.tv_age, item?.modelAge)
        helper.setText(R.id.tv_sign, item?.modelSign)
    }

    override fun setNewData(newData: MutableList<Model>?) {
        if (data.isEmpty() || newData?.isNotEmpty() != true) {
            super.setNewData(newData)
        } else {
            mData = newData
        }
    }
}