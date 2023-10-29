package com.Instagram.service;

import com.Instagram.Exceptions.UserException;
import com.Instagram.dto.UserDto;
import com.Instagram.modal.User;
import com.Instagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) throws UserException {

        if(user.getEmail() == null || user.getPassword() == null || user.getUsername() == null
                || user.getName() == null) {
            throw new UserException("All fields are mandatory");
        }

        Optional<User> isEmailExist = userRepository.findByEmail(user.getEmail());
        if(isEmailExist.isPresent()) {
            throw new UserException("Email is already registered");
        }

        Optional<User> isUsernameExist = userRepository.findByUsername(user.getUsername());
        if(isUsernameExist.isPresent()) {
            throw new UserException("Username already taken");
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(newUser);
    }

    @Override
    public User findUserById(Integer userId) throws UserException {
        Optional<User> opt = userRepository.findById(userId);
        if(opt.isPresent()) return opt.get();
        throw new UserException("User does not exist with id : " + userId);
    }

    @Override
    public User findUserProfile(String token) throws UserException {
        return null;
    }

    @Override
    public User findUserByUsername(String username) throws UserException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            return user.get();
        }
        throw new UserException("User does not exist with username : " + username);
    }

    @Override
    public String followUser(Integer reqUserId, Integer followUserId) throws UserException {
        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        UserDto follower = new UserDto();
        follower.setName(reqUser.getName());
        follower.setEmail(reqUser.getEmail());
        follower.setUsername((reqUser.getUsername()));
        follower.setId(reqUser.getId());
        follower.setUserImage(reqUser.getImage());

        followUser.getFollower().add(follower);

        UserDto following = new UserDto();
        following.setName(followUser.getName());
        following.setEmail(followUser.getEmail());
        following.setUsername((followUser.getUsername()));
        following.setId(followUser.getId());
        following.setUserImage(followUser.getImage());

        reqUser.getFollowing().add(following);

        userRepository.save(reqUser);
        userRepository.save(followUser);

        return "You are following " + followUser.getUsername();
    }

    @Override
    public String unFollowUser(Integer reqUserId, Integer followUserId) throws UserException {
        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        Set<UserDto> followers = followUser.getFollower();

        for(UserDto user : followers) {
            if(user.getId() == reqUserId) {
                followers.remove(user);
                break;
            }
        }

        Set<UserDto> following = reqUser.getFollowing();

        for(UserDto user : following) {
            if(user.getId() == followUserId) {
                followers.remove(user);
                break;
            }
        }

        userRepository.save(reqUser);
        userRepository.save(followUser);

        return "You have unfollowed " + followUser.getUsername();
    }

    @Override
    public List<User> findUserByIds(List<Integer> userIds) throws UserException {
        List<User> users = userRepository.findAllUsersByUserIds(userIds);
        if(users.size() == 0) {
            throw new UserException("No user found");
        }
        return users;
    }

    @Override
    public List<User> searchUser(String query) throws UserException {
        List<User> users = userRepository.findByQuery(query);
        if(users.size() == 0) {
            throw new UserException("User not found");
        }
        return users;
    }

    @Override
    public User updateUserDetails(User updatedUser, User existingUser) throws UserException {
        return null;
    }

}
