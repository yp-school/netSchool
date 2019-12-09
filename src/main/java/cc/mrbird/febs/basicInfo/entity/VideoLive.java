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
 * @date 2019-12-06 08:58:29
 */
@Data
@TableName("video_live")
public class VideoLive {
    /**
     * 直播id
     */
    @TableId(value = "live_id", type = IdType.AUTO)
    private Integer liveId;
    /**
     * 课程id
     */
    @TableField(value = "course_id")
    private Integer courseId;

    /**
     * 直播标题
     */
    @TableField("video_title")
    private String videoTitle;

    /**
     * 直播简介
     */
    @TableField("video_content")
    private String videoContent;

    /**
     * 播放地址
     */
    @TableField("video_play_url")
    private String videoPlayUrl;

    /**
     * 是否已经播放
     */
    @TableField("is_play")
    private String isPlay;

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
     * 编辑时间
     */
    @TableField("edit_time")
    private Date editTime;

    /**
     * 课程名称
     */
    @TableField(exist = false)
    private String courseName;

}