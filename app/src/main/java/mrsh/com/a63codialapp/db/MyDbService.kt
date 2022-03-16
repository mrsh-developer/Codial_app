package mrsh.com.a63codialapp.db

import mrsh.com.a63codialapp.models.Guruhlar
import mrsh.com.a63codialapp.models.Kurslar
import mrsh.com.a63codialapp.models.Mentorlar
import mrsh.com.a63codialapp.models.Talaba


interface MyDbService {

    fun addKurslar(kurslar: Kurslar)
    fun showKurslar(): List<Kurslar>
    fun getKurslarById(id: Int): Kurslar
    fun editKurslar(kurslar: Kurslar): Int
    fun deleteKurslar(kurslar: Kurslar)

    fun addTalaba(talaba: Talaba)
    fun showTalaba(): List<Talaba>
    fun getDeleteTalabaByKurslarId(id: Int): Talaba
    fun editTalaba(talaba: Talaba): Int
    fun deleteTalaba(talaba: Talaba)

    fun addGuruhlar(guruhlar: Guruhlar)
    fun showGuruhlar(): List<Guruhlar>
    fun getGuruhlarById(id: Int): Guruhlar
    fun editGuruhlar(guruhlar: Guruhlar): Int
    fun deleteGuruhlar(guruhlar: Guruhlar)

    fun addMentorlar(mentorlar: Mentorlar)
    fun showMentorlar(): List<Mentorlar>
    fun getMentorlarById(id: Int): Mentorlar
    fun editMentorlar(mentorlar: Mentorlar): Int
    fun deleteMentorlar(mentorlar: Mentorlar)



}