package cn.com.jmw.m;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.common.ui.drop.DropManager;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.dialog.CommentDialog;
import com.netease.nim.uikit.dialog.WorkOrderDoalog;
import com.netease.nim.uikit.push.PushHelper;
import com.netease.nim.uikit.push.PushRegisterIdListener;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import cn.com.jmw.m.netease.DemoCache;
import cn.com.jmw.m.netease.session.SessionHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        registerOnlineStatusObserver();



        PushHelper.getInstance().getHuaweiRegisterID(new PushRegisterIdListener() {
            @Override
            public void registerResult(String registerId) {
                System.out.println("华为推送ID-\t" + registerId);
            }
        });

        PushHelper.getInstance().getXiaomiRegisterID(new PushRegisterIdListener() {
            @Override
            public void registerResult(String registerId) {
                System.out.println("小米推送ID-\t" + registerId);
            }
        });

        PushHelper.getInstance().getOppoRegisterID(new PushRegisterIdListener() {
            @Override
            public void registerResult(String registerId) {
                System.out.println("OPPO推送ID-\t" + registerId);
            }
        });

        PushHelper.getInstance().getVivoRegisterID(new PushRegisterIdListener() {
            @Override
            public void registerResult(String registerId) {
                System.out.println("Vivo推送ID-\t" + registerId);
            }
        });


    }


    private void initView() {
        findViewById(R.id.loginBtn).setOnClickListener(this);
        findViewById(R.id.loginOutBtn).setOnClickListener(this);
        findViewById(R.id.bt).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.loginBtn:
                String account = "jmw_84";
                String token = "8aee69f4ad8f2546f07657768c808638";
                loginImAccount(account, token);
                break;
            case R.id.loginOutBtn:
//                logoutIm(this);
                MyOrderAttachment myOrderAttachment = new MyOrderAttachment();
                myOrderAttachment.setTitle("五月天最帅");
                IMMessage stickerMessage = MessageBuilder.createCustomMessage("2859179205", SessionTypeEnum.Team, "sadal", myOrderAttachment);
                NIMClient.getService(MsgService.class).sendMessage(stickerMessage,false);
//                NIMClient.getService(MsgService.class).saveMessageToLocal(
                break;
            case R.id.bt:
                SessionHelper.startTeamSession(this,"2859179205");
//                SessionHelper.startP2PSession(this, "jmw_12345");
                break;
        }
    }

    private static void registerOnlineStatusObserver() {
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(new Observer<StatusCode>() {
            @Override
            public void onEvent(StatusCode code) {
                if (code.wontAutoLogin()) {
                    // 被踢出、账号被禁用、密码错误等情况，自动登录失败，需要返回到登录界面进行重新登录操作
                    LogUtil.e("登录回调", "被踢出、账号被禁用、密码错误等情况，自动登录失败");
                } else {
                    if (code == StatusCode.NET_BROKEN) {
                        LogUtil.e("登录回调", "当前网络不可用");
                    } else if (code == StatusCode.UNLOGIN) {
                        LogUtil.e("登录回调", "未登录");
                    } else if (code == StatusCode.CONNECTING) {
                        LogUtil.e("登录回调", "连接中...");
                    } else if (code == StatusCode.LOGINING) {
                        LogUtil.e("登录回调", "登录中...");
                    } else if (code == StatusCode.LOGINED) {
                        LogUtil.e("登录回调", "登录成功");
                    }
                }
            }
        }, true);
    }

    private void loginImAccount(final String imAccount, final String imToken) {
        NimUIKit.login(new LoginInfo(imAccount, imToken), new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo loginInfo) {
                DemoCache.setAccount(imAccount);
                // 初始化消息提醒配置
                LogUtil.e("登录回调", "云信用户成功登录");
            }

            @Override
            public void onFailed(int i) {
                LogUtil.e("登录回调", "云信用户登录失败重启登录服务");

            }

            @Override
            public void onException(Throwable throwable) {
                LogUtil.e("登录回调", "云信用户登录异常" + throwable.getMessage());
            }
        });
    }

    public void showDialog() {
        CommentDialog commentDialog = new CommentDialog(this);
        commentDialog.show();
    }

    public static void logoutIm(Context mContext) {
        //如果用户手动登出，不再接收消息和提醒
        NIMClient.getService(AuthService.class).logout();
        // 清理缓存&注销监听&清除状态
        NimUIKit.logout();
        DemoCache.clear();
        DropManager.getInstance().destroy();
    }
}
