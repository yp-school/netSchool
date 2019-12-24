package cc.mrbird.febs.basicInfo.service;


import cc.mrbird.febs.basicInfo.entity.AlicdnResource;
import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author wsq
 * @date 2019-12-20 14:36:46
 */
public interface IAlicdnResourceService extends IService<AlicdnResource> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param alicdnResource alicdnResource
     * @return IPage<AlicdnResource>
     */
    IPage<AlicdnResource> findAlicdnResources(QueryRequest request, AlicdnResource alicdnResource);

    /**
     * 查询（所有）
     *
     * @param alicdnResource alicdnResource
     * @return List<AlicdnResource>
     */
    List<AlicdnResource> findAlicdnResources(AlicdnResource alicdnResource);

    /**
     * 新增
     *
     * @param alicdnResource alicdnResource
     */
    void createAlicdnResource(AlicdnResource alicdnResource);

    /**
     * 修改
     *
     * @param alicdnResource alicdnResource
     */
    void updateAlicdnResource(AlicdnResource alicdnResource);

    /**
     * 删除
     *
     * @param alicdnResource alicdnResource
     */
    void deleteAlicdnResource(AlicdnResource alicdnResource);

    void deleteAlicdnResource2(String resourceIds);
}
