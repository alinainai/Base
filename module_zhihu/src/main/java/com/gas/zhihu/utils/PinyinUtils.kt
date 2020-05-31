package com.gas.zhihu.utils

import net.sourceforge.pinyin4j.PinyinHelper
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination
import java.util.*


fun String.toFirstPySpell(): String? {
    return PinyinUtils.toFirstPySpell(this)
}

fun String.toPinYin(): String? {
    return PinyinUtils.toPinYin(this)
}

fun String.toAllPySpell(): String? {
    return PinyinUtils.toAllPySpell(this)
}

object PinyinUtils {

    /**
     * 获得汉语拼音首个字母
     *
     * @param inputString 汉字
     * @return
     */
    fun toFirstPySpell(inputString: String?): String? {
        if (!inputString.isNullOrEmpty()) {
            val nameChar = inputString.toCharArray()
            val temp = toPinYin(nameChar[0].toString())
            temp?.toCharArray()?.first()?.let {
                return if (it.toString().matches(Regex("[a-z]+", RegexOption.IGNORE_CASE))) {
                    it.toString().toUpperCase(Locale.ROOT)
                } else "#"
            }
        }
        return "#"
    }

    /**
     * 将字符串中的中文转化为拼音,英文字符不变
     *
     * @param inputString 汉字
     * @return
     */
    fun toPinYin(inputString: String?): String? {
        var output = ""
        if (!inputString.isNullOrEmpty()) {
            val format = HanyuPinyinOutputFormat()
            format.caseType = HanyuPinyinCaseType.LOWERCASE
            format.toneType = HanyuPinyinToneType.WITHOUT_TONE
            format.vCharType = HanyuPinyinVCharType.WITH_V
            val input = inputString.trim { it <= ' ' }.toCharArray()
            try {
                for (i in input.indices) {
                    output += if (input[i].toString().matches(Regex("[\\u4E00-\\u9FA5]+"))) {
                        val temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format)
                        temp[0]
                    } else input[i].toString()
                }
            } catch (e: BadHanyuPinyinOutputFormatCombination) {
                e.printStackTrace()
            }
        }
        return output
    }

    /**
     * 汉字转换为汉语拼音首字母，英文字符不变
     *
     * @param inputString 汉字
     * @return 拼音
     */
    fun toAllPySpell(inputString: String?): String? {
        var output = ""
        if (!inputString.isNullOrEmpty()) {
            val format = HanyuPinyinOutputFormat()
            format.caseType = HanyuPinyinCaseType.LOWERCASE
            format.toneType = HanyuPinyinToneType.WITHOUT_TONE
            format.vCharType = HanyuPinyinVCharType.WITH_V
            val input = inputString.trim { it <= ' ' }.toCharArray()
            try {
                for (i in input.indices) {
                    output += if (input[i].toString().matches(Regex("[\\u4E00-\\u9FA5]+"))) {
                        val temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format)
                        temp[0].toCharArray().first().toLowerCase()
                    } else input[i].toString()
                }
            } catch (e: BadHanyuPinyinOutputFormatCombination) {
                e.printStackTrace()
            }
        }
        return output
    }

}