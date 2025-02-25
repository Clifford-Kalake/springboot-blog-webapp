package org.kalakec.blog.service;

import org.kalakec.blog.dto.CommentDto;

public interface CommentService {
    void createComment(String postUrl, CommentDto commentDto);
}
