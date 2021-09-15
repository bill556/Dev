package com.sino.frame.common.net

import java.io.Serializable

/**
 * @Author: Bill
 * @CreateDate: 2021/9/8 9:40 上午
 * @Description: 网络日志拦截器Bean
 */
class HttpBean : Serializable {
    var requestMethod = ""
    var requestUrl = ""
    var requestHeader = ""
    var requestBody = ""
    var responseStatus = ""
    var responseHeader = ""
    var responseBody = ""
    override fun toString(): String {
        return """
            $requestMethod
            ------------requestUrl------------
            $requestUrl
            ------------requestHeader---------
            $requestHeader
            -------------requestBody---------
            $requestBody
            ------------responseStatus---------
            $responseStatus
            ------------responseHeader--------
            $responseHeader
            -------------responseBody---------
            $responseBody
            """.trimIndent()
    }
}