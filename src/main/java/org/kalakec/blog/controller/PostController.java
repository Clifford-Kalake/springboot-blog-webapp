package org.kalakec.blog.controller;

import jakarta.validation.Valid;
import org.kalakec.blog.dto.PostDto;
import org.kalakec.blog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create handler method, GET request and return model and view
    @GetMapping("/admin/posts")
    public String posts(Model model) {
        List<PostDto> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "admin/posts";
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
        PostDto postDto = postService.findpostById(postId);
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
        try {
            PostDto postDto = postService.findpostByUrl(postUrl);
            model.addAttribute("post", postDto);
            return "admin/view_post"; // Make sure this template exists
        } catch (RuntimeException e) {
            System.out.println("Post not found with URL: " + postUrl);
            return "redirect:/admin/posts?error=notfound"; // Redirect to posts page with an error message
        }
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
        String url = title.replaceAll("\\s ", "-");
        url = url.replaceAll("[^a-zA-Z0-9-]", "-");
        return url;
    }
}

