package cc.mrbird.febs.basicInfo.controller;

import cc.mrbird.febs.basicInfo.entity.NoticeAnnouncement;
import cc.mrbird.febs.basicInfo.entity.PictureNews;
import cc.mrbird.febs.basicInfo.service.IPictureNewsService;
import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import com.alibaba.fastjson.JSONObject;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Controller
 *
 * @author wsq
 * @date 2019-12-03 11:51:17
 */
@Slf4j
@Validated
@Controller
@RequestMapping("pictureNews")
public class PictureNewsController extends BaseController {

    @Autowired
    private IPictureNewsService pictureNewsService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "pictureNews")
    private String pictureNewsIndex(){
        return FebsUtil.view("pictureNews/pictureNews");
    }

    @GetMapping("pictureNews")
    @ResponseBody
    public FebsResponse getAllPictureNewss(PictureNews pictureNews) {
        return new FebsResponse().success().data(pictureNewsService.findPictureNewss(pictureNews));
    }

    @GetMapping("pictureNews/list")
    @ResponseBody
    public FebsResponse pictureNewsList(QueryRequest request, PictureNews pictureNews) {
        Map<String, Object> dataTable = getDataTable(this.pictureNewsService.findPictureNewss(request, pictureNews));
        return new FebsResponse().success().data(dataTable);
    }

    @Log("pictureNews")
    @GetMapping("pictureNews/selectInfById/{pictureId}")
    @ResponseBody
    public FebsResponse selectInfById(@NotNull(message = "{required}") @PathVariable Integer pictureId)
            throws FebsException {
        try {
            PictureNews pictureNews = this.pictureNewsService.selectPictureNewsById(pictureId);
            return new FebsResponse().success().data(pictureNews);
        } catch (Exception e) {
            String message = "查询pictureNews失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("新增PictureNews")
    @PostMapping("pictureNews")
    @ResponseBody
    @RequiresPermissions("pictureNews:add")
    public FebsResponse addPictureNews(@Valid PictureNews pictureNews) throws FebsException {
        try {
            this.pictureNewsService.createPictureNews(pictureNews);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "新增PictureNews失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除PictureNews")
    @GetMapping("pictureNews/delete")
    @ResponseBody
    @RequiresPermissions("pictureNews:delete")
    public FebsResponse deletePictureNews(PictureNews pictureNews) throws FebsException {
        try {
            this.pictureNewsService.deletePictureNews(pictureNews);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除PictureNews失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除pictureNews")
    @GetMapping("pictureNews/delete/{pictureIds}")
    @ResponseBody
    @RequiresPermissions("pictureNews:delete")
    public FebsResponse deleteClassInfo(@NotBlank(message = "{required}") @PathVariable String pictureIds)
            throws FebsException {
        try {
            this.pictureNewsService.deletePictureNewsInfo(pictureIds);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除schoolTeacherinfo失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("修改PictureNews")
    @PostMapping("pictureNews/update")
    @ResponseBody
    @RequiresPermissions("pictureNews:update")
    public FebsResponse updatePictureNews(PictureNews pictureNews) throws FebsException {
        try {
            this.pictureNewsService.updatePictureNews(pictureNews);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "修改PictureNews失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("pictureNews/excel")
    @ResponseBody
    @RequiresPermissions("pictureNews:export")
    public void export(QueryRequest queryRequest, PictureNews pictureNews, HttpServletResponse response) throws FebsException {
        try {
            List<PictureNews> pictureNewss = this.pictureNewsService.findPictureNewss(queryRequest, pictureNews).getRecords();
            ExcelKit.$Export(PictureNews.class, response).downXlsx(pictureNewss, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @RequestMapping(value="upload",method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(HttpServletRequest request, @Param("file") MultipartFile file) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String res = sdf.format(new Date());
        //服务器上使用
         String rootPath =request.getServletContext().getRealPath("");//target的目录
        //本地使用
        //String rootPath ="E:\\";
        //原始名称
        String originalFilename = file.getOriginalFilename();
        //新的文件名称
        String newFileName = res+originalFilename.substring(originalFilename.lastIndexOf("."));
        //新文件
        File newFile = new File(rootPath+ File.separator+newFileName);
        //判断目标文件所在的目录是否存在
        if(!newFile.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            newFile.getParentFile().mkdirs();
        }
        System.out.println(newFile);
        //将内存中的数据写入磁盘
        file.transferTo(newFile);
        //完整的url
        String fileUrl =  newFileName;
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> map2 = new HashMap<String,Object>();
        map.put("code",0);//0表示成功，1失败
        map.put("msg","上传成功");//提示消息
        map.put("data",map2);
        map2.put("src",fileUrl);//图片url
        map2.put("title",newFileName);//图片名称，这个会显示在输入框里
        String result = new JSONObject(map).toString();
        return result;
    }
}
