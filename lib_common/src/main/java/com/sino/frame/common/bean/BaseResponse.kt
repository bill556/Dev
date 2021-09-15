package com.sino.frame.common.bean

/**
 * 公共Response
 *
 * @author
 * @since 2021/8/5 11:33 下午
 */
data class BaseResponse<T>(
    val code: Int,
    val success: Boolean,
    val msg: String,
    val `data`: T,
)