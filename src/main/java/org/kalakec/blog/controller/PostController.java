package org.kalakec.blog.controller;

import jakarta.validation.Valid;
import org.kalakec.blog.dto.CommentDto;
import org.kalakec.blog.dto.PostDto;
import org.kalakec.blog.service.CommentService;
import org.kalakec.blog.service.PostService;
import org.kalakec.blog.util.ROLE;
import org.kalakec.blog.util.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    private PostService postService;

    private CommentService commentService;

    public PostController(PostService postService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    //create handler method, GET request and return model and view
    @GetMapping("/admin/posts")
    public String posts(Model model) {
        String role = SecurityUtils.getRole();
        List<PostDto> posts = null;
        if(ROLE.ROLE_ADMIN.name().equals(role)) {
            posts = postService.findAllPosts();
        }else {
            posts = postService.findPostsByUser();
        }
        model.addAttribute("posts", posts);
        return "admin/posts";
    }

    //handler method to handle list comments request
    @GetMapping("/admin/posts/comments")
    public String postComments(@PathVariable("postId") Long postId, Model model){
        String role = SecurityUtils.getRole();
        List<CommentDto> comments = null;
        if(ROLE.ROLE_ADMIN.name().equals(role)) {
            comments = commentService.findAllComments();
        }else{
            comments = commentService.findCommentsByPost();
        }
        model.addAttribute("comments", comments);
        return "admin/comments";
    }

    //handler method to handle delete comment request
    @GetMapping("/admin/posts/comments/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId){
        commentService.deleteComment(commentId);
        return "redirect:/admin/posts/comments";
    }

    //handler method to handle new post request
    @GetMapping("/admin/posts/newpost")
    public String newPostForm(Model model){
        PostDto postDto = new PostDto();
        model.addAttribute("post", postDto);
        return "admin/create_post";
    }

    @PostMapping("/admin/posts")
    public String createPost(@Valid @ModelAttribute("post") PostDto postDto, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("post", postDto);
            return "admin/create_post";
        }
        postDto.setUrl(getUrl(postDto.getTitle()));
        postService.createPost(postDto);
        return "redirect:/admin/posts";
    }

    //Handler method to handle edit post request
    @GetMapping("/admin/posts/{postId}/edit")
    public String editPostForm(@PathVariable("postId") Long postId, Model model){
        PostDto postDto = postService.findPostById(postId);
        model.addAttribute("post", postDto);
        return "admin/edit_post";
    }

    //Handler method to handle edit post form submit request
    @PostMapping("/admin/posts/{postId}")
    public String updatePost(@PathVariable("postId") Long postId, @Valid @ModelAttribute("post") PostDto postDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("post", postDto);
            return "admin/edit_post";
        }
        postDto.setId(postId);
        postDto.setUrl(getUrl(postDto.getTitle()));
        postService.updatePost(postDto);
        return "redirect:/admin/posts";
    }

    //handler method to handle delete post request
    @GetMapping("/admin/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId){
        postService.deletePost(postId);
        return "redirect:/admin/posts";
    }

    @GetMapping("/admin/posts/{postUrl}/view")
    public String viewPost(@PathVariable("postUrl") String postUrl, Model model) {
        PostDto postDto = postService.findPostByUrl(postUrl);
        model.addAttribute("post", postDto);
        return "admin/view_post";
    }



    //handler method to handle search post request
    @GetMapping("/admin/posts/search")
    public String searchPosts(@RequestParam(value = "query") String query, Model model){
        List<PostDto> posts = postService.searchPosts(query);
        model.addAttribute("posts", posts);
        return "admin/posts";
    }

    public static String getUrl(String postTitle){
        String title = postTitle.trim().toLowerCase();
        String url = title.replaceAll("\\s+", "-");
        url = url.replaceAll("[^a-zA-Z0-9-]", "-");
        return url;
    }
}