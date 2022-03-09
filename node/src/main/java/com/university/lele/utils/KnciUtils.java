package com.university.lele.utils;

import java.io.File;
import java.util.Vector;

public class KnciUtils {
    /**
     * 遍历文件夹中的图片
     *
     * @param root 遍历的跟路径
     * @return 存储有所有文本文件绝对路径的字符串数组
     */
    public Vector<String> recursionPhoto(String root, Vector<String> vecFile) {
        File file = new File(root);
        File[] subFile = file.listFiles();
        for (int i = 0; i < subFile.length; i++) {
            String fileName = subFile[i].getName();
            if (subFile[i].isDirectory()) { // 判断是文件还是文件夹
                recursionPhoto(subFile[i].getAbsolutePath(), vecFile); // 文件夹则递归
            } else if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".jpeg")
                    || fileName.endsWith(".PNG")) {
                String AP = subFile[i].getAbsolutePath();// 绝对路径
//				System.out.println(AP);
                vecFile.add(AP);
            }
        }
        return vecFile;
    }
    /**
     * 遍历文件夹中的文本文件
     *
     * @param root 遍历的跟路径
     * @return 存储有所有文本文件绝对路径的字符串数组
     */
    public Vector<String> recursionWord(String root, Vector<String> vecFile) {
        File file = new File(root);
        File[] subFile = file.listFiles();
        for (int i = 0; i < subFile.length; i++) {
            String fileName = subFile[i].getName();
            if (subFile[i].isDirectory()) { // 判断是文件还是文件夹
                recursionWord(subFile[i].getAbsolutePath(), vecFile); // 文件夹则递归
            } else if (fileName.endsWith(".doc") || fileName.endsWith(".docx") || fileName.endsWith(".txt")) {
                String AP = subFile[i].getAbsolutePath();// 绝对路径
//				System.out.println(AP);
                vecFile.add(AP);
            }
        }
        return vecFile;
    }

    /**
     * 绝对路径中提取文件名,注意FN[1]的获取要与图片处理模块中的新建文件夹路径的获取方法一致！
     */
    public String[] getFileName(String path) {
        File f = new File(path);
//		String Fname = f.getName()//.replaceAll(" ", ""); // 将双空格替换为“-”
        String fn = f.getName().replaceAll(" ", ""); // 空格必须替换，否则新建文件夹报错
        // 获取文件名
        String temp[] = fn.split("\\.");
        String fname = "\\" + temp[0];
        String pf = f.getParent();// 父路径
        String[] FN = { fn, pf + fname };// 拼接好，方便遍历用
//		System.err.println("---" + Fname);
        return FN;
    }

}
