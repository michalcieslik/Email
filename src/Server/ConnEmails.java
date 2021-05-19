package Server;


import Comunication.Mail;
import Comunication.Message;
import Comunication.SendMails;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;

// "jdbc:mysql://localhost:3306/Inbox" -> Inbox
// "jdbc:mysql://localhost:3306/Email accounts" -> email acc

public class ConnEmails {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public ConnEmails() throws SQLException {
        //URL of database
        String URL = "jdbc:mysql://localhost:3306/Inbox";
        //password to database
        String password = "admin";
        //login to database
        String login = "root";
        connection = DriverManager.getConnection(URL, login, password);              //Establishing connection with DB
    }

    public boolean createInbox(String account) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS {0} (messId int NOT NULL AUTO_INCREMENT, " +
                "sender varchar(30) NOT NULL , title varchar (20) NOT NULL ,date_ varchar(20) NOT NULL , " +
                "message text NOT NULL , PRIMARY KEY (messId))";
        preparedStatement = connection.prepareStatement(sql);

        if (preparedStatement.executeUpdate(MessageFormat.format(sql, string_converter(account))) == 0) {
            System.out.println("Utworzony skrzynke odbiorcza");
            return true;
        } else {
            System.out.println("Istnieje juz taka skrzynka odbiorcza");
            return false;
        }

    }

    public boolean deleteInbox(String account) throws SQLException {
        try {
            String sql = "DROP TABLE {0}";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate(MessageFormat.format(sql, string_converter(account)));
            System.out.println("Udalo usunac sie skrzynke odbiorcza");
            return true;
        } catch (Exception e) {
            System.out.println("Brak skrzynki odbiorczej dla danego konta");
            return false;
        }

    }

    public Message getListMess(String account, String password) throws SQLException {
        try {
            ConnAcc connAcc = new ConnAcc();
            if (connAcc.isAcc(account, password)) {
                connAcc.closeConn();
                ArrayList<Message> messages = new ArrayList<>();
                String sql = "SELECT * FROM {0}";
                preparedStatement = connection.prepareStatement(MessageFormat.format(sql, string_converter(account)));
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    messages.add(new Mail("SendMails", account, null, true, resultSet.getString("sender"),
                            resultSet.getInt("messId"), resultSet.getString("title"),
                            resultSet.getString("date_"), resultSet.getString("message")));
                }
                if (messages.size() != 0)
                    return new SendMails("SendMails", account, null, true, messages);
                else
                    return new SendMails("SendMails", account, null, true, new ArrayList<>());
            } else
                connAcc.closeConn();
                return new SendMails("Fail", account, null, false, new ArrayList<>());
        } catch (Exception e) {
            System.out.println("Fatal error");
            return new SendMails("Fail", account, null, false, new ArrayList<>());
        }
    }


    public boolean deleteMess(String account, String password, int id) throws SQLException {
        try {
            ConnAcc connAcc = new ConnAcc();
            if (connAcc.isAcc(account, password)) {
                connAcc.closeConn();
                String sql = "DELETE FROM {0} WHERE messId = ?";
                preparedStatement = connection.prepareStatement(MessageFormat.format(sql, string_converter(account)));
                preparedStatement.setInt(1, id);
                if (preparedStatement.executeUpdate() != 0) {
                    System.out.println("Udane usuniecie maila");
                    return true;
                } else {
                    System.out.println("Brak rekordu lub tabeli");
                    return false;
                }
            } else {
                connAcc.closeConn();
                return false;
            }
        } catch (Exception e) {
            System.out.println("Brak rekordu lub tabeli");
            return false;
        }

    }

    public boolean insertMess(String account, String sender, String title, String date, String messege) throws SQLException {
        try {
            String sql = "INSERT INTO {0} (sender, title, date_, message) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(MessageFormat.format(sql, string_converter(account)));
            preparedStatement.setString(1, sender);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, date);
            preparedStatement.setString(4, messege);
            preparedStatement.executeUpdate();
            System.out.println("Zapisano wiadomosc");
            return true;
        } catch (Exception e) {
            System.out.println("Brak skrzynki odbiorczej");
            return false;
        }

    }

    private String string_converter(String account) {
        return "`" + account + "`";
    }

    public void closeConn() throws SQLException {
        connection.close();
    }
}
