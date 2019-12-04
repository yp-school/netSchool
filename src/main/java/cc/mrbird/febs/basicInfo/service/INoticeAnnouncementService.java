package cc.mrbird.febs.basicInfo.service;


import cc.mrbird.febs.basicInfo.entity.NoticeAnnouncement;
import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author wsq
 * @date 2019-12-04 09:11:19
 */
public interface INoticeAnnouncementService extends IService<NoticeAnnouncement> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param noticeAnnouncement noticeAnnouncement
     * @return IPage<NoticeAnnouncement>
     */
    IPage<NoticeAnnouncement> findNoticeAnnouncements(QueryRequest request, NoticeAnnouncement noticeAnnouncement);

    /**
     * 查询（所有）
     *
     * @param noticeAnnouncement noticeAnnouncement
     * @return List<NoticeAnnouncement>
     */
    List<NoticeAnnouncement> findNoticeAnnouncements(NoticeAnnouncement noticeAnnouncement);

    /**
     * 新增
     *
     * @param noticeAnnouncement noticeAnnouncement
     */
    void createNoticeAnnouncement(NoticeAnnouncement noticeAnnouncement);

    /**
     * 修改
     *
     * @param noticeAnnouncement noticeAnnouncement
     */
    void updateNoticeAnnouncement(NoticeAnnouncement noticeAnnouncement);

    /**
     * 删除
     *
     * @param noticeAnnouncement noticeAnnouncement
     */
    void deleteNoticeAnnouncement(NoticeAnnouncement noticeAnnouncement);

    void deleteNoticeAnnouncementInfo(String noticeIds);
}
