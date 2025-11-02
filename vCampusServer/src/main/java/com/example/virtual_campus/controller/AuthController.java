package com.example.virtual_campus.controller;

import com.example.virtual_campus.Utils.MD5;
import com.example.virtual_campus.model.Role;
import com.example.virtual_campus.model.RoleName;
import com.example.virtual_campus.model.Student;
import com.example.virtual_campus.model.User;
import com.example.virtual_campus.repository.RoleRepository;
import com.example.virtual_campus.repository.UserRepository;
import com.example.virtual_campus.repository.StudentRepository;
import com.example.virtual_campus.service.StudentService;
import com.example.virtual_campus.service.UserService;
import com.example.virtual_campus.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 用户认证控制器
 *
 * @author cui'jing'han
 * @since 2024/8/27 14:54
 */
@RestController
@RequestMapping("/auth")

public class AuthController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StudentRepository studentRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public boolean registerUser(String name, String password) {
        if (userRepository.existsByName(name)) {//用户名称已存在
            return false;
        }

        System.out.println("创建用户中");
        User user = new User();
        // 加密码
        user.setName(name);
        user.setPassword(MD5.encrypt(password));
        /*
        // 设置角色默认学生
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleName.ROLE_STUDENT)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);
         */
        user.setUsertype(2);
        user.setCollege("待填");
        user.setTelephone_number("待填");
        user.setBalance(0.0);
        userRepository.save(user);
        System.out.println("设置为学生");
        Student student = new Student();
        student.setUser(user);
        student.setGrade("待填");
        student.setMajor("待填");
        student.setSex("待填");
        student.setStatus("在读"); // 默认状态为在读
        studentRepository.save(student);
        System.out.println("学生创建成功");
        Long studentId = student.getId();
        System.out.println("StudentId:"+studentId);
        user.setUserId(Long.toString(studentId));
        userRepository.save(user);
        System.out.println("用户创建成功");
        return true;
    }
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public Integer login(String name,String password) {
        Optional<User> user = userRepository.findByName(name);
        if(user.isEmpty()) {
            System.out.println("用户不存在");
            return 0;
        }
        if(!Objects.equals(user.get().getPassword(),MD5.encrypt(password))) {
            System.out.println("密码错误");
            System.out.println("输入密码："+MD5.encrypt(password));
            System.out.println("正确密码："+user.get().getPassword());
            return 1;
        }
        System.out.println(user.get().getUsertype());
        if(user.get().getUsertype()==0){
            return 2;
        }
        if(user.get().getUsertype()==1){
            return 3;
        }
        if(user.get().getUsertype()==2){
            return 4;
        }
        return 0;
    }

    public Long getUserId(String name) {
        return userRepository.findByName(name).get().getId();
    }

    @Autowired
    private UserController usercontroller;
    @Autowired
    private UserService userservice;

    public Integer AuthorChangePassword(Long id,String pwd,String newpwd1,String newpwd2) {
        System.out.println(id);
        User userDetails = userRepository.findById(id).get();
        System.out.println(userDetails.getName());
        if(!Objects.equals(userDetails.getPassword(),MD5.encrypt(pwd))) {
            System.out.println("输入的原密码错误");
            System.out.println("错误密码"+pwd);
            System.out.println("正确密码"+userDetails.getPassword());
            return 0;
        }
        if(!Objects.equals(newpwd1,newpwd2)){
            System.out.println("两次填写新密码不一致");
            return 1;
        }
        System.out.println("正在修改密码");
        userDetails.setPassword(MD5.encrypt(newpwd1));
        usercontroller.updateUser(id,userDetails);
        System.out.println("修改密码成功");
        return 2;
    }

    public Boolean AuthorDeleteUser(Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) return false;
        userRepository.delete(user.get());
        return true;
    }

    public Boolean AuthorAddUser(Long id,String name,Integer usertype){
        if(userRepository.existsById(id)) return false;
        if(userRepository.existsByName(name)) return false;
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(MD5.encrypt("123456"));//默认密码
        user.setUsertype(usertype);
        user.setBalance(0.0);
        userRepository.save(user);
        return true;
    }

    public Optional<User> SearchById(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> SearchByName(String name){
        return userRepository.findByName(name);
    }

    public Boolean AuthorEdit(Long id, String name, Integer usertype){
        if(usertype == 4) return false;
        User user = userRepository.findById(id).get();
        user.setName(name);
        user.setUsertype(usertype);
        usercontroller.updateUser(id,user);
        return true;
    }
}