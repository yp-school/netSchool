package cc.mrbird.febs.basicInfo.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 *  Entity
 *
 * @author wsq
 * @date 2019-12-20 14:36:46
 */
@Data
@TableName("alicdn_resource")
public class AlicdnResource {
    /**
     * 资源id
     */
    @TableId(value = "resource_id", type = IdType.AUTO)
    private Integer resourceId;

    /**
     * 图片
     */
    @TableField("image")
    private String image;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 时间
     */
    @TableField("datetime")
    private String datetime;

    /**
     * 资源
     */
    @TableField("source")
    private String source;

    /**
     * 链接
     */
    @TableField("link")
    private String link;

}