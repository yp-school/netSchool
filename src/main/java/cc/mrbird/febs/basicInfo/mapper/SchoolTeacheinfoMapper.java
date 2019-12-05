package cc.mrbird.febs.basicInfo.mapper;

import cc.mrbird.febs.basicInfo.entity.SchoolTeacheinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 *  Mapper
 *
 * @author wsq
 * @date 2019-12-02 11:44:57
 */
public interface SchoolTeacheinfoMapper extends BaseMapper<SchoolTeacheinfo> {

    IPage<SchoolTeacheinfo> selectSchoolTeacherList(Page<SchoolTeacheinfo> page, @Param("schoolTeacheinfo") SchoolTeacheinfo schoolTeacheinfo);
}
