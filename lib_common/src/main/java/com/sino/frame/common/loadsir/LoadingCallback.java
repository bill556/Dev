package com.sino.frame.common.loadsir;

import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.sino.frame.common.R;

/**
 * LoadSir 加载中视图
 *
 * @author
 * @since 2021年09月07日13:45:38
 */
public class LoadingCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.common_layout_loading;
    }


    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
