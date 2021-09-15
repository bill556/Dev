package com.sino.frame.home.net

import com.sino.frame.common.bean.BaseResponse
import com.sino.frame.common.bean.home.AppletsMenuBean
import com.sino.frame.common.bean.home.DictBean
import retrofit2.http.*

/**
 * 首页模块的接口代理类
 *
 * @author
 * @since 2021/8/5 10:55 下午
 */
interface HomeApiService {

    /**
     * 获取字典数据
     * @param code String
     * @return BaseResponse<List<DictBean>>
     */
    @GET("sino-system/dict/get")
    suspend fun getDict(@Query("code") code: String): BaseResponse<List<DictBean>>

    /**
     * 获取tab数据
     * @return BaseResponse<List<DictBean>>
     */
    @GET("sino-system/appletsMenu/listAccount")
    suspend fun getTab(): BaseResponse<List<AppletsMenuBean>>
}
