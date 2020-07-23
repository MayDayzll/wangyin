package com.netease.nim.uikit;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

public class recordingDialog extends Dialog {


    private final Context mContext;

    public recordingDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }
}
