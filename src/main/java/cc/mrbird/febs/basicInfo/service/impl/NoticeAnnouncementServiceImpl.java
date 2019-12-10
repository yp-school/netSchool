package cc.mrbird.febs.basicInfo.service.impl;

import cc.mrbird.febs.basicInfo.entity.NoticeAnnouncement;
import cc.mrbird.febs.basicInfo.entity.Timetable;
import cc.mrbird.febs.basicInfo.mapper.NoticeAnnouncementMapper;
import cc.mrbird.febs.basicInfo.service.INoticeAnnouncementService;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.User;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
 * @date 2019-12-04 09:11:19
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class NoticeAnnouncementServiceImpl extends ServiceImpl<NoticeAnnouncementMapper, NoticeAnnouncement> implements INoticeAnnouncementService {

    @Autowired
    private NoticeAnnouncementMapper noticeAnnouncementMapper;

    @Override
    public IPage<NoticeAnnouncement> findNoticeAnnouncements(QueryRequest request, NoticeAnnouncement noticeAnnouncement) {
        LambdaQueryWrapper<NoticeAnnouncement> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        if (StringUtils.isNotEmpty(noticeAnnouncement.getNoticeTitle())) {
            queryWrapper.eq(NoticeAnnouncement::getNoticeTitle, noticeAnnouncement.getNoticeTitle());
        }
        Page<NoticeAnnouncement> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<NoticeAnnouncement> findNoticeAnnouncements(NoticeAnnouncement noticeAnnouncement) {
	    LambdaQueryWrapper<NoticeAnnouncement> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createNoticeAnnouncement(NoticeAnnouncement noticeAnnouncement) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        noticeAnnouncement.setPublishTime(new Date());
        noticeAnnouncement.setEditorId(user.getUserId());
        noticeAnnouncement.setEditorName(user.getUsername());
        this.save(noticeAnnouncement);
    }

    @Override
    @Transactional
    public void updateNoticeAnnouncement(NoticeAnnouncement noticeAnnouncement) {
        this.saveOrUpdate(noticeAnnouncement);
    }

    @Override
    @Transactional
    public void deleteNoticeAnnouncement(NoticeAnnouncement noticeAnnouncement) {
        LambdaQueryWrapper<NoticeAnnouncement> wapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wapper);
	}

    @Override
    public void deleteNoticeAnnouncementInfo(String noticeIds) {
        List<String> list = Arrays.asList(noticeIds.split(StringPool.COMMA));
        baseMapper.deleteBatchIds(list);
    }

    @Override
    public NoticeAnnouncement selectNoticeById(Integer noticeId) {
        return baseMapper.selectById(noticeId);
    }
}
