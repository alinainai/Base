package com.gas.test.ui.kotlin

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder:RecyclerView.ViewHolder {

    constructor(itemView: View):super(itemView)

    private val viewHashMap=SparseArray<View>()

    fun<T:View> getView(id:Int):T{

        var view= viewHashMap.get(id)
        if(view==null){
            view=itemView.findViewById(id)
            viewHashMap.put(id,view)
        }
        return view as T

    }

}