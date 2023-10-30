package com.Instagram.service;

import com.Instagram.Exceptions.PostException;
import com.Instagram.Exceptions.UserException;
import com.Instagram.dto.UserDto;
import com.Instagram.modal.Post;
import com.Instagram.modal.User;
import com.Instagram.repository.PostRepository;
import com.Instagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Post createPost(Post post, Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setUserImage(user.getImage());
        userDto.setEmail(user.getEmail());

        post.setUser(userDto);
        Post createdPost = postRepository.save(post);
        return createdPost;
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(post.getUser().getId().equals(user.getId())) {
            postRepository.deleteById(post.getId());
            return "Post successfully deleted";
        }
        throw new PostException("User not authorise to delete post.");
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) throws UserException {
        List<Post> posts = postRepository.findByUserId(userId);
        if(posts.size() == 0) {
            throw new UserException("This user does not have any posts.");
        }
        return posts;
    }

    @Override
    public Post findPostById(Integer postId) throws PostException {
        Optional<Post> opt = postRepository.findById(postId);
        if(opt.isPresent()) return opt.get();
        throw new PostException("Post not found with id : " + postId);
    }

    @Override
    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException, UserException {
        List<Post> posts = postRepository.findAllPostsByUserIds(userIds);
        if(posts.size() == 0) throw new PostException("No posts available");
        return posts;
    }

    @Override
    public String savedPost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(!user.getSavedPost().contains(post)) {
            user.getSavedPost().add(post);
            userRepository.save(user);
        }
        return "Post saved successfully";
    }

    @Override
    public String unSavedPost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(user.getSavedPost().contains(post)) {
            user.getSavedPost().remove(post);
            userRepository.save(user);
        }
        return "Post unsaved successfully";
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setUserImage(user.getImage());
        userDto.setEmail(user.getEmail());

        post.getLikedByUsers().add(userDto);

        return postRepository.save(post);
    }

    @Override
    public Post unLikePost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setUserImage(user.getImage());
        userDto.setEmail(user.getEmail());

        post.getLikedByUsers().remove(userDto);

        return postRepository.save(post);
    }
}
