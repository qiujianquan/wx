package com.gzh.wx.enmu;

/**
 * @author Qiujianquan
 * @date 2018/8/1418:49
 */
public enum MenuKey {

    /**
     * 歌曲类
     */
    HOT_SONG("V1001_HOT_SONG_MUSIC","飙升榜"),NET_HOT_SONG("V1001_NET_HOT_SONG_MUSIC","网络红歌"),TOP_500("V1001_TOP500_MUSIC","TOP500"),HUAYU_SONG("V1001_HUAYU_SONG_MUSIC","华语新歌"),XINAO_SONG("V1001_XINAO_SONG_MUSIC","洗脑神曲"),


    /**
     * 功能类
     */
    CHANGE_NEWS("SYSTEM_CHANGE_NEWS","换一组"),HELP("SYSTEM_HELP","帮助")
    ;


    private String code;
    private String name;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    MenuKey(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
