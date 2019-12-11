package cc.mrbird.febs.basicInfo.mapper;

import cc.mrbird.febs.basicInfo.entity.VideoLive;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 *  Mapper
 *
 * @author wsq
 * @date 2019-12-06 08:58:29
 */
public interface VideoLiveMapper extends BaseMapper<VideoLive> {

    IPage<VideoLive> selectVideoLiveList(Page<VideoLive> page, @Param("videoLive") VideoLive videoLive);

}
