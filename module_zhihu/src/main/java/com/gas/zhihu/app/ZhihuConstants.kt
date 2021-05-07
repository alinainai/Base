/*
 * Copyright 2018 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gas.zhihu.app

import java.io.File

/**
 * ================================================
 * Created by JessYan on 25/04/2018 16:48
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
interface ZhihuConstants {
    companion object {
        const val DETAIL_ID = "detail_id"
        const val ENCODING = "UTF-8"
        const val DEFAULT_TYPE = "-1"

        /**
         * 用户名
         */
        const val ZHIHU_USER_NAME = "qqqqqqqq"

        /**
         * 密码
         */
        const val ZHIHU_PASSWORD = "qqqqqq"
        const val ZHIHU_TEST_IMAGE_FILe_NAME = "testimage"

        //json配置信息
        val DATA_JSON_PATH = "config" + File.separator + "datajson.json"
        val EXPERIENCE_JSON_PATH = "config" + File.separator + "paperinit.json"

        //图片
        const val IMAGE_ZIP = "testimage.zip"
        val IMAGE_ZIP_ASSET = "data" + File.separator + IMAGE_ZIP
        const val IMAGE_ZIP_FOLDER = "testimage"

        //文件
        const val FILE_ZIP = "tempfile.zip"
        const val FILE_ZIP_FOLDER = "tempfile"
        val FILE_ZIP_ASSET = "data" + File.separator + FILE_ZIP
        const val FILE_TYPE_WORD = "word"
        const val FILE_TYPE_PDF = "pdf"
    }
}