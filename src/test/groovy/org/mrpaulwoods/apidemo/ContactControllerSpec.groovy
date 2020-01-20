package org.mrpaulwoods.apidemo

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import spock.lang.Specification

class ContactControllerSpec extends Specification {

    ContactService contactService = Mock(ContactService)
    ContactController controller = new ContactController(contactService)

    def setup() {
        0 * _
    }

    Pageable pageable1 = Mock(Pageable)

    Page<Contact> page1 = Mock(Page)

    ContactForm contactForm1 = new ContactForm(
            firstName: "alpha",
            middleName: "bravo",
            lastName: "charlie"
    )

    Contact contact1 = new Contact(id: 100)

    def "search delegates to the service"() {
        when:
        controller.search(pageable1)

        then:
        1 * contactService.search(pageable1) >> page1
    }

    def "create delegates to the service and builds the location"() {
        when:
        def ret = controller.create(contactForm1)

        then:
        1 * contactService.create(contactForm1) >> contact1

        and:
        ret.statusCode == HttpStatus.CREATED
        ret.headers.location[0] == "/contact/100"
        ret.body == contact1
    }

    def "read delegates to the service"() {
        when:
        def ret = controller.read(contact1.id)

        then:
        1 * contactService.read(contact1.id) >> contact1

        and:
        ret.statusCode == HttpStatus.OK
        ret.body == contact1
    }

    def "update delegates to the service"() {
        when:
        def ret = controller.update(contact1.id, contactForm1)

        then:
        1 * contactService.update(contact1.id, contactForm1) >> contact1

        and:
        ret.statusCode == HttpStatus.OK
        ret.body == contact1
    }

    def "delete delegates to the service"() {
        when:
        def ret = controller.delete(contact1.id)

        then:
        1 * contactService.delete(contact1.id)

        and:
        ret.statusCode == HttpStatus.NO_CONTENT
    }

}
