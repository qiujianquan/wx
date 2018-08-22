package com.gzh.wx.music_collector.controller;

import com.gzh.wx.music_collector.dto.HotSinger;
import com.gzh.wx.music_collector.dto.Rank;
import com.gzh.wx.music_collector.util.HttpClientUtil;

import java.io.IOException;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author Qiujianquan
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/8/1517:22
 */
public class RankCollector {

    public Rank collect(String url) throws IOException {
        return getRank(url);
    }

    private Rank getRank(String rankUrl) throws IOException {
        Rank rank = new Rank();
        String body = HttpClientUtil.get(rankUrl);
        Document doc = Jsoup.parse(body);
        rank.setScope(doc.select("a[class=current]").attr("title"));
        rank.setUpdateTime(doc.select("span[class=rank_update]").text());
        List<Element> aElements = doc.select("a[data-active=playDwn]");
        for(int i = 0; i < aElements.size(); i++){
            String[] splitArray = aElements.get(i).text().split("-");
            String name = splitArray[0].toString().trim();
            String song = splitArray[1].toString().trim();
            rank.getHotSingerList().add(new HotSinger(doc.select("a[class=current]").attr("title"), name, song));
        }
        return rank;
    }
}