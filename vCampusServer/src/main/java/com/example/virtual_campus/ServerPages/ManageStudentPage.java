package com.example.virtual_campus.ServerPages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.virtual_campus.controller.StudentController;
import com.example.virtual_campus.controller.UserController;
import com.example.virtual_campus.model.Student;
import com.example.virtual_campus.model.User;
import com.example.virtual_campus.repository.StudentRepository;
import com.example.virtual_campus.repository.UserRepository;
import com.example.virtual_campus.service.StudentService;
import com.example.virtual_campus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManageStudentPage {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserController userController;
    @Autowired
    private StudentController studentController;
    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;

    public void work(String function, ObjectInputStream in, ObjectOutputStream out)
            throws IOException, ClassNotFoundException {
        if (function != null) {
            System.out.println("读入第二段指向:"+function);
            switch (function) {
                case "requestAllClass":
                    System.out.println("请求所有用户信息中");
                    List<Student> allstudent = studentController.getAllStudents();
                    out.writeObject((Integer)allstudent.size());
                    for(Student student : allstudent){
                        out.writeObject(student.getId());
                        out.writeObject(student.getName());
                        out.writeObject(student.getGrade());
                        out.writeObject(student.getMajor());
                        out.writeObject(student.getStatus());
                    }
                    System.out.println("请求完成");
                    out.flush();
                    break;
                case "SelectSomeone":
                    System.out.println("正在查找对应数据中");
                    String id = (String) in.readObject();
                    String name = (String) in.readObject();
                    String grade = (String) in.readObject();
                    String major = (String) in.readObject();
                    String status = (String) in.readObject();
                    if(Objects.equals(id,"") && Objects.equals(name,"") && Objects.equals(grade,"") && Objects.equals(major,"") && Objects.equals(status,"")){
                        out.writeObject(1);//输入为空
                    }
                    else if(!Objects.equals(id,"")){
                        Long Id = Long.parseLong(id);
                        allstudent = studentController.getAllStudents();
                        Integer somestudent = 0;
                        for(Student student : allstudent){
                            if(Objects.equals(student.getId(),Id)){
                                somestudent ++;
                            }
                        }
                        if(somestudent == 0){
                            out.writeObject(2);
                        }
                        else{
                            out.writeObject(3);
                            out.writeObject(somestudent);
                            for(Student student : allstudent){
                                if(Objects.equals(student.getId(),Id)){
                                    out.writeObject(student.getId());
                                    out.writeObject(student.getName());
                                    out.writeObject(student.getGrade());
                                    out.writeObject(student.getMajor());
                                    out.writeObject(student.getStatus());
                                }
                            }
                        }
                    }
                    else if(!Objects.equals(name,"")){
                        allstudent = studentController.getAllStudents();
                        Integer somestudent = 0;
                        for(Student student : allstudent){
                            if(Objects.equals(student.getName(),name)){
                                somestudent ++;
                            }
                        }
                        if(somestudent == 0){
                            out.writeObject(2);
                        }
                        else{
                            out.writeObject(3);
                            out.writeObject(somestudent);
                            for(Student student : allstudent){
                                if(Objects.equals(student.getName(),name)){
                                    out.writeObject(student.getId());
                                    out.writeObject(student.getName());
                                    out.writeObject(student.getGrade());
                                    out.writeObject(student.getMajor());
                                    out.writeObject(student.getStatus());
                                }
                            }
                        }
                    }
                    else if(!Objects.equals(grade,"")){
                        allstudent = studentController.getAllStudents();
                        Integer somestudent = 0;
                        for(Student student : allstudent){
                            if(Objects.equals(student.getGrade(),grade)){
                                somestudent ++;
                            }
                        }
                        if(somestudent == 0){
                            out.writeObject(2);
                        }
                        else{
                            out.writeObject(3);
                            out.writeObject(somestudent);
                            for(Student student : allstudent){
                                if(Objects.equals(student.getGrade(),grade)){
                                    out.writeObject(student.getId());
                                    out.writeObject(student.getName());
                                    out.writeObject(student.getGrade());
                                    out.writeObject(student.getMajor());
                                    out.writeObject(student.getStatus());
                                }
                            }
                        }
                    }
                    else if(!Objects.equals(major,"")){
                        allstudent = studentController.getAllStudents();
                        Integer somestudent = 0;
                        for(Student student : allstudent){
                            if(Objects.equals(student.getMajor(),major)){
                                somestudent ++;
                            }
                        }
                        if(somestudent == 0){
                            out.writeObject(2);
                        }
                        else{
                            out.writeObject(3);
                            out.writeObject(somestudent);
                            for(Student student : allstudent){
                                if(Objects.equals(student.getMajor(),major)){
                                    out.writeObject(student.getId());
                                    out.writeObject(student.getName());
                                    out.writeObject(student.getGrade());
                                    out.writeObject(student.getMajor());
                                    out.writeObject(student.getStatus());
                                }
                            }
                        }
                    }
                    else if(!Objects.equals(status,"")){
                        allstudent = studentController.getAllStudents();
                        Integer somestudent = 0;
                        for(Student student : allstudent){
                            if(Objects.equals(student.getStatus(),status)){
                                somestudent ++;
                            }
                        }
                        if(somestudent == 0){
                            out.writeObject(2);
                        }
                        else{
                            out.writeObject(3);
                            out.writeObject(somestudent);
                            for(Student student : allstudent){
                                if(Objects.equals(student.getStatus(),status)){
                                    out.writeObject(student.getId());
                                    out.writeObject(student.getName());
                                    out.writeObject(student.getGrade());
                                    out.writeObject(student.getMajor());
                                    out.writeObject(student.getStatus());
                                }
                            }
                        }
                    }
                    System.out.println("查找完成");
                    out.flush();
                    break;
                case "StudentEdit":
                    id = (String) in.readObject();
                    name = (String) in.readObject();
                    grade = (String) in.readObject();
                    major = (String) in.readObject();
                    status = (String) in.readObject();
                    Long Id = Long.parseLong(id);
                    Optional<Student> student = studentRepository.findById(Id);
                    if(student.isEmpty()){
                        out.writeObject(false);
                    }
                    else{
                        student.get().setName(name);
                        student.get().setGrade(grade);
                        student.get().setMajor(major);
                        student.get().setStatus(status);
                        studentService.updateStudent(Id, student.get());
                        out.writeObject(true);
                    }
            }
        } else {
            System.out.println("Unknown function: " + function);
        }
    }
}
