package cc.mrbird.febs.basicInfo.service.impl;

import cc.mrbird.febs.basicInfo.entity.NetTimetable;
import cc.mrbird.febs.basicInfo.entity.SchoolTeacheinfo;
import cc.mrbird.febs.basicInfo.mapper.NetTimetableMapper;
import cc.mrbird.febs.basicInfo.service.INetTimetableService;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.User;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
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
 * @date 2019-12-04 16:49:28
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class NetTimetableServiceImpl extends ServiceImpl<NetTimetableMapper, NetTimetable> implements INetTimetableService {

    @Autowired
    private NetTimetableMapper netTimetableMapper;

    @Override
    public IPage<NetTimetable> findNetTimetables(QueryRequest request, NetTimetable netTimetable) {
        Page<NetTimetable> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findNetTimetables(page, netTimetable);

    }

    @Override
    public List<NetTimetable> findNetTimetables(NetTimetable netTimetable) {
	    LambdaQueryWrapper<NetTimetable> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createNetTimetable(NetTimetable netTimetable) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        netTimetable.setCreateUserId(user.getUserId());
        netTimetable.setCreateUserName(user.getUsername());
        this.save(netTimetable);
    }

    @Override
    @Transactional
    public void updateNetTimetable(NetTimetable netTimetable) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        netTimetable.setCreateUserId(user.getUserId());
        netTimetable.setCreateUserName(user.getUsername());
        this.saveOrUpdate(netTimetable);
    }

    @Override
    @Transactional
    public void deleteNetTimetable(NetTimetable netTimetable) {
        LambdaQueryWrapper<NetTimetable> wapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wapper);
	}

    /**
     * 支持多行删除
     * @param courseIds
     */
    @Override
    public void deleteNetTimetables(String courseIds) {
        List<String> list = Arrays.asList(courseIds.split(StringPool.COMMA));
        baseMapper.deleteBatchIds(list);
    }

    /**
     * 教师下拉树
     * @param request
     * @param netTimetable
     * @return
     */
    @Override
    public IPage<NetTimetable> selectNetTimetableList(QueryRequest request, NetTimetable netTimetable) {
        Page<NetTimetable> page = new Page<>(request.getPageNum(), request.getPageSize());
        IPage<NetTimetable> pageList = this.baseMapper.selectNetTimetableList(page,netTimetable);
        return pageList;
    }
}
