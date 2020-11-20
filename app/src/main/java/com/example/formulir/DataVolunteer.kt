package com.example.formulir

class DataVolunteer {
    var name: String? = null
    var address: String? = null
    var phone: String? = null
    var idnumber: String? = null
    var key: String? = null

    constructor() {}

    constructor(name: String?, address: String?, phone: String?, idnumber: String?){
        this.name = name
        this.address = address
        this.phone = phone
        this.idnumber = idnumber
    }

}