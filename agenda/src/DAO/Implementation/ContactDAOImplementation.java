package DAO.Implementation;

import model.Contact;
import DAO.ContactDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ContactDAOImplementation implements ContactDAO {

    String url = "jdbc:mysql://localhost:3306/agenda";
    String username = "root";
    String password = "admin";


    @Override
    public void create(Contact contact) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String createQuery = "INSERT INTO contact (firstName, lastName, phoneNumber, email) VALUES (?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(createQuery)) {
                statement.setString(1, contact.firstName);
                statement.setString(2, contact.lastName);
                statement.setString(3, contact.phoneNumber);
                statement.setString(4, contact.email);

                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Contact> findByFirstName(Contact contact) {
        List<Contact> contactList = new ArrayList<>();
        String firstName = contact.firstName;

        ResultSet rs = null;

        try(Connection connection = DriverManager.getConnection(url,username, password)) {
            String byNameQuery = "SELECT * from contact WHERE firstName like CONCAT('%',?,'%')";

            try(PreparedStatement statement = connection.prepareStatement(byNameQuery)){
                statement.setString(1, firstName);
                rs = statement.executeQuery();

                contactList = mapResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return contactList;
    }

    @Override
    public List<Contact> findByLastName(Contact contact) {

        List<Contact> contactList = new ArrayList<>();
        String lastName = contact.lastName;

        ResultSet rs = null;

        try(Connection connection = DriverManager.getConnection(url, username, password)){
            String byLastNameQuery = "SELECT * from contact WHERE lastName like CONCAT('%',?,'%')";

            try(PreparedStatement statement = connection.prepareStatement(byLastNameQuery)){
                statement.setString(1, lastName);
                rs = statement.executeQuery();

                contactList = mapResultSet(rs);
            }
        } catch (NumberFormatException e){
            e.printStackTrace();
            throw new RuntimeException("Last name error");
        } catch (SQLException s){
            if(s instanceof SQLSyntaxErrorException){}
            Logger.getAnonymousLogger().info("Exception Problem");
        } finally {
            try{
                if (rs != null) rs.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return contactList;
    }

    @Override
    public List<Contact> findByPhoneNumber(Contact contact) {
        List<Contact> contactList = new ArrayList<>();
        ResultSet rs = null;

        try(Connection connection = DriverManager.getConnection(url, username, password)){
            String byPhoneQuery = "SELECT * FROM contact WHERE phoneNumber like CONCAT('%', ?, '%')";

            try(PreparedStatement statement = connection.prepareStatement(byPhoneQuery)) {
                statement.setString(1, contact.phoneNumber);
                rs = statement.executeQuery();

                contactList = mapResultSet(rs);
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try{
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return contactList;
    }

    @Override
    public List<Contact> findByEmail(Contact contact) {

        List<Contact> contactList = new ArrayList<>();
        ResultSet rs = null;

        try (Connection connection = DriverManager.getConnection(url, username, password)){
            String byEmailQuery = "SELECT * from contact WHERE email like CONCAT('%',?,'%')";

            try(PreparedStatement statement = connection.prepareStatement(byEmailQuery)){
                statement.setString(1, contact.email);
                rs = statement.executeQuery();

                return mapResultSet(rs);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try{
                if (rs != null) rs.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Contact> allContacts() {
        List<Contact> contactList = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, username, password)){
            String getAll = "SELECT * from contact";

            try(PreparedStatement statement = connection.prepareStatement(getAll)) {
                ResultSet rs = statement.executeQuery();

                contactList = mapResultSet(rs);

                rs.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return contactList;
    }

    @Override
    public int deleteContact(Contact contact) {
        int affectedRows = 0;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String deleteQuery = "DELETE FROM contact WHERE phoneNumber like CONCAT('%',?,'%')";

            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                statement.setString(1, contact.phoneNumber);
                affectedRows = statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    private List<Contact> mapResultSet(ResultSet rs) throws SQLException {
        List<Contact> contactList = new ArrayList<>();
        while (rs.next()) {
            String firsName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String phoneNumber = rs.getString("phoneNumber");
            String email = rs.getString("email");
            Contact contact = new Contact();
            contact.firstName = firsName;
            contact.lastName = lastName;
            contact.phoneNumber = phoneNumber;
            contact.email = email;

            contactList.add(contact);
        }
        return contactList;
    }
}
