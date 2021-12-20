package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.Group;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Component
@Slf4j
@RequiredArgsConstructor
public class GroupDAO implements CrudOperations<Group, Integer> {
    private final SessionFactory sessionFactory;

    @Override
    public Group save(Group group) {
        log.debug("save('{}') called", group);
        Integer id;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            id = (Integer) session.save(group);
            session.getTransaction().commit();
        }
        group.setId(id);
        log.debug("save(group) returned '{}'", group);
        return group;
    }

    @Override
    public Optional<Group> findById(Integer id) {
        log.debug("findById('{}') called", id);
        Group result;
        try (Session session = sessionFactory.openSession()) {
            result = session.get(Group.class, id);
        }
        log.debug("findById('{}') returned '{}'", id, result);
        return ofNullable(result);
    }


    @Override
    public List<Group> findAll() {
        log.debug("findAll() called");
        List<Group> result = null;
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Group.class);
            result = criteria.list();
        }
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    @Override
    public long count() {
        log.debug("count() called");
        long result;
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Group.class);
            criteria.setProjection(Projections.rowCount());
            result = (Long) criteria.uniqueResult();
        }
        log.debug("count() returned '{}'", result);
        return result;
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        Group group = new Group();
        group.setId(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(group);
            session.getTransaction().commit();
        }
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(Group group) {
        log.debug("delete('{}') called", group);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(group);
            session.getTransaction().commit();
        }
        log.debug("delete('{}') was success", group);
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM groups").executeUpdate();
            session.getTransaction().commit();
        }
        log.debug("deleteAll() was success");
    }

    @Override
    public void update(Group group) {
        log.debug("update('{}') called", group);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(group);
            session.getTransaction().commit();
        }
        log.debug("update('{}') was success", group);
    }

}
