package com.netease.nim.uikit.push.vivo;

import android.content.Context;
import android.util.Log;

import com.netease.nimlib.sdk.mixpush.VivoPushMessageReceiver;
import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.sdk.OpenClientPushMessageReceiver;

/**
 * Copyright (C) 2020 jmw.com.cn Inc. All rights reserved.
 * <p>
 * Author:Created Jmw by HeJingzhou on 2020-04-24 15:10
 * <p>
 * Company:北京天创时代信息技术有限公司
 * <p>
 * Email:tcoywork@163.com
 * <p>
 * Effect:
 */
public class VivoPushReceiver extends VivoPushMessageReceiver {
    @Override
    public void onNotificationMessageClicked(Context context, UPSNotificationMessage upsNotificationMessage) {
    }

    @Override
    public void onReceiveRegId(Context context, String s) {
        //当首次turnOnPush成功或regId发生改变时，回调此方法
        Log.i("zll", "Vivo推送消息内容onReceiveRegId: "+s);
    }
}
