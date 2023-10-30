package com.Instagram.controller;

import com.Instagram.Exceptions.PostException;
import com.Instagram.Exceptions.UserException;
import com.Instagram.modal.Post;
import com.Instagram.modal.User;
import com.Instagram.response.MessageResponse;
import com.Instagram.service.PostService;
import com.Instagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPostHandler(@RequestBody Post post, @RequestHeader("Authorization") String token)
            throws UserException {
        User user = userService.findUserProfile(token);
        Post createdPost = postService.createPost(post, user.getId());
        return new ResponseEntity<Post>(createdPost, HttpStatus.OK);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Post>> findPostByUserIdHandler(@PathVariable("userId") Integer userId) throws
            PostException, UserException {
        List<Post> posts = postService.findPostByUserId(userId);
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @GetMapping("/following/{ids}")
    public ResponseEntity<List<Post>> findAllPostsByUserIdsHandler(@PathVariable("ids") List<Integer> userIds) throws
            PostException, UserException {
        List<Post> posts = postService.findAllPostByUserIds(userIds);
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPostByPostIdHandler(@PathVariable("postId") Integer postId) throws PostException {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable("postId") Integer postId,
                                                @RequestHeader("Authorization") String token)
            throws UserException, PostException {

        User user = userService.findUserProfile(token);
        Post likedPost = postService.likePost(postId, user.getId());
        return new ResponseEntity<Post>(likedPost, HttpStatus.OK);
    }

    @PutMapping("/unlike/{postId}")
    public ResponseEntity<Post> unLikePostHandler(@PathVariable("postId") Integer postId,
                                                @RequestHeader("Authorization") String token)
            throws UserException, PostException {

        User user = userService.findUserProfile(token);
        Post unLikedPost = postService.unLikePost(postId, user.getId());
        return new ResponseEntity<Post>(unLikedPost, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<MessageResponse> deletePostHandler(@PathVariable("postId") Integer postId,
                                                             @RequestHeader("Authorization") String token)
            throws UserException, PostException {

        User user = userService.findUserProfile(token);
        String message = postService.deletePost(postId, user.getId());
        MessageResponse messageResponse = new MessageResponse(message);

        return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.ACCEPTED);
    }

    @PutMapping("/save_post/{postId}")
    public ResponseEntity<MessageResponse> savePostHandler(@PathVariable("postId") Integer postId,
                                                @RequestHeader("Authorization") String token)
            throws UserException, PostException {

        User user = userService.findUserProfile(token);
        System.out.println(user);
        String message = postService.savedPost(postId, user.getId());

        MessageResponse messageResponse = new MessageResponse(message);

        return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.ACCEPTED);
    }

    @PutMapping("/unsave_post/{postId}")
    public ResponseEntity<MessageResponse> unSavePostHandler(@PathVariable("postId") Integer postId,
                                                           @RequestHeader("Authorization") String token)
            throws UserException, PostException {

        User user = userService.findUserProfile(token);
        String message = postService.unSavedPost(postId, user.getId());
        MessageResponse messageResponse = new MessageResponse(message);

        return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.ACCEPTED);
    }

}
