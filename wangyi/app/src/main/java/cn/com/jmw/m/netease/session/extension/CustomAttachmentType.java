package cn.com.jmw.m.netease.session.extension;

/**
 * Created by zhoujianghua on 2015/4/9.
 */
public interface CustomAttachmentType {
    // 多端统一
    int Guess = 1;//石头剪刀布
    int SnapChat = 2;//阅后即焚
    int Sticker = 3;//贴图
    int RTS = 4;//白板的发起结束消息
    int RedPacket = 5;
    int OpenedRedPacket = 6;
    int MultiRetweet = 15;//多条消息合并转发
    int MyOrderCustomMsg = 10002; //自定义消息
}
