package org.kalakec.blog.controller;

import jakarta.validation.Valid;
import org.kalakec.blog.dto.CommentDto;
import org.kalakec.blog.dto.PostDto;
import org.kalakec.blog.service.CommentService;
import org.kalakec.blog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CommentController {
    private CommentService commentService;
    private PostService postService;

    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    //handler method  to create form submit request
    public String createComment(@PathVariable("postUrl") String postUrl,
                                @Valid @ModelAttribute("comment") CommentDto commentDto, BindingResult result,
                                Model model) {
        PostDto postDto = postService.findPostByUrl(postUrl);
        if (result.hasErrors()) {
            model.addAttribute("post", postDto);
            model.addAttribute("comment", commentDto);
            return "blog/blog_post";
        }
        commentService.createComment(postUrl, commentDto);
        return "redirect:/post/" + postUrl;
    }
}
