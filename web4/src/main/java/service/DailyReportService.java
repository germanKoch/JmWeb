package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void clearAll() {
        getDailyReportDao().clearAll();
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        return getDailyReportDao().getAllDailyReport();
    }


    public DailyReport getLastReport() {
        return getDailyReportDao().getLastReport();
    }

    public void buyCar(long price) {
        getDailyReportDao().buyCar(price);
    }

    public void newDay() {
        DailyReport dailyReport = new DailyReport(0L, 0L);
        getDailyReportDao().addReport(dailyReport);
    }

    private DailyReportDao getDailyReportDao() {
        return new DailyReportDao(sessionFactory.openSession());
    }

}
