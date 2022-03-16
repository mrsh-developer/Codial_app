package mrsh.com.a63codialapp.models

class Talaba{

    var id:Int? = null
    var name:String? = null
    var familyasi:String? = null
    var otasiniIsmi:String? = null
    var number:String? = null
    var data:String? = null
    var mentorlarID:String? = null
    var guruhlarID:String? = null
    var kurslarID:Kurslar? = null



    constructor(
        id: Int?,
        name: String?,
        familyasi: String?,
        otasiniIsmi: String?,
        number: String?,
        data: String?,
        mentorlarID: String?,
        guruhlarID: String?,
        kurslarID: Kurslar?
    ) {
        this.id = id
        this.name = name
        this.familyasi = familyasi
        this.otasiniIsmi = otasiniIsmi
        this.number = number
        this.data = data
        this.mentorlarID = mentorlarID
        this.guruhlarID = guruhlarID
        this.kurslarID = kurslarID
    }

    constructor(
        name: String?,
        familyasi: String?,
        otasiniIsmi: String?,
        number: String?,
        data: String?,
        mentorlarID: String?,
        guruhlarID: String?,
        kurslarID: Kurslar?
    ) {
        this.name = name
        this.familyasi = familyasi
        this.otasiniIsmi = otasiniIsmi
        this.number = number
        this.data = data
        this.mentorlarID = mentorlarID
        this.guruhlarID = guruhlarID
        this.kurslarID = kurslarID
    }
}