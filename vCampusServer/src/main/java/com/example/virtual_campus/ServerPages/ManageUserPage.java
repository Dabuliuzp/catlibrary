package com.example.virtual_campus.ServerPages;

import com.example.virtual_campus.controller.AuthController;
import com.example.virtual_campus.controller.UserController;
import com.example.virtual_campus.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class ManageUserPage {

    @Autowired
    private AuthController authController;
    @Autowired
    private UserController userController;

    public void work(String function, ObjectInputStream in, ObjectOutputStream out)
            throws IOException, ClassNotFoundException {
        if (function != null) {
            System.out.println("读入第二段指向:"+function);
            switch (function) {
                case "requestAllClass":
                    System.out.println("请求所有用户信息中");
                    List<User> alluser = userController.getAllUsers();
                    out.writeObject((Integer)alluser.size());
                    for(User user : alluser){
                        out.writeObject(user.getId());
                        out.writeObject(user.getName());
                        out.writeObject(user.getUsertype());
                    }
                    System.out.println("请求完成");
                    out.flush();
                    break;
                case "SelectSomeone":
                    System.out.println("正在查找对应数据中");
                    String id = (String) in.readObject();
                    String name = (String) in.readObject();
                    String usertype = (String) in.readObject();
                    if(Objects.equals(id,"") && Objects.equals(name,"") && Objects.equals(usertype,"")){
                        out.writeObject(1);//输入为空
                    }
                    else if(!Objects.equals(id,"")){
                        Long userId = Long.parseLong(id);
                        Optional<User> SearchuserId = authController.SearchById(userId);
                        if(SearchuserId.isEmpty()) out.writeObject(2);//输入有误
                        else{
                            out.writeObject(3);
                            out.writeObject(1);
                            out.writeObject(SearchuserId.get().getId());
                            out.writeObject(SearchuserId.get().getName());
                            out.writeObject(SearchuserId.get().getUsertype());
                        }
                    }
                    else if(!Objects.equals(name,"")){
                        Optional<User> SearchuserId = authController.SearchByName(name);
                        if(SearchuserId.isEmpty()) out.writeObject(2);//输入有误
                        else{
                            out.writeObject(3);
                            out.writeObject(1);
                            out.writeObject(SearchuserId.get().getId());
                            out.writeObject(SearchuserId.get().getName());
                            out.writeObject(SearchuserId.get().getUsertype());
                        }
                    }
                    else if(!Objects.equals(usertype,"")){
                        Integer userType = 3;
                        if(Objects.equals(usertype,"管理员")){
                            userType = 0;
                        }
                        else if(Objects.equals(usertype,"教师")) {
                            userType = 1;
                        }
                        else if(Objects.equals(usertype,"学生")){
                            userType = 2;
                        }
                        if(userType == 3){
                            out.writeObject(2);//输入有误
                        }
                        else{
                            out.writeObject(3);
                            List<User> someuser = userController.getAllUsers();
                            Integer someusernum = 0;
                            for(User user : someuser){
                                if(Objects.equals(user.getUsertype(),userType)){
                                    someusernum ++;
                                }
                            }
                            out.writeObject(someusernum);
                            for(User user : someuser){
                                if(Objects.equals(user.getUsertype(),userType)){
                                    out.writeObject(user.getId());
                                    out.writeObject(user.getName());
                                    out.writeObject(user.getUsertype());
                                }
                            }
                        }
                    }
                    System.out.println("查找完成");
                    out.flush();
                    break;
                case "edit":
                    System.out.println("正在修改中");
                    id = (String) in.readObject();
                    name = (String) in.readObject();
                    usertype = (String) in.readObject();
                    Long userId = Long.parseLong(id);
                    Integer userType = 3;
                    if(Objects.equals(usertype,"管理员")){
                        userType = 0;
                    }
                    else if(Objects.equals(usertype,"教师")) {
                        userType = 1;
                    }
                    else if(Objects.equals(usertype,"学生")){
                        userType = 2;
                    }
                    out.writeObject(authController.AuthorEdit(userId,name,userType));
                    System.out.println("修改完成");
                    out.flush();
                    break;
                case "Delete":
                    System.out.println("正在删除中");
                    String DelId = (String) in.readObject();
                    if(Objects.equals(DelId,"")){
                        out.writeObject(false);
                    }
                    else{
                        Long delid = Long.parseLong(DelId);
                        out.writeObject(authController.AuthorDeleteUser(delid));
                    }
                    out.flush();
                    break;
                case "AddUsers":
                    System.out.println("正在添加中");
                    String AddId = (String) in.readObject();
                    name = (String) in.readObject();
                    usertype = (String) in.readObject();
                    if(Objects.equals(AddId,"")){
                        out.writeObject(false);
                    }
                    else{
                        Long addid = Long.parseLong(AddId);
                        userType = 3;
                        if(Objects.equals(usertype,"管理员")){
                            userType = 0;
                        }
                        else if(Objects.equals(usertype,"教师")) {
                            userType = 1;
                        }
                        else if(Objects.equals(usertype,"学生")){
                            userType = 2;
                        }
                        out.writeObject(authController.AuthorAddUser(addid,name,userType));
                    }
                    out.flush();
                    break;
            }
        } else {
            System.out.println("Unknown function: " + function);
        }
    }
}