package cc.mrbird.febs.basicInfo.service.impl;

import cc.mrbird.febs.basicInfo.config.Constant;
import cc.mrbird.febs.basicInfo.entity.AlicdnResource;
import cc.mrbird.febs.basicInfo.entity.NoticeAnnouncement;
import cc.mrbird.febs.basicInfo.entity.PictureNews;
import cc.mrbird.febs.basicInfo.mapper.AlicdnResourceMapper;
import cc.mrbird.febs.basicInfo.mapper.PictureNewsMapper;
import cc.mrbird.febs.basicInfo.service.IAlicdnResourceService;
import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Arrays;
import java.util.List;

/**
 *  Service实现
 *
 * @author wsq
 * @date 2019-12-20 14:36:46
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AlicdnResourceServiceImpl extends ServiceImpl<AlicdnResourceMapper, AlicdnResource> implements IAlicdnResourceService {

    @Autowired
    private AlicdnResourceMapper alicdnResourceMapper;
    @Autowired
    private PictureNewsMapper pictureNewsMapper;

    @Override
    public IPage<AlicdnResource> findAlicdnResources(QueryRequest request, AlicdnResource alicdnResource) {
        LambdaQueryWrapper<AlicdnResource> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        if (StringUtils.isNotEmpty(alicdnResource.getTitle())) {
            queryWrapper.like(AlicdnResource::getTitle, alicdnResource.getTitle());
        }
        queryWrapper.orderByDesc(AlicdnResource::getDateTime);
        Page<AlicdnResource> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<AlicdnResource> findAlicdnResources(AlicdnResource alicdnResource) {
	    List<AlicdnResource> alicdnResourceList = this.baseMapper.findAlicdnResources(alicdnResource);
		return alicdnResourceList;
    }

    @Override
    @Transactional
    public void createAlicdnResource(AlicdnResource alicdnResource) {
        PictureNews pictureNews = pictureNewsMapper.selectById(alicdnResource.getLink());
        String realLink = Constant.linkUrl + alicdnResource.getLink();
        alicdnResource.setLink(realLink);
        //link实际存的是pictureId，根据这个id查找到图片新闻对应的图片,并存储
        alicdnResource.setImage(pictureNews.getPictureUrl());
        this.save(alicdnResource);
    }

    @Override
    @Transactional
    public void updateAlicdnResource(AlicdnResource alicdnResource) {
        PictureNews pictureNews = pictureNewsMapper.selectById(alicdnResource.getLink());
        String realLink = Constant.linkUrl + alicdnResource.getLink();
        alicdnResource.setLink(realLink);
        //link实际存的是pictureId，根据这个id查找到图片新闻对应的图片,并存储
        alicdnResource.setImage(pictureNews.getPictureUrl());
        this.saveOrUpdate(alicdnResource);
    }

    @Override
    @Transactional
    public void deleteAlicdnResource(AlicdnResource alicdnResource) {
        LambdaQueryWrapper<AlicdnResource> wapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wapper);
	}

    @Override
    public void deleteAlicdnResource2(String resourceIds) {
        List<String> list = Arrays.asList(resourceIds.split(StringPool.COMMA));
        baseMapper.deleteBatchIds(list);
    }
}
