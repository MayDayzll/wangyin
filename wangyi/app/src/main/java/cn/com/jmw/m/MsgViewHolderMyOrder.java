package cn.com.jmw.m;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.nim.uikit.business.session.viewholder.MsgViewHolderBase;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;

/**
 * @describe:
 * @author: Gao Chunfa
 * @time: 2018/3/30-下午5:32
 * @Company: 猎象网络科技
 * @other:
 */
public class MsgViewHolderMyOrder extends MsgViewHolderBase {


    public MsgViewHolderMyOrder(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    public int getContentResId() {
        //自己定义的布局
        return R.layout.layout_message;
    }

    @Override
    public void inflateContentView() {
        MyOrderAttachment attachment = (MyOrderAttachment) message.getAttachment();
        String title = attachment.getTitle();
        TextView mTvtitle = findViewById(R.id.title);
        mTvtitle.setText(title);
    }

    @Override
    public void bindContentView() {

    }

    //若是要自己修改气泡背景
    // 当是发送出去的消息时，内容区域背景的drawable id
    @Override
    public int rightBackground() {
        return R.drawable.banyuan;
    }

    @Override
    public int leftBackground() {
        return R.drawable.banyuan;
    }
}



