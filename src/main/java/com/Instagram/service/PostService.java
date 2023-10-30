package com.Instagram.service;

import com.Instagram.Exceptions.PostException;
import com.Instagram.Exceptions.UserException;
import com.Instagram.modal.Post;

import java.util.List;

public interface PostService {

    public Post createPost(Post post, Integer userId) throws UserException;

    public String deletePost(Integer postId, Integer userId) throws PostException, UserException;

    public List<Post> findPostByUserId(Integer userId) throws UserException;

    public Post findPostById(Integer postId) throws PostException;

    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException, UserException;

    public String savedPost(Integer postId, Integer userId) throws PostException, UserException;

    public String unSavedPost(Integer postId, Integer userId) throws PostException, UserException;

    public Post likePost(Integer postId, Integer userId) throws PostException, UserException;

    public Post unLikePost(Integer postId, Integer userId) throws PostException, UserException;

}
