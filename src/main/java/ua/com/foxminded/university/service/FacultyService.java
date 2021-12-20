package ua.com.foxminded.university.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.FacultyDAO;
import ua.com.foxminded.university.entities.Faculty;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacultyService {

    private final FacultyDAO facultyDAO;

    public Faculty save(Faculty faculty) {
        log.debug("save('{}') called", faculty);
        Faculty result = facultyDAO.save(faculty);
        log.debug("save('{}') was success. Returned '{}'", faculty, result);
        return result;
    }

    public Faculty findById(Integer id) {
        log.debug("findById('{}') called");
        NotFoundException notFoundException = new NotFoundException("Faculty not found by id = " + id);
        Faculty result = facultyDAO.findById(id).orElse(null);
        if (result == null) throw notFoundException;
        result.setName(result.getName().trim());
        result.setDescription(result.getDescription().trim());
        log.debug("findById('{}') returned '{}'", id, result);
        return result;
    }


    public List<Faculty> findAll() {
        log.debug("findAll() called");
        List<Faculty> result = facultyDAO.findAll();
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    public long count() {
        log.debug("count() called");
        long result = facultyDAO.count();
        log.debug("count() returned '{}'", result);
        return result;
    }

    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        facultyDAO.deleteById(id);
        log.debug("deleteById('{}') was success", id);
    }

    public void delete(Faculty faculty) {
        log.debug("delete('{}') called", faculty);
        facultyDAO.delete(faculty);
        log.debug("delete('{}') was success", faculty);
    }

    public void deleteAll() {
        log.debug("deleteAll() called");
        facultyDAO.deleteAll();
        log.debug("deleteAll() was success");
    }


    public void update(Faculty faculty) {
        log.debug("update('{}') called", faculty);
        facultyDAO.update(faculty);
        log.debug("update('{}') was success", faculty);
    }
}
