package org.kalakec.blog.service.Impl;

import org.kalakec.blog.dto.PostDto;
import org.kalakec.blog.entity.Post;
import org.kalakec.blog.mapper.PostMapper;
import org.kalakec.blog.repository.PostRepository;
import org.kalakec.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<PostDto> findAllPosts() {
        List<Post> posts = postRepository.findAll();
        System.out.println("Number of posts found: " + posts.size()); // Debugging line
        return posts.stream()
                .map(PostMapper::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createPost(PostDto postDto) {
        Post post = PostMapper.mapToPost(postDto);
        postRepository.save(post);
    }

    @Override
    public PostDto findpostById(Long postId) {
        return postRepository.findById(postId)
                .map(PostMapper::mapToPostDto)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + postId));
    }

    @Override
    public void updatePost(PostDto postDto) {
        Post post = PostMapper.mapToPost(postDto);
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new RuntimeException("Post not found with ID: " + postId);
        }
        postRepository.deleteById(postId);
    }

    @Override
    public PostDto findpostByUrl(String postUrl) {
        return postRepository.findByUrl(postUrl)
                .map(PostMapper::mapToPostDto)
                .orElseThrow(() -> new RuntimeException("Post not found with URL: " + postUrl));
    }


    @Override
    public List<PostDto> searchPosts(String query) {
        List<Post> posts = postRepository.searchPosts(query);
        return posts.stream()
                .map(PostMapper::mapToPostDto)
                .collect(Collectors.toList());
    }
}
