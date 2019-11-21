package com.gas.test.ui.kotlin

class View {

    interface OnClickListener{
        fun onClick(view:View)
    }

    fun setOnClickListener(lisenter:(View)->Unit){

    }

}

fun main(){

 val view=View()
    view.setOnClickListener (:: onClick)
}

fun onClick(view:View){
    println("被点击了")
}