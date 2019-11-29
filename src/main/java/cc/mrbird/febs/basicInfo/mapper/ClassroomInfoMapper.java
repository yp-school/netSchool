package cc.mrbird.febs.basicInfo.mapper;

import cc.mrbird.febs.basicInfo.entity.ClassInfo;
import cc.mrbird.febs.basicInfo.entity.ClassroomInfo;
import cc.mrbird.febs.system.entity.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Mapper
 *
 * @author psy
 * @date 2019-08-23 15:45:24
 */
public interface ClassroomInfoMapper extends BaseMapper<ClassroomInfo> {

	public List<ClassroomInfo> getClassroomInfoByCityCountry(@Param("map") Map<String, String> map);

	public Integer getClassroomCount(@Param("map") Map<String, String> map);

	public List<ClassroomInfo> findClassroomByMainSchoolId(@Param("schoolId") Integer schoolId);

	/**
	 * 按部门查询 
	 * @param page
	 * @param classroomInfo
	 * @param deptId
	 * @return
	 */
	IPage<ClassroomInfo> findClassroomInfosByDept(Page<?> page, @Param("classroomInfo") ClassroomInfo classroomInfo,
			@Param("deptId") String deptId);

	/**
	 * 查询班级所有方法
	 * @param page
	 * @param classroomInfo
	 * @return
	 */
    IPage<ClassroomInfo> selectClassroomInfos(Page<ClassroomInfo> page,@Param("classroomInfo") ClassroomInfo classroomInfo);

	/**
	 * 根据部门Id查询部门信息
	 * @param dept
	 * @return
	 */
    Dept selectDeptById(String dept);
}
