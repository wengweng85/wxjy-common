package com.insigma.weixin.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
    /**
     * 文件操作工具类
     * creatTxtFile------>1.生成文件
     * writeTxtFile------>2.将内容添加到文件中
     * readTxtFile------->3.读取文件内容
     * replace----------->4.文件重命名(先复制再删除)
     * copyFile---------->5.复制文件内容(将一个文件内容复制到另一个文件内)
     * getAllFileName---->6.得到指定文件夹里的所有文件的文件名
     * fileCopy---------->7.复制单个文件(给定源文件、目标文件路径：路径+文件名；指定是否允许覆盖)
     * copyDirectory----->8.复制整个目录(给定源目录、目标目录路径：路径+目录名；指定是否允许覆盖)
     * deleteFile-------->9.删除文件(给定要删除的文件名：路径+文件名)
     * deleteDirectory--->10.删除文件夹以及目录下的文件     先删除文件夹里的所有文件与文件夹，再删除目录
     * moveFolder-------->11.移动文件/文件夹(先复制再删除源文件/源文件夹)
     */
    /**
     * 1
     * 创建文件
     * path：创建文件的保存目录路径
     * filename:文件名称
     */
    public static boolean creatTxtFile(String path , String filename) throws IOException {
        boolean flag = false; //创建文件失败
        String filenameTemp = path + "/" + filename + ".txt";
        File file = new File(filenameTemp);
        if (!file.exists()) {
            file.createNewFile();
            flag = true; //创建文件成功
        }
        return flag;
    }

    /**
     * 2
     * 将内容写入文件
     * content:即将写入文件的内容
     * filenameTemp:要写入的文件路径      文件路径+文件名称
     */
    public static boolean writeTxtFile (String content,String filenameTemp) throws IOException {
        boolean flag = false; //写入失败
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            File file = new File(filenameTemp); // 文件路径
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));//将文件读入输入流

            StringBuffer buf = new StringBuffer();
            buf.append(content);

            //将内容写入到文件中
            pw = new PrintWriter(new FileOutputStream(file));
            pw.write(buf.toString());
            pw.flush();
            flag = true; //写入成功
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }if (br != null) {
                br.close();
            }
        }
        return flag;
    }

    /**
     * 3
     * 读取文件内容
     * filePath：要读取的文件路径
     */
    public static String readTxtFile(String filePath){
        StringBuffer buff = new StringBuffer("");
        try {
            String encoding="UTF-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String str = null;
                while((str = bufferedReader.readLine()) != null){
                    buff.append(str);
                }
                read.close();
            }else{
                System.err.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        return buff.toString();
    }

    /**
     * 4
     * 给文件重命名(先复制再删除)
     * oldpath:旧的文件路径
     * newfilename:新文件名称
     */
    public static void replace(String oldpath , String newfilename) throws IOException{
        File file = new File(oldpath);
        if(oldpath.contains("\\")){
            oldpath = oldpath.replace("\\", "/");
        }
        int index = oldpath.lastIndexOf("/");
        String qianzhui = oldpath.substring(0, index);
        String path = qianzhui + "/" + newfilename + ".txt";
        File ff = new File(path);
        if (!ff.exists()) {
            ff.createNewFile();
        }
        copyFile(file,ff);
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * 5
     * 复制文件内容(将一个文件内容复制到另一个文件内)
     * sourceFile:源文件
     * targetFile:目标文件
     */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));// 新建文件输入流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));// 新建文件输出流并对它进行缓冲

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }

            outBuff.flush();// 刷新此缓冲的输出流
        } catch (Exception e) {
            e.printStackTrace();
        }finally {// 关闭流
            if (inBuff != null) inBuff.close();
            if (outBuff != null) outBuff.close();
        }
    }

    /**
     * 6
     * 得到指定文件夹里的所有文件的文件名
     * path:文件夹路径
     */
    public static List<String> getAllFileName(String path){
        List<String> ss= new ArrayList<String>();
        File file = new File(path);//得到指定文件夹
        File[] files = file.listFiles();//指定目录下的所有文件及文件夹
        if(null != files && files.length > 0){
            for(int i = 0 ; i < files.length ; i++){
                if(files[i].isFile()){//判断是否是文件
                    ss.add(files[i].getName());
                }
            }
        }
        return ss;
    }

    /**
     * 7
     * 复制单个文件
     * sourceFileName:源文件名(路径 +文件名)
     * targetFileName：目标文件名(路径+文件名)
     * overlay: 如果目标文件存在，是否覆盖          true:允许覆盖           false:不允许覆盖
     * 如果复制成功返回true，否则返回false
     */
    public static boolean fileCopy(String sourceFileName, String targetFileName,boolean overlay) {
        File sourceFile = new File(sourceFileName);
        //1.判断源文件是否存在
        if (!sourceFile.exists()) {
            System.err.println("复制文件失败，源文件不存在");
            return false;
        } else if (!sourceFile.isFile()) {//是否是文件
            System.err.println("复制文件失败,源文件：" + sourceFileName + "不是一个文件");
            return false;
        }

        //2.判断目标文件是否存在
        File targetFile = new File(targetFileName);
        if (targetFile.exists()) {
            //如果目标文件存在并允许覆盖
            if (overlay) {//允许覆盖
                new File(targetFileName).delete();//删除已经存在的目标文件，无论目标文件是目录还是单个文件
            }else{
                System.err.println("目标文件存在，您选择不允许覆盖，文件不进行复制");
                return true;
            }
        } else {
            // 如果目标文件所在目录不存在，则创建目录
            if (!targetFile.getParentFile().exists()) {// 目标文件所在目录不存在
                if (!targetFile.getParentFile().mkdirs()) {
                    System.err.println("复制文件失败，创建目标文件所在目录失败");
                    return false;
                }
            }
        }

        //3.复制文件
        int byteread = 0; // 读取的字节数
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(sourceFile);
            out = new FileOutputStream(targetFile);
            byte[] buffer = new byte[1024];

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 8
     * 复制整个目录
     * srcDirName：源目录的目录名(路径+目录名)
     * destDirName：目标目录的目录名(路径+目录名)
     * overlay：如果目标目录存在，是否覆盖         true:允许覆盖           false:不允许覆盖
     * 如果复制成功返回true，否则返回false
     */
    public static boolean copyDirectory(String srcDirName, String destDirName, boolean overlay) {
        //1.判断源目录是否存在
        File srcDir = new File(srcDirName);
        if (!srcDir.exists()) {
            System.err.println("复制目录失败,源目录" + srcDirName + "不存在");
            return false;
        } else if (!srcDir.isDirectory()) {//是否为目录文件夹
            System.err.println("复制目录失败,源目录" + srcDirName + "不是目录");
            return false;
        }

        //2.如果目标目录名不是以文件分隔符结尾，则加上文件分隔符:\
        if (!destDirName.endsWith(File.separator)) {//if(a.endsWith(b))判断字符串a是不是以字符串b结尾
            destDirName = destDirName + File.separator;
        }

        //3.判断目标文件夹是否存在
        File destDir = new File(destDirName);
        if (destDir.exists()) {
            if (overlay) {// 如果允许覆盖则删除已存在的目标目录
                new File(destDirName).delete();
            } else {
                System.err.println("目标文件存在，您选择不允许覆盖，文件夹不进行复制");
                return true;
            }
        } else {// 创建目的目录
            if (!destDir.mkdirs()) {
                System.out.println("复制目录失败，创建目的目录失败");
                return false;
            }
        }

        //4.复制文件夹下的所有文件及文件夹
        boolean flag = true;
        File[] files = srcDir.listFiles();//得到源目录下的所有文件和文件夹
        for (int i = 0; i < files.length; i++) {
            // 复制文件
            if (files[i].isFile()) {//文件
                flag = fileCopy(files[i].getAbsolutePath(),destDirName + files[i].getName(), overlay);
                if (!flag)
                    break;
            } else if (files[i].isDirectory()) {//文件夹
                flag = copyDirectory(files[i].getAbsolutePath(),destDirName + files[i].getName(), overlay);
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.err.println("复制目录" + srcDirName + "至" + destDirName + "失败");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 9
     * 删除文件
     * path:删除文件的文件路径：文件路径+文件名
     */
    public static boolean deleteFile(String path){
        File ff = new File(path);
        if(ff.exists() && ff.isFile()){
            ff.delete();
            return true;
        }else{
            System.err.println(path+"--->没有找到该路径的文件");
            return false;
        }
    }

    /**
     * 10
     * 删除文件夹以及目录下的文件     先删除文件夹里的所有文件与文件夹，再删除目录
     * path:被删除目录的文件路径
     * 文件夹删除成功:true，否则:false
     */
    public static boolean deleteDirectory(String path) {
        boolean flag;
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        File dirFile = new File(path);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            System.err.println(path + "不存在/不是一个文件夹");
            return false;
        }
        flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {//删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            }else {//删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        if (dirFile.delete()) {//删除当前目录
            return true;
        } else {
            return false;
        }
    }

    /**
     * 11
     * 移动文件/文件夹(先复制再删除源文件/源文件夹)
     * sourcepath:要移动的源文件/源文件夹路径
     * tagerpath:移动后的路径
     * overlay：如果目标目录存在，是否覆盖         true:允许覆盖           false:不允许覆盖
     */
    public static boolean moveFolder(String sourcepath,String tagerpath,boolean overlay){
        boolean bb = false;
        File file = new File(sourcepath);
        if(!file.exists()){
            System.err.println("要移动的文件/文件夹路径不存在");
            return bb;
        }else{
            if(file.isFile()){//文件
                if( fileCopy(sourcepath,tagerpath,overlay) ){
                    if( deleteFile(sourcepath) ){
                        System.err.println(sourcepath+"文件移动成功");
                        bb = true;
                    }
                }
            }else {
                if( copyDirectory(sourcepath,tagerpath,overlay) ){
                    if( deleteDirectory(sourcepath) ){
                        System.err.println(sourcepath+"文件夹移动成功");
                        bb = true;
                    }
                }
            }
            return bb;
        }
    }
}