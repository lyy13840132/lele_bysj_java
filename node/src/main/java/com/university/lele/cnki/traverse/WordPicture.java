package com.university.lele.cnki.traverse;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;

import java.io.*;
import java.util.List;

/**
 * 
 * @ClassName: WordPicture 
 * @Description: 自动提取doc文档和docx文档中的图片存入文档对应文件夹，若已存在文件夹则清空重写数据（防止重复）
 * @author HuDaoquan
 * @date 2019年7月1日 下午9:44:35 
 * @version v1.0
 */
public class WordPicture {
	/**
	 * 绝对路径中提取文件名,注意FN[1]的获取要与图片处理模块中的新建文件夹路径的获取方法一致！
	 *
	 * @param path
	 * @return 文件名+新建文件夹路径
	 */
	public String[] getFileName(String path) {
		File f = new File(path);
//		String Fname = f.getName()//.replaceAll(" ", ""); // 将双空格替换为“-”
		String fn = f.getName().replaceAll(" ", ""); // ".doc"前面的空格新建文件夹会省略
		// 获取文件名
		String temp[] = fn.split("\\.");
		String fname = "\\" + temp[0];
		String pf = f.getParent();// 父路径
		String[] FN = { fn, pf + fname };// 拼接好，方便遍历用
//		System.err.println("---" + Fname);
		return FN;
	}

	/**
	 * 读取Word中的图片
	 *
	 * @param path 文件路径
	 * @return
	 */
	public void readWordPicture(String path) {
		File file = new File(path);
		// 获取相对路径
		String parentPath = file.getParent(); // 取得父路径
//		System.out.println("parentPath" + file.getName());
		String Fname = file.getName().replaceAll(" ", "");
//		System.out.println("---------" + Fname);
		String temp[] = Fname.split("\\.");
		String fileName = "\\" + temp[0];
//		System.out.println("fileName:" + fileName);
		InputStream is = null;
		HWPFDocument doc = null;
		XWPFDocument docx = null;
		POIXMLTextExtractor extractor = null;
		try {
			is = new FileInputStream(file);
			if (path.endsWith(".doc")) {
				doc = new HWPFDocument(is);
				// 文档图片内容
				PicturesTable picturesTable = doc.getPicturesTable();
				List<Picture> pictures = picturesTable.getAllPictures();
				// 创建存储图片的文件夹，已存在则清空
				File fileP = new File(parentPath + fileName);
				if (!fileP.exists()) {
					fileP.mkdirs();
				} else {
					delAllFile(parentPath + fileName); // 删除完里面所有内容
				}
				// 将图片存入本地
				int index = 0;
				for (Picture picture : pictures) {
					File F = new File(fileP, "图片" + (index++) + "." + picture.suggestFileExtension());
					OutputStream out = new FileOutputStream(F);
					picture.writeImageContent(out);
					out.close();
				}
			} else if (path.endsWith("docx")) {
				docx = new XWPFDocument(is);
				extractor = new XWPFWordExtractor(docx);
				File fileP = new File(parentPath + fileName);
				// 创建存储图片的文件夹
				if (!fileP.exists()) {
					fileP.mkdirs();
				} else {
					delAllFile(parentPath + fileName); // 删除完里面所有内容
				}
				// 文档图片内容
				List<XWPFPictureData> pictures = docx.getAllPictures();
				for (XWPFPictureData picture : pictures) {
					byte[] bytev = picture.getData();
					// 输出图片到磁盘
//					System.out.println("picture.getFileName():-----" + picture.getFileName()); // picture.getFileName():-----image1.png
					File F = new File(fileP, picture.getFileName());
//					System.err.println(F.getName());
					FileOutputStream out = new FileOutputStream(F);
					out.write(bytev);
					out.close();
				}
			} else {
				System.out.println("此文件不是word文件！");
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			try {
				if (extractor != null) {
					extractor.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 删除指定文件夹下所有文件
	 *
	 * @param path 文件夹完整绝对路径
	 */
	public void delAllFile(String path) {
		File file = new File(path);
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
		}
	}

	public static void main(String[] args) {
		String path = "D:\\我的文档\\桌面\\测试\\1500890226  李芝强  微机原理第4次实验 .doc";
		WordPicture wp = new WordPicture();
		wp.readWordPicture(path);
	}
}
