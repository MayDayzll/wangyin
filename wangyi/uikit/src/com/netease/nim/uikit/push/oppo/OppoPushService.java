package com.netease.nim.uikit.push.oppo;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.heytap.msp.push.mode.DataMessage;
import com.netease.nimlib.sdk.mixpush.OppoPushMessageService;

/**
 * Copyright (C) 2020 jmw.com.cn Inc. All rights reserved.
 * <p>
 * Author:Created Jmw by HeJingzhou on 2020-04-26 11:40
 * <p>
 * Company:北京天创时代信息技术有限公司
 * <p>
 * Email:tcoywork@163.com
 * <p>
 * Effect:
 */
public class OppoPushService extends OppoPushMessageService {
    public OppoPushService() {
        super();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }



}
