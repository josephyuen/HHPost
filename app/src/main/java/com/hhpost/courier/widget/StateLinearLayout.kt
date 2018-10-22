package com.hhpost.courier.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * Creator: 李佳胜
 * FuncDesc:  同步更改子控件状态的线性布局
 * copyright  ©2018-2020 艾戴特 Corporation. All rights reserved.
 */
class StateLinearLayout: LinearLayout{

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        if(childCount > 0){
            for (i in 0 until  childCount){
                getChildAt(i).isSelected = selected
            }
        }
    }
}