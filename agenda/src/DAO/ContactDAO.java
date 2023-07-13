package DAO;

import model.Contact;

import java.util.List;

public interface ContactDAO {
    void create(Contact contact);
    List<Contact> findByFirstName(Contact contact);
    List<Contact> findByLastName(Contact contact);
    List<Contact> findByPhoneNumber(Contact contact);
    List<Contact> findByEmail(Contact contact);
    List<Contact> allContacts();
    int deleteContact(Contact contact);


}
