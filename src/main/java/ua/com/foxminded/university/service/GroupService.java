package ua.com.foxminded.university.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.formatter.LessonDtoFormatter;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {
    private final GroupDAO groupDAO;

    public Group save(Group group) {
        log.debug("save('{}') called", group);
        Group result = groupDAO.save(group);
        log.debug("save('{}') returned '{}'", group, result);
        return result;
    }

    public Group findById(Integer id) {
        log.debug("findById('{}') called", id);
        NotFoundException notFoundException = new NotFoundException("Group not found by id = " + id);
        Group result = groupDAO.findById(id).orElse(null);
        if (result == null) throw notFoundException;
        log.debug("findById('{}') returned '{}'", id, result);
        return result;
    }


    public List<Group> findAll() {
        log.debug("findAll() called");
        List<Group> result = groupDAO.findAll();
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    public long count() {
        log.debug("count() called");
        long result = groupDAO.count();
        log.debug("count() returned '{}'", result);
        return result;
    }

    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        groupDAO.deleteById(id);
        log.debug("deleteById('{}') was success", id);
    }

    public void delete(Group group) {
        log.debug("delete('{}') called", group);
        groupDAO.delete(group);
        log.debug("delete('{}') was success", group);
    }

    public void deleteAll() {
        log.debug("deleteAll() called");
        groupDAO.deleteAll();
        log.debug("deleteAll() was success");
    }
}
