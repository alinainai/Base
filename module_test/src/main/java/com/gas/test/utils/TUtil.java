package com.gas.test.utils;

public class TUtil {

    public static void jdkPrintln(String str) {
        if (str == null)
            str = "";
        System.out.println(String.format("======================= %s =====================", str));
    }

    public static void jdkPrintlnEquit(String name, Object obj) {
        if (name == null)
            name = "";
        if (obj == null)
            System.out.println("obj is a null object");
        else
            System.out.println(String.format("%s= %s", name, obj.toString()));
    }


}
