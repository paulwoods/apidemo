import org.mrpaulwoods.apidemo.ContactNotFoundException

class ContactNotFoundExceptionSpec {

    def "the exception has the correct message"() {

        expect:
        new ContactNotFoundException(123).message == "The contact was not found: 123"
    }

}
