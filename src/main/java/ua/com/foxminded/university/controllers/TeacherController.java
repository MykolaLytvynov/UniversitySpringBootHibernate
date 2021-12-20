package ua.com.foxminded.university.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.university.dao.EmployeeDAO;
import ua.com.foxminded.university.entities.Position;
import ua.com.foxminded.university.entities.Subject;
import ua.com.foxminded.university.entities.person.Employee;
import ua.com.foxminded.university.dto.TeacherDto;
import ua.com.foxminded.university.service.EmployeeService;
import ua.com.foxminded.university.service.PositionService;
import ua.com.foxminded.university.service.SubjectService;


import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {
    private final EmployeeService employeeService;
    private final SubjectService subjectService;
    private final PositionService positionService;
    private final EmployeeDAO employeeDAO;

    @GetMapping
    public String getAll(Model model) {
        log.info("getAll() called");
        List<TeacherDto> result = employeeService.getAllTeacher();
        model.addAttribute("teachers", result);
        log.info("Exit: {}", result);
        return "/employees/teachers/getAll";
    }

    @GetMapping("/new")
    public String getPageCreateTeacher(Model model) {
        log.info("getPageCreateTeacher() called");
        model.addAttribute("teacher", new TeacherDto());
        List<Position> allPosition = positionService.findAll();
        model.addAttribute("positions", allPosition);
        List<Subject> subjectsWithoutTeacher = subjectService.getSubjectsWithoutTeacher();
        model.addAttribute("subjectsWithoutTeacher", subjectsWithoutTeacher);
        log.info("Exit: {}, {}", allPosition, subjectsWithoutTeacher);
        return "/employees/teachers/new";
    }

    @PostMapping
    public String create(@ModelAttribute("teacher") Employee teacher,
                         @RequestParam("subjectId") Integer subjectId, Model model) {
        log.info("Enter: create('{}', '{}')", teacher, subjectId);
        Employee employee = employeeService.save(teacher);
        Subject subject = subjectService.findById(subjectId);
        subject.setTeacher(employee);
        subjectService.update(subject);
        log.info("Exit: {}", employee);
        return "redirect:/employees/" + employee.getId();
    }

}
