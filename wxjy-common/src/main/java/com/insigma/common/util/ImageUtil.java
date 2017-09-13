package com.insigma.common.util;

import com.insigma.resolver.AppException;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageUtil {


	/**
	 * �õ�ͼƬ����
	 * @param file
	 * @return
	 */
	public static String getImgWidth(File file){
		try{
			BufferedImage bufferedImage = ImageIO.read(file);
			return new Integer(bufferedImage.getWidth()).toString();
		}catch (IOException e){
			return "";
		}
	}

	/**
	 * �õ�ͼƬ���
	 * @param file
	 * @return
	 */
	public static String getImgHeight(File file) {
		try{
			BufferedImage bufferedImage = ImageIO.read(file);
			return new Integer(bufferedImage.getHeight()).toString();
		}catch (IOException e){
			return "";
		}
	}


	/**
	 * ͼ���и�ģ�     *
	 * @param srcImageFile            Դͼ���ַ
	 * @param dirImageFile            ��ͼ���ַ
	 * @param x                       Ŀ����Ƭ���x����
	 * @param y                      Ŀ����Ƭ���y����
	 * @param destWidth              Ŀ����Ƭ���
	 * @param destHeight             Ŀ����Ƭ�߶�
	 */
	public static boolean abscut(String srcImageFile,String dirImageFile, int x, int y, int destWidth, int destHeight) throws AppException{
		try {
			//������
			Image img;
			ImageFilter cropFilter;
			// ��ȡԴͼ��
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getWidth(); // Դͼ���
			int srcHeight = bi.getHeight(); // Դͼ�߶�
			if (srcWidth >= destWidth && srcHeight >= destHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				// �Ľ����뷨:�Ƿ���ö��̼߳ӿ��и��ٶ�
				// �ĸ������ֱ�Ϊͼ���������Ϳ��
				// ��: CropImageFilter(int x,int y,int width,int height)
				cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
				img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, null); // ������С���ͼ
				g.dispose();
				// ���Ϊ�ļ�
				ImageIO.write(tag, "JPEG", new File(dirImageFile));
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	/**
	 * ����ͼ��
	 *
	 * @param srcImageFile       Դͼ���ļ���ַ
	 * @param result             ���ź��ͼ���ַ
	 * @param scale              ���ű���
	 * @param flag               ����ѡ��:true �Ŵ�; false ��С;
	 */
	public static void scale(String srcImageFile, String result, float scale, boolean flag) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile)); // �����ļ�
			int width = src.getWidth(); // �õ�Դͼ��
			int height = src.getHeight(); // �õ�Դͼ��
			if (flag) {
				// �Ŵ�
				width = new Float(width * scale).intValue();
				height = new Float(height * scale).intValue();
			} else {
				// ��С
				width = new Float(width * scale).intValue();
				height = new Float(height * scale).intValue();
			}
			Image image = src.getScaledInstance(width, height,Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // ������С���ͼ
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// ������ļ���
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �������ɰ�ָ����Ⱥ͸߶ȵ�ͼ��
	 * @param srcImageFile       Դͼ���ļ���ַ
	 * @param result             �µ�ͼ���ַ
	 * @param _width             �����µ�ͼ����
	 * @param _height            �����µ�ͼ��߶�
	 */
	public static void scale(String srcImageFile, String result, int _width,int _height) {
		scale(srcImageFile,result,_width,_height,0,0);
	}

	/**
	 * �������ɰ�ָ����Ⱥ͸߶ȵ�ͼ��
	 * @param srcImageFile       Դͼ���ļ���ַ
	 * @param result             �µ�ͼ���ַ
	 * @param _width             �����µ�ͼ����
	 * @param _height            �����µ�ͼ��߶�
	 * @param x                  x
	 * @param y                  y
	 */
	public static void scale(String srcImageFile, String result, int _width,int _height,int x,int y) {
		try {

			BufferedImage src = ImageIO.read(new File(srcImageFile)); // �����ļ�

			int width = src.getWidth(); // �õ�Դͼ��
			int height = src.getHeight(); // �õ�Դͼ��

			if (width > _width) {
				width = _width;
			}
			if (height > _height) {
				height = _height;
			}
			Image image = src.getScaledInstance(width, height,Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, x, y, null); // ������С���ͼ
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// ������ļ���
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ͼ������ת�� GIF->JPG GIF->PNG PNG->JPG PNG->GIF(X)
	 */
	public static void convert(String source, String result) {
		try {
			File f = new File(source);
			f.canRead();
			f.canWrite();
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, "JPG", new File(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
