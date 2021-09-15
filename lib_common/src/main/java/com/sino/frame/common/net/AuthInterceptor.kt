package com.sino.frame.common.net

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * @Author: Bill
 * @CreateDate: 2020/11/5 2:20 PM
 * @Description: 拦截添加token并静默刷新token
 */
class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        //添加token
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZW5hbnRfaWQiOiIwMDAwMDAiLCJhY2NvdW50X3R5cGUiOjIxLCJ1c2VyX25hbWUiOiIxNzc2MzYyNDcxMCIsInJlYWxfbmFtZSI6IjE3NzYzNjI0NzEwIiwiYXZhdGFyIjoiaHR0cHM6Ly90aGlyZHd4LnFsb2dvLmNuL21tb3Blbi92aV8zMi9RMGo0VHdHVGZUSkxBeGlhN0ppY3RYbVducm11dnZEQ1hRZXYxRnJhQWRNY3RkYXEwQUdOZEhVd3NZWjFLaWFqSHlxbndUNVdvWHlGSzRZNGgweUN4SGZwQS8xMzIiLCJvYmplY3RfaWQiOiIxMzcxMzYwMzkxMzcyMDk5NTg1IiwiYXV0aG9yaXRpZXMiOlsid2VpeGluX3VzZXIiXSwiY2xpZW50X2lkIjoic2luby1hcHAiLCJyb2xlX25hbWUiOiJ3ZWl4aW5fdXNlciIsImxpY2Vuc2UiOiJwb3dlcmVkIGJ5IHNpbm9jYXJlLmNvbSIsImF1ZCI6WyJ7fSJdLCJvcmdfZ3VpZCI6IiIsInVzZXJfaWQiOiIxMzcxMzYwMzkxMTIyNDU2NTc4Iiwicm9sZV9pZCI6IjExNzgyMTMxMjE4NTExOTk0OTAiLCJwaG9uZSI6IjE3NzYzNjI0NzEwIiwib3JnX2lkIjoiIiwic2NvcGUiOlsiYWxsIl0sIm5pY2tfbmFtZSI6IkJpbGwxIiwiZ3VpZCI6IiIsImV4cCI6MTYyOTYwMjk5OCwiZW1wbG95ZWVfd29ya251bSI6IiIsImp0aSI6IjI3MGMxMzBjLTczZWItNGEyMi1iYmYxLWZjYWM0MDkzMzllYyIsImFjY291bnQiOiIxNzc2MzYyNDcxMCJ9.XjXKgds0bkCqmuPrRSddKgbgwdNbR8S3rYkLe54PooQ"
        if (!TextUtils.isEmpty(token)) {
            val updateRequest =
                originalRequest.newBuilder()
                    .addHeader(HttpHeaders.HEAD_KEY_AUTHORIZATION, token)
                    .addHeader(HttpHeaders.HEAD_KEY_PLATFORM_TYPE, 1.toString())
                    .build()
            return chain.proceed(updateRequest)
        }
        return chain.proceed(originalRequest)
    }
}