package org.mrpaulwoods.apidemo.integration

import org.mrpaulwoods.apidemo.ContactForm
import org.mrpaulwoods.apidemo.ContactService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings("GroovyAssignabilityCheck")
class ContactIT extends Specification {

    @LocalServerPort
    int localServerPort

    @Autowired
    ContactService contactService

    RestTemplate restTemplate = new RestTemplate()

    def setup() {

        // tell restTemplate to not throw exceptions on check http statuses
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            boolean hasError(ClientHttpResponse response) throws IOException {
                return false
            }

            @Override
            void handleError(ClientHttpResponse response) throws IOException {
                // do nothing
            }
        })
    }

    def "index by default gets the first 200 records"() {
        when:
        ResponseEntity<Map> ret = restTemplate.getForEntity("http://localhost:${localServerPort}/contact", HashMap)

        then:
        ret.statusCode == HttpStatus.OK
        ret.body.content[0].firstName == "first0"
        ret.body.content[0].middleName == "middle0"
        ret.body.content[0].lastName == "last0"
    }

    def "index gets the second page of 10"() {
        when:
        ResponseEntity<Map> ret = restTemplate.getForEntity("http://localhost:${localServerPort}/contact?page=1&size=10", HashMap)

        then:
        ret.statusCode == HttpStatus.OK
        ret.body.content[0].firstName == "first10"
        ret.body.content[0].middleName == "middle10"
        ret.body.content[0].lastName == "last10"
        ret.body.content[9].firstName == "first19"
        ret.body.content[9].middleName == "middle19"
        ret.body.content[9].lastName == "last19"
    }

    def "create a new contact"() {

        ContactForm form = new ContactForm(
                firstName: "James",
                middleName: "Earl",
                lastName: "Jones"
        )

        when:
        ResponseEntity<Map> ret = restTemplate.postForEntity("http://localhost:${localServerPort}/contact", form, HashMap)

        then:
        ret.statusCode == HttpStatus.CREATED
        ret.body.firstName == "James"
        ret.body.middleName == "Earl"
        ret.body.lastName == "Jones"
        ret.body.id != null

        cleanup:
        contactService.delete((Long) (ret.body.id))
    }

    def "read a contact"() {
        when:
        ResponseEntity<Map> ret = restTemplate.getForEntity("http://localhost:${localServerPort}/contact/100", HashMap)

        then:
        ret.body.id == 100
        ret.statusCode == HttpStatus.OK
        ret.body.firstName == "first99"
        ret.body.middleName == "middle99"
        ret.body.lastName == "last99"
    }

    def "update a contact"() {

        ContactForm form = new ContactForm(
                firstName: "first-updated",
                middleName: "middle-updated",
                lastName: "last-updated"
        )

        when:
        ResponseEntity<Map> ret = restTemplate.postForEntity("http://localhost:${localServerPort}/contact/100", form, HashMap)

        then:
        ret.statusCode == HttpStatus.OK
        ret.body.firstName == "first-updated"
        ret.body.middleName == "middle-updated"
        ret.body.lastName == "last-updated"
        ret.body.id != null

        when: "verify the change was made"
        ret = restTemplate.getForEntity("http://localhost:${localServerPort}/contact/100", HashMap)

        then:
        ret.body.id == 100
        ret.statusCode == HttpStatus.OK
        ret.body.firstName == "first-updated"
        ret.body.middleName == "middle-updated"
        ret.body.lastName == "last-updated"
    }

    def "delete a contact"() {

        when: "delete the contact"
        restTemplate.delete("http://localhost:${localServerPort}/contact/1")
        ResponseEntity<Map> ret = restTemplate.getForEntity("http://localhost:${localServerPort}/contact/1", HashMap)

        then:
        ret.statusCode == HttpStatus.NOT_FOUND
        ret.body.message == "The contact was not found: 1"
    }

}
