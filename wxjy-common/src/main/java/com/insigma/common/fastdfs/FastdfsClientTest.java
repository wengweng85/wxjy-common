package com.insigma.common.fastdfs;


import org.junit.Test;

import java.io.FileInputStream;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class FastdfsClientTest {

	/**
	 * 上传文件测试
	 */
	@Test
	public void testUpload(){
		try{
			long start=new Date().getTime();
			for(int i=0;i<10;i++){
				Fastdfs fastdfs=Fastdfs.getInstance();
				String filepath="d:/test.jpg";
				fastdfs.uploadFile(filepath);
			}
			long end=new Date().getTime();
			System.out.println("耗费:"+(end-start)+"毫秒");
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	//@Test
	public void testMultiThreadUpload(){
		
	}

	/**
	 * 上传主从文件测试
	 */
	//@Test
	public void testSlaveUpload(){
		try{
			long start=new Date().getTime();
			Fastdfs fastdfs=Fastdfs.getInstance();
			String filePath="d:/test.jpg";
			String result=fastdfs.uploadFile(filePath);
			System.out.println(result);
			long end=new Date().getTime();
			System.out.println("耗费:"+(end-start)+"毫秒");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**	 * 下载测试
	 */
	//@Test
	public void testDownload(){
		try{
			long start=new Date().getTime();
			Fastdfs fastdfs=Fastdfs.getInstance();
			String downloadfilepath="/group1/M00/00/00/Cr6OBVnxa5KADbNVB7jLnZd7Z5I048.zip";
			fastdfs.downloadFile(downloadfilepath);
			long end=new Date().getTime();
			System.out.println("耗费:"+(end-start)+"毫秒");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 删除测试
	 */
	//@Test
	public void testDelete(){
		try{
			long start=new Date().getTime();
			Fastdfs fastdfs=Fastdfs.getInstance();
			String downloadfilepath="/group1/M00/00/00/Cr6OBFn8H8SADrRFB7jLnZd7Z5I022.zip";
			fastdfs.deleteFile(downloadfilepath);
			long end=new Date().getTime();
			System.out.println("耗费:"+(end-start)+"毫秒");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*public static void main(String [] args){
		final Fastdfs fastdfs=Fastdfs.getInstance();
		final String filepath="d:/test.jpg";
		ExecutorService executorService=Executors.newFixedThreadPool(10);
		for(int i=0;i<10;i++){
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					try{
						long start=new Date().getTime();
						fastdfs.uploadFile(filepath);
						long end=new Date().getTime();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
		}
	}*/

}
