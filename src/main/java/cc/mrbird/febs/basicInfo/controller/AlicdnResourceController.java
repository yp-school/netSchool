package cc.mrbird.febs.basicInfo.controller;

import cc.mrbird.febs.basicInfo.entity.AlicdnResource;
import cc.mrbird.febs.basicInfo.service.IAlicdnResourceService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 *  Controller
 *
 * @author wsq
 * @date 2019-12-20 14:36:46
 */
@Slf4j
@Validated
@Controller
@RequestMapping("alicdnResource")
public class AlicdnResourceController extends BaseController {

    @Autowired
    private IAlicdnResourceService alicdnResourceService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "alicdnResource")
    private String alicdnResourceIndex(){
        return FebsUtil.view("alicdnResource/alicdnResource");
    }

    @GetMapping("alicdnResource")
    @ResponseBody
    public FebsResponse getAllAlicdnResources(AlicdnResource alicdnResource) {
        return new FebsResponse().success().data(alicdnResourceService.findAlicdnResources(alicdnResource));
    }

    @GetMapping("alicdnResource/list")
    @ResponseBody
    public FebsResponse alicdnResourceList(QueryRequest request, AlicdnResource alicdnResource) {
        Map<String, Object> dataTable = getDataTable(this.alicdnResourceService.findAlicdnResources(request, alicdnResource));
        return new FebsResponse().success().data(dataTable);
    }

    @Log("新增AlicdnResource")
    @PostMapping("alicdnResource")
    @ResponseBody
    public FebsResponse addAlicdnResource(@Valid AlicdnResource alicdnResource) throws FebsException {
        try {
            this.alicdnResourceService.createAlicdnResource(alicdnResource);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "新增AlicdnResource失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除AlicdnResource")
    @GetMapping("alicdnResource/delete")
    @ResponseBody
    public FebsResponse deleteAlicdnResource(AlicdnResource alicdnResource) throws FebsException {
        try {
            this.alicdnResourceService.deleteAlicdnResource(alicdnResource);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除AlicdnResource失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("修改AlicdnResource")
    @PostMapping("alicdnResource/update")
    @ResponseBody
    public FebsResponse updateAlicdnResource(AlicdnResource alicdnResource) throws FebsException {
        try {
            this.alicdnResourceService.updateAlicdnResource(alicdnResource);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "修改AlicdnResource失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("alicdnResource/excel")
    @ResponseBody
    public void export(QueryRequest queryRequest, AlicdnResource alicdnResource, HttpServletResponse response) throws FebsException {
        try {
            List<AlicdnResource> alicdnResources = this.alicdnResourceService.findAlicdnResources(queryRequest, alicdnResource).getRecords();
            ExcelKit.$Export(AlicdnResource.class, response).downXlsx(alicdnResources, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
