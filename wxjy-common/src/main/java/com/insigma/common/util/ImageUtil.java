package com.insigma.common.util;

import com.insigma.resolver.AppException;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import javax.imageio.ImageIO;

public class ImageUtil {

	/**
	 * 得到图片长度
	 * @param file
	 * @return
	 */
	public static int getImgWidth(File file){
		try{
			BufferedImage bufferedImage = ImageIO.read(file);
			return bufferedImage.getWidth();
		}catch (IOException e){
			return 0;
		}
	}

	/**
	 * 得到图片宽度
	 * @param file
	 * @return
	 */
	public static int getImgHeight(File file) {
		try{
			BufferedImage bufferedImage = ImageIO.read(file);
			return bufferedImage.getHeight();
		}catch (IOException e){
			return 0;
		}
	}


	/**
	 * 图像切割（改）     *
	 * @param srcImageFile            源图像地址
	 * @param dirImageFile            新图像地址
	 * @param x                       目标切片起点x坐标
	 * @param y                      目标切片起点y坐标
	 * @param destWidth              目标切片宽度
	 * @param destHeight             目标切片高度
	 */
	public static boolean abscut(String srcImageFile,String dirImageFile, int x, int y, int destWidth, int destHeight) throws AppException{
		try {
			//先缩放
			Image img;
			ImageFilter cropFilter;
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getWidth(); // 源图宽度
			int srcHeight = bi.getHeight(); // 源图高度
			if (srcWidth >= destWidth && srcHeight >= destHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				// 改进的想法:是否可用多线程加快切割速度
				// 四个参数分别为图像起点坐标和宽高
				// 即: CropImageFilter(int x,int y,int width,int height)
				cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
				img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, null); // 绘制缩小后的图
				g.dispose();
				// 输出为文件
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
	 * 缩放图像
	 *
	 * @param srcImageFile       源图像文件地址
	 * @param result             缩放后的图像地址
	 * @param scale              缩放比例
	 * @param flag               缩放选择:true 放大; false 缩小;
	 */
	public static void scale(String srcImageFile, String result, float scale, boolean flag) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长
			if (flag) {
				// 放大
				width = new Float(width * scale).intValue();
				height = new Float(height * scale).intValue();
			} else {
				// 缩小
				width = new Float(width * scale).intValue();
				height = new Float(height * scale).intValue();
			}
			Image image = src.getScaledInstance(width, height,Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重新生成按指定宽度和高度的图像
	 * @param srcImageFile       源图像文件地址
	 * @param result             新的图像地址
	 * @param new_width             设置新的图像宽度
	 * @param new_height            设置新的图像高度
	 */
	public static void scale(String srcImageFile, String result, int new_width,int new_height) {
		scale(srcImageFile,result,new_width,new_height,0,0);
	}

	/**
	 * 重新生成按指定宽度和高度的图像
	 * @param srcImageFile       源图像文件地址
	 * @param result             新的图像地址
	 * @param new_width             设置新的图像宽度
	 * @param new_height            设置新的图像高度
	 * @param x                  x
	 * @param y                  y
	 */
	public static void scale(String srcImageFile, String result, int new_width,int new_height,int x,int y) {
		try {

			BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件

			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长

			if (width > new_width) {
				width = new_width;
			}
			if (height > new_height) {
				height = new_height;
			}
			Image image = src.getScaledInstance(width, height,Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, x, y, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像类型转换 GIF->JPG GIF->PNG PNG->JPG PNG->GIF(X)
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

	/**
	 * 根据指定大小和指定精度压缩图片
	 *
	 * @param srcPath 源图片地址
	 * @param desPath 目标图片地址
	 * @param desFileSize 指定图片大小，单位kb
	 * @param accuracy 精度，递归压缩的比率，建议小于0.9
	 * @return
	 */
	public static String commpressPicForScale(String srcPath, String desPath, long desFileSize, double accuracy) {
		if (!new File(srcPath).exists()) {
			return null;
		}
		try {
			File srcFile = new File(srcPath);
			long srcFileSize = srcFile.length();
			System.out.println("源图片：" + srcPath + "，大小：" + srcFileSize / 1024 + "kb");

			// 1、先转换成jpg
			Thumbnails.of(srcPath).scale(1f).toFile(desPath);
			// 递归压缩，直到目标文件大小小于desFileSize
			commpressPicCycle(desPath, desFileSize, accuracy);

			File desFile = new File(desPath);
			System.out.println("目标图片：" + desPath + "，大小" + desFile.length()
					/ 1024 + "kb");
			System.out.println("图片压缩完成！");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return desPath;
	}

	/**
	 *
	 * @param desPath
	 * @param desFileSize
	 * @param accuracy
	 * @throws IOException
	 */
	private static void commpressPicCycle(String desPath, long desFileSize, double accuracy) throws IOException {
		File srcFileJPG = new File(desPath);
		long srcFileSizeJPG = srcFileJPG.length();
		// 2、判断大小，如果小于500kb，不压缩；如果大于等于500kb，压缩
		if (srcFileSizeJPG <= desFileSize * 1024) {
			return;
		}
		// 计算宽高
		BufferedImage bim = ImageIO.read(srcFileJPG);
		int srcWdith = bim.getWidth();
		int srcHeigth = bim.getHeight();
		int desWidth = new BigDecimal(srcWdith).multiply(new BigDecimal(accuracy)).intValue();
		int desHeight = new BigDecimal(srcHeigth).multiply(new BigDecimal(accuracy)).intValue();

		Thumbnails.of(desPath).size(desWidth, desHeight)
				.outputQuality(accuracy).toFile(desPath);
		commpressPicCycle(desPath, desFileSize, accuracy);
	}

	public static void main(String[] args) {

		commpressPicForScale("C:\\Users\\123\\Desktop\\1.png",
				"C:\\Users\\123\\Desktop\\12.jpg", 500, 0.8); // 图片小于500kb
	}



}
