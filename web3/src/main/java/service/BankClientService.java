package service;

import dao.BankClientDAO;
import exception.DBException;
import model.BankClient;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class BankClientService {

    public BankClientService() {
    }

    public BankClient getClientById(long id) throws DBException {
        try {
            return getBankClientDAO().getClientById(id);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public BankClient getClientByName(String name) {
        try {
            return getBankClientDAO().getClientByName(name);
        } catch (SQLException e) {
            return null;
        }
    }

    public List<BankClient> getAllClient() {
        try {
            return getBankClientDAO().getAllBankClient();
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean deleteClient(String name) throws DBException {
        try {
            BankClientDAO DAO = getBankClientDAO();
            if (DAO.isClientExist(name)) {
                DAO.deleteClient(name);
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean validateClient(BankClient client) throws SQLException {
        return getBankClientDAO().validateClient(client.getName(),client.getPassword());
    }

    public boolean addClient(BankClient client) throws DBException {
        try {
            BankClientDAO DAO = getBankClientDAO();
            if (!DAO.isClientExist(client.getName())) {
                DAO.addClient(client);
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean sendMoneyToClient(BankClient sender, String name, Long value) throws DBException {
        try {
            BankClientDAO DAO = getBankClientDAO();
            BankClient recipient = DAO.getClientByName(name);
            if (recipient != null && DAO.isClientHasSum(sender.getName(), value)) {
                DAO.updateClientsMoney(sender.getName(), sender.getPassword(), -value);
                DAO.updateClientsMoney(recipient.getName(), recipient.getPassword(), value);
                return true;
            }

            return false;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void cleanUp() throws DBException {
        BankClientDAO dao = getBankClientDAO();
        try {
            dao.dropTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void createTable() throws DBException {
        BankClientDAO dao = getBankClientDAO();
        try {
            dao.createTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("web3?").          //db name
                    append("user=german&").          //login
                    append("password=Germanpaulivalerman123");       //password

            System.out.println("URL: " + url + "\n");

            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private static BankClientDAO getBankClientDAO() {
        return new BankClientDAO(getMysqlConnection());
    }
}
