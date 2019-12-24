package cc.mrbird.febs.basicInfo.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 *  Entity
 *
 * @author wsq
 * @date 2019-12-04 09:11:19
 */
@Data
@TableName("notice_announcement")
public class NoticeAnnouncement {
    /**
     * 公告id
     */
    @TableId(value = "notice_id", type = IdType.AUTO)
    private Integer noticeId;

    /**
     * 公告标题
     */
    @TableField("notice_title")
    private String noticeTitle;

    /**
     * 公告内容
     */
    @TableField("notice_content")
    private String noticeContent;

    /**
     * 公告时间
     */
    @TableField("notice_time")
    private String noticeTime;

    /**
     * 公告图片
     */
    @TableField("notice_picture")
    private String noticePicture;

    /**
     * 编辑人id
     */
    @TableField("editor_id")
    private String editorId;

    /**
     * 编辑人姓名
     */
    @TableField("editor_name")
    private String editorName;

    /**
     * 发布时间
     */
    @TableField("publish_time")
    private Date publishTime;

    /**
     * 发布时间
     */
    @TableField("publish_company")
    private String publishCompany;
}