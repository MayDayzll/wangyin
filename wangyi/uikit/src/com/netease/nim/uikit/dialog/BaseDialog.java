package com.netease.nim.uikit.dialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.netease.nim.uikit.R;

public class BaseDialog extends Dialog {

    private Context mContext;
    private int getLevel;

    public int getLevel() {
        return getLevel;
    }

    public void setLevel(int getLevel) {
        this.getLevel = getLevel;
    }

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        mContext = context;
    }
}