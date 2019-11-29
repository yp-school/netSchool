package cc.mrbird.febs.resource.service;

import cc.mrbird.febs.resource.entity.Comment;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author lb
 * @date 2019-08-17 19:45:42
 */
public interface ICommentService extends IService<Comment> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param comment comment
     * @return IPage<Comment>
     */
    IPage<Comment> findComments(QueryRequest request, Comment comment);

    /**
     * 查询（所有）
     *
     * @param comment comment
     * @return List<Comment>
     */
    List<Comment> findComments(Comment comment);

    /**
     * 新增
     *
     * @param comment comment
     */
    void createComment(Comment comment);

    /**
     * 修改
     *
     * @param comment comment
     */
    void updateComment(Comment comment);

    /**
     * 删除
     *
     * @param String commentIds
     */
    void deleteComments(List<String> commentIds);
    
    /**
     * 通过资源 id 删除
     *
     * @param List<String> commentIds
     */
    void deleteCommentsByResourceId(List<String> resourceIds);
    
    /**
	 * 增加回复数
	 * @param commentId
	 * @param num
	 */
	void increaseReplayCount(Long commentId, Integer num);

	/**
	 * 检查创建者是否为当前用户
	 * @param commentIds
	 * @param username
	 * @return
	 */
	boolean checkCreator(List<String> commentIds, String username);
}
