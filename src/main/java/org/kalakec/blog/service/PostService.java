package org.kalakec.blog.service;

import org.kalakec.blog.dto.PostDto;
import org.kalakec.blog.entity.Post;

import java.util.List;

public interface PostService {
    List<PostDto> findAllPosts();
}
