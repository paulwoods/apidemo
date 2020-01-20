package org.mrpaulwoods.apidemo

import org.springframework.data.jpa.repository.JpaRepository

interface ContactRepository extends JpaRepository<Contact, Long> {

}
