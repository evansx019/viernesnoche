import DAO.Implementation.ContactDAOImplementation;
import model.Contact;
import services.ContactService;

public class Main {
    public static void main(String[] args) {
        ContactService contactService = new ContactService(new ContactDAOImplementation());
        Contact contact = new Contact();
        contactService.createContact(contact);

        // search all contact
//        contactService.searchAllContacts(contact);

        //find contact by firstname
        contact.firstName = "George";
//        contactService.findByFirstname(contact);

        //find contact by lastname
        contact.lastName = "garcia";
//        contactService.findByLastName(contact);

        //find contact by phonenumber
        contact.phoneNumber = "829";
//      contactService.findByPhoneNumber(contact);

        //find contact by phonenumber
        contact.email = "ka";
//        contactService.findByEmail(contact);


        //delete contact
//        contact.phoneNumber = "8296767667";
//        contactService.deleteContact(contact);
    }
}
