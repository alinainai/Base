package com.lib.commonsdk.constants

import com.lib.commonsdk.kotlin.extension.app.getResources


val shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime).toLong()
val longAnimTime = getResources().getInteger(android.R.integer.config_longAnimTime).toLong()
