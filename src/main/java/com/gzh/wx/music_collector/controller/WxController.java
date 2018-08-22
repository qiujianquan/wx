package com.gzh.wx.music_collector.controller;

import com.gzh.wx.enmu.MenuKey;
import com.gzh.wx.music_collector.handler.*;
import com.gzh.wx.music_collector.matcher.WhoAmIMatcher;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxConsts;
import com.soecode.wxtools.api.WxMessageRouter;
import com.soecode.wxtools.api.WxService;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutMessage;
import com.soecode.wxtools.util.xml.XStreamTransformer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Qiujianquan
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/8/1312:15
 */
@RestController
@RequestMapping("/wx")
public class WxController {
    /**
     * IServer 接口是集成所有wx-tools已经实现的微信接口，统一调用入口。它的实现是WxService.java。
     */
    private IService iService = new WxService();

    @GetMapping
    public String check(String signature, String timestamp, String nonce, String echostr) {
        if (iService.checkSignature(signature, timestamp, nonce, echostr)) {
            return echostr;
        }
        return null;
    }

    @PostMapping
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // 创建一个路由器
        WxMessageRouter router = new WxMessageRouter(iService);
        try {
            // 微信服务器推送过来的是XML格式。
            WxXmlMessage wx = XStreamTransformer.fromXml(WxXmlMessage.class, request.getInputStream());
            System.out.println("消息：\n " + wx.toString());
            /**
             * 这里解释一下：next()和end()的意思。
             * next() 表示消息经过第一个规则（Rule）之后，允许继续匹配下面的规则，代表着同一个消息有可能被多个Handler处理。
             * end() 表示规则的结束。当消息满足某条规则时遇到end()，不会再往下匹配规则，就此结束。
             */
            /**
             * 消息回复
             */
            router.rule().msgType(WxConsts.XML_MSG_TEXT).matcher(new WhoAmIMatcher()).handler(new WhoAmIHandler()).end()
                    .rule().msgType(WxConsts.XML_MSG_TEXT).handler(ConfigHander.getInstance()).end()
                    .rule().msgType(WxConsts.XML_MSG_TEXT).handler(ChangeNewsHandler.getInstance()).end();

            /**
             * 点击事件
             */
            router.rule().event(WxConsts.EVT_CLICK).eventKey(MenuKey.HELP.getCode()).handler(HelpDocHandler.getInstance())
                    .next()
                    .rule().eventKey(MenuKey.HOT_SONG.getCode()).handler(RankHandler.getInstance())
                    .next()
                    .rule().eventKey(MenuKey.HUAYU_SONG.getCode()).handler(RankHandler.getInstance())
                    .next()
                    .rule().eventKey(MenuKey.NET_HOT_SONG.getCode()).handler(RankHandler.getInstance())
                    .next()
                    .rule().eventKey(MenuKey.XINAO_SONG.getCode()).handler(RankHandler.getInstance())
                    .next()
                    .rule().eventKey(MenuKey.TOP_500.getCode()).handler(RankHandler.getInstance())
                    .end();
            // 把消息传递给路由器进行处理
            WxXmlOutMessage xmlOutMsg = router.route(wx);
            System.out.println(xmlOutMsg.toString());
            if (xmlOutMsg != null)
                // 因为是明文，所以不用加密，直接返回给用户
                out.print(xmlOutMsg.toXml());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }

    }

}