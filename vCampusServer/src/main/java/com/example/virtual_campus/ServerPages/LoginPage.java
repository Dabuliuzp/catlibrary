package com.example.virtual_campus.ServerPages;

import com.example.virtual_campus.Utils.JWTUtils;
import com.example.virtual_campus.controller.AuthController;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.example.virtual_campus.Server;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginPage {

    @Autowired
    private AuthController authController;

     public void work(String function, ObjectInputStream in, ObjectOutputStream out)
            throws IOException, ClassNotFoundException {
        if (function != null) {
            System.out.println("读入第二段指向:"+function);
            String userId = null;
            String password = null;
            switch (function) {
                case "Login":
                    userId = (String) in.readObject();
                    password = (String) in.readObject();
                    System.out.println(userId);
                    System.out.println(password);
                    Integer LoginConfirm = authController.login(userId, password);
                    out.writeObject(LoginConfirm);
                    if(LoginConfirm>1) {
                        Long currentUserId = authController.getUserId(userId);
                        // 生成JWT令牌
                        String token = JWTUtils.generateToken(userId, currentUserId, LoginConfirm);
                        System.out.println("生成的JWT令牌: " + token);
                        // 发送用户ID和JWT令牌
                        out.writeObject(currentUserId);
                        out.writeObject(token);
                    }
                    out.flush();
                    break;
                case "Register":
                    userId = (String) in.readObject();
                    password = (String) in.readObject();
                    System.out.println(userId);
                    System.out.println(password);
                    boolean isSuccess = authController.registerUser(userId, password);
                    out.writeObject(isSuccess);
                    out.flush();
                    break;
                case "ChangePassword":
                    Long id = (Long) in.readObject();
                    String pwd = (String) in.readObject();
                    String newpwd1 = (String) in.readObject();
                    String newpwd2 = (String) in.readObject();
                    Integer changepasswordconfirm = authController.AuthorChangePassword(id, pwd, newpwd1, newpwd2);
                    out.writeObject(changepasswordconfirm);
                    out.flush();
                    break;
            }
        } else {
            System.out.println("Unknown function: " + function);
        }
    }
}
