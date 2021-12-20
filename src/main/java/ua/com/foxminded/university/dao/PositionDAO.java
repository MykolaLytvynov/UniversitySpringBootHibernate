package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.Position;


import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Component
@Slf4j
@RequiredArgsConstructor
public class PositionDAO implements CrudOperations<Position, Integer> {
    private final SessionFactory sessionFactory;

    @Override
    public Position save(Position position) {
        log.debug("save('{}') called", position);
        Integer id;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            id = (Integer) session.save(position);
            session.getTransaction().commit();
        }
        position.setId(id);
        log.debug("save(position) returned '{}'", position);
        return position;
    }

    @Override
    public Optional<Position> findById(Integer id) {
        log.debug("findById('{}') called", id);
        Position result;
        try (Session session = sessionFactory.openSession()) {
            result = session.get(Position.class, id);
        }
        log.debug("findById('{}') returned '{}'", id, result);
        return ofNullable(result);
    }


    @Override
    public List<Position> findAll() {
        log.debug("findAll() called");
        List<Position> result = null;
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Position.class);
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
            Criteria criteria = session.createCriteria(Position.class);
            criteria.setProjection(Projections.rowCount());
            count = (Long) criteria.uniqueResult();
        }
        log.debug("count() returned '{}'", count);
        return count;
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        Position position = new Position();
        position.setId(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(position);
            session.getTransaction().commit();
        }
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(Position position) {
        log.debug("delete('{}') called", position);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(position);
            session.getTransaction().commit();
        }
        log.debug("delete('{}') was success", position);
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM positions").executeUpdate();
            session.getTransaction().commit();
        }
        log.debug("deleteAll() was success");
    }

    @Override
    public void update(Position position) {
        log.debug("update('{}') called", position);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(position);
            session.getTransaction().commit();
        }
        log.debug("update('{}') was success", position);
    }
}
