package dao;

import entities.SpaceShip;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class SpaceShipDao {

    private final SpaceMarineDao spaceMarineDAO = new SpaceMarineDao();

    public Optional<SpaceShip> findById(Long id) {
        try (final Session session = HibernateUtil.getSessionFactory().openSession()) {
            final Transaction transaction = session.beginTransaction();
            final SpaceShip ship = session.find(SpaceShip.class, id);
            transaction.commit();

            return Optional.ofNullable(ship);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public long save(final SpaceShip ship) {
        Transaction transaction = null;
        try (final Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            final Long id = (Long) session.save(ship);
            transaction.commit();

            return id;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw ex;
        }
    }


    public void update(final SpaceShip ship) {
        ship.getSpaceMarine().forEach(el -> el.setSpaceShip(ship));
        ship.getSpaceMarine().forEach(el -> {
            if (spaceMarineDAO.getSpaceMarine(el.getId()).isPresent()) {
                spaceMarineDAO.updateSpaceMarine(el);
            } else {
                spaceMarineDAO.createSpaceMarine(el);
            }
        });
    }


    public List<SpaceShip> getAllSpaceShips() {
        Transaction transaction = null;
        try (final Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            final List<SpaceShip> spaceMarines = session.createQuery("from SpaceShip").getResultList();
            transaction.commit();

            return spaceMarines;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

            return null;
        }
    }
}
