package services;

import DAO.ContactDAO;
import model.Contact;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactService {

    private ContactDAO contactDAO;

    List<Contact> contactList = new ArrayList<>();

    public ContactService(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    public void createContact(Contact contact) {
        contact.firstName = "amparo";
        contact.lastName = "hilario";
        contact.phoneNumber = "8497779999";
        contact.email = "amparo@.com";

        notNullValidation(contact.firstName);
        notNullValidation(contact.lastName);
        notNullValidation(contact.phoneNumber);
        phoneNumberLength(contact.phoneNumber);
        notNullValidation(contact.email);
        emailValidation(contact.email);

        contactDAO.create(contact);
    }

    public List<Contact> searchAllContacts(Contact contact) {
        contactList = contactDAO.allContacts();

        for (Contact i : contactList){
            showContacts(i);
        }

        return contactDAO.allContacts();
    }

    public List<Contact> findByFirstname(Contact contact) {
        contactList = contactDAO.findByFirstName(contact);

        for (Contact i : contactList){
            showContacts(i);
        }
        return contactDAO.findByFirstName(contact);
    }

    public List<Contact> findByLastName(Contact contact) {
        contactList = contactDAO.findByLastName(contact);

        for (Contact i : contactList) {
            showContacts(i);
        }
        return contactDAO.findByLastName(contact);
    }

    public List<Contact> findByPhoneNumber(Contact contact) {
        contactList = contactDAO.findByPhoneNumber(contact);

        for (Contact i : contactList){
            showContacts(i);
        }
        return contactDAO.findByPhoneNumber(contact);
    }

    public List<Contact> findByEmail(Contact contact) {
        return contactDAO.findByEmail(contact);
    }

    public int deleteContact(Contact contact) {
        return contactDAO.deleteContact(contact);
    }

    //number validation
    public boolean validateNumber(String contact){
        try {
            BigInteger.valueOf(Long.parseLong(contact));
        } catch (NumberFormatException e) {
            System.out.println("Please insert a valid number");
            return false;
        }
        return true;
    }

    // not null field validation
    public boolean notNullValidation(String contact) {
        if (contact == null) {
            System.out.println("Field cannot be null");
            return false;
        } else if (contact.isBlank()) {
            System.out.println("Field cannot be empty");
            return false;
        }
        return true;
    }

    // phonenumber length validation
    public boolean phoneNumberLength(String contact){
        if (contact.length() > 10){
            System.out.println("the phone number cannot exceed 10 digits");
            return false;
        } else if (contact.length() < 10) {
            System.out.println("the phone number cannot be less than 10 digits");
            return false;
        } else if (contact.length() == 10){
            return true;
        }
        return true;
    }

    // email validation
    public boolean emailValidation(String email) {
        String regex = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches() == true){
            return true;
        } else {
            System.out.println("please insert a valid email.");
        }
        return false;
    }

    //Method to present all contacts
    public Contact showContacts(Contact contact) {
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("Nombre: " + contact.firstName);
        System.out.println("Apellido: " + contact.lastName);
        System.out.println("Teléfono: " + contact.phoneNumber);
        System.out.println("Correo electrónico: " + contact.email);
        System.out.println("-------------------------------------------------------------------------------------");

        return null;
    }
}
