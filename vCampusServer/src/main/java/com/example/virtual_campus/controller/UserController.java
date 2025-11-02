package com.example.virtual_campus.controller;

import com.example.virtual_campus.model.User;
import com.example.virtual_campus.model.RoleName;
import com.example.virtual_campus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
/*
    @GetMapping
    public Integer SearchUsers(Long id, String name, String usertype) {
        if(id == 0 && name == "" && usertype == "")
        return userService.getAllUsers();
    }
*/
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{id}/assign-role")
    public ResponseEntity<?> assignRole(@PathVariable Long id, @RequestParam RoleName role) {
        userService.assignRoleToUser(id, role);
        return ResponseEntity.ok("Role assigned successfully");
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Validated @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Validated @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // 充值功能，增加用户对应余额
    @PutMapping("/{id}/balance")
    public ResponseEntity<User> recharge(@PathVariable Long id, @RequestParam Double balance) {
        User updatedUser = userService.AddBalance(id, balance);
        return ResponseEntity.ok(updatedUser);
    }
}