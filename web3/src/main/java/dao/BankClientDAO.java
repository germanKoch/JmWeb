package dao;

import model.BankClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankClientDAO {

    private Connection connection;

    public BankClientDAO(Connection connection) {
        this.connection = connection;
    }

    public List<BankClient> getAllBankClient() throws SQLException {
        int id;
        long money;
        String name;
        String password;
        List<BankClient> list = new ArrayList<>();

        Statement stmt = connection.createStatement();
        stmt.execute("select * from bank_client");
        ResultSet result = stmt.getResultSet();

        while (result.next()) {
            id = result.getInt("id");
            money = result.getLong("money");
            name = result.getString("name");
            password = result.getString("password");

            list.add(new BankClient(id, name, password, money));
        }

        result.close();
        stmt.close();

        return list;
    }

    public boolean validateClient(String name, String password) throws SQLException {
        boolean isInBase = false;

        PreparedStatement stmt = connection.prepareStatement("select * from bank_client where name= ? and password= ?");
        stmt.setString(1, name);
        stmt.setString(2, password);
        stmt.execute();
        ResultSet result = stmt.getResultSet();

        isInBase = result.next();
        result.close();
        stmt.close();

        return isInBase;
    }

    public boolean isClientExist(String name) throws SQLException {
        boolean clientIsExist = false;

        Statement stmt = connection.createStatement();
        stmt.execute("select * from bank_client where name='"+ name +"'");
        ResultSet result = stmt.getResultSet();

        clientIsExist = result.next();
        result.close();
        stmt.close();

        return clientIsExist;
    }

    public void updateClientsMoney(String name, String password, Long transactValue) throws SQLException {
        String sql = "update bank_client set money = money + ? where name = ? and password = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, transactValue);
        stmt.setString(2, name);
        stmt.setString(3, password);

        stmt.executeUpdate();
        stmt.close();
    }

    public BankClient getClientById(long id) throws SQLException {
        BankClient bankClient = null;

        Statement stmt = connection.createStatement();
        stmt.execute("select * from bank_client where id='" + id + "'");
        ResultSet result = stmt.getResultSet();

        if (result.next()) {
            bankClient = new BankClient(id, result.getString("name"), result.getString("password"), result.getLong("money"));
        }

        result.close();
        stmt.close();

        return bankClient;
    }

    public boolean isClientHasSum(String name, Long expectedSum) throws SQLException {
        boolean hasSum = false;
        Statement stmt = connection.createStatement();
        stmt.execute("select * from bank_client where name='" + name + "'");
        ResultSet result = stmt.getResultSet();

        if (result.next()) {
            hasSum = result.getLong("money") >= expectedSum;
        }

        return hasSum;
    }

    public long getClientIdByName(String name) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("select * from bank_client where name='" + name + "'");
        ResultSet result = stmt.getResultSet();
        result.next();
        Long id = result.getLong(1);
        result.close();
        stmt.close();
        return id;
    }

    public BankClient getClientByName(String name) throws SQLException {
        BankClient bankClient = null;

        Statement stmt = connection.createStatement();
        stmt.execute("select * from bank_client where name='" + name + "'");
        ResultSet result = stmt.getResultSet();

        if (result.next()) {
            int id = result.getInt("id");
            String password = result.getString("password");
            long money = result.getLong("money");
            bankClient = new BankClient(id, name, password, money);
        }

        result.close();
        stmt.close();

        return bankClient;
    }

    public void addClient(BankClient client) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("insert into bank_client(name,password,money) values ('" + client.getName() + "','"
                + client.getPassword() + "'," + client.getMoney() + ")");

        stmt.close();

    }

    public void deleteClient(String name) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("delete from table bank_client where name='" + name + "'");

        stmt.close();
    }

    public void createTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("create table if not exists bank_client (id bigint auto_increment, name varchar(256), password varchar(256), money bigint, primary key (id))");
        stmt.close();
    }

    public void dropTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS bank_client");
        stmt.close();
    }
}
