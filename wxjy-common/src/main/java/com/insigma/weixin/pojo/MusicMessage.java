package com.insigma.weixin.pojo;

/**
 *
 * Description:
 * MusicMessage.java Create on 2014-1-3 下午05:16:48
 * @author cupeas@163.com
 * @version 1.0
 * Copyright (c) 2014 Company,Inc. All Rights Reserved.
 */
public class MusicMessage extends BaseMessage {
    // 音乐
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}
