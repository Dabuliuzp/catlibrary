package com.example.virtual_campus.service;

import com.example.virtual_campus.model.Student;
import com.example.virtual_campus.model.User;
import com.example.virtual_campus.repository.StudentRepository;
import com.example.virtual_campus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public Student addStudent(Student student, Long userId) {// 添加学生
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        student.setUser(user);
        return studentRepository.save(student);
    }

    public Optional<Student> updateStudent(Long id, Student studentDetails) {// 更新学生
        return studentRepository.findById(id).map(student -> {
            student.setName(studentDetails.getName());
            student.setGrade(studentDetails.getGrade());
            student.setMajor(studentDetails.getMajor());
            student.setStatus(studentDetails.getStatus());
            return studentRepository.save(student);
        });
    }

    public List<Student> getAllStudents() {// 获取所有学生信息
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {// 获取指定学生信息
        return studentRepository.findById(id);
    }

    public void deleteStudent(Long id) {// 删除学生信息
        studentRepository.deleteById(id);
    }

    public void changeStudentStatus(Long studentId, String status) {// 改变学生学籍状态
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        student.setStatus(status);
        studentRepository.save(student);
    }
}
