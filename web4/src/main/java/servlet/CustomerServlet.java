package servlet;

import com.google.gson.Gson;
import model.Car;
import model.DailyReport;
import service.CarService;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String json = gson.toJson(CarService.getInstance().getAllCars());
        resp.getWriter().println(json);
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");

        List<Car> cars = CarService.getInstance()
                .getCarsByBrandModelPlate(model, brand, licensePlate);

        if (DailyReportService.getInstance().getAllDailyReports().size()==0) {
            DailyReportService.getInstance().newDay();
        }
        if (!cars.isEmpty()) {
            Car car = cars.get(0);
            DailyReportService.getInstance().buyCar(car.getPrice());
            CarService.getInstance().deleteCar(car.getId());
        }
    }
}
