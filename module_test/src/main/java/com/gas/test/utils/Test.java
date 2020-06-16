package com.gas.test.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
//        List<String> list = new ArrayList<>();
//        List<String> ll= new LinkedList<>();
//        for (int i = 0; i < 5; i++) {
//            list.add("1111"+i);
//        }
//        for (int i = 0; i < 3; i++) {
//            ll.add("1111"+i);
//        }
//        list.addAll(6,ll);
//        System.out.println(list.toString());

        String as= "123,456,789,";
        String[] array= as.split(",");
        System.out.println(array.length);
        for (String s : array) {
            System.out.println(s);
        }


    }

}
