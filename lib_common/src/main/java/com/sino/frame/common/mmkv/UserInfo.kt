package com.sino.frame.common.mmkv

/**
 * 应用模块:
 *
 *
 * 类描述: 用户信息
 *
 *
 *
 * @author Bill
 * @since 2020-01-27
 */
class UserInfo {
    /**
     * id : 1344476823593562114
     * accountId : 1344476822260518913
     * nickname : Leii
     * userName : 三诺
     * sex : 1
     * image : https://thirdwx.qlogo.cn/mmopen/vi_32/LsGv43H1OBg2HjYRUVx7m4PjpPSqCEJib8Zx8pEcudRibu48ib4jTtzZd52smxnk5UsHxH50Xxa0eHaoqPc57iaIww/132
     * phone : 18539663367
     * birthday : 2021-01-11
     * address : 北京北京市东城区
     * completeInfo : 1
     * createTime : 2020-12-31 10:53:48
     * professional : -1
     * addressCode : 110000000000-110100000000-110101000000
     * rankCount : 2
     * wxUnionId : opm8mw8fdg1QlrEUCQEXSDR8yWXI
     * specialSign : 1
     */
    var id: String? = null
    var accountId: String? = null
    var nickname: String? = null
    var userName: String? = null
    var sex = 0
    var image: String? = null
    var phone: String? = null
    var birthday: String? = null
    var address: String? = null
    var completeInfo = 0
    var createTime: String? = null
    var professional = 0
    var addressCode: String? = null
    var rankCount = 0
    var wxUnionId: String? = null
    var specialSign = 0
    var age = 0
    var idNum: String? = null
    var pregnancies = 0
    var diabeteType: String? = null
    var diabeteShow = 0
    var diabeteYear: String? = null
    var diabetesTime: String? = null

    override fun toString(): String {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", accountId='" + accountId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", userName='" + userName + '\'' +
                ", sex=" + sex +
                ", image='" + image + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                ", completeInfo=" + completeInfo +
                ", createTime='" + createTime + '\'' +
                ", professional=" + professional +
                ", addressCode='" + addressCode + '\'' +
                ", rankCount=" + rankCount +
                ", wxUnionId='" + wxUnionId + '\'' +
                ", specialSign=" + specialSign +
                ", age=" + age +
                ", idNum='" + idNum + '\'' +
                ", pregnancies=" + pregnancies +
                ", diabeteType='" + diabeteType + '\'' +
                ", diabeteShow=" + diabeteShow +
                ", diabeteYear='" + diabeteYear + '\'' +
                ", diabetesTime='" + diabetesTime + '\'' +
                '}'
    }
}