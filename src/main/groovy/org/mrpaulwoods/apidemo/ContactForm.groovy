package org.mrpaulwoods.apidemo

import com.sun.istack.NotNull

import javax.persistence.Column
import javax.validation.constraints.Size

class ContactForm {

    @NotNull
    @Size(min = 1, max = 50)
    String firstName

    @Size(min = 0, max = 50)
    String middleName

    @NotNull
    @Size(min = 1, max = 50)
    String lastName

}
