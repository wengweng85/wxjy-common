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
     * ��ȡָ��ʱ���ڵ�ͼƬ
     * @param videoFilename:��Ƶ·��
     * @param thumbFilename:ͼƬ����·��
     * @param width:ͼƬ��
     * @param height:ͼƬ��
     * @param hour:ָ��ʱ
     * @param min:ָ����
     * @param sec:ָ����
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
            System.out.print("���ڽ��н�ͼ�����Ժ�");
            while (in.read(re) != -1) {
                System.out.print(".");
            }
            System.out.println("...");
            in.close();
            System.out.println("��Ƶ��ͼ���...");*/



        System.out.println("��ʼ��ȡͼƬ..");
        InputStream stderr = process.getErrorStream();
        InputStreamReader isr = new InputStreamReader(stderr);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null)
            ;
        process.waitFor();
        System.out.println("��Ƶ��ͼ���...");
        if(br != null) {
            br.close();
            System.out.println("��Ƶ��ͼBufferedReader");
        }
        if(isr != null) {
            System.out.println("��Ƶ��ͼInputStreamReader");
            isr.close();
        }
        if(stderr != null) {
            System.out.println("��Ƶ��ͼInputStream");
            stderr.close();
        }

        }catch (Exception ex){

            System.out.println("��Ƶ��ͼʧ��..."+ex.getMessage());
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