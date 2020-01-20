package org.mrpaulwoods.apidemo

import groovy.transform.Canonical
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@Canonical
class Contact {

    @GeneratedValue
    @Id
    Long id

    @Column(length = 50, nullable = false)
    String firstName

    @Column(length = 50)
    String middleName

    @Column(length = 50, nullable = false)
    String lastName

}
