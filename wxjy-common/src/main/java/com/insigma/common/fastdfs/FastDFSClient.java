package com.insigma.common.fastdfs;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * FastDFSClient
 */
public class FastDFSClient {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(FastDFSClient.class);
	private static TrackerClient trackerClient;
	private static TrackerServer trackerServer;
	private static StorageClient storageClient;
	private static StorageServer storageServer;

	static {
		try {
			InputStream in = Fastdfs.class.getClassLoader().getResourceAsStream("fdfs_client.properties");
			ClientGlobal.init(in);
			trackerClient = new TrackerClient();
			trackerServer = trackerClient.getConnection();
			storageServer = trackerClient.getStoreStorage(trackerServer);
		} catch (Exception e) {
			logger.error("FastDFS Client Init Fail!",e);
		}
	}

	/**
	 * 文件上传
	 * @param file
	 * @return
	 */
	public static String[] upload(FastDFSFile file) {
		logger.info("File Name: " + file.getName() + "File Length:" + file.getContent().length);

		NameValuePair[] meta_list = new NameValuePair[1];
		meta_list[0] = new NameValuePair("author", file.getAuthor());

		long startTime = System.currentTimeMillis();
		String[] uploadResults = null;
		try {
			storageClient = new StorageClient(trackerServer, storageServer);
			uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), null);
		} catch (IOException e) {
			logger.error("IO Exception when uploadind the file:" + file.getName(), e);
		} catch (Exception e) {
			logger.error("Non IO Exception when uploadind the file:" + file.getName(), e);
		}
		logger.info("upload_file time used:" + (System.currentTimeMillis() - startTime) + " ms");

		if (uploadResults == null) {
			logger.error("upload file fail, error code:" + storageClient.getErrorCode());
		}
		String groupName = uploadResults[0];
		String remoteFileName = uploadResults[1];

		logger.info("upload file successfully!!!" + "group_name:" + groupName + ", remoteFileName:" + " " + remoteFileName);
		return uploadResults;
	}

	/**
	 * getFile
	 * @param groupName
	 * @param remoteFileName
	 * @return
	 */
	public static FileInfo getFile(String groupName, String remoteFileName) {
		try {
			storageClient = new StorageClient(trackerServer, storageServer);
			return storageClient.get_file_info(groupName, remoteFileName);
		} catch (IOException e) {
			logger.error("IO Exception: Get File from Fast DFS failed", e);
		} catch (Exception e) {
			logger.error("Non IO Exception: Get File from Fast DFS failed", e);
		}
		return null;
	}

	/**
	 * downFile
	 * @param groupName
	 * @param remoteFileName
	 * @return
	 */
	public static InputStream downFile(String groupName, String remoteFileName) {
		try {
			storageClient = new StorageClient(trackerServer, storageServer);
			byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
			InputStream ins = new ByteArrayInputStream(fileByte);
			return ins;
		} catch (IOException e) {
			logger.error("IO Exception: Get File from Fast DFS failed", e);
		} catch (Exception e) {
			logger.error("Non IO Exception: Get File from Fast DFS failed", e);
		}
		return null;
	}


	/**
	 * 下载
	 */

	public static byte[] downFile(String file_path) {
		InputStream data = null;
		try {
			String remoteFileName = getFileNameFormFilePath(file_path);
			String groupName = getGroupFormFilePath(file_path);
			System.out.println(remoteFileName+","+groupName);
			data = FastDFSClient.downFile(groupName, remoteFileName);
			int size = data.available();
			byte[] buffer = new byte[size];
			IOUtils.read(data, buffer);
			return buffer;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			IOUtils.closeQuietly(data);
		}
	}

	/**
	 * 根据fastDFS返回的path得到文件名
	 * @param path fastDFS返回的path
	 * @return
	 */
	public static String getFileNameFormFilePath(String path) {
		String path_temp = path.substring(path.indexOf("/")+1);
		path_temp = path_temp.substring(path_temp.indexOf("/")+1);
		return path_temp;
	}

	/**
	 * 根据fastDFS返回的path得到文件的组名
	 * @param path fastDFS返回的path
	 * @return
	 */
	public static String getGroupFormFilePath(String path){
		return path.split("/")[1];
	}



	/**
	 * deleteFile
	 * @param groupName
	 * @param remoteFileName
	 * @throws Exception
	 */
	public static void deleteFile(String groupName, String remoteFileName) throws Exception {
		storageClient = new StorageClient(trackerServer, storageServer);
		int i = storageClient.delete_file(groupName, remoteFileName);
		logger.info("delete file successfully!!!" + i);
	}

	/**
	 * getStoreStorages
	 * @param groupName
	 * @return
	 * @throws IOException
	 */
	public static StorageServer[] getStoreStorages(String groupName) throws IOException {
		return trackerClient.getStoreStorages(trackerServer, groupName);
	}

	/**
	 * getFetchStorages
	 * @param groupName
	 * @param remoteFileName
	 * @return
	 * @throws IOException
	 */
	public static ServerInfo[] getFetchStorages(String groupName, String remoteFileName) throws IOException {
		return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
	}

	/**
	 * getTrackerUrl
	 * @return
	 */
	public static String getTrackerUrl() {
		return "http://"+trackerServer.getInetSocketAddress().getHostString()+":"+ClientGlobal.getG_tracker_http_port()+"/";
	}

	/**
	 * 使用fastdfs方式保存文件
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	public static String saveFile(MultipartFile multipartFile) throws IOException {
		return saveFile(multipartFile.getOriginalFilename(),multipartFile.getInputStream());
	}


	/**
	 * 使用fastdfs方式保存文件
	 * @param fileItem
	 * @return
	 * @throws IOException
	 */
	public static String saveFile(FileItem fileItem) throws IOException {
		return saveFile(fileItem.getName(),fileItem.getInputStream());
	}

	/**
	 * 使用fastdfs方式保存文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String saveFile(File file) throws IOException {
		return saveFile(file.getName(),new FileInputStream(file));
	}
	/**
	 *
	 * @param fileName
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private static String saveFile(String fileName, InputStream inputStream) throws IOException {
		String[] fileAbsolutePath={};
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		byte[] file_buff = null;
		if(inputStream!=null){
			int len1 = inputStream.available();
			file_buff = new byte[len1];
			inputStream.read(file_buff);
		}
		inputStream.close();
		FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
		try {
			fileAbsolutePath = FastDFSClient.upload(file);  //upload to fastdfs
		} catch (Exception e) {
			System.err.println("upload file Exception!");
		}
		if (fileAbsolutePath==null) {
			System.err.println("upload file failed,please upload again!");
		}
		//		String path=FastDFSClient.getTrackerUrl()+fileAbsolutePath[0]+ "/"+fileAbsolutePath[1];
		String path=fileAbsolutePath[0]+ "/"+fileAbsolutePath[1];
		return path;
	}
}