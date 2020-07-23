package com.netease.nim.uikit.push.huawei;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.huawei.hms.push.RemoteMessage;
import com.netease.nimlib.sdk.mixpush.HWPushMessageService;

import java.util.Arrays;

/**
 * Copyright (C) 2020 jmw.com.cn Inc. All rights reserved.
 * <p>
 * Author:Created Jmw by HeJingzhou on 2020-04-22 12:53
 * <p>
 * Company:北京天创时代信息技术有限公司
 * <p>
 * Email:tcoywork@163.com
 * <p>
 * Effect:
 */
public class HuaweiPushService extends HWPushMessageService {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public HuaweiPushService() {
        super();
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String data = remoteMessage.getData();
        String from = remoteMessage.getFrom();
        System.out.println("推送-华为-数据" + data + "\n" + from + "\t" + remoteMessage.toString());

        Log.e("zll", "getCollapseKey: " + remoteMessage.getCollapseKey()
                + "\n getData: " + remoteMessage.getData()
                + "\n getFrom: " + remoteMessage.getFrom()
                + "\n getTo: " + remoteMessage.getTo()
                + "\n getMessageId: " + remoteMessage.getMessageId()
                + "\n getOriginalUrgency: " + remoteMessage.getOriginalUrgency()
                + "\n getUrgency: " + remoteMessage.getUrgency()
                + "\n getSendTime: " + remoteMessage.getSentTime()
                + "\n getMessageType: " + remoteMessage.getMessageType()
                + "\n getTtl: " + remoteMessage.getTtl());

        RemoteMessage.Notification notification = remoteMessage.getNotification();

        Log.e("zll", "\n getImageUrl: " + notification.getImageUrl()
                + "\n getTitle: " + notification.getTitle()
                + "\n getTitleLocalizationKey: " + notification.getTitleLocalizationKey()
                + "\n getTitleLocalizationArgs: " + Arrays.toString(notification.getTitleLocalizationArgs())
                + "\n getBody: " + notification.getBody()
                + "\n getBodyLocalizationKey: " + notification.getBodyLocalizationKey()
                + "\n getBodyLocalizationArgs: " + Arrays.toString(notification.getBodyLocalizationArgs())
                + "\n getIcon: " + notification.getIcon()
                + "\n getSound: " + notification.getSound()
                + "\n getTag: " + notification.getTag()
                + "\n getColor: " + notification.getColor()
                + "\n getClickAction: " + notification.getClickAction()
                + "\n getChannelId: " + notification.getChannelId()
                + "\n getLink: " + notification.getLink()
                + "\n getNotifyId: " + notification.getNotifyId());

    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
        System.out.println("推送onMessageSent" + s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
        System.out.println("推送onSendError" + e);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        System.out.println("推送onNewToken" + s);
    }



    @Override
    public int onStartCommand(Intent intent, int i, int i1) {
        return super.onStartCommand(intent, i, i1);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
