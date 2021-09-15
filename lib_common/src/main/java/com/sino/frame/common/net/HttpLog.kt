package com.sino.frame.common.net

import com.orhanobut.logger.Logger
import okhttp3.logging.HttpLoggingInterceptor

/**
 *
 * @Author:         Bill
 * @CreateDate:     2021/9/8 8:59 上午
 * @Description:    网络拦截器打印
 *
 */
class HttpLog : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Logger.t("HttpLog").i(message)
    }
}
