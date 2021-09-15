package com.sino.frame.common.bean.home

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @Author: Bill
 * @CreateDate: 2021/3/3 1:46 PM
 * @Description: 导航栏
 */
@Entity
class AppletsMenuBean : Serializable {
    /**
     * id : 1364115609360539650
     * createAccount :
     * createOrg :
     * createTime : 2021-02-23 15:31:20
     * updateAccount :
     * updateTime : 2021-02-23 15:31:20
     * status : 1
     * isDeleted : 0
     * menuId : 10002
     * name : 饮食
     * isOpen : 0
     * sort : 1
     * accountId : -1
     */
    var id: String? = null
    var createAccount: String? = null
    var createOrg: String? = null
    var createTime: String? = null
    var updateAccount: String? = null
    var updateTime: String? = null
    var status = 0

    @SerializedName("isDeleted")
    var deleted = 0

    @PrimaryKey
    lateinit var menuId: String
    var name: String? = null

    @SerializedName("isOpen")
    var open = 0
    var sort = 0
    var accountId: String? = null
}