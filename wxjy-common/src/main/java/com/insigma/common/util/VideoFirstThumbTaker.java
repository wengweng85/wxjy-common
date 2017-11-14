package com.insigma.common.util;

import java.io.IOException;

/***
 *
 * �õ���һ�루Ҳ�ǵ�һ֡��ͼƬ
 */
public class VideoFirstThumbTaker extends VideoThumbTaker
{
    public VideoFirstThumbTaker(String ffmpegApp)
    {
        super(ffmpegApp);
    }

    public void getThumb(String videoFilename, String thumbFilename, int width,
                         int height) throws IOException, InterruptedException
    {
        super.getThumb(videoFilename, thumbFilename, width, height, 0, 0, 1);
    }
}