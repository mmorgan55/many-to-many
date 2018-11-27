//package edu.cnm.deepdive.manytomany.controller;
//
//import edu.cnm.deepdive.manytomany.model.dao.ProjectRepository;
//import edu.cnm.deepdive.manytomany.model.dao.StudentRepository;
//import edu.cnm.deepdive.manytomany.model.entity.Student;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.ExposesResourceFor;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@ExposesResourceFor(Student.class)
//@RequestMapping("/students")
//public class StudentController {
//
//  private ProjectRepository projectRepository;
//  private StudentRepository studentRepository;
//
//  @Autowired
//
//  public StudentController(ProjectRepository projectRepository,
//      StudentRepository studentRepository) {
//    this.projectRepository = projectRepository;
//    this.studentRepository = studentRepository;
//  }
//
//  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//
//}
