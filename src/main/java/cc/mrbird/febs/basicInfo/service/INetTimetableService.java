package cc.mrbird.febs.basicInfo.service;


import cc.mrbird.febs.basicInfo.entity.NetTimetable;
import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author wsq
 * @date 2019-12-04 16:49:28
 */
public interface INetTimetableService extends IService<NetTimetable> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param netTimetable netTimetable
     * @return IPage<NetTimetable>
     */
    IPage<NetTimetable> findNetTimetables(QueryRequest request, NetTimetable netTimetable);

    /**
     * 查询（所有）
     *
     * @param netTimetable netTimetable
     * @return List<NetTimetable>
     */
    List<NetTimetable> findNetTimetables(NetTimetable netTimetable);

    /**
     * 新增
     *
     * @param netTimetable netTimetable
     */
    void createNetTimetable(NetTimetable netTimetable);

    /**
     * 修改
     *
     * @param netTimetable netTimetable
     */
    void updateNetTimetable(NetTimetable netTimetable);

    /**
     * 删除
     *
     * @param netTimetable netTimetable
     */
    void deleteNetTimetable(NetTimetable netTimetable);

    void deleteNetTimetables(String courseIds);

    IPage<NetTimetable> selectNetTimetableList(QueryRequest request, NetTimetable netTimetable);

    NetTimetable selectNetTimetableById(Integer courseId);
}
