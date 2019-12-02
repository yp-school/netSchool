package cc.mrbird.febs.basicInfo.controller;

import cc.mrbird.febs.basicInfo.entity.SchoolTeacheinfo;
import cc.mrbird.febs.basicInfo.service.ISchoolTeacheinfoService;
import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 *  Controller
 *
 * @author thy
 * @date 2019-12-02 11:44:57
 */
@Slf4j
@Validated
@Controller
@RequestMapping("schoolTeacherinfo")
public class SchoolTeacheinfoController extends BaseController {

    @Autowired
    private ISchoolTeacheinfoService schoolTeacheinfoService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "schoolTeacherinfo")
    private String schoolTeacheinfoIndex(){
        return FebsUtil.view("schoolTeacherinfo/schoolTeacherinfo");
    }

    @GetMapping("schoolTeacherinfo")
    @ResponseBody
    @RequiresPermissions("schoolTeacherinfo:view")
    public FebsResponse getAllSchoolTeacheinfos(SchoolTeacheinfo schoolTeacheinfo) {
        return new FebsResponse().success().data(schoolTeacheinfoService.findSchoolTeacheinfos(schoolTeacheinfo));
    }

    @GetMapping("schoolTeacherinfo/list")
    @ResponseBody
    @RequiresPermissions("schoolTeacherinfo:view")
    public FebsResponse schoolTeacheinfoList(QueryRequest request, SchoolTeacheinfo schoolTeacheinfo) {
        Map<String, Object> dataTable = getDataTable(this.schoolTeacheinfoService.findSchoolTeacheinfos(request, schoolTeacheinfo));
        return new FebsResponse().success().data(dataTable);
    }

    @Log("新增schoolTeacherinfo")
    @PostMapping("schoolTeacherinfo")
    @ResponseBody
    @RequiresPermissions("schoolTeacherinfo:add")
    public FebsResponse addSchoolTeacheinfo(@Valid SchoolTeacheinfo schoolTeacheinfo) throws FebsException {
        try {
            this.schoolTeacheinfoService.createSchoolTeacheinfo(schoolTeacheinfo);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "新增SchoolTeacherinfo失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除SchoolTeacheinfo")
    @GetMapping("schoolTeacherinfo/delete")
    @ResponseBody
    @RequiresPermissions("schoolTeacherinfo:delete")
    public FebsResponse deleteSchoolTeacheinfo(SchoolTeacheinfo schoolTeacheinfo) throws FebsException {
        try {
            this.schoolTeacheinfoService.deleteSchoolTeacheinfo(schoolTeacheinfo);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除SchoolTeacherinfo失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除SchoolTeacheinfo")
    @GetMapping("schoolTeacherinfo/delete/{teacherIds}")
    @ResponseBody
    @RequiresPermissions("schoolTeacherinfo:delete")
    public FebsResponse deleteClassInfo(@NotBlank(message = "{required}") @PathVariable String teacherIds)
            throws FebsException {
        try {
            this.schoolTeacheinfoService.deleteTeacherInfo(teacherIds);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除schoolTeacherinfo失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("修改SchoolTeacherinfo")
    @PostMapping("schoolTeacherinfo/update")
    @ResponseBody
    @RequiresPermissions("schoolTeacherinfo:update")
    public FebsResponse updateSchoolTeacheinfo(SchoolTeacheinfo schoolTeacheinfo) throws FebsException {
        try {
            this.schoolTeacheinfoService.updateSchoolTeacheinfo(schoolTeacheinfo);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "修改SchoolTeacheinfo失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("schoolTeacherinfo/excel")
    @ResponseBody
    @RequiresPermissions("schoolTeacherinfo:export")
    public void export(QueryRequest queryRequest, SchoolTeacheinfo schoolTeacheinfo, HttpServletResponse response) throws FebsException {
        try {
            List<SchoolTeacheinfo> schoolTeacheinfos = this.schoolTeacheinfoService.findSchoolTeacheinfos(queryRequest, schoolTeacheinfo).getRecords();
            ExcelKit.$Export(SchoolTeacheinfo.class, response).downXlsx(schoolTeacheinfos, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
