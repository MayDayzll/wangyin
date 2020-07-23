package com.netease.nim.uikit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.netease.nim.uikit.R;

public class WorkOrderDoalog extends BaseDialog {


    public WorkOrderDoalog(@NonNull Context context) {
        super(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_workorder, null);
        setContentView(inflate);
    }
    @Override
    public void show() {
        if (getWindow() != null) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        }
        super.show();
        fullScreenImmersive(getWindow().getDecorView());
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        if (layoutParams != null) {
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            getWindow().getDecorView().setPadding(0, 0, 0, 0);
            getWindow().setAttributes(layoutParams);
        }
    }

    public void fullScreenImmersive(View view) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;
                view.setSystemUiVisibility(uiOptions);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
