package com.sino.frame.common.ui.helper

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.StyleSpan
import com.google.android.material.tabs.TabLayout

/**
 *
 * @Author:         Bill
 * @CreateDate:     2021/8/20 10:16 上午
 * @Description:    tab样式
 *
 */
object TabStyleHelper {

    /**
     * tab选择时候的样式更改
     * @param tab Tab
     * @param isSelect Boolean
     */
    fun updateText(tab: TabLayout.Tab, isSelect: Boolean) {
        if (TextUtils.isEmpty(tab.text)) return
        val trim = tab.text.toString().trim()
        val spannableString = SpannableString(trim)
        val styleSpan = StyleSpan(if (isSelect) Typeface.BOLD else Typeface.NORMAL)
        spannableString.setSpan(styleSpan, 0, trim.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        tab.text = spannableString
    }
}