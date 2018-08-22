package com.gzh.wx.music_collector.interceptor;

import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxMessageInterceptor;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.exception.WxErrorException;

import java.util.Map;

/**
 * @author Qiujianquan
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/8/1518:30
 */
public class DemoInterceptor implements WxMessageInterceptor {
   // Demo 拦截器，可以通过WxService做更加复杂的拦截，例如身份验证，权限验证等操作。

    public boolean intercept(WxXmlMessage wxMessage, Map context, IService wxService) throws WxErrorException {
        //可以使用wxService的微信API方法
        //可以在Handler和Interceptor传递消息，使用context上下文
        //可以实现自己的业务逻辑
        //这里就不编写验证关注三天以上的用户了
//        if(/*用户关注时长大于3天*/){
//            return true;
//        }
        return false;
    }



   //配合拦截器的陆游规则 家在controller那 router.rule().matcher(new DemoMatcher()).interceptor(new DemoInterceptor()).handler(new DemoMessageHandler()).end();

}
