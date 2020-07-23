package com.netease.nim.uikit.push;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.heytap.msp.push.HeytapPushManager;
import com.heytap.msp.push.callback.ICallBackResultService;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.agconnect.config.LazyInputStream;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.netease.nim.uikit.BuildConfig;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;
import com.vivo.push.ups.CodeResult;
import com.vivo.push.ups.UPSTurnCallback;
import com.vivo.push.ups.VUpsManager;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Copyright (C) 2020 jmw.com.cn Inc. All rights reserved.
 * <p>
 * Author:Created Jmw by HeJingzhou on 2020-04-24 11:07
 * <p>
 * Company:北京天创时代信息技术有限公司
 * <p>
 * Email:tcoywork@163.com
 * <p>
 * Effect:
 */
public class PushHelper {
    private String TAG = getClass().getSimpleName();
    private static PushHelper mPushHelper;
    private static Context mContext;

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        mContext = context;
    }

    public static PushHelper getInstance() {
        if (mPushHelper == null) {
            mPushHelper = new PushHelper();
        }
        return mPushHelper;
    }

    /**
     * 注册华为推送
     *
     * @return 华为Token
     */
    public void registerHuaWeiPush() {
        if ("HUAWEI".equals(Build.MANUFACTURER) || "Huawei".equals(Build.MANUFACTURER) || "huawei".equals(Build.MANUFACTURER)) {
            AGConnectServicesConfig config = AGConnectServicesConfig.fromContext(mContext);
            config.overlayWith(new LazyInputStream(mContext) {
                public InputStream get(Context context) {
                    try {
                        return context.getAssets().open("agconnect-services.json");
                    } catch (IOException e) {
                        return null;
                    }
                }
            });
        }
    }

    /**
     * 注册小米推送
     */
    public void registerXiaomiPush() {
        if ("XIAOMI".equals(Build.MANUFACTURER) || "Xiaomi".equals(Build.MANUFACTURER) || "xiaomi".equals(Build.MANUFACTURER)) {
            if (shouldInit()) {
                MiPushClient.registerPush(mContext, "2882303761517322053", "5281732258053");
            }
            LoggerInterface newLogger = new LoggerInterface() {

                @Override
                public void setTag(String tag) {
                    // ignore
                }

                @Override
                public void log(String content, Throwable t) {
                    Log.d(TAG, content, t);
                }

                @Override
                public void log(String content) {
                    Log.d(TAG, content);
                }
            };
            Logger.setLogger(mContext, newLogger);
        }
    }

    /**
     * 注册Oppo推送
     */
    public void registerOppoPush() {
        if ("OPPO".equals(Build.MANUFACTURER) || "Oppo".equals(Build.MANUFACTURER) || "oppo".equals(Build.MANUFACTURER)) {
            HeytapPushManager.init(mContext, BuildConfig.DEBUG);
            if (HeytapPushManager.isSupportPush()) {
                HeytapPushManager.register(mContext, "EOrYbwXe8wg80s8sO8840gg4o", "c34bbdE4b51532dffA47e3C5c19c239e", new ICallBackResultService() {
                    @Override
                    public void onRegister(int i, String s) {
                        if (i == 0) {
                            HeytapPushManager.requestNotificationPermission();
                            notifyChannel(mContext.getApplicationContext());
                            SharedPreferences app2 = mContext.getSharedPreferences("appConfig", 0);
                            //获取Editor对象
                            SharedPreferences.Editor edit = app2.edit();
                            edit.putString("oppoPushId", s);
                            edit.apply();
                            System.out.println("推送：oppo  code:" + i + "\t\t" + s);
                        }
                    }

                    @Override
                    public void onUnRegister(int i) {

                    }

                    @Override
                    public void onSetPushTime(int i, String s) {

                    }

                    @Override
                    public void onGetPushStatus(int i, int i1) {

                    }

                    @Override
                    public void onGetNotificationStatus(int i, int i1) {

                    }
                });
            } else {
                System.out.println("System.out.println(OPPO推送registerOppoPush: 注册失败);\n");
            }
        }
    }

    //oppo 设置通知通道 兼容Android8.0及以上机型
    private static void notifyChannel(Context context) {
        if ("OPPO".equals(Build.MANUFACTURER) || "Oppo".equals(Build.MANUFACTURER) || "oppo".equals(Build.MANUFACTURER)) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = "default";
                    String channelName = "Default_Channel";
                    String channelDescription = "this is default channel!";
                    NotificationChannel mNotificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
                    mNotificationChannel.setDescription(channelDescription);
                    ((NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE)).createNotificationChannel(mNotificationChannel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 注册VivoPush
     */
    public void registerVivoPush() {
        if ("VIVO".equals(Build.MANUFACTURER) || "Vivo".equals(Build.MANUFACTURER) || "vivo".equals(Build.MANUFACTURER)) {
            PushClient.getInstance(mContext).initialize();
            PushClient.getInstance(mContext).turnOnPush(new IPushActionListener() {
                @Override
                public void onStateChanged(int state) {
                    Log.i(TAG, "推送Vivo Push 注册: " + state);
                }

            });
            VUpsManager.getInstance().turnOnPush(mContext, new UPSTurnCallback() {

                @Override
                public void onResult(CodeResult codeResult) {

                    if (codeResult.getReturnCode() == 0) {
                        Log.d(TAG, "推送Vivo onResult 初始化成功");
                    } else {
                        Log.d(TAG, "推送Vivo onResult初始化失败");
                    }

                }

            });
        }

    }


    /**
     * 获取华为推送ID
     *
     * @param pushRegisterIdListener 监听回调事件
     */
    public void getHuaweiRegisterID(final PushRegisterIdListener pushRegisterIdListener) {
        if ("HUAWEI".equals(Build.MANUFACTURER) || "Huawei".equals(Build.MANUFACTURER) || "huawei".equals(Build.MANUFACTURER)) {
            if (pushRegisterIdListener != null) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            String appId = AGConnectServicesConfig.fromContext(mContext).getString("client/app_id");
                            String token = HmsInstanceId.getInstance(mContext).getToken(appId, "HCM");
                            pushRegisterIdListener.registerResult(token);
                        } catch (ApiException e) {
                            Log.i(this.getClass().getName(), "run: Huawei Push 获取TokenError" + e.getMessage());
                            pushRegisterIdListener.registerResult("");
                        }
                    }
                }.start();
            }
        }
    }

    /**
     * 获取小米推送ID
     *
     * @param pushRegisterIdListener 监听回调
     */
    public void getXiaomiRegisterID(PushRegisterIdListener pushRegisterIdListener) {
        if ("XIAOMI".equals(Build.MANUFACTURER) || "Xiaomi".equals(Build.MANUFACTURER) || "xiaomi".equals(Build.MANUFACTURER)) {
            if (pushRegisterIdListener != null) {
                String regId = MiPushClient.getRegId(mContext);
                pushRegisterIdListener.registerResult(regId);
            }
        }

    }

    /**
     * 获取OPPO推送ID
     *
     * @param pushRegisterIdListener 监听回调
     */
    public void getOppoRegisterID(PushRegisterIdListener pushRegisterIdListener) {
        if ("OPPO".equals(Build.MANUFACTURER) || "Oppo".equals(Build.MANUFACTURER) || "oppo".equals(Build.MANUFACTURER)) {
            if (pushRegisterIdListener != null) {
                if (HeytapPushManager.isSupportPush()) {
                    String registerID = HeytapPushManager.getRegisterID();
                    if (TextUtils.isEmpty(registerID)) {
                        SharedPreferences app2 = mContext.getSharedPreferences("appConfig", 0);
                        String oppoPushId = app2.getString("oppoPushId", "");
                        pushRegisterIdListener.registerResult(oppoPushId);
                    } else {
                        pushRegisterIdListener.registerResult(HeytapPushManager.getRegisterID());
                    }
                } else {
                    pushRegisterIdListener.registerResult("");
                }
            }
        }
    }

    /**
     * 获取VIvo注册ID
     *
     * @param pushRegisterIdListener 监听回调
     */
    public void getVivoRegisterID(PushRegisterIdListener pushRegisterIdListener) {
        if ("VIVO".equals(Build.MANUFACTURER) || "Vivo".equals(Build.MANUFACTURER) || "vivo".equals(Build.MANUFACTURER)) {
            if (pushRegisterIdListener != null) {
                String regId = PushClient.getInstance(mContext).getRegId();
                if (!TextUtils.isEmpty(regId)) {
                    pushRegisterIdListener.registerResult(regId);
                } else {
                    pushRegisterIdListener.registerResult("");
                }
            }
        }
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = mContext.getApplicationInfo().processName;
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

}
