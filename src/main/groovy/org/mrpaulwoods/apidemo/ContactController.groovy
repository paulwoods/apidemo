package org.mrpaulwoods.apidemo

import groovy.util.logging.Slf4j
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

@RestController
@RequestMapping("/contact")
@Slf4j
class ContactController {

    private final ContactService contactService

    ContactController(
            ContactService contactService
    ) {
        this.contactService = contactService
    }

    @GetMapping
    ResponseEntity<Page<Contact>> search(Pageable pageable) {
        log.info("search(pageable:$pageable)")
        ResponseEntity.ok contactService.search(pageable)
    }

    @PostMapping
    ResponseEntity<Contact> create(@RequestBody @Valid ContactForm contactForm) {
        log.info("create(contactForm:$contactForm)")
        Contact contact = contactService.create(contactForm)
        ResponseEntity.created("/contact/${contact.id}".toURI()).body(contact)
    }

    @GetMapping("/{id}")
    ResponseEntity<Contact> read(@PathVariable Long id) {
        log.info("read(id:$id)")
        ResponseEntity.ok contactService.read(id)
    }

    @PostMapping("/{id}")
    ResponseEntity<Contact> update(@PathVariable Long id, @RequestBody @Valid ContactForm contactForm) {
        log.info("update(id: $id, contactForm:$contactForm)")
        ResponseEntity.ok(contactService.update(id, contactForm))
    }

    @DeleteMapping("/{id}")
    ResponseEntity delete(@PathVariable Long id) {
        log.info("delete(id: $id)")
        contactService.delete id
        ResponseEntity.noContent().build()
    }

}
