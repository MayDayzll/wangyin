package com.netease.nim.uikit;

import android.content.Context;

import com.netease.nim.uikit.business.session.actions.BaseAction;
import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;
import com.netease.nim.uikit.business.session.activity.TeamMessageActivity;
import com.netease.nim.uikit.dialog.WorkOrderDoalog;

public class messageDialog extends BaseAction {

    private final Context context;

    /**
     * 构造函数
     */
    public messageDialog(Context context) {
        super(R.drawable.evaluate, R.string.input_panel_evaluate);
        this.context = context;
    }

    @Override
    public void onClick() {
        TeamMessageActivity activity = (TeamMessageActivity) getActivity();
        activity.isEvaluation();

    }
}
