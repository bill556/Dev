package com.sino.frame.main.ui.repo

import com.sino.frame.base.mvvm.m.BaseRepository
import com.sino.frame.main.net.MainApiService
import javax.inject.Inject

/**
 * 首页数据仓库
 *
 * @author
 * @since 2021/7/31 5:18 下午
 */
class MainRepository @Inject constructor() : BaseRepository() {

    @Inject
    lateinit var mApi: MainApiService
}