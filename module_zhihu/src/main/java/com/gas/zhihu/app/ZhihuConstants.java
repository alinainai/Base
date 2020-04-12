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
package com.gas.zhihu.app;

import java.io.File;

/**
 * ================================================
 * Created by JessYan on 25/04/2018 16:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface ZhihuConstants {
    String DETAIL_ID = "detail_id";
    String DETAIL_TITLE = "detail_title";
    String DETAIL_URL = "detail_url";
    String ENCODING = "UTF-8";
    /**
     * 用户名
     */
    String ZHIHU_USER_NAME = "qqqqqqqq";
    /**
     * 密码
     */
    String ZHIHU_PASSWORD = "qqqqqq";

    String ZHUHI_IMAGE_ZIP_FILE_NAME = "testimage.zip";
    String ZHIHU_ASSET_DATA_JSON_PATH = "config" + File.separator + "datajson.json";
    String ZHIHU_ASSET_IMAGE_ZIP_PATH = "data" + File.separator + ZHUHI_IMAGE_ZIP_FILE_NAME;
    String ZHIHU_TEST_IMAGE_FILe_NAME = "testimage";
}
