package org.mrpaulwoods.apidemo

class ContactNotFoundException extends RuntimeException {
    ContactNotFoundException(Long id) {
        super("The contact was not found: $id")
    }
}
