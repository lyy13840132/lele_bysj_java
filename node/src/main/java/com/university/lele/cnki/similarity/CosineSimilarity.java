package com.university.lele.cnki.similarity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 
 * @ClassName: CosineSimilarity 
 * @Description: 余弦相似度算法计算两个字符串相似度
 * @author HuDaoquan
 * @date 2019年7月1日 下午9:40:56 
 * @version v1.0
 */
public class CosineSimilarity {
	Map<String, int[]> vectorMap = new HashMap<String, int[]>();
	int[] tempArray = null;

	/**
	 * 词频统计
	 *
	 * @param content1
	 * @param content2
	 */
	public void wordCount(List<String> content1, List<String> content2) {
		vectorMap.clear();
		for (int i = 0; i < content1.size(); i++) {
			// content1.get(i)分好词后的第i个词
			if (vectorMap.containsKey(content1.get(i))) { // 判断第i个词有没有在vectorMap中，如果在则在加一 实现统计词频
				vectorMap.get(content1.get(i))[0]++; // 会get到一个String类型
			} else {
				tempArray = new int[2];
				tempArray[0] = 1;
				tempArray[1] = 0;
				vectorMap.put(content1.get(i), tempArray);
			}
		}
		for (int i = 0; i < content2.size(); i++) {
			if (vectorMap.containsKey(content2.get(i))) {
				vectorMap.get(content2.get(i))[1]++;
			} else {
				tempArray = new int[2];
				tempArray[0] = 0;
				tempArray[1] = 1;
				vectorMap.put(content2.get(i), tempArray);
			}
		}
	}

	/**
	 * 求余弦相似度
	 *
	 * @param content1
	 * @param content2
	 * @return double型结果
	 */
	public double sim(List<String> content1, List<String> content2) {
		wordCount(content1, content2);
		double result = 0;
		if (sqrtMulti(vectorMap) == 0) {
			return result;
		}
		result = pointMulti(vectorMap) / sqrtMulti(vectorMap);
		return result;
	}

	/**
	 * 求分母
	 *
	 * @param paramMap
	 * @return
	 */
	private double sqrtMulti(Map<String, int[]> paramMap) {
		double result = 0;
		double result1 = 0;
		result1 = squares(paramMap); // 分母上的平方和
		result = Math.sqrt(result1);
		return result;
	}

	/**
	 * 求平方和
	 * @param paramMap
	 * @return
	 */
	private double squares(Map<String, int[]> paramMap) {
		double result1 = 0;
		double result2 = 0;
		Set<String> keySet = paramMap.keySet();
		for (String key : keySet) {
			int temp[] = paramMap.get(key);
			result1 += (temp[0] * temp[0]);
			result2 += (temp[1] * temp[1]);
		}
		return result1 * result2;
	}

	/**
	 * 点乘法求分子
	 * @param paramMap
	 * @return
	 */
	private double pointMulti(Map<String, int[]> paramMap) {
		double result = 0;
		for (String key : paramMap.keySet()) {
			int temp[] = paramMap.get(key);
			result += (temp[0] * temp[1]);
		}
//	        System.err.println("result-----------"+result);
		return result;
	}

}