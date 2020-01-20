package org.mrpaulwoods.apidemo

import groovy.util.logging.Slf4j
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
@Slf4j
class ContactService {

    private final ContactRepository contactRepository

    ContactService(
            ContactRepository contactRepository
    ) {
        this.contactRepository = contactRepository
    }

    Contact create(ContactForm contactForm) {
        log.debug("create(contactForm:$contactForm")
        create copy(contactForm, new Contact())
    }

    Contact create(Contact contact) {
        log.debug("create(contact:$contact")
        contactRepository.save contact
    }

    Contact read(Long id) {
        log.debug("read(id:$id")
        contactRepository.findById(id).orElseThrow { new ContactNotFoundException(id) }
    }

    Contact update(Long id, ContactForm contactForm) {
        log.debug("update(id:$id, contactForm:$contactForm")
        update copy(contactForm, read(id))
    }

    Contact update(Contact contact) {
        log.debug("update(contact:$contact")
        contactRepository.save contact
    }

    void delete(Long id) {
        log.debug("delete(id:$id")
        contactRepository.deleteById id

    }

    void delete(Contact contact) {
        log.debug("delete(contact:$contact")
        contactRepository.delete contact
    }

    Page<Contact> search(Pageable pageable) {
        log.debug("search(pageable:$pageable")
        contactRepository.findAll(pageable)
    }

    Contact copy(ContactForm contactForm, Contact contact) {
        contact.firstName = contactForm.firstName
        contact.middleName = contactForm.middleName
        contact.lastName = contactForm.lastName
        contact
    }

    long count() {
        log.debug("count()")
        contactRepository.count()
    }

}
