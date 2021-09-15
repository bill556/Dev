package com.sino.frame.common.net

import com.orhanobut.logger.Logger
import okhttp3.*
import okio.Buffer
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * @Author: Bill
 * @CreateDate: 2021/9/8 9:36 上午
 * @Description: 网络日志拦截器
 */
internal class HttpInterceptor(private val isDebug: Boolean) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        //如果不是调试模式，不进行拦截
        if (!isDebug) {
            return chain.proceed(request)
        }

        val captureEntity = HttpBean()
        val requestBody = request.body
        val hasRequestBody = requestBody != null
        val connection: Connection? = chain.connection()
        val protocol = connection?.protocol() ?: Protocol.HTTP_1_1
        var requestStartMessage = request.method + "   " + protocol
        if (hasRequestBody) {
            requestStartMessage += " (" + requestBody!!.contentLength() + "-byte body)"
        }
        captureEntity.requestMethod = requestStartMessage
        captureEntity.requestUrl = request.url.toString()
        val headerBuffer = StringBuffer()
        if (hasRequestBody) {
            if (requestBody!!.contentType() != null) {
                headerBuffer.append("Content-Type: " + requestBody.contentType())
            }
            if (requestBody.contentLength() != -1L) {
                headerBuffer.append("Content-Length: " + requestBody.contentLength())
            }
        }
        val headers = request.headers
        run {
            var i = 0
            val count = headers.size
            while (i < count) {
                val name = headers.name(i)
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(name, ignoreCase = true)) {
                    headerBuffer.append(name + ": " + headers.value(i))
                }
                i++
            }
        }
        captureEntity.requestHeader = headerBuffer.toString()
        if (!bodyEncoded(request.headers)) {
            val buffer = Buffer()
            if (requestBody != null) {
                requestBody.writeTo(buffer)
                var charset = UTF8
                val contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }
                val requestBodyBuffer = StringBuffer()
                if (isPlaintext(buffer)) {
                    if (requestBody != null && requestBody is FormBody) {
                        val formBody = requestBody
                        for (i in 0 until formBody.size) {
                            requestBodyBuffer.append(formBody.name(i)).append(":").append(formBody.value(i))
                        }
                    }
                    // requestBodyBuffer.append(buffer.readString(charset)).append("\n");
                    requestBodyBuffer.append(request.method + " (" + requestBody.contentLength() + "-byte body)")
                } else {
                    requestBodyBuffer.append(request.method + " (binary " + requestBody.contentLength() + "-byte body omitted)")
                }
                captureEntity.requestBody = requestBodyBuffer.toString()
            }
        }
        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            captureEntity.responseBody = "HTTP FAILED:$e"
            Logger.t("HttpInterceptor").i(captureEntity.toString())
            throw e
        }
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        val responseBody = response.body
        val contentLength = responseBody!!.contentLength()
        captureEntity.responseStatus = "<-- " + response.code + ' ' + response.message + ' ' + response.request.url + " (" + tookMs + "ms" + "" + ')'
        val respHeaders = response.headers
        val responseHeader = StringBuffer()
        var i = 0
        val count = respHeaders.size
        while (i < count) {
            responseHeader.append(respHeaders.name(i) + ": " + respHeaders.value(i))
            i++
        }
        captureEntity.responseHeader = responseHeader.toString()
        if (!bodyEncoded(response.headers)) {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()
            var charset = UTF8
            val contentType = responseBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF8)
            }
            if (!isPlaintext(buffer)) {
                //captureData.append("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)").append("\n");
                captureEntity.responseBody = "非文本信息"
                Logger.t("HttpInterceptor").i(captureEntity.toString())
                return response
            }
            if (contentLength != 0L) {
                captureEntity.responseBody = buffer.clone().readString(charset!!)
            }
            captureEntity.responseStatus = captureEntity.responseStatus + "<-- END HTTP (" + buffer.size + "-byte body)"
        }
        Logger.t("HttpInterceptor").i(captureEntity.toString())
        return response
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"]
        return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
    }

    companion object {
        private val UTF8 = Charset.forName("UTF-8")
        fun isPlaintext(buffer: Buffer): Boolean {
            return try {
                val prefix = Buffer()
                val byteCount = if (buffer.size < 64) buffer.size else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                true
            } catch (e: EOFException) {
                false // Truncated UTF-8 sequence.
            }
        }
    }
}