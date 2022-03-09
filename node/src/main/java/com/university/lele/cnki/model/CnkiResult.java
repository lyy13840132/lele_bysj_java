package com.university.lele.cnki.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @ClassName: Result 
 * @Description: 此类是DAO类，用来存储比较结果。结果类，不建议新增其他功能
 * @author HuDaoquan
 * @date 2019年7月1日 下午9:27:06 
 * @version v1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CnkiResult implements Comparable<CnkiResult> {
	private String username1;
	private String name1; // 文件名1
	private String username2;
	private String name2; // 被比较的文件名
	private Double sim; // 总相似度
	private Double jaccard_sim; // jaccard相似度
	private Double ConSim; // 余弦相似度
	private String crib;//
	private int isSimRate;//是否超过教师设定速率 1表示未超过，0表示超过。


	@Override
	public int compareTo(CnkiResult arg0) {
		// 这里定义排序的规则。
		return arg0.getSim().compareTo(this.getSim());// 降序
//        return this.getSim().compareTo(arg0.getSim()); //升序
	}
}
