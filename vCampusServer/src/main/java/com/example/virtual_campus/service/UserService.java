package com.example.virtual_campus.service;

import com.example.virtual_campus.model.Role;
import com.example.virtual_campus.model.RoleName;
import com.example.virtual_campus.model.User;
import com.example.virtual_campus.exception.ResourceNotFoundException;
import com.example.virtual_campus.repository.RoleRepository;
import com.example.virtual_campus.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
/*
    public User getUserById(long id) {
        User user =userRepository.getOne(id);
        Optional<User> optionalUser = userRepository.findById(id);
        try{
            optionalUser.get();
        }catch (Exception ex){
            return null;
        }
        return optionalUser.get();
    }
 */

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        System.out.println("查找用户。。。");
        User user = getUserById(id);
        user.setName(userDetails.getName());
        user.setBalance(userDetails.getBalance());
        user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public User AddBalance(Long id, Double balance) {
        User user = getUserById(id);
        user.setBalance(user.getBalance() + balance);
        return userRepository.save(user);
    }

    public Optional<User> findByName(String name) {// 查找用户
        return userRepository.findByName(name);
    }

    public void assignRoleToUser(Long userId, RoleName roleName) {// 分配角色
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        //user.getRoles().add(role);
        userRepository.save(user);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public void logoutUser(String name) {
        // 实现用户登出逻辑，通常由前端和会话管理处理
    }

    public Set<String> getUserRoles(String username) {
        Optional<User> userOptional = userRepository.findByName(username);
        if (userOptional.isPresent()) {
            //return userOptional.get().getRoleNames();
            return null;
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
