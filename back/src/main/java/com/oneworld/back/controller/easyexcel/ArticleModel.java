package com.oneworld.back.controller.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangqiao
 * @date 2019/9/18 16:48
 */
@Getter
@Setter
public class ArticleModel extends BaseRowModel {
    @ExcelProperty(value = "标题", index = 0)
    private String content;

}
