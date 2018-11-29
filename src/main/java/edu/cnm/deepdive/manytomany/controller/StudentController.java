package edu.cnm.deepdive.manytomany.controller;

import edu.cnm.deepdive.manytomany.model.dao.ProjectRepository;
import edu.cnm.deepdive.manytomany.model.dao.StudentRepository;
import edu.cnm.deepdive.manytomany.model.entity.Project;
import edu.cnm.deepdive.manytomany.model.entity.Student;
import java.util.List;
import java.util.NoSuchElementException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(Student.class)
@RequestMapping("/students")
public class StudentController {

  private ProjectRepository projectRepository;
  private StudentRepository studentRepository;

  @Autowired
  public StudentController(ProjectRepository projectRepository,
      StudentRepository studentRepository) {
    this.projectRepository = projectRepository;
    this.studentRepository = studentRepository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Student> list() {
    return studentRepository.findAllByOrderByNameAsc();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Student> post (@RequestBody Student student) {
    studentRepository.save(student);
    return ResponseEntity.created(student.getHref()).body(student);
  }

  @GetMapping(value = "{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Student get(@PathVariable("studentId") long studentId) {
    return studentRepository.findById(studentId).get();
  }

  @GetMapping(value = "{studentId}/projects", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Project> projectList(@PathVariable("studentId") long studentId) {
    return get(studentId).getProjects();
  }

  @PostMapping(value = "{studentId}/projects", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Project> postProject(@PathVariable("studentId") long studentId,
      @RequestBody Project partialProject) {
    Project project = projectRepository.findById(partialProject.getId()).get();
    Student student = get(studentId);
    student.getProjects().add(project);
    studentRepository.save(student);
    return ResponseEntity.created(student.getHref()).body(project);
  }

  @Transactional
  @DeleteMapping(value = "{studentId}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("studentId") long studentId) {
    studentRepository.deleteById(studentId);
//    Student student = get(studentId);
//    List<Project> projects = student.getProjects();
//    for (Project project : projects) {
//      project.getStudents().remove(student);
//    }
//    projectRepository.saveAll(projects);
//    studentRepository.delete(student);
  }

  @DeleteMapping(value = "{studentId}/projects/{projectId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProject(@PathVariable("studentId") long studentID,
      @PathVariable("projectId") long projectId) {
    Student student = get(studentID);
    Project project = projectRepository.findById(projectId).get();
    if (student.getProjects().remove(project)) {
      studentRepository.save(student);
    } else {
      throw new NoSuchElementException();
    }
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found.")
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound() {
  }
}
