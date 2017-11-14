package com.insigma.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * @author reyo
 * FFMPEG homepage http://ffmpeg.org/about.html
 * By Google Get first and last thumb of a video using Java and FFMpeg
 * From http://www.codereye.com/2010/05/get-first-and-last-thumb-of-video-using.html
 */

public class VideoThumbTaker
{
    protected String ffmpegApp;

    public VideoThumbTaker(String ffmpegApp)
    {
        this.ffmpegApp = ffmpegApp;
    }

    @SuppressWarnings("unused")
    /****
     * 获取指定时间内的图片
     * @param videoFilename:视频路径
     * @param thumbFilename:图片保存路径
     * @param width:图片长
     * @param height:图片宽
     * @param hour:指定时
     * @param min:指定分
     * @param sec:指定秒
     * @throws IOException
     * @throws InterruptedException
     */
    public void getThumb(String videoFilename, String thumbFilename, int width,
                         int height, int hour, int min, float sec) {
        try{
            ProcessBuilder processBuilder = new ProcessBuilder(ffmpegApp, "-y",
                    "-i", videoFilename, "-vframes", "1", "-ss", hour + ":" + min
                    + ":" + sec, "-f", "mjpeg", "-s", width + "*" + height,
                    "-an", thumbFilename);


            Process process = processBuilder.start();
         /*   InputStream in =process.getInputStream();
            byte[] re = new byte[1024];
            System.out.print("正在进行截图，请稍候");
            while (in.read(re) != -1) {
                System.out.print(".");
            }
            System.out.println("...");
            in.close();
            System.out.println("视频截图完成...");*/



        System.out.println("开始获取图片..");
        InputStream stderr = process.getErrorStream();
        InputStreamReader isr = new InputStreamReader(stderr);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null)
            ;
        process.waitFor();
        System.out.println("视频截图完成...");
        if(br != null) {
            br.close();
            System.out.println("视频截图BufferedReader");
        }
        if(isr != null) {
            System.out.println("视频截图InputStreamReader");
            isr.close();
        }
        if(stderr != null) {
            System.out.println("视频截图InputStream");
            stderr.close();
        }

        }catch (Exception ex){

            System.out.println("视频截图失败..."+ex.getMessage());
        }

    }

   /* public static void main(String[] args)
    {
        VideoThumbTaker videoThumbTaker = new VideoThumbTaker("D:\\ffmpeg\\bin\\ffmpeg.exe");
        try
        {
            videoThumbTaker.getThumb("f:/reyo/test.mkv", "C:\\thumbTest.png",    800, 600, 0, 0, 9);
            System.out.println("over");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }*/
}