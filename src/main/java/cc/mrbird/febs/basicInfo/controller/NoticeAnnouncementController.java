package cc.mrbird.febs.basicInfo.controller;

import cc.mrbird.febs.basicInfo.entity.NoticeAnnouncement;
import cc.mrbird.febs.basicInfo.service.INoticeAnnouncementService;
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
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 *  Controller
 *
 * @author wsq
 * @date 2019-12-04 09:11:19
 */
@Slf4j
@Validated
@Controller
@RequestMapping("notice")
public class NoticeAnnouncementController extends BaseController {

    @Autowired
    private INoticeAnnouncementService noticeAnnouncementService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "noticeAnnouncement")
    private String noticeAnnouncementIndex(){
        return FebsUtil.view("noticeAnnouncement/noticeAnnouncement");
    }

    @GetMapping("noticeAnnouncement")
    @ResponseBody
    public FebsResponse getAllNoticeAnnouncements(NoticeAnnouncement noticeAnnouncement) {
        return new FebsResponse().success().data(noticeAnnouncementService.findNoticeAnnouncements(noticeAnnouncement));
    }

    @GetMapping("noticeAnnouncement/list")
    @ResponseBody
    public FebsResponse noticeAnnouncementList(QueryRequest request, NoticeAnnouncement noticeAnnouncement) {
        Map<String, Object> dataTable = getDataTable(this.noticeAnnouncementService.findNoticeAnnouncements(request, noticeAnnouncement));
        return new FebsResponse().success().data(dataTable);
    }

    @Log("noticeAnnouncement")
    @GetMapping("noticeAnnouncement/selectInfById/{noticeId}")
    @ResponseBody
    public FebsResponse selectInfById(@NotNull(message = "{required}") @PathVariable Integer noticeId)
            throws FebsException {
        try {
            NoticeAnnouncement noticeAnnouncement = this.noticeAnnouncementService.selectNoticeById(noticeId);
            return new FebsResponse().success().data(noticeAnnouncement);
        } catch (Exception e) {
            String message = "查询noticeAnnouncement失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("新增NoticeAnnouncement")
    @PostMapping("noticeAnnouncement")
    @ResponseBody
    @RequiresPermissions("noticeAnnouncement:add")
    public FebsResponse addNoticeAnnouncement(@Valid NoticeAnnouncement noticeAnnouncement) throws FebsException {
        try {
            this.noticeAnnouncementService.createNoticeAnnouncement(noticeAnnouncement);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "新增NoticeAnnouncement失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除NoticeAnnouncement")
    @GetMapping("noticeAnnouncement/delete")
    @ResponseBody
    @RequiresPermissions("noticeAnnouncement:delete")
    public FebsResponse deleteNoticeAnnouncement(NoticeAnnouncement noticeAnnouncement) throws FebsException {
        try {
            this.noticeAnnouncementService.deleteNoticeAnnouncement(noticeAnnouncement);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除NoticeAnnouncement失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除NoticeAnnouncement")
    @GetMapping("noticeAnnouncement/delete/{noticeIds}")
    @ResponseBody
    @RequiresPermissions("noticeAnnouncement:delete")
    public FebsResponse deleteNotice(@NotBlank(message = "{required}") @PathVariable String noticeIds)
            throws FebsException {
        try {
            this.noticeAnnouncementService.deleteNoticeAnnouncementInfo(noticeIds);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除NoticeAnnouncement失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("修改NoticeAnnouncement")
    @PostMapping("noticeAnnouncement/update")
    @ResponseBody
    @RequiresPermissions("noticeAnnouncement:update")
    public FebsResponse updateNoticeAnnouncement(NoticeAnnouncement noticeAnnouncement) throws FebsException {
        try {
            this.noticeAnnouncementService.updateNoticeAnnouncement(noticeAnnouncement);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "修改NoticeAnnouncement失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("noticeAnnouncement/excel")
    @ResponseBody
    @RequiresPermissions("noticeAnnouncement:export")
    public void export(QueryRequest queryRequest, NoticeAnnouncement noticeAnnouncement, HttpServletResponse response) throws FebsException {
        try {
            List<NoticeAnnouncement> noticeAnnouncements = this.noticeAnnouncementService.findNoticeAnnouncements(queryRequest, noticeAnnouncement).getRecords();
            ExcelKit.$Export(NoticeAnnouncement.class, response).downXlsx(noticeAnnouncements, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
