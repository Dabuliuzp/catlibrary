package com.example.virtual_campus.ServerPages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.example.virtual_campus.model.Product;
import com.example.virtual_campus.model.User;
import com.example.virtual_campus.repository.UserRepository;
import com.example.virtual_campus.service.CourseService;
import com.example.virtual_campus.service.StudentService;
import com.example.virtual_campus.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentSchoolPage {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public void work(String function, ObjectInputStream in, ObjectOutputStream out)
            throws IOException, ClassNotFoundException {
        if (function != null) {
            System.out.println("读入第二段指向:"+function);
            switch (function) {
                case "GetMySchool":
                    System.out.println("查看信息中");
                    Long userid = (Long) in.readObject();
                    User user = userRepository.findById(userid).get();
                    out.writeObject(user.getName());
                    out.writeObject(user.getCollege());
                    out.writeObject(user.getUserId());
                    out.writeObject(user.getBalance());
                    System.out.println("查看完成");
                    out.flush();
                    break;
                case "GetBalance":
                    System.out.println("充值中");
                    userid = (Long) in.readObject();
                    String money = (String) in.readObject();
                    Double Money = Double.parseDouble(money);
                    user = userRepository.findById(userid).get();
                    userService.AddBalance(userid, Money);
                    System.out.println("充值完成");
                    out.flush();
                    break;
            }
        } else {
            System.out.println("Unknown function: " + function);
        }
    }
}
