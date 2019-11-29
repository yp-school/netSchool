package cc.mrbird.febs.resource.service;

import cc.mrbird.febs.resource.entity.CommentReplay;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author lb
 * @date 2019-08-17 19:46:00
 */
public interface ICommentReplayService extends IService<CommentReplay> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param commentReplay commentReplay
     * @return IPage<CommentReplay>
     */
    IPage<CommentReplay> findCommentReplays(QueryRequest request, CommentReplay commentReplay);

    /**
     * 查询（所有）
     *
     * @param commentReplay commentReplay
     * @return List<CommentReplay>
     */
    List<CommentReplay> findCommentReplays(CommentReplay commentReplay);

    /**
     * 新增
     *
     * @param commentReplay commentReplay
     */
    void createCommentReplay(CommentReplay commentReplay);

    /**
     * 修改
     *
     * @param commentReplay commentReplay
     */
    void updateCommentReplay(CommentReplay commentReplay);

    /**
     * 删除
     *
     * @param String commentReplayIds
     */
    void deleteCommentReplays(String commentReplayIds);
    
    /**
     * 通过评论 id 删除
     *
     * @param List<String> commentIds
     */
    void deleteCommentReplaysByCommentId(List<String> commentIds);
    
}
