package com.gzh.wx.music_collector.matcher;

import com.soecode.wxtools.api.WxMessageMatcher;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.util.StringUtils;

/**
 * @author Qiujianquan
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: 消息匹配器 实现了 WxMessageMatcher（消息匹配器）接口用于一些简单的匹配，可以自定义匹配逻辑，如格式验证。匹配成功则继续往下执行，否则不允许通过。
 * @date 2018/8/1518:04
 */

    public class WhoAmIMatcher implements WxMessageMatcher {
    /**
     * 后期可以做个语言库匹配自动回复
     * @param message
     * @return
     */
        @Override
        public boolean match(WxXmlMessage message) {
            if(StringUtils.isNotEmpty(message.getContent())){
                if(message.getContent().equals("我是谁")){
                    return true;
                }
            }
            return false;
        }


    }
