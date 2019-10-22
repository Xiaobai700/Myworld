package com.oneworld.back.controller;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.oneworld.back.controller.easyexcel.ArticleModel;
import com.oneworld.back.controller.easyexcel.ErrorCodeModel;
import com.oneworld.back.controller.easyexcel.ExcelListener;
import com.oneworld.back.service.ErrorCodeService;
import com.oneworld.back.utils.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author zhangqiao
 * @date 2019/10/22 13:19
 */
@RestController
@RequestMapping("/errorCode")
public class ErrorCodeController {
    @Autowired
    private ErrorCodeService errorCodeService;
    /**
     * 查询文章列表
     */
    @RequiresPermissions("errorCode:list")
    @GetMapping("/listErrorCode")
    public JSONObject listErrorCode(HttpServletRequest request) {
        return errorCodeService.listErrorCode(CommonUtil.request2Json(request));
    }

    /**
     * 新建错误码
     * */
    @RequiresPermissions("errorCode:add")
    @PostMapping("/save")
    public JSONObject addErrorCode(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "errorCode,errorMsg,moduleId,remark");
        return errorCodeService.saveErrorCode(requestJson);
    }

    /**
     * 更新错误码
     * */
    @RequiresPermissions("errorCode:update")
    @PostMapping("/update")
    public JSONObject updateErrorCode(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id,errorCode,errorMsg,moduleId,remark");
        return errorCodeService.updateErrorCode(requestJson);
    }

    /**
     * 批量删除错误码
     * */
    @RequiresPermissions("errorCode:delete")
    @PostMapping("/delete")
    public JSONObject deleteErrorCode(@RequestBody Long[] deleteArr) {
        return errorCodeService.deleteErrorCodes(deleteArr);
    }

    /**
     * 导入
     * */
    @RequestMapping("/importExcel")
    public Map importErrorCodeExcel(@RequestParam("file") MultipartFile file)throws IOException {
        Map returnMap = new HashMap();
        InputStream inputStream = file.getInputStream();
        ExcelListener listener = new ExcelListener();
        try {
            ExcelReader excelReader = new ExcelReader(inputStream,null,listener);
            excelReader.read(new Sheet(1,1, ErrorCodeModel.class));
        }catch (ExcelAnalysisException e){
            e.printStackTrace();
            returnMap.put("code",1);
            returnMap.put("msg","请使用谷歌浏览器！");
            return returnMap;
        }
        List<Object> list = listener.getDatas();
        //转换数据类型,并插入到数据库
        for (int i = 0; i < list.size(); i++) {
            ErrorCodeModel errorCodeModel = (ErrorCodeModel) list.get(i);
            //插入数据库
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("errorCode",errorCodeModel.getErrorCode());
            jsonObject.put("errorMsg",errorCodeModel.getErrorMsg());
            jsonObject.put("moduleId","ams");
            jsonObject.put("remark",errorCodeModel.getRemark());
           errorCodeService.saveErrorCode(jsonObject);
        }
        returnMap.put("code",0);
        returnMap.put("msg","导入成功！");
        return returnMap;
    }

    /**
     * 导出
     * */
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + "catagory.xls");
            response.setContentType("application/msexcel;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setHeader("fileName","errorCode.xlsx");

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ErrorCodeModel.class);
            sheet.setSheetName("errorCode列表");

            List<ErrorCodeModel> errorCodeModelList = new ArrayList<>();
            //获取数据
            List<JSONObject> list = errorCodeService.selectAll();

            Iterator iterator = list.iterator();
            while (iterator.hasNext()){
                JSONObject jsonObject = (JSONObject) iterator.next();
                ErrorCodeModel errorCodeModel = new ErrorCodeModel();
                errorCodeModel.setErrorCode(jsonObject.get("errorCode").toString());
                errorCodeModel.setErrorMsg(jsonObject.get("errorMsg").toString());
                errorCodeModel.setModuleId(jsonObject.get("moduleId").toString());
                errorCodeModel.setRemark(jsonObject.get("remark").toString());
                errorCodeModelList.add(errorCodeModel);
            }
            //输出
            writer.write(errorCodeModelList, sheet);
            writer.finish();
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
