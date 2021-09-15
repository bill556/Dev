package com.sino.frame.common.mmkv

import com.lzx.pref.KvPrefModel
import com.lzx.pref.getWithKey
import com.lzx.pref.saveWithKey

object MMKVFileDemo : KvPrefModel("defaultName", MmKvPref()) {
    var people: People? by objPrefNullable(People().apply { age = 100;name = "阿四大四大所大所大所大所多" })
    var otherpeople: People by objPref()
    var name: String by stringPref()
    var otherName: String? by stringPrefNullable()
    var height: Long by longPref()
    var weight: Float by floatPref()
    var isGay: Boolean? by booleanPrefNullable(false, key = "是否是变态")

    var userinfo: UserInfo by objPref()

    var haha: String? = null
    var haha1: String? = null
    var haha2: String? = null
}

/**
 * 兼容java的写法
 */
object MMKVFileDemoJava {
    @JvmStatic
    var people: People?
        get() = MMKVFileDemo.people
        set(value) {
            MMKVFileDemo.people = value
        }

    @JvmStatic
    var name: String
        get() = MMKVFileDemo.name
        set(value) {
            MMKVFileDemo.name = value
        }

    @JvmStatic
    fun setHaha(key: String, value: String) {
        MMKVFileDemo.saveWithKey(MMKVFileDemo::haha2, key, value)
    }

    @JvmStatic
    fun getHaha(key: String) = MMKVFileDemo.getWithKey<Int>(MMKVFileDemo::haha, key)
}

class People {
    var age: Int = 0
    var name: String? = null

    override fun toString(): String {
        return "People(age=$age, name=$name)"
    }
}