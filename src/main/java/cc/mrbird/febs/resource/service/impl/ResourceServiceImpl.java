package cc.mrbird.febs.resource.service.impl;

import cc.mrbird.febs.basicInfo.entity.School;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.service.RedisService;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dingding.config.Constant;
import cc.mrbird.febs.resource.entity.Resource;
import cc.mrbird.febs.resource.mapper.ResourceMapper;
import cc.mrbird.febs.resource.service.ICommentService;
import cc.mrbird.febs.resource.service.IResourceService;
import cc.mrbird.febs.resource.service.ISubjectResourceService;
import cc.mrbird.febs.search.service.IEsResourceService;
import cc.mrbird.febs.system.entity.Dept;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.service.IDeptService;
import cc.mrbird.febs.system.service.IUserDeptService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 *  Service实现
 *
 * @author lb
 * @date 2019-08-17 19:44:02
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private ISubjectResourceService subjectResourceService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IEsResourceService esResourceService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IUserDeptService userDeptService;
    
    @Override
	public Resource findDetailById(Long resourceId) {
		return resourceMapper.findDetailById(resourceId);
	}

	@Override
	public IPage<Resource> findDetails(Resource resource, QueryRequest request) {
        //登录用户只能看见相关的课程表数据
        String deptIds = "";
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Dept> userDeptList = userDeptService.getDeptByUserId(user.getUserId());
        if(userDeptList != null){
            for(Dept dept:userDeptList){
                deptIds = deptIds + dept.getDeptId() + ",";
            }
            resource.setDeptIds(deptIds.substring(0,deptIds.length()-1));
        }
		Page<Resource> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "resource_id", FebsConstant.ORDER_DESC, false);
		return resourceMapper.findDetails(page, resource);
	}

    @Override
    public IPage<Resource> findResources(QueryRequest request, Resource resource) {
        LambdaQueryWrapper<Resource> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        if (StringUtils.isNotBlank(resource.getResourceName())) {
            queryWrapper.like(Resource::getResourceName, resource.getResourceName());
        }
        if (StringUtils.isNotBlank(resource.getCreator())) {
            queryWrapper.eq(Resource::getCreator, resource.getCreator());
        }
        if (resource.getDeptId() != null) {
            queryWrapper.eq(Resource::getDeptId, resource.getDeptId());
        }
        if (resource.getSubjectId() !=null ) {
            queryWrapper.eq(Resource::getSubjectId, resource.getSubjectId());
        }
        if (resource.getGradeId() != null) {
            queryWrapper.eq(Resource::getGradeId, resource.getGradeId());
        }
        if (resource.getStatus() != null) {
            queryWrapper.eq(Resource::getStatus, resource.getStatus());
        }
        if (resource.getCategoryId() != null) {
            queryWrapper.eq(Resource::getCategoryId, resource.getCategoryId());
        }
        Page<Resource> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<Resource> findResources(Resource resource) {
	    LambdaQueryWrapper<Resource> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
	    if (StringUtils.isNotBlank(resource.getResourceName())) {
            queryWrapper.like(Resource::getResourceName, resource.getResourceName());
        }
        if (StringUtils.isNotBlank(resource.getCreator())) {
            queryWrapper.eq(Resource::getCreator, resource.getCreator());
        }
        if (resource.getDeptId() != null) {
            queryWrapper.eq(Resource::getDeptId, resource.getDeptId());
        }
        if (resource.getSubjectId() !=null ) {
            queryWrapper.eq(Resource::getSubjectId, resource.getSubjectId());
        }
        if (resource.getGradeId() != null) {
            queryWrapper.eq(Resource::getGradeId, resource.getGradeId());
        }
        if (resource.getStatus() != null) {
            queryWrapper.eq(Resource::getStatus, resource.getStatus());
        }
        if (resource.getCategoryId() != null) {
            queryWrapper.eq(Resource::getCategoryId, resource.getCategoryId());
        }
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createResource(Resource resource) {
        List<String> r=this.deptService.getParentDeptIds(resource.getDeptId());
        resourceDept(r,resource);
    	resource.setCreateTime(new Date());
        this.save(resource);
        esResourceService.save(resource.getResourceId());
    }
    
    @Override
	public void createResources(List<Resource> resources) {
		for(int i=0; i<resources.size(); i++){
            List<String> r=this.deptService.getParentDeptIds(resources.get(i).getDeptId());
            resourceDept(r,resources.get(i));
            this.save(resources.get(i));
			esResourceService.save(resources.get(i).getResourceId());
		}
	}

    @Override
    @Transactional
    public void updateResource(Resource resource) {
        List<String> r=this.deptService.getParentDeptIds(resource.getDeptId());
        resourceDept(r,resource);
    	resource.setModifyTime(new Date());
    	resource.setStatus(null);
        this.saveOrUpdate(resource);
        esResourceService.save(resource.getResourceId());
    }


    @Override
    @Transactional
    public void deleteResources(List<String> resourceIds) {
    	if(resourceIds.size()>0){
	        this.baseMapper.delete(new QueryWrapper<Resource>().lambda().in(Resource::getResourceId, resourceIds));
	        subjectResourceService.deleteSubjectResourcesByResourceId(resourceIds); // 删除专题关联
	        commentService.deleteCommentsByResourceId(resourceIds); // 删除评论
    	}
    	esResourceService.delete(resourceIds);
	}

	@Override
	public void increaseCommentCount(Long resourceId, Integer num) {
		resourceMapper.increaseCommentCount(resourceId, num);
	}

	@Override
	public void increaseReadCount(Long resourceId, Integer num) {
		resourceMapper.increaseReadCount(resourceId, num);
	}
	
	@Override
	public boolean checkCreator(List<String> resourceIds, String username) {
    	LambdaQueryWrapper<Resource> queryWrapper = new LambdaQueryWrapper<>();
    	queryWrapper.in(Resource::getResourceId, resourceIds);
    	queryWrapper.ne(Resource::getCreator, username);
    	List<Resource> list = this.baseMapper.selectList(queryWrapper);
    	if(list.size() > 0)
    		return false;
		return true;
	}
	
	@Override
	public int updateStatus(List<String> resourceIds, Integer status){
		LambdaQueryWrapper<Resource> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(Resource::getResourceId, resourceIds);
		Resource resource = new Resource();
		resource.setStatus(status);
		return this.baseMapper.update(resource, queryWrapper);
	}

    /**
     * 根据省市区统计资源总量_基本统计查询
     */
    @Override
    public int getResourceCountById(String provinceId, String cityDeptId, String countryDeptId) {
            return this.baseMapper.getResourceCountById(provinceId, cityDeptId,countryDeptId);
    }

    /**
     * 根据省市区统计资源总量
     */
    public int getResourceCount(String deptId){
        return this.baseMapper.getResourceCount(deptId, null);
    }

	@Override
	public Map<String, Integer> getResourceCountEveryMonth(String deptId) {
		Map<String, Integer> map = new TreeMap<String, Integer>();
		Calendar cale = Calendar.getInstance();  	
        int year = cale.get(Calendar.YEAR);  
        int month = cale.get(Calendar.MONTH);
        cale.set(Calendar.HOUR_OF_DAY, 0);//控制时
        cale.set(Calendar.MINUTE, 0);//控制分
        cale.set(Calendar.SECOND, 0);//控制秒
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String hkey = FebsConstant.RES_MONTH_COUNT;
        for(int i = month; i >= 0; i-- ){
        	cale.set(year, i, 1);
        	String date = sdf.format(cale.getTime());      
        	Integer count = 0;
        	if(redisService.hget(hkey, date) != null){
				count = Integer.valueOf(redisService.hget(hkey, date));
        	} else {
        		count = this.baseMapper.getResourceCount(deptId, date);
        		redisService.hset(hkey, date, count.toString());
        	}
        	map.put(date.substring(0,7), count);
        }
		return map;
	}

	public static void resourceDept(List<String> r,Resource resource){
        if(r.size()==3){
            resource.setCountryId(r.get(0));
            resource.setCityId(r.get(1));
            resource.setProvinceId(r.get(2));
        }else if(r.size()==2){
            resource.setCountryId(Constant.COUNTRY_ALL_SELECT_DEPT_ID);
            resource.setCityId(r.get(0));
            resource.setProvinceId(r.get(1));
        }else if(r.size()==1){
            resource.setCountryId(Constant.COUNTRY_ALL_SELECT_DEPT_ID);
            resource.setCityId(Constant.CITY_ALL_SELECT_DEPT_ID);
            resource.setProvinceId(r.get(0));
        }
    }
}
