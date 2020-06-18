package DAO;

import model.DailyReport;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigInteger;
import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public DailyReport getDailyReport(long id) {
        DailyReport dailyReport = (DailyReport) session.load(DailyReport.class, id);
        session.close();
        return dailyReport;
    }

    public void addReport(DailyReport dailyReport) {
        Transaction transaction = session.beginTransaction();
        session.save(dailyReport);
        transaction.commit();
        session.close();
    }

    public void clearAll() {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("DELETE DailyReport");
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    private long getLastId() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> list=  session.createQuery("FROM DailyReport").list();
        transaction.commit();
        if (list.size()>0) {
            return list.get(list.size()-1).getId();
        }

        return 0;
    }

    public DailyReport getLastReport() {
        return getDailyReport(getLastId()-1);
    }

    public void buyCar(long price) {
        long lastId = getLastId();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("UPDATE DailyReport SET " +
                "earnings = earnings + :price" +
                ", soldCars = soldCars + 1" +
                " WHERE id = :id");
        query.setParameter("price", price);
        query.setParameter("id", lastId);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

}
