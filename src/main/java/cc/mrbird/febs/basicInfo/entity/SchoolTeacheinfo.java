package cc.mrbird.febs.basicInfo.entity;


import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 *  Entity
 *
 * @author wsq
 * @date 2019-12-02 11:44:57
 */
@Data
@TableName("school_teacher_info")
public class SchoolTeacheinfo {
    /**
     * 教师id
     */
    @TableId(value = "teacher_id", type = IdType.AUTO)
    private Integer teacherId;

    /**
     * 教师姓名
     */
    @TableField("teacher_name")
    private String teacherName;

    /**
     * 性别
     */
    @TableField("sex")
    private String sex;

    /**
     * 教师介绍
     */
    @TableField("teacher_introduction")
    private String teacherIntroduction;

    /**
     * 教师荣誉
     */
    @TableField("honour")
    private String honour;

    /**
     * 教师荣誉图片
     */
    @TableField("honour_image")
    private String honourImage;

    /**
     * 电话号码
     */
    @TableField("telephone")
    private String telephone;

    /**
     * 老师相册
     */
    @TableField("img_urls")
    private String imgUrls;



}