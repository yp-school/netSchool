package cc.mrbird.febs.basicInfo.controller;

import cc.mrbird.febs.basicInfo.entity.NetTimetable;
import cc.mrbird.febs.basicInfo.entity.SchoolTeacheinfo;
import cc.mrbird.febs.basicInfo.service.INetTimetableService;
import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
 * @author wsq
 * @date 2019-12-04 16:49:28
 */
@Slf4j
@Validated
@Controller
@RequestMapping("netTimetable")
public class NetTimetableController extends BaseController {

    @Autowired
    private INetTimetableService netTimetableService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "netTimetable")
    private String netTimetableIndex(){
        return FebsUtil.view("netTimetable/netTimetable");
    }

    @GetMapping("netTimetable")
    @ResponseBody
    @RequiresPermissions("netTimetable:view")
    public FebsResponse getAllNetTimetables(NetTimetable netTimetable) {
        return new FebsResponse().success().data(netTimetableService.findNetTimetables(netTimetable));
    }

    @GetMapping("netTimetable/list")
    @ResponseBody
    @RequiresPermissions("netTimetable:view")
    public FebsResponse netTimetableList(QueryRequest request, NetTimetable netTimetable) {
        Map<String, Object> dataTable = getDataTable(this.netTimetableService.findNetTimetables(request, netTimetable));
        return new FebsResponse().success().data(dataTable);
    }

    /**
     * 教师下拉列表
     * @param request
     * @param netTimetable
     * @return
     */
    @GetMapping("netTimetable/web/list")
    @ResponseBody
    @RequiresPermissions("netTimetable:view")
    public FebsResponse netTimetableWebList(QueryRequest request, NetTimetable netTimetable) {

        IPage<NetTimetable> netTimetableIPage = this.netTimetableService.selectNetTimetableList(request, netTimetable);
        Map<String, Object> dataTable = getDataTable(netTimetableIPage);
        return new FebsResponse().success().data(dataTable);
    }

    @Log("新增NetTimetable")
    @PostMapping("netTimetable")
    @ResponseBody
    @RequiresPermissions("netTimetable:add")
    public FebsResponse addNetTimetable(@Valid NetTimetable netTimetable) throws FebsException {
        try {
            this.netTimetableService.createNetTimetable(netTimetable);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "新增NetTimetable失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除NetTimetable")
    @GetMapping("netTimetable/delete")
    @ResponseBody
    @RequiresPermissions("netTimetable:delete")
    public FebsResponse deleteNetTimetable(NetTimetable netTimetable) throws FebsException {
        try {
            this.netTimetableService.deleteNetTimetable(netTimetable);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除NetTimetable失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除NetTimetable")
    @GetMapping("netTimetable/delete/{courseIds}")
    @ResponseBody
    @RequiresPermissions("netTimetable:delete")
    public FebsResponse deleteNetTimetables(@NotBlank(message = "{required}") @PathVariable String courseIds)
            throws FebsException {
        try {
            this.netTimetableService.deleteNetTimetables(courseIds);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除netTimetable失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("修改NetTimetable")
    @PostMapping("netTimetable/update")
    @ResponseBody
    @RequiresPermissions("netTimetable:update")
    public FebsResponse updateNetTimetable(NetTimetable netTimetable) throws FebsException {
        try {
            this.netTimetableService.updateNetTimetable(netTimetable);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "修改NetTimetable失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("netTimetable/excel")
    @ResponseBody
    @RequiresPermissions("netTimetable:export")
    public void export(QueryRequest queryRequest, NetTimetable netTimetable, HttpServletResponse response) throws FebsException {
        try {
            List<NetTimetable> netTimetables = this.netTimetableService.findNetTimetables(queryRequest, netTimetable).getRecords();
            ExcelKit.$Export(NetTimetable.class, response).downXlsx(netTimetables, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
