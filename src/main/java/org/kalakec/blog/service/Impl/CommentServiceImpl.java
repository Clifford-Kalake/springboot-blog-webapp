package org.kalakec.blog.service.Impl;

import org.kalakec.blog.dto.CommentDto;
import org.kalakec.blog.entity.Comment;
import org.kalakec.blog.entity.Post;
import org.kalakec.blog.mapper.CommentMapper;
import org.kalakec.blog.repository.CommentRepository;
import org.kalakec.blog.repository.PostRepository;
import org.kalakec.blog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    //We don't have to use the @Autowired annotation anymore because spring 4.3 onwards will automatically inject the dependencies
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void createComment(String postUrl, CommentDto commentDto) {
        Post post = postRepository.findByUrl(postUrl).get();
        Comment comment = CommentMapper.mapToComment(commentDto);
        comment.setPost(post);
        commentRepository.save(comment);    //save comment
    }

    @Override
    public List<CommentDto> findAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(CommentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
