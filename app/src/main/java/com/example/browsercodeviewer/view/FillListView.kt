package com.example.browsercodeviewer.view

import android.content.Context
import android.util.AttributeSet
import android.view.View.MeasureSpec
import android.widget.ListView

/**
 * У стандартного ListView возникают проблемы при установке значения высоты wrap_content
 */
class FillListView : ListView {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2,
                MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }

}