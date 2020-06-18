package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public Car getCar(long id) {
        Car car = (Car) session.load(Car.class, id);
        session.close();
        return car;
    }

    public List<Car> getAllCar() {
        Transaction transaction = session.beginTransaction();
        List<Car> cars = session.createQuery("FROM Car").list();
        transaction.commit();
        session.close();
        return cars;
    }

    public void addCar(Car car) {
        Transaction transaction = session.beginTransaction();
        session.save(car);
        transaction.commit();
        session.close();
    }

    public void clearAll() {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("DELETE Car");
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public List<Car> getCarsByBrand(String brand) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Car where brand = :brand");
        query.setParameter("brand", brand);
        List<Car> cars = query.list();
        transaction.commit();
        session.close();
        return cars;
    }

    public List<Car> getCarsByModel(String model) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Car where model = :model");
        query.setParameter("model", model);
        List<Car> cars = query.list();
        transaction.commit();
        session.close();
        return cars;
    }

    public List<Car> getCarsByPlate(String licensePlate) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Car where licensePlate = :licensePlate");
        query.setParameter("licensePlate", licensePlate);
        List<Car> cars = query.list();
        transaction.commit();
        session.close();
        return cars;
    }

    public List<Car> getCarsByBrandModelPlate(String model, String brand, String licensePlate) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Car where " +
                "model = :model and brand = :brand and licensePlate = :licensePlate");
        query.setParameter("model", model);
        query.setParameter("brand", brand);
        query.setParameter("licensePlate", licensePlate);
        List<Car> cars = query.list();
        transaction.commit();
        session.close();
        return cars;
    }

    public void deleteCar(long id) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("DELETE Car WHERE id = :id");
        query.setParameter("id",id);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

}
