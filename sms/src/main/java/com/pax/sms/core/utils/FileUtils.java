package com.pax.sms.core.utils;

import com.pax.sms.core.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by zhuxl@paxsz.com  on 2017/7/7.
 */
public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 压缩文件夹
     *
     * @param sourceDIR   文件夹名称（包含路径）
     * @param zipFilePath 生成zip文件名
     * @author liuxiangwei
     */
    public static void zipDir(String sourceDIR, String zipFilePath) {
        FileOutputStream target = null;
        ZipOutputStream out = null;
        BufferedOutputStream objBufferedOutputStream = null;
        try {
            target = new FileOutputStream(zipFilePath);
            objBufferedOutputStream = new BufferedOutputStream(target);
            out = new ZipOutputStream(objBufferedOutputStream);
            int BUFFER_SIZE = 1024;
            byte buff[] = new byte[BUFFER_SIZE];
            File dir = new File(sourceDIR);
            if (!dir.isDirectory()) {
                throw new IllegalArgumentException(sourceDIR + " is not a directory!");
            }
            File files[] = dir.listFiles();

            for (int i = 0; i < files.length; i++) {
                FileInputStream fi = new FileInputStream(files[i]);
                BufferedInputStream origin = new BufferedInputStream(fi);
                ZipEntry entry = new ZipEntry(files[i].getName());
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(buff)) != -1) {
                    out.write(buff, 0, count);
                }
                origin.close();
                fi.close();
            }

        } catch (IOException e) {
            logger.error("文件或目录不存在", e);
            throw new BusinessException("文件或目录不存在");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (target != null) {
                    target.close();
                }
                if (objBufferedOutputStream != null) {
                	objBufferedOutputStream.close();
                }
            } catch (Exception e) {
            	logger.error("打包失败");
                throw new BusinessException("打包失败");
            }
        }
    }

    public static boolean deleteDir(String dir) {
        //如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            logger.error("删除目录失败" + dir + "目录不存在!");
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            //删除子目录
            else {
                flag = deleteDir(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            logger.error("删除目录失败");
            return false;
        }

        //删除当前目录
        if (dirFile.delete()) {
            logger.info("删除目录" + dir + "成功!");
            return true;
        } else {
            logger.error("删除目录" + dir + "失败!");
            return false;
        }
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            Boolean succeedDelete = file.delete();
            if (succeedDelete) {
                logger.info("删除单个文件" + fileName + "成功!");
                return true;
            } else {
                logger.error("删除单个文件" + fileName + "失败!");
                return true;
            }
        } else {
            logger.error("删除单个文件" + fileName + "失败!");
            return false;
        }
    }

    /**
     * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
     *
     * @param filename
     * @return
     * @throws IOException
     */


    public static StringBuffer readAsString(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(filename), "UTF-8"));

            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            return sb;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException("读取文件出错");
        }
    }

    public static void main(String[] args) {
        String template = FileUtils.readAsString("template/email_template.html").toString();
        String regex = "\\{\\{}}";
        template=template.replaceFirst("\\{\\{}}", "111");
        System.out.println(template);
        System.out.println(readAsString("template/email_template.html").toString().replaceFirst("\\{\\{}}","xxxx"));

    }
}