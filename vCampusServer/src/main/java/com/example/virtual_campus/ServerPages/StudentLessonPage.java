package com.example.virtual_campus.ServerPages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.virtual_campus.controller.CourseController;
import com.example.virtual_campus.model.Course;
import com.example.virtual_campus.model.Product;
import com.example.virtual_campus.model.User;
import com.example.virtual_campus.repository.CourseRepository;
import com.example.virtual_campus.repository.StudentRepository;
import com.example.virtual_campus.repository.UserRepository;
import com.example.virtual_campus.service.UserService;
import com.example.virtual_campus.service.StudentService;
import com.example.virtual_campus.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentLessonPage {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseController courseController;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    public void work(String function, ObjectInputStream in, ObjectOutputStream out)
            throws IOException, ClassNotFoundException {
        if (function != null) {
            System.out.println("读入第二段指向:"+function);
            switch (function) {
                case "addMyLesson":
                    System.out.println("添加课程");
                    Long userid = (Long) in.readObject();
                    String id = (String) in.readObject();
                    if (Objects.equals(id,"")) {
                        out.writeObject(1);
                        out.flush();
                        break;
                    }
                    Long Id = Long.parseLong(id);
                    String studentid = userRepository.findById(userid).get().getUserId();
                    Long StudentId = Long.parseLong(studentid);
                    System.out.println("StudentId:"+StudentId);
                    int res = courseService.enrollCourse(StudentId, Id);
                    out.writeObject(res);
                    System.out.println("添加成功");
                    out.flush();
                    break;
                case "requestAllClass":
                    System.out.println("请求所有用户信息中");
                    List<Course> allcourse = courseController.getAllCourses();
                    out.writeObject((Integer)allcourse.size());
                    for(Course course : allcourse){
                        out.writeObject(course.getId());
                        out.writeObject(course.getName());
                        out.writeObject(course.getTeacher());
                        out.writeObject(course.getCredits());
                        out.writeObject(course.getSchedule());
                        out.writeObject(course.getCapacity());
                    }
                    System.out.println("请求完成");
                    out.flush();
                    break;
                case "requestAllClassForStudent":
                    System.out.println("请求课表中");
                    userid = (Long) in.readObject();
                    User user = userRepository.findById(userid).get();
                    Long Studentid = Long.parseLong(user.getUserId());
                    System.out.println("Studentid:"+Studentid);
                    allcourse = courseController.getCoursesByStudent(Studentid).getBody();
                    out.writeObject((Integer)allcourse.size());
                    for(Course course : allcourse){
                        out.writeObject(course.getId());
                        out.writeObject(course.getName());
                        out.writeObject(course.getTeacher());
                        out.writeObject(course.getCredits());
                        out.writeObject(course.getSchedule());
                        out.writeObject(course.getCapacity());
                    }
                    System.out.println("请求完成");
                    out.flush();
                    break;
                case "SelectSomeone":
                    System.out.println("正在查找对应数据中");
                    id = (String) in.readObject();
                    String name = (String) in.readObject();
                    String teacher = (String) in.readObject();
                    String ischoose = (String) in.readObject();
                    if(Objects.equals(id,"") && Objects.equals(name,"")){
                        out.writeObject(1);//输入为空
                    }
                    else if(!Objects.equals(id,"")){
                        Long courseId = Long.parseLong(id);
                        Optional<Course> SearchcourseId = courseController.SearchById(courseId);
                        if(SearchcourseId.isEmpty()) out.writeObject(2);//输入有误
                        else{
                            out.writeObject(3);
                            out.writeObject(1);
                            out.writeObject(SearchcourseId.get().getId());
                            out.writeObject(SearchcourseId.get().getName());
                            out.writeObject(SearchcourseId.get().getTeacher());
                            out.writeObject(SearchcourseId.get().getCredits());
                            out.writeObject(SearchcourseId.get().getSchedule());
                            out.writeObject(SearchcourseId.get().getCapacity());
                        }
                    }
                    else if(!Objects.equals(name,"")){
                        allcourse = courseRepository.findAll();
                        Integer somecourse = 0;
                        for(Course course : allcourse){
                            if(Objects.equals(course.getName(),name)){
                                somecourse ++;
                            }
                        }
                        if(somecourse == 0){
                            out.writeObject(2);
                        }
                        else{
                            out.writeObject(3);
                            out.writeObject(somecourse);
                            for(Course course : allcourse){
                                if(Objects.equals(course.getName(),name)){
                                    out.writeObject(course.getId());
                                    out.writeObject(course.getName());
                                    out.writeObject(course.getTeacher());
                                    out.writeObject(course.getCredits());
                                    out.writeObject(course.getSchedule());
                                    out.writeObject(course.getCapacity());
                                }
                            }
                        }
                    }
                    else if(!Objects.equals(teacher,"")){
                        allcourse = courseRepository.findAll();
                        Integer somecourse = 0;
                        for(Course course : allcourse){
                            if(Objects.equals(course.getTeacher(),teacher)){
                                somecourse ++;
                            }
                        }
                        if(somecourse == 0){
                            out.writeObject(2);
                        }
                        else{
                            out.writeObject(3);
                            out.writeObject(somecourse);
                            for(Course course : allcourse){
                                if(Objects.equals(course.getTeacher(),teacher)){
                                    out.writeObject(course.getId());
                                    out.writeObject(course.getName());
                                    out.writeObject(course.getTeacher());
                                    out.writeObject(course.getCredits());
                                    out.writeObject(course.getSchedule());
                                    out.writeObject(course.getCapacity());
                                }
                            }
                        }
                    }
                    System.out.println("查找完成");
                    out.flush();
                    break;
                case "quitMyLesson":
                    System.out.println("LessonList里AddMyLesson");
                    userid = (Long) in.readObject();
                    id = (String) in.readObject();
                    if (Objects.equals(id,"")) {
                        out.writeObject(2);
                        out.flush();
                        break;
                    }
                    Id = (Long) Long.parseLong(id);
                    studentid = userRepository.findById(userid).get().getUserId();
                    StudentId = Long.parseLong(studentid);
                    courseService.dropCourse(StudentId, Id);
                    out.writeObject(3);
                    out.flush();
                    break;
            }
        } else {
            System.out.println("Unknown function: " + function);
        }
    }
}
