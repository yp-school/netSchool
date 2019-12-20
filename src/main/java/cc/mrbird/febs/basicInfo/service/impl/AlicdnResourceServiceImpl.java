package cc.mrbird.febs.basicInfo.service.impl;

import cc.mrbird.febs.basicInfo.entity.AlicdnResource;
import cc.mrbird.febs.basicInfo.mapper.AlicdnResourceMapper;
import cc.mrbird.febs.basicInfo.service.IAlicdnResourceService;
import cc.mrbird.febs.common.entity.QueryRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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

    @Override
    public IPage<AlicdnResource> findAlicdnResources(QueryRequest request, AlicdnResource alicdnResource) {
        LambdaQueryWrapper<AlicdnResource> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<AlicdnResource> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<AlicdnResource> findAlicdnResources(AlicdnResource alicdnResource) {
	    LambdaQueryWrapper<AlicdnResource> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createAlicdnResource(AlicdnResource alicdnResource) {
        this.save(alicdnResource);
    }

    @Override
    @Transactional
    public void updateAlicdnResource(AlicdnResource alicdnResource) {
        this.saveOrUpdate(alicdnResource);
    }

    @Override
    @Transactional
    public void deleteAlicdnResource(AlicdnResource alicdnResource) {
        LambdaQueryWrapper<AlicdnResource> wapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wapper);
	}
}
