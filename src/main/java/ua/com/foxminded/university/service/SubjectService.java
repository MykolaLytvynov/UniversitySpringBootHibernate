package ua.com.foxminded.university.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.SubjectDAO;
import ua.com.foxminded.university.entities.Subject;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectDAO subjectDAO;

    public Subject save(Subject subject) {
        log.debug("save('{}') called", subject);
        Subject result = subjectDAO.save(subject);
        log.debug("save('{}') returned '{}'", subject, result);
        return result;
    }

    public Subject findById(Integer id) {
        log.debug("findById('{}') called", id);
        NotFoundException notFoundException = new NotFoundException("Subject not found by id = " + id);
        Subject result = subjectDAO.findById(id).orElse(null);
        if (result == null) throw notFoundException;
        result.setName(result.getName().trim());
        result.setDescription(result.getDescription().trim());
        log.debug("findById('{}') returned '{}'", id, result);
        return result;
    }

    public List<Subject> findAll() {
        log.debug("findAll() called");
        List<Subject> result = subjectDAO.findAll();
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    public long count() {
        log.debug("count() called");
        long result = subjectDAO.count();
        log.debug("count() returned '{}'", result);
        return result;
    }

    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        subjectDAO.deleteById(id);
        log.debug("deleteById('{}') was success", id);
    }

    public void delete(Subject subject) {
        log.debug("delete('{}') called", subject);
        subjectDAO.delete(subject);
        log.debug("delete('{}') was success", subject);
    }

    public void deleteAll() {
        log.debug("deleteAll() called");
        subjectDAO.deleteAll();
        log.debug("deleteAll() was success");
    }

    public void update(Subject subject) {
        log.debug("update('{}') called", subject);
        subjectDAO.update(subject);
        log.debug("update('{}') was success", subject);
    }

    public List<Subject> getAllSubjectsOneTeacher(Integer id) {
        log.debug("getAllSubjectsOneTeacher('{}') called", id);
        List<Subject> result = subjectDAO.getAllSubjectsOneTeacher(id);
        log.debug("update('{}') returned '{}'", id, result);
        return result;
    }

    public List<Subject> getSubjectsWithoutTeacher() {
        log.debug("getSubjectsWithoutTeacher() called");
        List<Subject> result = subjectDAO.getSubjectsWithoutTeacher();
        log.debug("getSubjectsWithoutTeacher() returned '{}'", result);
        return result;
    }
}
