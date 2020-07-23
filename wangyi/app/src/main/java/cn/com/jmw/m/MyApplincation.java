package cn.com.jmw.m;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.huawei.hms.support.common.ActivityMgr;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.UIKitOptions;
import com.netease.nim.uikit.business.contact.core.query.PinYin;
import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;
import com.netease.nim.uikit.push.PushHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.mixpush.NIMPushClient;
import com.netease.nimlib.sdk.util.NIMUtil;

import cn.com.jmw.m.netease.DemoCache;
import cn.com.jmw.m.netease.NIMInitManager;
import cn.com.jmw.m.netease.NimSDKOptionConfig;
import cn.com.jmw.m.netease.event.DemoOnlineStateContentProvider;
import cn.com.jmw.m.netease.mixpush.DemoMixPushMessageHandler;
import cn.com.jmw.m.netease.mixpush.DemoPushContentProvider;
import cn.com.jmw.m.netease.preference.UserPreferences;
import cn.com.jmw.m.netease.session.SessionHelper;

public class MyApplincation extends Application {

    private Context context = this;

    @Override
    public void onCreate() {
        super.onCreate();

        PushHelper.init(this);
//        PushHelper.getInstance().registerHuaWeiPush();
        PushHelper.getInstance().registerXiaomiPush();
        PushHelper.getInstance().registerOppoPush();
        PushHelper.getInstance().registerVivoPush();
        /**
         * 注意：每个进程都会创建自己的Application 然后调用onCreate() 方法，
         * 如果用户有自己的逻辑需要写在Application#onCreate()（还有Application的其他方法）中，一定要注意判断进程，不能把业务逻辑写在core进程，
         * 理论上，core进程的Application#onCreate()（还有Application的其他方法）只能做与im sdk 相关的工作
         */
        initIM();


    }


    private void initIM() {
        DemoCache.setContext(this);
        // 4.6.0 开始，第三方推送配置入口改为 SDKOption#mixPushConfig，旧版配置方式依旧支持。
        NIMClient.init(this, getLoginInfo(), NimSDKOptionConfig.getSDKOptions(this));
        // 以下逻辑只在主进程初始化时执行
        if (NIMUtil.isMainProcess(this)) {

            ActivityMgr.INST.init(this);
            // 注册自定义推送消息处理，这个是可选项
            NIMPushClient.registerMixPushMessageHandler(new DemoMixPushMessageHandler());

            // 初始化红包模块，在初始化UIKit模块之前执行
//            NIMRedPacketClient.init(this);
            // init pinyin
            PinYin.init(this);
            PinYin.validate();
            // 初始化UIKit模块
            initUIKit();
            // 初始化消息提醒
            NIMClient.toggleNotification(UserPreferences.getNotificationToggle());
            //关闭撤回消息提醒
//            NIMClient.toggleRevokeMessageNotification(false);
            // 云信sdk相关业务初始化
            NIMInitManager.getInstance().init(true);
            // 初始化音视频模块
//            initAVChatKit();
            // 初始化rts模块
//            initRTSKit();
            // 更新消息提醒配置 StatusBarNotificationConfig，以设置不响铃为例。
            StatusBarNotificationConfig config = UserPreferences.getStatusConfig();
            config.ring = false;
            //头像
            config.notificationSmallIconId = R.drawable.down_icon;
            //震动
            config.vibrate = true;
            config.ledARGB = R.color.color_green_01d9ae;

            config.titleOnlyShowAppName =true;
            config.notificationEntrance = new MainActivity().getClass();
            NIMClient.updateStatusBarNotificationConfig(config);
        }
    }

    /**
     * 获取云信IM的accid 、token，accid 只用于 IM 功能的鉴权即用来验证该账户是否是IM账户，是否可以使用IM功能
     */
    private LoginInfo getLoginInfo() {
        // TODO: 2020/7/16  读取本地存储的云信账户和token
        String account = "jmw_84";
        String token = "8aee69f4ad8f2546f07657768c808638";

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    private void initUIKit() {
        // 初始化
        NimUIKit.init(this, buildUIKitOptions());

        // 设置地理位置提供者。如果需要发送地理位置消息，该参数必须提供。如果不需要，可以忽略。
//        NimUIKit.setLocationProvider(new NimDemoLocationProvider());

        // IM 会话窗口的定制初始化。
        SessionHelper.init();

        // 聊天室聊天窗口的定制初始化。
//        ChatRoomSessionHelper.init();

        // 通讯录列表定制初始化
//        ContactHelper.init();

        // 添加自定义推送文案以及选项，请开发者在各端（Android、IOS、PC、Web）消息发送时保持一致，以免出现通知不一致的情况
        NimUIKit.setCustomPushContentProvider(new DemoPushContentProvider());
        // 在线状态定制初始化。
        NimUIKit.setOnlineStateContentProvider(new DemoOnlineStateContentProvider());
    }

    private UIKitOptions buildUIKitOptions() {
        UIKitOptions options = new UIKitOptions();
        // 设置app图片/音频/日志等缓存目录
        options.appCacheDir = NimSDKOptionConfig.getAppCacheDir(this) + "/app";
        return options;
    }


}



