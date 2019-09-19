package com.oneworld.back.controller.easyexcel;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangqiao
 * @date 2019/9/6 9:40
 */
public class ExcelListener extends AnalysisEventListener {
    private List<Object> datas = new ArrayList<>();

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        datas.add(o);
        //根据业务做处理
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
