package com.pgs.post.service;

import com.pgs.post.domain.Comment;
import com.pgs.post.payload.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Page<Comment> getAllPostComments(Pageable pageable, Long postId);

    void addComment(Long postId, String content);

    Comment updateComment(Long postId, Long commentId, String newContent);

    void deleteComment(Long postId, Long commentId);

    void likeComment(Long postId, Long commentId);

    void unlikeComment(Long postId, Long commentId);

    Page<UserInfo> getLikingUserInfo(Pageable pageable, Long postId, Long commentId);
}
