package cc.mrbird.febs.basicInfo.service.impl;

import cc.mrbird.febs.basicInfo.entity.SchoolTeacheinfo;
import cc.mrbird.febs.basicInfo.entity.VideoLive;
import cc.mrbird.febs.basicInfo.mapper.VideoLiveMapper;
import cc.mrbird.febs.basicInfo.service.IVideoLiveService;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.User;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *  Service实现
 *
 * @author wsq
 * @date 2019-12-06 08:58:29
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class VideoLiveServiceImpl extends ServiceImpl<VideoLiveMapper, VideoLive> implements IVideoLiveService {

    @Autowired
    private VideoLiveMapper videoLiveMapper;

    @Override
    public IPage<VideoLive> findVideoLives(QueryRequest request, VideoLive videoLive) {
        Page<VideoLive> page = new Page<>(request.getPageNum(), request.getPageSize());
        IPage<VideoLive> pageList = this.baseMapper.selectVideoLiveList(page,videoLive);
        return pageList;
    }

    @Override
    public List<VideoLive> findVideoLives(VideoLive videoLive) {
	    LambdaQueryWrapper<VideoLive> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createVideoLive(VideoLive videoLive) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        videoLive.setEditorId(user.getUserId());
        videoLive.setEditorName(user.getUsername());
        videoLive.setEditTime(new Date());
        this.save(videoLive);
    }

    @Override
    @Transactional
    public void updateVideoLive(VideoLive videoLive) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        videoLive.setEditorId(user.getUserId());
        videoLive.setEditorName(user.getUsername());
        videoLive.setEditTime(new Date());
        this.saveOrUpdate(videoLive);
    }

    @Override
    @Transactional
    public void deleteVideoLive(VideoLive videoLive) {
        LambdaQueryWrapper<VideoLive> wapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wapper);
	}

    @Override
    public void deleteVideoLives(String courseIds) {
        List<String> list = Arrays.asList(courseIds.split(StringPool.COMMA));
        baseMapper.deleteBatchIds(list);
    }
}
