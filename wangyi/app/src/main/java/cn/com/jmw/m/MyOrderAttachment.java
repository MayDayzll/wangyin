package cn.com.jmw.m;

import com.alibaba.fastjson.JSONObject;

import cn.com.jmw.m.netease.session.extension.CustomAttachment;
import cn.com.jmw.m.netease.session.extension.CustomAttachmentType;

/**
 * @describe: -订单 10002
*/
public class MyOrderAttachment extends CustomAttachment {

    public String title;
    public String content;
 
    public MyOrderAttachment() {
        super(CustomAttachmentType.MyOrderCustomMsg);
    }

    @Override
    protected void parseData(JSONObject data) {
        title=data.getString("title");
        content=data.getString("content");
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        
        data.put("title",title);
        data.put("content",content);
      
        return data;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

