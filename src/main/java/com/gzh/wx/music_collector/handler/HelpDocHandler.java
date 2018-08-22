package com.gzh.wx.music_collector.handler;

import com.gzh.wx.music_collector.constants.ResponseConst;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxMessageHandler;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutMessage;
import com.soecode.wxtools.exception.WxErrorException;

import java.util.Map;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

/**
 * @author Qiujianquan
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/8/1421:03
 */
public class HelpDocHandler implements WxMessageHandler {

    private static HelpDocHandler instance = null;

    private boolean isRun = false;

    private HelpDocHandler(){}

    /**
     * 这是个单例写法  写完之后 改成多线程的～～
     * @return
     */
    public static synchronized HelpDocHandler getInstance(){
        if (instance == null) {
            instance = new HelpDocHandler();
        }
        return instance;
    }

    private synchronized  boolean getIsRun() {
        return isRun;
    }

    private synchronized void setRun(boolean run) {
        isRun = run;
    }


    @Override
    public WxXmlOutMessage handle(WxXmlMessage wxMessage, Map<String, Object> context, IService iService) throws WxErrorException {
        System.out.println("进入帮助handler");
        WxXmlOutMessage response = null;
        if (!getIsRun()) {
            //加锁
            setRun(true);
            response = execute(wxMessage);

            System.out.println(response.toString());
            //解锁
            setRun(false);
        }
        return response;
    }

    /**
     * 给微信上的小伙伴发送帮助说明～
     * @param wxMessage
     * @return
     */
    private WxXmlOutMessage execute(WxXmlMessage wxMessage) {
        System.out.println("进入");

        return WxXmlOutMessage.TEXT().content(ResponseConst.HELP).toUser(wxMessage.getFromUserName()).fromUser(wxMessage.getToUserName()).build();


    }
}