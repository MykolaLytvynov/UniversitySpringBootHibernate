package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.ClassRoom;


import java.util.List;
import java.util.Optional;
import static java.util.Optional.ofNullable;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClassRoomDAO implements CrudOperations<ClassRoom, Integer> {
    private final SessionFactory sessionFactory;

    @Override
    public ClassRoom save(ClassRoom classRoom) {
        log.debug("save('{}') called", classRoom);
        Integer id;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            id = (Integer) session.save(classRoom);
            session.getTransaction().commit();
        }
        classRoom.setId(id);
        log.debug("save(Classroom) was success. Returned '{}'", classRoom);
        return classRoom;
    }


    @Override
    public Optional<ClassRoom> findById(Integer id) {
        log.debug("findById('{}') called", id);
        ClassRoom result;
        try (Session session = sessionFactory.openSession()) {
            result = session.get(ClassRoom.class, id);
        }
        log.debug("findById('{}') returned '{}'", id, result);
        return ofNullable(result);
    }


    @Override
    public List<ClassRoom> findAll() {
        log.debug("findAll() called");
        List<ClassRoom> result = null;
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(ClassRoom.class);
            criteria.addOrder(Order.asc("id"));
            result = criteria.list();
        }
        log.debug("findAll() returned '{}'", result);
        return result;
    }


    @Override
    public long count() {
        log.debug("count() called");
        long count;
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(ClassRoom.class);
            criteria.setProjection(Projections.rowCount());
            count = (Long) criteria.uniqueResult();
        }
        log.debug("count() returned '{}'", count);
        return count;
    }


    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(classRoom);
            session.getTransaction().commit();
        }
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(ClassRoom classRoom) {
        log.debug("delete('{}') called", classRoom);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(classRoom);
            session.getTransaction().commit();
        }
        log.debug("delete('{}') was success", classRoom);
    }


    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM classroom").executeUpdate();
            session.getTransaction().commit();
        }
        log.debug("deleteAll() was success");
    }


    @Override
    public void update(ClassRoom classRoom) {
        log.debug("update('{}') called", classRoom);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(classRoom);
            session.getTransaction().commit();
        }
        log.debug("update('{}') was success", classRoom);
    }

}
