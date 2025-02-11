package org.kalakec.blog.service;

import org.kalakec.blog.dto.PostDto;
import org.kalakec.blog.entity.Post;

import java.util.List;

public interface PostService {
    List<PostDto> findAllPosts();

    void createPost(PostDto postDto);

    PostDto findpostById(Long postId);

    void updatePost(PostDto postDto);

    void deletePost(Long postId);

    PostDto findpostByUrl(String postUrl);

    List<PostDto> searchPosts(String query);
}
