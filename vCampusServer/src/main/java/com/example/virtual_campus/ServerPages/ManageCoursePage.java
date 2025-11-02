package com.example.virtual_campus.ServerPages;

import com.example.virtual_campus.controller.AuthController;
import com.example.virtual_campus.controller.CourseController;
import com.example.virtual_campus.controller.CourseController;
import com.example.virtual_campus.model.Book;
import com.example.virtual_campus.model.Course;
import com.example.virtual_campus.model.Course;
import com.example.virtual_campus.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class ManageCoursePage {

    @Autowired
    private CourseController courseController;
    @Autowired
    private CourseRepository courseRepository;

    public void work(String function, ObjectInputStream in, ObjectOutputStream out)
            throws IOException, ClassNotFoundException {
        if (function != null) {
            System.out.println("读入第二段指向:"+function);
            switch (function) {
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
                case "SelectSomeone":
                    System.out.println("正在查找对应数据中");
                    String id = (String) in.readObject();
                    String name = (String) in.readObject();
                    String teacher = (String) in.readObject();
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
                case "EditCourse":
                    System.out.println("正在修改中");
                    id = (String) in.readObject();
                    name = (String) in.readObject();
                    teacher = (String) in.readObject();
                    String credits = (String) in.readObject();
                    String schedule = (String) in.readObject();
                    String capacity = (String) in.readObject();
                    Long courseId = Long.parseLong(id);
                    int Credits = Integer.parseInt(credits);
                    int Capacity=Integer.parseInt(capacity);
                    out.writeObject(courseController.CourseEdit(courseId,name,teacher,Credits,schedule,Capacity));
                    System.out.println("修改完成");
                    out.flush();
                    break;
                case "DeleteCourse":
                    System.out.println("正在删除中");
                    String DelId = (String) in.readObject();
                    if(Objects.equals(DelId,"")){
                        out.writeObject(false);
                    }
                    else{
                        Long delid = Long.parseLong(DelId);
                        out.writeObject(courseController.CourseDelete(delid));
                    }
                    out.flush();
                    break;
                case "AddCourse":
                    System.out.println("正在添加中");
                    String AddId = (String) in.readObject();
                    name = (String) in.readObject();
                    teacher = (String) in.readObject();
                    credits = (String) in.readObject();
                    schedule = (String) in.readObject();
                    capacity = (String) in.readObject();
                    if(Objects.equals(AddId,"") || Objects.equals(name,"") || Objects.equals(teacher,"") || Objects.equals(credits,"") || Objects.equals(schedule,"") || Objects.equals(capacity,"")){
                        out.writeObject(false);
                    }
                    else{
                        Long addid = Long.parseLong(AddId);
                        Credits = Integer.parseInt(credits);
                        Capacity=Integer.parseInt(capacity);
                        out.writeObject(courseController.CourseAdd(addid,name,teacher,Credits,schedule,Capacity));
                    }
                    out.flush();
                    break;
            }
        } else {
            System.out.println("Unknown function: " + function);
        }
    }
}
