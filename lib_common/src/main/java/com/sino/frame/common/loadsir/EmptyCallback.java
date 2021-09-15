package com.sino.frame.common.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.sino.frame.common.R;

/**
 * LoadSir 空布局
 *
 * @author
 * @since 2021年09月07日13:45:31
 */
public class EmptyCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.common_layout_empty;
    }
}
