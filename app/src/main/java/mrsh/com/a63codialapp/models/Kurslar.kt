package mrsh.com.a63codialapp.models

import java.io.Serializable

class Kurslar : Serializable {
    var id: Int? = null
    var kursNomi: String? = null
    var kursHaqidaMalumot: String? = null

    constructor(id: Int?, kursNomi: String?, kursHaqidaMalumot: String?) {
        this.id = id
        this.kursNomi = kursNomi
        this.kursHaqidaMalumot = kursHaqidaMalumot
    }

    constructor(kursNomi: String?, kursHaqidaMalumot: String?) {
        this.kursNomi = kursNomi
        this.kursHaqidaMalumot = kursHaqidaMalumot
    }


}