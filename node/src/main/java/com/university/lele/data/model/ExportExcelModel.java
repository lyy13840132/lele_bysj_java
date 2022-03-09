package com.university.lele.data.model;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExportExcelModel {
    //设置excel表头名称
    @ExcelProperty(value = "学号",index = 0)
    private String username;
    @ExcelProperty(value = "姓名",index = 1)
    private String name;
    @ExcelProperty(value = "班级",index = 2)
    private String grade;
    @ExcelProperty(value = "互评成绩",index = 3)
    private String avgStar;
    @ExcelProperty(value = "正常签到",index = 4)
    private String normalStatusAll;
    @ExcelProperty(value = "迟到签到",index = 5)
    private String lateStatusAll;
    @ExcelProperty(value = "外勤签到",index = 6)
    private String normalFieldAll;
    @ExcelProperty(value = "外勤迟到",index = 7)
    private String lateFieldAll;
    @ExcelProperty(value = "报告是否有相似度较高的记录",index = 8)
    private  String isHighSim;
    @ExcelProperty(value = "最终成绩",index = 9)
    private String mark;
}
