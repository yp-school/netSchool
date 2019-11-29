package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.DeptTree;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.DateUtil;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.dingding.config.Constant;
import cc.mrbird.febs.dingding.util.AddressListUtil;
import cc.mrbird.febs.dingding.vo.Department;
import cc.mrbird.febs.dingding.vo.DepartmentListIFVO;
import cc.mrbird.febs.system.entity.Dept;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.mapper.DeptMapper;
import cc.mrbird.febs.system.service.IDeptService;
import cc.mrbird.febs.system.service.IUserDeptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author lb
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {


    @Autowired
	private IUserDeptService userDeptService;

    
    private int maxDeptGrade = 4;

    /**
     * 树形结构dept显示
     * @return
     */
    @Override
    public List<DeptTree<Dept>> findDepts() {
    	LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
    	queryWrapper.lt(Dept::getDeptGrade, maxDeptGrade);
        List<Dept> depts = this.baseMapper.selectList(queryWrapper);
        List<DeptTree<Dept>> trees = this.convertDepts(depts);
        return TreeUtil.buildDeptTree(trees, "1");
    }
    /**
     * 获取部门
     * @return
     */
    @Override
    public List<Dept> selectDepts() {
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.le(Dept::getDeptGrade, maxDeptGrade);
        List<Dept> depts = this.baseMapper.selectList(queryWrapper);
        return depts;
    }

    @Override
    public List<DeptTree<Dept>> getLimitDeptTree(String userId) {
//    	 获取所有父部门
//    	String arrays = AddressListUtil.getUserParentDepts(userId);
//    	if(arrays == null)
//    		return new ArrayList<DeptTree<Dept>>();
//		List<Long> parentIds = new ArrayList<>();
//		JSONArray jsonArray = JSONArray.fromObject(arrays);
//		for (int i = 0; i < jsonArray.size(); i++) {
//			JSONArray array = jsonArray.getJSONArray(i);
//			for (int j = 0; j < array.size(); j++){
//				if(!parentIds.contains(array.getLong(j)))
//					parentIds.add(array.getLong(j));
//			}
//		}
//		LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.in(Dept::getDeptId, parentIds);
//        queryWrapper.lt(Dept::getDeptGrade, maxDeptGrade);
//        queryWrapper.orderByAsc(Dept::getOrderNum);
//        List<Dept> parentDepts = this.baseMapper.selectList(queryWrapper);
    	
    	// 获取所有父部门
    	List<List<Dept>> parentDepts = getAllParentDept(userId);
    	
		// 获取用户所属部门    	
		List<Dept> userDepts = userDeptService.getDeptByUserId(userId);
		List<String> deptIds1 = new ArrayList<>(); // 一级部门id
		List<String> deptIds2 = new ArrayList<>(); // 二级部门id
		long deptGrade;
		for(int i=0; i<userDepts.size(); i++){
			deptGrade = userDepts.get(i).getDeptGrade();
			if(deptGrade == 1)
				deptIds1.add(userDepts.get(i).getDeptId());
			else if(deptGrade == 2)
				deptIds2.add(userDepts.get(i).getDeptId());
		}
		
		List<Dept> depts2 = new ArrayList<>();
		List<Dept> depts3 = new ArrayList<>();		
		// 获取二级部门
		LambdaQueryWrapper<Dept> queryWrapper;
		if(!deptIds1.isEmpty()){
			queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.in(Dept::getParentId, deptIds1);
			queryWrapper.orderByAsc(Dept::getOrderNum);

            Dept tempDept = new Dept();
            tempDept.setDeptId(Constant.CITY_ALL_SELECT_DEPT_ID);
            tempDept.setDeptName("全部");
            tempDept.setParentId(deptIds1.get(0));
            depts2.add(tempDept);

			depts2.addAll(this.baseMapper.selectList(queryWrapper));


			for(int i = 0; i < depts2.size(); i++)
				if(!deptIds2.contains(depts2.get(i).getDeptId()))
					deptIds2.add(depts2.get(i).getDeptId());
		}
		// 获取三级部门
		if(!deptIds2.isEmpty()){
			queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.in(Dept::getParentId, deptIds2);
			queryWrapper.orderByAsc(Dept::getOrderNum);
			depts3 = this.baseMapper.selectList(queryWrapper);

			for(int i = 0 ; i < deptIds2.size(); i++){
                Dept tempDept = new Dept();
                tempDept.setDeptId(Constant.COUNTRY_ALL_SELECT_DEPT_ID);
                tempDept.setDeptName("全部");
                tempDept.setParentId(deptIds2.get(i));
                depts2.add(tempDept);
            }
		}
		
		// 合并所有部门
		List<Dept> depts = new ArrayList<>();
		depts.addAll(depts2);
		depts.addAll(depts3);
		for(int i=0; i<parentDepts.size(); i++){
			List<Dept> list = parentDepts.get(i);
			for(int j=0; j<list.size(); j++){
				if(list.get(j).getDeptGrade() < 4 && !depts.contains(list.get(j)))
					depts.add(list.get(j));
			}
		}
		
        List<DeptTree<Dept>> trees =  this.convertDepts(depts);
        return TreeUtil.buildDeptTree(trees, "1");
    }

    @Override
    public List<Dept> findDepts(Dept dept, QueryRequest request) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(dept.getDeptName()))
            queryWrapper.lambda().eq(Dept::getDeptName, dept.getDeptName());
        if (StringUtils.isNotBlank(dept.getParentId()))
            queryWrapper.lambda().eq(Dept::getParentId, dept.getParentId());
        SortUtil.handleWrapperSort(request, queryWrapper, "orderNum", FebsConstant.ORDER_ASC, true);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createDept(Dept dept) {
        String parentId = dept.getParentId();
        if (parentId == null)
            dept.setParentId("0");
        dept.setCreateTime(new Date());
        this.save(dept);
    }

    @Override
    @Transactional
    public void updateDept(Dept dept) {
        dept.setModifyTime(new Date());
        this.baseMapper.updateById(dept);
    }

    @Override
    @Transactional
    public void deleteDepts(String[] deptIds) {
       this.delete(Arrays.asList(deptIds));
    }

    private List<DeptTree<Dept>> convertDepts(List<Dept> depts){
        List<DeptTree<Dept>> trees = new ArrayList<>();
        depts.forEach(dept -> {
            DeptTree<Dept> tree = new DeptTree<>();
            tree.setId(String.valueOf(dept.getDeptId()));
            tree.setParentId(String.valueOf(dept.getParentId()));
            tree.setName(dept.getDeptName());
            tree.setData(dept);
            trees.add(tree);
        });
        return trees;
    }

    private void delete(List<String> deptIds) {
        removeByIds(deptIds);

        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dept::getParentId, deptIds);
        List<Dept> depts = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(depts)) {
            List<String> deptIdList = new ArrayList<>();
            depts.forEach(d -> deptIdList.add(String.valueOf(d.getDeptId())));
            this.delete(deptIdList);
        }
    }

    public long findGradeByParentId(long parentId){

        return this.baseMapper.findGradeByParentId(parentId);
    }

    /**
     * 同步钉钉部门数据
     */
    public void synchDingDeptData(){
        DepartmentListIFVO departmentListIFVO = AddressListUtil.synchDingDeptData();
        List<Department> departments = departmentListIFVO.getDepartment();
        for (int i = 0; i < departments.size(); i++){
            Department department = departments.get(i);
            Dept dept = new Dept();
            dept.setDeptId(department.getId());
            dept.setDeptName(department.getName());
            dept.setParentId(department.getParentid());
            dept.setModifyTime(DateUtil.getNowDateTime());
            dept.setDeptGrade(0l);
            this.saveOrUpdate(dept);
        }
        // 设置部门级别值
         for (int i = 0 ; i < departments.size(); i++) {
            Department department = departments.get(i);
            String deptId = department.getId();
            long deptGrade = 0;
            String parentId = department.getParentid();
            if(!"0".equals(parentId) && StringUtils.isNotEmpty(parentId)){   //过滤掉企业的部门节点数据
                long parentDeptGrade = checkSynchParentDeptInfo(parentId);
                Dept dept = new Dept();
                dept.setDeptId(department.getId());
                dept.setDeptGrade(parentDeptGrade + 1l);
                dept.setOrderNum(i + 1);
                this.saveOrUpdate(dept);
            }
        }
    }

    public long checkSynchParentDeptInfo(String deptId){
        Dept dept = this.getById(deptId);
        String parentId = dept.getParentId();
        if("0".equals(parentId)){ //网络联校部门  企业部门节点
            return 0l;
        }else{
            long parentDeptGrade = checkSynchParentDeptInfo(parentId);
            return parentDeptGrade + 1;
        }
    }
    
    @Override
	public List<List<Dept>> getAllParentDept(String userId) {
		List<List<Dept>> result = new ArrayList<>();
		List<Dept> list = this.userDeptService.getDeptByUserId(userId);
		String parentId;
		//循环将所有的父级部门添加到集合中
		for(Dept dept: list){
			List<Dept> parents = new ArrayList<>();
			parents.add(dept);
			parentId = dept.getParentId();
			while(!"1".equals(parentId) && parents.size() < 5){
				Dept parentDept = this.baseMapper.selectById(parentId);
				parents.add(parentDept);
				parentId = parentDept.getParentId();
			}
			result.add(parents);
		}
		return result;
	}

    @Override
    public List<Dept> getAllCityData(){
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.in(Dept::getDeptGrade, 2);
        List<Dept> depts = baseMapper.selectList(queryWrapper);
        return depts;
    }


    @Override
    public Dept getNameByDeptId(String deptId){
       /* List<Dept> d=this.deptMapper.getNameByDeptId(provinceDeptId,cityDeptId,countryDeptId);
        return d;*/
       return this.baseMapper.selectById(deptId);
    }
    
    /**
     * 获取一个部门的所有父部门id,包括当前部门id
     */
	@Override
	public List<String> getParentDeptIds(String deptId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
		List<String> parentIds = new ArrayList<>();
		if(deptId == null){
		    return parentIds;
        }
		//查询parent_id是否 是 ‘全部’的值
		String parentId = selectMaxRoleId(deptId);
		String compareDeptId = parentId;
		while(!"1".equals(parentId) && parentIds.size() < 5){
			Dept parentDept = this.baseMapper.selectById(parentId);
			if(parentDept != null){
                parentIds.add(parentDept.getDeptId());
                parentId = parentDept.getParentId();
            }
            //如果parentId的值没有改变,则需要跳出循环,否则这就是个死循环
            if(parentId.equals(compareDeptId)){
                return parentIds;
            }
		}
		return parentIds;
	}

    /**
     * 如果传入的deptId是‘全部的id’,那么则需要访问当前用户最高级权限的deptId，再返回
     */
    @Override
    public String selectMaxRoleId(String deptId){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String parentId = deptId;
        if(Constant.CITY_ALL_SELECT_DEPT_ID.equals(parentId) || Constant.COUNTRY_ALL_SELECT_DEPT_ID.equals(parentId)){
            //如果用户选择的是全部,则查询出当前用户对应的最大权限的部门id
            if(StringUtils.isNotEmpty(user.getUserId())){
                //获取用户所属部门
                List<Dept> userDepts = userDeptService.getDeptByUserId(user.getUserId());
                //随意定义一个部门表不可能会出现的deptGrade作比较
                Long deptGrade = 5L;
                String maxDeptId = "";
                //一个人可能有多个部门，选取权限最大的部门id
                for(Dept dept:userDepts){
                    Long compareId = dept.getDeptGrade();
                    if(compareId < deptGrade){
                        deptGrade = compareId;
                        maxDeptId = dept.getDeptId();
                    }
                }
                parentId = maxDeptId;
            }
        }
        return parentId;
    }
}
