package cc.mrbird.febs.basicInfo.controller;

import cc.mrbird.febs.basicInfo.entity.SchoolTeacheinfo;
import cc.mrbird.febs.basicInfo.entity.VideoLive;
import cc.mrbird.febs.basicInfo.service.IVideoLiveService;
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
 * @date 2019-12-06 08:58:29
 */
@Slf4j
@Validated
@Controller
@RequestMapping("videoLive")
public class VideoLiveController extends BaseController {

    @Autowired
    private IVideoLiveService videoLiveService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "videoLive")
    private String videoLiveIndex(){
        return FebsUtil.view("videoLive/videoLive");
    }

    @GetMapping("videoLive")
    @ResponseBody
    public FebsResponse getAllVideoLives(VideoLive videoLive) {
        return new FebsResponse().success().data(videoLiveService.findVideoLives(videoLive));
    }

    @GetMapping("videoLive/list")
    @ResponseBody
    public FebsResponse videoLiveList(QueryRequest request, VideoLive videoLive) {
        Map<String, Object> dataTable = getDataTable(this.videoLiveService.findVideoLives(request, videoLive));
        return new FebsResponse().success().data(dataTable);
    }

    @Log("videoLive")
    @GetMapping("videoLive/selectInfById/{liveId}")
    @ResponseBody
    public FebsResponse selectInfById(@NotNull(message = "{required}") @PathVariable Integer liveId)
            throws FebsException {
        try {
            VideoLive videoLive = this.videoLiveService.selectVideoLiveById(liveId);
            return new FebsResponse().success().data(videoLive);
        } catch (Exception e) {
            String message = "查询videoLive失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("新增VideoLive")
    @PostMapping("videoLive")
    @ResponseBody
    @RequiresPermissions("videoLive:add")
    public FebsResponse addVideoLive(@Valid VideoLive videoLive) throws FebsException {
        try {
            this.videoLiveService.createVideoLive(videoLive);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "新增VideoLive失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除VideoLive")
    @GetMapping("videoLive/delete")
    @ResponseBody
    @RequiresPermissions("videoLive:delete")
    public FebsResponse deleteVideoLive(VideoLive videoLive) throws FebsException {
        try {
            this.videoLiveService.deleteVideoLive(videoLive);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除VideoLive失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除NetTimetable")
    @GetMapping("videoLive/delete/{courseIds}")
    @ResponseBody
    @RequiresPermissions("videoLive:delete")
    public FebsResponse deleteVideoLives(@NotBlank(message = "{required}") @PathVariable String courseIds)
            throws FebsException {
        try {
            this.videoLiveService.deleteVideoLives(courseIds);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除videoLive失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("修改VideoLive")
    @PostMapping("videoLive/update")
    @ResponseBody
    @RequiresPermissions("videoLive:update")
    public FebsResponse updateVideoLive(VideoLive videoLive) throws FebsException {
        try {
            this.videoLiveService.updateVideoLive(videoLive);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "修改VideoLive失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("videoLive/excel")
    @ResponseBody
    @RequiresPermissions("videoLive:export")
    public void export(QueryRequest queryRequest, VideoLive videoLive, HttpServletResponse response) throws FebsException {
        try {
            List<VideoLive> videoLives = this.videoLiveService.findVideoLives(queryRequest, videoLive).getRecords();
            ExcelKit.$Export(VideoLive.class, response).downXlsx(videoLives, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
