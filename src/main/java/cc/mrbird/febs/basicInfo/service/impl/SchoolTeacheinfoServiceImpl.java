package cc.mrbird.febs.basicInfo.service.impl;

import cc.mrbird.febs.basicInfo.entity.News;
import cc.mrbird.febs.basicInfo.entity.SchoolTeacheinfo;
import cc.mrbird.febs.basicInfo.mapper.SchoolTeacheinfoMapper;
import cc.mrbird.febs.basicInfo.service.ISchoolTeacheinfoService;
import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import org.apache.commons.lang3.StringUtils;
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
 * @date 2019-12-02 11:44:57
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SchoolTeacheinfoServiceImpl extends ServiceImpl<SchoolTeacheinfoMapper, SchoolTeacheinfo> implements ISchoolTeacheinfoService {

    @Autowired
    private SchoolTeacheinfoMapper schoolTeacheinfoMapper;

    @Override
    public IPage<SchoolTeacheinfo> findSchoolTeacheinfos(QueryRequest request, SchoolTeacheinfo schoolTeacheinfo) {
        LambdaQueryWrapper<SchoolTeacheinfo> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        if (StringUtils.isNotBlank(schoolTeacheinfo.getTeacherName())) {
            queryWrapper.like(SchoolTeacheinfo::getTeacherName, schoolTeacheinfo.getTeacherName());
        }
        Page<SchoolTeacheinfo> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<SchoolTeacheinfo> findSchoolTeacheinfos(SchoolTeacheinfo schoolTeacheinfo) {
	    LambdaQueryWrapper<SchoolTeacheinfo> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createSchoolTeacheinfo(SchoolTeacheinfo schoolTeacheinfo) {
        this.save(schoolTeacheinfo);
    }

    @Override
    @Transactional
    public void updateSchoolTeacheinfo(SchoolTeacheinfo schoolTeacheinfo) {
        this.saveOrUpdate(schoolTeacheinfo);
    }

    @Override
    @Transactional
    public void deleteSchoolTeacheinfo(SchoolTeacheinfo schoolTeacheinfo) {
        LambdaQueryWrapper<SchoolTeacheinfo> wapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wapper);
	}

    @Override
    public void deleteTeacherInfo(String teacherIds) {
        List<String> list = Arrays.asList(teacherIds.split(StringPool.COMMA));
        baseMapper.deleteBatchIds(list);
    }
}
