package com.sino.frame.common.bean.home

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: Bill
 * @CreateDate: 2021/1/26 2:22 PM
 * @Description: 字典bean
 */
@Entity
data class DictBean(
    /**
     * id :
     * parentId :
     * code : feedback_type
     * dictKey : 1002
     * dictValue : 瓜分试条活动
     * sort : 2
     * remark :
     * isDeleted : -1
     */
    @PrimaryKey
    var id: String,
    var parentId: String,
    var code: String,
    var dictKey: Int,
    var dictValue: String,
    var sort: Int,
    var remark: String,
    var isDeleted: Int
)