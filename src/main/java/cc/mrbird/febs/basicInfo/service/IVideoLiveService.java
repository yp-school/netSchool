package cc.mrbird.febs.basicInfo.service;


import cc.mrbird.febs.basicInfo.entity.VideoLive;
import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author wsq
 * @date 2019-12-06 08:58:29
 */
public interface IVideoLiveService extends IService<VideoLive> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param videoLive videoLive
     * @return IPage<VideoLive>
     */
    IPage<VideoLive> findVideoLives(QueryRequest request, VideoLive videoLive);

    /**
     * 查询（所有）
     *
     * @param videoLive videoLive
     * @return List<VideoLive>
     */
    List<VideoLive> findVideoLives(VideoLive videoLive);

    /**
     * 新增
     *
     * @param videoLive videoLive
     */
    void createVideoLive(VideoLive videoLive);

    /**
     * 修改
     *
     * @param videoLive videoLive
     */
    void updateVideoLive(VideoLive videoLive);

    /**
     * 删除
     *
     * @param videoLive videoLive
     */
    void deleteVideoLive(VideoLive videoLive);

    void deleteVideoLives(String courseIds);
}
