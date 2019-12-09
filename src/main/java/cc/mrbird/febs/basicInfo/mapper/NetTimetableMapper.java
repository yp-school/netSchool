package cc.mrbird.febs.basicInfo.mapper;

import cc.mrbird.febs.basicInfo.entity.NetTimetable;
import cc.mrbird.febs.basicInfo.entity.SchoolTeacheinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 *  Mapper
 *
 * @author wsq
 * @date 2019-12-04 16:49:28
 */
public interface NetTimetableMapper extends BaseMapper<NetTimetable> {

    IPage<NetTimetable> findNetTimetables(Page<NetTimetable> page, @Param("netTimetable") NetTimetable netTimetable);

    IPage<NetTimetable> selectNetTimetableList(Page<NetTimetable> page, @Param("netTimetable") NetTimetable netTimetable);
}
