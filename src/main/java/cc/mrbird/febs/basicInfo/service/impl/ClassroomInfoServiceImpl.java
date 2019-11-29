package cc.mrbird.febs.basicInfo.service.impl;

import cc.mrbird.febs.basicInfo.entity.ClassroomInfo;
import cc.mrbird.febs.basicInfo.entity.SchoolTimetable;
import cc.mrbird.febs.basicInfo.mapper.ClassroomInfoMapper;
import cc.mrbird.febs.basicInfo.service.IClassroomInfoService;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;

import cc.mrbird.febs.common.utils.LiveRadioReqUtil;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.system.entity.Dept;
import cc.mrbird.febs.system.service.IUserDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 *  Service实现
 *
 * @author psy
 * @date 2019-08-23 15:45:24
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ClassroomInfoServiceImpl extends ServiceImpl<ClassroomInfoMapper, ClassroomInfo> implements IClassroomInfoService {

    @Autowired
    private ClassroomInfoMapper classroomInfoMapper;
    @Autowired
    private IUserDeptService userDeptService;


    @Override
    public IPage<ClassroomInfo> findClassroomInfos(QueryRequest request, ClassroomInfo classroomInfo) {
        Page<ClassroomInfo> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "id", FebsConstant.ORDER_ASC, false);
        // 获取用户所属部门
        List<Dept> userDepts = userDeptService.getDeptByUserId(classroomInfo.getUserId());
        //一开始进入教室管理时,查询出当前用户所属部门的学校,如果是条件查询，则需要进入else
        if(classroomInfo.getDept() == null || "null".equals(classroomInfo.getDept()) || "111".equals(classroomInfo.getDept()) || "222".equals(classroomInfo.getDept())){
        //随意定义一个部门表不可能会出现的deptGrade作比较
        Long deptGrade = 5L;
        //一个人可能有多个部门，选取权限最大的部门id
        for(Dept dept:userDepts){
            Long compareId = dept.getDeptGrade();
            if(compareId < deptGrade){
                deptGrade = compareId;
            }
            //根据情况条件查询
            selectDeptId(dept,classroomInfo);
        }

        }else{
            //根据部门id查询出相应数据,例如deptGrade
            Dept tDept = this.baseMapper.selectDeptById(classroomInfo.getDept());
            //根据情况条件查询
            selectDeptId(tDept,classroomInfo);
        }
        return this.baseMapper.selectClassroomInfos(page, classroomInfo);
    }

    @Override
    public List<ClassroomInfo> findClassroomInfos(ClassroomInfo classroomInfo) {
	    LambdaQueryWrapper<ClassroomInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (classroomInfo.getSchoolId() != null) {
            queryWrapper.eq(ClassroomInfo::getSchoolId, classroomInfo.getSchoolId());
        }
        if (StringUtils.isNotBlank(classroomInfo.getSubject())) {
            queryWrapper.eq(ClassroomInfo::getSubject, classroomInfo.getSubject());
        }
        if (StringUtils.isNotBlank(classroomInfo.getGrade())) {
            queryWrapper.eq(ClassroomInfo::getGrade, classroomInfo.getGrade());
        }
        if (classroomInfo.getState() != null) {
            queryWrapper.eq(ClassroomInfo::getState, classroomInfo.getState());
        }
        if (classroomInfo.getId() != null) {
            queryWrapper.eq(ClassroomInfo::getId, classroomInfo.getId());
        }
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createClassroomInfo(ClassroomInfo classroomInfo) {
        String[] radioUrls = LiveRadioReqUtil.getNewLiveRadioUrl();
        if(radioUrls != null && radioUrls.length >= 2){
            classroomInfo.setUrl(radioUrls[0]);
            classroomInfo.setPlayUrl(radioUrls[1]);
        }
        this.save(classroomInfo);
    }

    @Override
    @Transactional
    public void updateClassroomInfo(ClassroomInfo classroomInfo) {
        this.saveOrUpdate(classroomInfo);
    }

    @Override
    @Transactional
    public void deleteClassroomInfo(String classroomIds) {
    	List<String> list = Arrays.asList(classroomIds.split(StringPool.COMMA));
        for (String id:list
             ) {
            ClassroomInfo info = this.getById(id);
            if(StringUtils.isNotEmpty(info.getUrl())) {
                LiveRadioReqUtil.deleteLiveRadioUrl(info.getUrl());
            }
        }
        baseMapper.deleteBatchIds(list);
	}
    
	@Override
	public void deleteClassroomInfosByschoolId(List<String> schoolIds) {
		if(schoolIds.size() > 0)
			this.baseMapper.delete(new QueryWrapper<ClassroomInfo>().lambda().in(ClassroomInfo::getSchoolId, schoolIds));
	}

    public List<ClassroomInfo> getClassroomInfoByCityCountry(String provinceId,String cityDeptId,String countryDeptId){
        Map<String,String> map = new HashMap<String,String>();
        map.put("provinceDeptId",provinceId);
        map.put("cityDeptId",cityDeptId);
        map.put("countryDeptId",countryDeptId);
        return this.baseMapper.getClassroomInfoByCityCountry(map);
    }

    public Integer getClassroomCount(String provinceId,String cityDeptId,String countryDeptId){
        Map<String,String> map = new HashMap<String,String>();
        map.put("provinceDeptId",provinceId);
        map.put("cityDeptId",cityDeptId);
        map.put("countryDeptId",countryDeptId);
        return this.baseMapper.getClassroomCount(map);
    }

    public List<ClassroomInfo> findClassroomByMainSchoolId(Integer mainSchoolId){
        return this.classroomInfoMapper.findClassroomByMainSchoolId(mainSchoolId);
    }

	@Override
	public IPage<ClassroomInfo> findClassroomInfosByDept(QueryRequest request, ClassroomInfo classroomInfo, String deptId) {
		Page<ClassroomInfo> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findClassroomInfosByDept(page, classroomInfo, deptId);
	}

    /**
     * 根据情况条件查询
     * @param tDept
     * @param classroomInfo
     */
    public static void selectDeptId(Dept tDept,ClassroomInfo classroomInfo){
        switch (tDept.getDeptGrade().intValue()) {
            case 1:
                classroomInfo.setProvinceDeptId(tDept.getDeptId());
                break;
            case 2:
                classroomInfo.setCityDeptId(tDept.getDeptId());
                break;
            case 3:
                classroomInfo.setCountryDeptId(tDept.getDeptId());
                break;
            case 4:
                classroomInfo.setDeptId(tDept.getDeptId());
                break;
            default:
        }
    }
}
