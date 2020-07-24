package com.lib.commonsdk.kotlin.utils


/**
 * 判断一个类是否是另一个类的子类，或者实现。
 *
 * 例如：
 * class SomeClass extends BaseClass implement Interface
 * class SomeChildClass extends SomeClass
 *
 * 的情况下，下述几种情况都会返回true。
 *
 * isInstanceOf(SomeClass.class, SomeClass.class)
 * isInstanceOf(SomeClass.class, BaseClass.class)
 * isInstanceOf(SomeClass.class, Interface.class)
 * isInstanceOf(SomeChildClass.class, BaseClass.class)
 * isInstanceOf(SomeChildClass.class, Interface.class)
 * isInstanceOf(SomeChildClass.class, SomeClass.class)
 *
 * @param cls  要判断的class
 * @param type 要判断的cls是不是符合的类型。
 * @return 如果cls是type的子类或者实现（包括多重继承的情况），返回true，否则返回false。
 */
fun isInstanceOf(cls: Class<*>?, type: Class<*>): Boolean {
    var clss = cls
    while (clss != null) {
        if (clss == type) {
            return true
        }
        val interfaces = clss.interfaces
        for (ifs in interfaces) {
            if (ifs == type) {
                return true
            }
        }
        clss = clss.superclass
    }
    return false
}

/**
 * 判断一个对象属于的类是否是另一个类的子类或者实现。
 *
 * 类似于[.isInstanceOf]，但是参数略有不同。
 *
 * @param obj  要判断的对象
 * @param type 要判断的对象是不是符合的类型。
 * @return 如果obj所属的类是cls的子类或者实现（包括多重继承的情况），返回true，否则返回false。
 */
fun isInstanceOf(obj: Any?, type: Class<*>): Boolean {
    return obj != null && isInstanceOf(obj.javaClass, type)
}
