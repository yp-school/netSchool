package cc.mrbird.febs.basicInfo.mapper;

import cc.mrbird.febs.basicInfo.entity.PictureNews;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 *  Mapper
 *
 * @author wsq
 * @date 2019-12-03 11:51:17
 */
public interface PictureNewsMapper extends BaseMapper<PictureNews> {

    IPage<PictureNews> findPictureNewsHide(Page<PictureNews> page, @Param("pictureNews") PictureNews pictureNews);
}
