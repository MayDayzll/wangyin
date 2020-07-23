package cn.com.jmw.m.netease.session.action;

import android.content.Context;

import com.netease.nim.uikit.business.session.actions.BaseAction;
import com.netease.nim.uikit.dialog.WorkOrderDoalog;

public class messageDialog extends BaseAction {

    private final Context context;

    /**
     * 构造函数
     *
     * @param iconResId 图标 res id
     * @param titleId   图标标题的string res id
     */
    protected messageDialog(Context context, int iconResId, int titleId) {
        super(iconResId, titleId);
        this.context = context;
    }

    @Override
    public void onClick() {

        new WorkOrderDoalog(context).show();

    }
}
