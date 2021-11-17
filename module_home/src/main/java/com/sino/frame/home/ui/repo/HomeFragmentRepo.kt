package com.sino.frame.home.ui.repo

import com.sino.frame.base.mvvm.m.BaseRepository
import com.sino.frame.common.db.dao.AppletsMenuDao
import com.sino.frame.common.helper.responseCodeExceptionHandler
import com.sino.frame.home.net.HomeApiService
import javax.inject.Inject

/**
 * HomeFragment的数据仓库层
 *
 * @author
 * @since 2021/8/5 10:59 下午
 */
class HomeFragmentRepo @Inject constructor() : BaseRepository() {

    @Inject
    lateinit var mApi: HomeApiService

    @Inject
    lateinit var appletsMenuDao: AppletsMenuDao

    /**
     * 获取DB里的tab数据
     * @return
     */
    fun getTabList() = appletsMenuDao.getAllFlow()

    /**
     * 请求网络获取tab数据
     * @return
     */
    suspend fun requestTabList() {
        mApi.getTab().run {
            responseCodeExceptionHandler(code, msg, successBlock = {
                appletsMenuDao.insert(data)
            })
        }
    }
    //不保存数据库的数据请使用以下方法
//    suspend fun getTab() = request<List<AppletsMenuBean>> {
//        mApi.getTab().run {
//            responseCodeExceptionHandler(code, msg)
//            emit(data)
//        }
//    }


}