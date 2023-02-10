package com.ewch.modules.jmp.cloud.service.impl.database;

import com.ewch.modules.jmp.dto.BankCard;
import com.ewch.modules.jmp.dto.Subscription;
import com.ewch.modules.jmp.dto.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBConfig {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/test";


    //  Database credentials
    private static final String USER = "sa";
    private static final String PASS = "";

    private static Connection connection;


    public static void init() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Class.forName(JDBC_DRIVER);
            cleanDatabase();
            createTables();
            insertMockData();
        } catch (SQLException ex) {
            System.out.println("Error starting DB connection.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error starting DB connection.");
        } catch (Exception ex) {
            System.out.println("Error starting DB connection.");
        }
    }

    public static void cleanDatabase() throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "DROP ALL OBJECTS";
        statement.execute(sql);
        System.out.println("Cleaned database.");
    }

    private static void createTables() throws SQLException {
        Statement statement = connection.createStatement();
        String sqlTableUsers = """
                CREATE TABLE IF NOT EXISTS USERS(
                    id INTEGER NOT NULL AUTO_INCREMENT,
                    name VARCHAR(50),
                    surname VARCHAR(50),
                    birthday DATE,
                    PRIMARY KEY(id)
                )
                """;
        statement.executeUpdate(sqlTableUsers);
        System.out.println("Created table Users.");

        String sqlTableSubscriptions = """
                CREATE TABLE IF NOT EXISTS SUBSCRIPTIONS(
                    id INTEGER NOT NULL AUTO_INCREMENT,
                    bankcard VARCHAR(16),
                    startDate DATE,
                    PRIMARY KEY(id)
                )
                """;
        statement.executeUpdate(sqlTableSubscriptions);
        System.out.println("Created table Subscriptions.");

        String sqlTableBankCards = """
                CREATE TABLE IF NOT EXISTS BANKCARDS(
                    id INTEGER NOT NULL AUTO_INCREMENT,
                    bankcard VARCHAR(16),
                    user_id INTEGER,
                    PRIMARY KEY(id),
                    FOREIGN KEY (user_id) REFERENCES USERS(id)
                )
                """;
        statement.executeUpdate(sqlTableBankCards);
        System.out.println("Created table BankCards.");
    }

    private static void insertMockData() throws SQLException {
        Statement statement = connection.createStatement();
        String sqlInsertUsers = "INSERT INTO USERS(name, surname, birthday) " + "VALUES ('Smoke', 'Cast', '1993-03-21')";
        statement.executeUpdate(sqlInsertUsers);
        sqlInsertUsers = "INSERT INTO USERS(name, surname, birthday) " + "VALUES ('Mona', 'Mar', '1992-07-07')";
        statement.executeUpdate(sqlInsertUsers);
        sqlInsertUsers = "INSERT INTO USERS(name, surname, birthday) " + "VALUES ('Dohko', 'Cas', '1996-01-18')";
        statement.executeUpdate(sqlInsertUsers);
        sqlInsertUsers = "INSERT INTO USERS(name, surname, birthday) " + "VALUES ('Tigro', 'Mar', '1998-02-01')";
        statement.executeUpdate(sqlInsertUsers);
        System.out.println("Inserted records into the tables.");
    }

    public List<User> getUsers() {
        List<User> usersList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String selectRecords = "SELECT id, name, surname, birthday FROM USERS";
            var resultSet = statement.executeQuery(selectRecords);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
                usersList.add(new User(name, surname, birthday));
            }
        } catch (SQLException e) {
            System.err.println("Error getting DB connection.");
        }
        return usersList;
    }

    public User createUser(User user) {
        try {
            Statement statement = connection.createStatement();
            String sqlInsertUser = "INSERT INTO USERS(name, surname, birthday) "
                    + "VALUES ('" + user.getName() + "', '" + user.getSurname() + "', '" + user.getBirthday() + "')";
            statement.executeUpdate(sqlInsertUser);
            System.out.println("User created successfully.");
        } catch (SQLException e) {
            System.err.println("Error getting DB connection.");
        }
        return user;
    }

    public Subscription createSubscription(Subscription subscription) {
        try {
            Statement statement = connection.createStatement();
            String sqlInsertSubscription = "INSERT INTO SUBSCRIPTIONS(bankcard, startDate) "
                    + "VALUES ('" + subscription.getBankcard() + "', '" + subscription.getStartDate() + "')";
            statement.executeUpdate(sqlInsertSubscription);
            System.out.println("Subscription created successfully.");
        } catch (SQLException e) {
            System.err.println("Error getting DB connection.");
        }
        return subscription;
    }

    public Subscription getSubscriptionByBankCardNumber(String bankCardNumber) {
        List<Subscription> subscriptionsList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String selectRecords = "SELECT id, bankcard, startDate FROM SUBSCRIPTIONS WHERE bankCard = " + bankCardNumber;
            var resultSet = statement.executeQuery(selectRecords);
            while (resultSet.next()) {
                String bankCard = resultSet.getString("bankCard");
                LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
                subscriptionsList.add(new Subscription(bankCard, startDate));
            }
        } catch (SQLException e) {
            System.err.println("Error getting DB connection.");
        }
        return !subscriptionsList.isEmpty() ? subscriptionsList.get(0) : null;
    }

    public List<BankCard> getAllBankCards() {
        List<BankCard> bankCardsList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String selectRecords = "SELECT id, number, user FROM BANKCARDS";
            var resultSet = statement.executeQuery(selectRecords);
            while (resultSet.next()) {
                String number = resultSet.getString("number");
                User user = (User) resultSet.getObject("user");
                LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
                bankCardsList.add(new BankCard(number, user));
            }
        } catch (SQLException e) {
            System.err.println("Error getting DB connection.");
        }
        return bankCardsList;
    }

    public List<Subscription> getAllSubscriptions() {
        List<Subscription> subscriptionList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String selectRecords = "SELECT id, bankcard, startDate FROM SUBSCRIPTIONS";
            var resultSet = statement.executeQuery(selectRecords);
            while (resultSet.next()) {
                String bankcard = resultSet.getString("bankcard");
                LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
                subscriptionList.add(new Subscription(bankcard, startDate));
            }
        } catch (SQLException e) {
            System.err.println("Error getting DB connection.");
        }
        return subscriptionList;
    }
}
