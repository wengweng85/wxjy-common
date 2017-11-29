package com.insigma.common.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;



public class VideoThumbTaker
{
    /**
     * ��ȡָ����Ƶ��֡������ΪͼƬ��ָ��Ŀ¼
     * @param videofile  Դ��Ƶ�ļ�·��
     * @param framefile  ��ȡ֡��ͼƬ���·��
     * @throws Exception
     */
    public static   void fetchFrame(String videofile, String framefile)
            throws Exception {
        long start = System.currentTimeMillis();
        File targetFile = new File(framefile);
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
        ff.start();
        int lenght = ff.getLengthInFrames();
        int i = 0;
        Frame f = null;
        while (i < lenght) {
            // ����ǰ1֡���������ȫ�ڵ�ͼƬ�����Լ��������
            f = ff.grabFrame();
            if ((i > 1) && (f.image != null)) {
                break;
            }
            i++;
        }
        opencv_core.IplImage img = f.image;
        int owidth = img.width();
        int oheight = img.height();
        // �Խ�ȡ��֡���еȱ�������
 /*       int width = 800;
        int height = (int) (((double) width / owidth) * oheight);*/
        int width = 1500;
        int height = 1000;
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
                0, 0, null);
        ImageIO.write(bi, "jpg", targetFile);
        //ff.flush();
        ff.stop();
        System.out.println(System.currentTimeMillis() - start);
    }


}