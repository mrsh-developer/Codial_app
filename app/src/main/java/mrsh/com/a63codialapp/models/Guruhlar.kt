package mrsh.com.a63codialapp.models

class Guruhlar {
    var id:Int? = null
    var guruhlarNomi:String? = null
    var ochilganGuruhlar:String? = null
    var ochilyotganGuruhlar:String? = null
    var kun:String? = null
    var vaqt:String? = null
    var mentorId:String? = null
    var kurslar:Kurslar? = null



    constructor(
        id: Int?,
        guruhlarNomi: String?,
        ochilganGuruhlar: String?,
        ochilyotganGuruhlar: String?,
        kun: String?,
        vaqt: String?,
        mentorId: String?,
        kurslar: Kurslar?
    ) {
        this.id = id
        this.guruhlarNomi = guruhlarNomi
        this.ochilganGuruhlar = ochilganGuruhlar
        this.ochilyotganGuruhlar = ochilyotganGuruhlar
        this.kun = kun
        this.vaqt = vaqt
        this.mentorId = mentorId
        this.kurslar = kurslar
    }

    constructor(
        guruhlarNomi: String?,
        ochilganGuruhlar: String?,
        ochilyotganGuruhlar: String?,
        kun: String?,
        vaqt: String?,
        mentorId: String?,
        kurslar: Kurslar?
    ) {
        this.guruhlarNomi = guruhlarNomi
        this.ochilganGuruhlar = ochilganGuruhlar
        this.ochilyotganGuruhlar = ochilyotganGuruhlar
        this.kun = kun
        this.vaqt = vaqt
        this.mentorId = mentorId
        this.kurslar = kurslar
    }
}