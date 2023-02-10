package com.ewch.modules.jmp.cloud.service.impl.database;

import com.ewch.modules.jmp.dto.BankCard;
import com.ewch.modules.jmp.dto.Subscription;
import com.ewch.modules.jmp.dto.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
        var statement = connection.createStatement();
        var sql = "DROP ALL OBJECTS";
        statement.execute(sql);
        System.out.println("Cleaned database.");
    }

    private static void createTables() throws SQLException {
        var statement = connection.createStatement();
        var sqlTableUsers = """
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

        var sqlTableSubscriptions = """
                CREATE TABLE IF NOT EXISTS SUBSCRIPTIONS(
                    id INTEGER NOT NULL AUTO_INCREMENT,
                    bankcard VARCHAR(16),
                    startDate DATE,
                    PRIMARY KEY(id)
                )
                """;
        statement.executeUpdate(sqlTableSubscriptions);
        System.out.println("Created table Subscriptions.");

        var sqlTableBankCards = """
                CREATE TABLE IF NOT EXISTS BANKCARDS(
                    id INTEGER NOT NULL AUTO_INCREMENT,
                    number VARCHAR(16),
                    user_id INTEGER,
                    PRIMARY KEY(id),
                    FOREIGN KEY (user_id) REFERENCES USERS(id)
                )
                """;
        statement.executeUpdate(sqlTableBankCards);
        System.out.println("Created table BankCards.");
    }

    private static void insertMockData() throws SQLException {
        var statement = connection.createStatement();
        var sqlInsertUsers = "INSERT INTO USERS(name, surname, birthday) " + "VALUES ('Smoke', 'Cast', '1993-03-21')";
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
            var selectRecords = "SELECT id, name, surname, birthday FROM USERS";
            var statement = connection.prepareStatement(selectRecords);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var id = resultSet.getInt("id");
                var name = resultSet.getString("name");
                var surname = resultSet.getString("surname");
                var birthday = resultSet.getDate("birthday").toLocalDate();
                usersList.add(new User(id, name, surname, birthday));
            }
        } catch (SQLException e) {
            System.err.println("Error in DB query.");
        }
        return usersList;
    }

    public User createUser(User user) {
        try {
            var sqlInsertUser = "INSERT INTO USERS(name, surname, birthday) VALUES (?, ?, ?)";
            var statement = connection.prepareStatement(sqlInsertUser, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setDate(3, Date.valueOf(user.getBirthday()));
            var affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (var generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            System.out.println("User created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating user.");
        }
        return user;
    }

    public BankCard createBankCard(BankCard bankCard) {
        try {
            var sqlInsertBankCard = "INSERT INTO BANKCARDS(number, user_id) VALUES (?, ?)";
            var statement = connection.prepareStatement(sqlInsertBankCard);
            statement.setString(1, bankCard.getNumber());
            statement.setInt(2, bankCard.getUser().getId());
            var affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating bank card failed, no rows affected.");
            }
            System.out.println("Bank card created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating bank card.");
        }
        return bankCard;
    }
    public Subscription createSubscription(Subscription subscription) {
        try {
            var sqlInsertSubscription = "INSERT INTO SUBSCRIPTIONS(bankcard, startDate) VALUES (?, ?)";
            var statement = connection.prepareStatement(sqlInsertSubscription);
            statement.setString(1, subscription.getBankcard());
            statement.setDate(2, Date.valueOf(subscription.getStartDate()));
            var affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating subscription failed, no rows affected.");
            }
            System.out.println("Subscription created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating subscription.");
        }
        return subscription;
    }

    public User getUserById(int user_id) {
        User user =  null;
        try {
            var selectRecords = "SELECT id, name, surname, birthday FROM USERS WHERE id = ?";
            var statement = connection.prepareStatement(selectRecords);
            statement.setInt(1, user_id);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var id = resultSet.getInt("id");
                var name = resultSet.getString("name");
                var surname = resultSet.getString("surname");
                var birthday = resultSet.getDate("birthday").toLocalDate();
                user = new User(id, name, surname, birthday);
            }
        } catch (SQLException e) {
            System.err.println("Error getting DB connection.");
        }
        return user;
    }


    public Subscription getSubscriptionByBankCardNumber(String bankCardNumber) {
        List<Subscription> subscriptionsList = new ArrayList<>();
        try {
            var selectRecords = "SELECT id, bankcard, startDate FROM SUBSCRIPTIONS WHERE bankCard = ?";
            var statement = connection.prepareStatement(selectRecords);
            statement.setString(1, bankCardNumber);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var bankCard = resultSet.getString("bankCard");
                var startDate = resultSet.getDate("startDate").toLocalDate();
                subscriptionsList.add(new Subscription(bankCard, startDate));
            }
        } catch (SQLException e) {
            System.err.println("Error getting subscription by bank card number.");
        }
        return !subscriptionsList.isEmpty() ? subscriptionsList.get(0) : null;
    }

    public List<BankCard> getAllBankCards() {
        List<BankCard> bankCardsList = new ArrayList<>();
        try {
            var selectRecords = "SELECT id, number, user_id FROM BANKCARDS";
            var statement = connection.prepareStatement(selectRecords);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var number = resultSet.getString("number");
                var user_id = resultSet.getInt("user_id");
                var user = getUserById(user_id);
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
            var selectRecords = "SELECT id, bankcard, startDate FROM SUBSCRIPTIONS";
            var statement = connection.prepareStatement(selectRecords);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var bankcard = resultSet.getString("bankcard");
                var startDate = resultSet.getDate("startDate").toLocalDate();
                subscriptionList.add(new Subscription(bankcard, startDate));
            }
        } catch (SQLException e) {
            System.err.println("Error getting DB connection.");
        }
        return subscriptionList;
    }
}
