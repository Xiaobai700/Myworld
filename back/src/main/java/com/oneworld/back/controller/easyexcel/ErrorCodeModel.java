package com.oneworld.back.controller.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangqiao
 * @date 2019/10/22 15:38
 */
@Setter
@Getter
public class ErrorCodeModel extends BaseRowModel {
    @ExcelProperty(value = "错误码", index = 0)
    private String errorCode;

    @ExcelProperty(value = "错误信息", index = 1)
    private String errorMsg;

    @ExcelProperty(value = "备注", index = 2)
    private String remark;

    @ExcelProperty(value = "所属模块", index = 3)
    private String moduleId;
}
