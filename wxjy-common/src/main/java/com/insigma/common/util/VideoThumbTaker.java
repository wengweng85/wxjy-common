package com.insigma.common.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

/**
 * 视频处理
 */
public class VideoThumbTaker {

    /**
     * 获取指定视频的帧并保存为图片至指定目录
     *
     * @param filePath       源视频文件路径
     * @param targerFilePath 截取帧的图片存放路径
     * @throws Exception
     */
    public static void fetchFrame(String filePath, String targerFilePath) throws Exception {
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(filePath);
        ff.start();
        int ffLength = ff.getLengthInFrames();
        Frame f = null;
        int i = 0;
        while (i < ffLength) {
            // 过滤前100帧
            f = ff.grabFrame();
            if ((i > 100) && (f.image != null)) {
                break;
            }
            i++;
        }
        doExecuteFrame(f, targerFilePath);
        ff.stop();
    }

    public static void doExecuteFrame(Frame f, String targerFilePath) {
        if (null == f || null == f.image) {
            return;
        }

        Java2DFrameConverter converter = new Java2DFrameConverter();

        String imageMat = "jpg";
        BufferedImage bi = converter.getBufferedImage(f);
        File output = new File(targerFilePath);
        try {
            ImageIO.write(bi, imageMat, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        // 测试
        fetchFrame("c:/webroot/fileroot/020203/201712/201712201017353sxhse.mp4",
                "c:/webroot/fileroot/020203/201712/201712201017353sxhse.jpg");
    }


}