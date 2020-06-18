package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.DBHelper;

import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public List<Car> getAllCars() {
        return getCarDao().getAllCar();
    }

    public void addCar(Car car) {
        getCarDao().addCar(car);
    }

    public void clearAll() {
        getCarDao().clearAll();
    }

    public void deleteCar(long id) {
        getCarDao().deleteCar(id);
    }

    public boolean isCarExist(String model, String brand, String licensePlate) {
        return !getCarsByBrandModelPlate(model, brand, licensePlate).isEmpty();
    }

    public boolean checkBrandCount(String brand) {
        int count = getCarDao().getCarsByBrand(brand).size();
        return count <= 10;
    }

    public List<Car> getCarsByBrandModelPlate(String model, String brand, String licensePlate) {
        return getCarDao().getCarsByBrandModelPlate(model, brand, licensePlate);
    }

    private CarDao getCarDao() {
        return new CarDao(sessionFactory.openSession());
    }


}
