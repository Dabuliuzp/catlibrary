package com.example.virtual_campus.controller;

import com.example.virtual_campus.model.Course;
import com.example.virtual_campus.repository.CourseRepository;
import com.example.virtual_campus.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseRepository courseRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        Course newCourse = courseService.addCourse(course);
        return ResponseEntity.ok(newCourse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course courseDetails) {
        return courseService.updateCourse(id, courseDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully");
    }

    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<?> enrollCourse(@RequestParam Long studentId, @PathVariable Long courseId) {
        courseService.enrollCourse(studentId, courseId);
        return ResponseEntity.ok("Enrolled successfully");
    }

    @DeleteMapping("/{courseId}/drop")
    public ResponseEntity<?> dropCourse(@RequestParam Long studentId, @PathVariable Long courseId) {
        courseService.dropCourse(studentId, courseId);
        return ResponseEntity.ok("Dropped course successfully");
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Course>> getCoursesByStudent(@PathVariable Long studentId) {
        List<Course> courses = courseService.getCoursesByStudent(studentId);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{courseId}/students")
    public List<?> getStudentsByCourse(@PathVariable Long courseId) {
        return courseService.getStudentsByCourse(courseId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{courseId}/schedule")
    public ResponseEntity<Course> updateCourseSchedule(@PathVariable Long courseId, @RequestParam String newSchedule) {
        Course updatedCourse = courseService.updateCourseSchedule(courseId, newSchedule);
        return ResponseEntity.ok(updatedCourse);
    }

    public Optional<Course> SearchById(Long id){
        return courseRepository.findById(id);
    }

    public Optional<Course> SearchByName(String name){
        return courseRepository.findByName(name);
    }

    public Boolean CourseEdit(Long id, String name, String teacher, int credits, String schedule, int capacity){
        if(!courseRepository.existsById(id)) return false;
        Course course = courseRepository.findById(id).get();
        course.setName(name);
        course.setTeacher(teacher);
        course.setCredits(credits);
        course.setSchedule((schedule));
        course.setCapacity(capacity);
        updateCourse(id, course);
        return true;
    }

    public boolean CourseDelete(Long id){
        if(!courseRepository.existsById(id)) return false;
        Course course = courseRepository.findById(id).get();
        courseRepository.delete(course);
        return true;
    }

    public boolean CourseAdd(Long id, String name, String teacher, int credits, String schedule, int capacity){
        if(courseRepository.existsById(id)) return false;
        Course course = new Course();
        course.setId(id);
        course.setName(name);
        course.setTeacher(teacher);
        course.setCredits(credits);
        course.setSchedule((schedule));
        course.setCapacity(capacity);
        courseRepository.save(course);
        return true;
    }
}
