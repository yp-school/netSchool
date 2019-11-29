package cc.mrbird.febs.basicInfo.service.impl;

import cc.mrbird.febs.basicInfo.entity.DeviceInfo;
import cc.mrbird.febs.basicInfo.entity.School;
import cc.mrbird.febs.basicInfo.mapper.DeviceInfoMapper;
import cc.mrbird.febs.basicInfo.mapper.SchoolTimetableMapper;
import cc.mrbird.febs.basicInfo.service.IDeviceInfoService;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.system.entity.Dept;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.service.IUserDeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Arrays;
import java.util.List;

/**
 *  Service实现
 *
 * @author psy
 * @date 2019-08-16 16:50:43
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeviceInfoServiceImpl extends ServiceImpl<DeviceInfoMapper, DeviceInfo> implements IDeviceInfoService {
    @Autowired
    private IUserDeptService userDeptService;
    @Autowired
    private SchoolTimetableMapper schoolTimetableMapper;
	
    @Override
    public IPage<DeviceInfo> findDeviceInfos(QueryRequest request, DeviceInfo deviceInfo) {
        //登录用户只能看见相关的设备信息
        String schoolIds = "";
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Dept> userDeptList = userDeptService.getDeptByUserId(user.getUserId());
        if(userDeptList != null){
            for(Dept dept:userDeptList){
                String deptId = dept.getDeptId();
                List<School> schoolList = schoolTimetableMapper.selectSchoolInfByDeptId(deptId);
                for(School school:schoolList){
                    schoolIds = schoolIds + String.valueOf(school.getSchoolId()) + ",";
                }
            }
            deviceInfo.setSchoolIds(schoolIds.substring(0,schoolIds.length()-1));
        }
        Page<DeviceInfo> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "device_id", FebsConstant.ORDER_ASC, false);
        return this.baseMapper.findDeviceDetailInfos(page, deviceInfo);
    }

    @Override
    public List<DeviceInfo> findDeviceInfos(DeviceInfo deviceInfo) {
	    LambdaQueryWrapper<DeviceInfo> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public DeviceInfo findDeviceById(Integer deviceId){
        return this.getById(deviceId);
    }

    @Override
    @Transactional
    public void createDeviceInfo(DeviceInfo deviceInfo) {

        this.save(deviceInfo);
    }

    @Override
    @Transactional
    public void updateDeviceInfo(DeviceInfo deviceInfo) {

        this.updateById(deviceInfo);
    }

    @Override
    @Transactional
    public void deleteDeviceInfo(String deviceIds) {
        List<String> list = Arrays.asList(deviceIds.split(StringPool.COMMA));
        this.baseMapper.delete(
                new QueryWrapper<DeviceInfo>().lambda().in(DeviceInfo::getDeviceId, list));
	}

	@Override
	public void deleteDeviceInfoByschoolId(List<String> schoolIds) {
		if(schoolIds.size()>0)
			this.baseMapper.delete(new QueryWrapper<DeviceInfo>().lambda().in(DeviceInfo::getSchoolId, schoolIds));
	}

    @Override
    public Integer countDeviceByDept(String deptName){
        return this.baseMapper.countDeviceByDept(deptName);
    }

    @Override
    public Integer countDeviceBySchool(String schoolName){
        return this.baseMapper.countDeviceBySchool(schoolName);
    }

	@Override
	public IPage<DeviceInfo> findDeviceInfosByDept(QueryRequest request, DeviceInfo deviceInfo, String deptId) {
		Page<DeviceInfo> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findDeviceInfosByDept(page, deviceInfo, deptId);
	}

    /**
     * excel批量导入设备信息
     * @param deviceInfoList
     */
    @Override
    public void insertDeviceInfo(List<DeviceInfo> deviceInfoList) {
        this.saveBatch(deviceInfoList);
       /* for(int i=0; i<schoolTimetable.size(); i++){
            schoolTimeTableService.save(schoolTimetable.get(i).get());
        }*/
    }
}
