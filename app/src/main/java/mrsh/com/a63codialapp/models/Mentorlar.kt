package mrsh.com.a63codialapp.models

class Mentorlar {
    var id:Int? = null
    var ism:String? = null
    var familya:String? = null
    var otasiniIsmi:String? = null
    var telephone:String? = null
    var kurslar:Kurslar? = null

    constructor(
        id: Int?,
        ism: String?,
        familya: String?,
        otasiniIsmi: String?,
        telephone: String?,
        kurslar: Kurslar?
    ) {
        this.id = id
        this.ism = ism
        this.familya = familya
        this.otasiniIsmi = otasiniIsmi
        this.telephone = telephone
        this.kurslar = kurslar
    }

    constructor(
        ism: String?,
        familya: String?,
        otasiniIsmi: String?,
        telephone: String?,
        kurslar: Kurslar?
    ) {
        this.ism = ism
        this.familya = familya
        this.otasiniIsmi = otasiniIsmi
        this.telephone = telephone
        this.kurslar = kurslar
    }

}