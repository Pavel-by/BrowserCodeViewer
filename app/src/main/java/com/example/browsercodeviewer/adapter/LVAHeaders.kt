package com.example.browsercodeviewer.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.browsercodeviewer.R
import com.example.browsercodeviewer.web.Header
import kotlinx.android.synthetic.main.list_item_headers.view.*

/**
 * Важное замечание! Список, передаваемый при инициализации, изменяется пользователем напрямую через этот адаптер
 */
class LVAHeaders(private var context: Activity, headers: List<Header>?) : BaseAdapter() {

    var headers: List<Header>? = headers
        set(headers) {
            field = headers
            notifyDataSetChanged()
        }

    override fun getView(index: Int, savedView: View?, parent: ViewGroup?): View {
        val view: View =
                if (savedView != null) savedView
                else {
                    context.layoutInflater.inflate(R.layout.list_item_headers, parent, false)
                }
        val item: Header = getItem(index)
        view.title.text = item.name
        view.value.text = item.data
        return view
    }

    override fun getCount(): Int {
        return if (headers == null) 0 else headers!!.size
    }

    override fun getItem(index: Int): Header {
        return headers!!.get(index)
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }
}