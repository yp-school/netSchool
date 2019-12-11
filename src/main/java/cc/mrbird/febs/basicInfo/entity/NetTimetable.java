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
 * @date 2019-12-04 16:49:28
 */
@Data
@TableName("net_timetable")
public class NetTimetable {
    /**
     * 课程id
     */
    @TableId(value = "course_id", type = IdType.AUTO)
    private Integer courseId;

    /**
     * 创建人id
     */
    @TableField("create_user_id")
    private String createUserId;

    /**
     * 创建人姓名
     */
    @TableField("create_user_name")
    private String createUserName;

    /**
     * 课程名称
     */
    @TableField("course_name")
    private String courseName;

    /**
     * 开课时间
     */
    @TableField("begin_date")
    private String beginDate;

    /**
     * 课程简介
     */
    @TableField("course_introduction")
    private String courseIntroduction;

    /**
     * 同步互动学校
     */
    @TableField("sy_interaction_school")
    private String syInteractionSchool;

    /**
     * 同步观看直播学校
     */
    @TableField("sy_viewer")
    private String syViewer;

    /**
     * 教师id
     */
    @TableField("teacher_id")
    private Integer teacherId;

    /**
     * 教师名称
     */
    @TableField(exist = false)
    private String teacherName;

    /**
     * 教师名称
     */
    @TableField(exist = false)
    private String isPlay;
}