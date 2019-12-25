package cc.mrbird.febs.basicInfo.service;


import cc.mrbird.febs.basicInfo.entity.PictureNews;
import cc.mrbird.febs.basicInfo.entity.SchoolTeacheinfo;
import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author wsq
 * @date 2019-12-03 11:51:17
 */
public interface IPictureNewsService extends IService<PictureNews> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param pictureNews pictureNews
     * @return IPage<PictureNews>
     */
    IPage<PictureNews> findPictureNewss(QueryRequest request, PictureNews pictureNews);

    /**
     * 查询（所有）
     *
     * @param pictureNews pictureNews
     * @return List<PictureNews>
     */
    List<PictureNews> findPictureNewss(PictureNews pictureNews);

    /**
     * 新增
     *
     * @param pictureNews pictureNews
     */
    void createPictureNews(PictureNews pictureNews);

    /**
     * 修改
     *
     * @param pictureNews pictureNews
     */
    void updatePictureNews(PictureNews pictureNews);

    /**
     * 删除
     *
     * @param pictureNews pictureNews
     */
    void deletePictureNews(PictureNews pictureNews);

    void deletePictureNewsInfo(String pictureIds);

    PictureNews selectPictureNewsById(Integer pictureId);

    IPage<PictureNews> findPictureNewsHide(QueryRequest request, PictureNews pictureNews);

    IPage<PictureNews> selectPictureNewsinfoWebList(QueryRequest request, PictureNews pictureNews);
}
