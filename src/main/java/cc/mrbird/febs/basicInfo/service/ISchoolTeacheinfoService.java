package cc.mrbird.febs.basicInfo.service;


import cc.mrbird.febs.basicInfo.entity.SchoolTeacheinfo;
import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author wsq
 * @date 2019-12-02 11:44:57
 */
public interface ISchoolTeacheinfoService extends IService<SchoolTeacheinfo> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param schoolTeacheinfo schoolTeacheinfo
     * @return IPage<SchoolTeacheinfo>
     */
    IPage<SchoolTeacheinfo> findSchoolTeacheinfos(QueryRequest request, SchoolTeacheinfo schoolTeacheinfo);

    /**
     * 查询（所有）
     *
     * @param schoolTeacheinfo schoolTeacheinfo
     * @return List<SchoolTeacheinfo>
     */
    List<SchoolTeacheinfo> findSchoolTeacheinfos(SchoolTeacheinfo schoolTeacheinfo);

    /**
     * 新增
     *
     * @param schoolTeacheinfo schoolTeacheinfo
     */
    void createSchoolTeacheinfo(SchoolTeacheinfo schoolTeacheinfo);

    /**
     * 修改
     *
     * @param schoolTeacheinfo schoolTeacheinfo
     */
    void updateSchoolTeacheinfo(SchoolTeacheinfo schoolTeacheinfo);

    /**
     * 删除
     *
     * @param schoolTeacheinfo schoolTeacheinfo
     */
    void deleteSchoolTeacheinfo(SchoolTeacheinfo schoolTeacheinfo);

    void deleteTeacherInfo(String teacherIds);

    IPage<SchoolTeacheinfo> selectSchoolTeacherList(QueryRequest request, SchoolTeacheinfo schoolTeacheinfo);

    SchoolTeacheinfo selectSchoolTeacherinfoById(Integer teacherId);
}
