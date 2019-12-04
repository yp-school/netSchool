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
 * @date 2019-12-03 11:51:17
 */
@Data
@TableName("picture_news")
public class PictureNews {
    /**
     * 图片id
     */
    @TableId(value = "picture_id", type = IdType.AUTO)
    private Integer pictureId;

    /**
     * 图片url
     */
    @TableField("picture_url")
    private String pictureUrl;

    /**
     * 新闻标题
     */
    @TableField("news_title")
    private String newsTitle;

    /**
     * 新闻内容
     */
    @TableField("news_content")
    private String newsContent;

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
     * 课程id
     */
    @TableField("course_id")
    private Integer courseId;
}