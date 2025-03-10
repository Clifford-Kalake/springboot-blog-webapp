package org.kalakec.blog.mapper;

import org.kalakec.blog.dto.PostDto;
import org.kalakec.blog.entity.Post;

import java.util.stream.Collectors;

public class PostMapper {
    //map Post entity to PostDto
    public static PostDto mapToPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .url(post.getUrl())
                .content(post.getContent())
                .shortDescription(post.getShortDescription())
                .createdOn(post.getCreatedOn())
                .updatedOn(post.getUpdatedOn())
                .comments(post.getComments().stream()
                        .map((comment -> CommentMapper.mapToCommentDto(comment)))
                        .collect(Collectors.toSet()))   //map comments
                .build();
    }

    //map PostDto to Entity
    public static Post mapToPost(PostDto postDto) {
        return Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .url(postDto.getUrl())
                .shortDescription(postDto.getShortDescription())
                .createdOn(postDto.getCreatedOn())
                .updatedOn(postDto.getUpdatedOn())
                .build();
    }
}
