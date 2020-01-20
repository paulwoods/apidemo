package org.mrpaulwoods.apidemo

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.annotation.PostConstruct

@Service
@Transactional
@Slf4j
class Bootstrap {

    private final ContactService contactService

    Bootstrap(
            ContactService contactService
    ) {
        this.contactService = contactService
    }

    @PostConstruct
    void bootstrap() {
        log.info("bootstrap()")
        if(0L == contactService.count()) {
            (0..1000).each {
                contactService.create new ContactForm(firstName: "first$it", middleName:"middle$it", lastName:"last$it")
            }
        }

    }

}
