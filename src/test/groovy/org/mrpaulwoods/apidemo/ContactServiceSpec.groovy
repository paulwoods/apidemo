package org.mrpaulwoods.apidemo

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class ContactServiceSpec extends Specification {

    ContactRepository contactRepository = Mock(ContactRepository)
    ContactService service = new ContactService(contactRepository)

    ContactForm contactForm1 = new ContactForm(
            firstName: "alpha",
            middleName: "bravo",
            lastName: "charlie"
    )

    Contact contact1 = new Contact(id: 100)

    def setup() {
        0 * _
    }

    def "create (contactForm) copies form to contact and saves it"() {
        when:
        def ret = service.create(contactForm1)

        then:
        1 * contactRepository.save(_) >> { Contact c ->
             c.id = 100
            c
        }

        and:
        ret.id == 100
        ret.firstName == "alpha"
        ret.middleName == "bravo"
        ret.lastName == "charlie"
    }

    def "create (Contact) saves the contact"() {
        when:
        def ret = service.create(contact1)

        then:
        1 * contactRepository.save(contact1) >> contact1

        and:
        ret == contact1
    }

    def "read (found) returns the contact"() {
        when:
        def ret = service.read(contact1.id)

        then:
        1 * contactRepository.findById(contact1.id) >> Optional.of(contact1)

        and:
        ret == contact1
    }

    def "read (not found) throws an exception"() {
        when:
        service.read contact1.id

        then:
        1 * contactRepository.findById(contact1.id) >> Optional.empty()

        and:
        thrown ContactNotFoundException
    }

    def "update (contactForm) read the contact, copies the fields and saves"() {
        when:
        def ret = service.update(contact1.id, contactForm1)

        then:
        1 * contactRepository.findById(contact1.id) >> Optional.of(contact1)
        1 * contactRepository.save(contact1) >> contact1

        and:
        ret.firstName == "alpha"
        ret.middleName == "bravo"
        ret.lastName == "charlie"
    }

    def "update (contact) saves the contact"() {
        when:
        def ret = service.update(contact1)

        then:
        1 * contactRepository.save(contact1) >> contact1

        and:
        ret == contact1
    }

    def "delete (id) deletes the contact"() {
        when:
        service.delete contact1.id

        then:
        1 * contactRepository.deleteById(contact1.id)
    }

    def "delete (contact) deletes the contact"() {
        when:
        service.delete contact1

        then:
        1 * contactRepository.delete(contact1)
    }

    def "search calls the repository with the pageable"() {

        Pageable pageable1 = Mock(Pageable)
        Page<Contact> page1 = Mock(Page)

        when:
        def ret = service.search(pageable1)

        then:
        1 * contactRepository.findAll(pageable1) >> page1

        and:
        ret == page1
    }

}
