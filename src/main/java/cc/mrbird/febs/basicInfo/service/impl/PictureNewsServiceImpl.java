package cc.mrbird.febs.basicInfo.service.impl;

import cc.mrbird.febs.basicInfo.entity.NoticeAnnouncement;
import cc.mrbird.febs.basicInfo.entity.PictureNews;
import cc.mrbird.febs.basicInfo.mapper.PictureNewsMapper;
import cc.mrbird.febs.basicInfo.service.IPictureNewsService;
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
 * @date 2019-12-03 11:51:17
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PictureNewsServiceImpl extends ServiceImpl<PictureNewsMapper, PictureNews> implements IPictureNewsService {

    @Autowired
    private PictureNewsMapper pictureNewsMapper;

    @Override
    public IPage<PictureNews> findPictureNewss(QueryRequest request, PictureNews pictureNews) {
        LambdaQueryWrapper<PictureNews> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        if (StringUtils.isNotEmpty(pictureNews.getNewsTitle())) {
            queryWrapper.eq(PictureNews::getNewsTitle, pictureNews.getNewsTitle());
        }
        Page<PictureNews> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<PictureNews> findPictureNewss(PictureNews pictureNews) {
	    LambdaQueryWrapper<PictureNews> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createPictureNews(PictureNews pictureNews) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        pictureNews.setPublishTime(new Date());
        pictureNews.setEditorId(user.getUserId());
        pictureNews.setEditorName(user.getUsername());
        this.save(pictureNews);
    }

    @Override
    @Transactional
    public void updatePictureNews(PictureNews pictureNews) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        pictureNews.setPublishTime(new Date());
        pictureNews.setEditorId(user.getUserId());
        pictureNews.setEditorName(user.getUsername());
        this.saveOrUpdate(pictureNews);
    }

    @Override
    @Transactional
    public void deletePictureNews(PictureNews pictureNews) {
        LambdaQueryWrapper<PictureNews> wapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wapper);
	}

    @Override
    public void deletePictureNewsInfo(String pictureIds) {
        List<String> list = Arrays.asList(pictureIds.split(StringPool.COMMA));
        baseMapper.deleteBatchIds(list);
    }
}
