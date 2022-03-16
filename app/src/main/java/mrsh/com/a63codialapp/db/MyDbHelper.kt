package mrsh.com.a63codialapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import mrsh.com.a63codialapp.models.Guruhlar
import mrsh.com.a63codialapp.models.Mentorlar
import mrsh.com.a63codialapp.models.Talaba


class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    MyDbService {


    companion object {

        const val DB_NAME = "codialApp"
        const val DB_VERSION = 1

        const val TABLE_KURSLAR = "kurslar"
        const val KURSLAR_ID = "id"
        const val KURSLAR_NAME = "name"
        const val KURSLAR_MALUMOT = "malumot"

        const val TABLE_TALABA = "talabalar"
        const val TALABA_ID = "id"
        const val TALABA_NAME = "name"
        const val TALABA_FAMILYASI = "familyasi"
        const val TALABA_OTASINI_ISMI = "otasini_ismi"
        const val TALABA_NUMBER = "number"
        const val TALABA_DATA = "data"
        const val TALABA_KURSLAR_ID = "kurslar_id"
        const val TALABA_MENTORLAR_ID = "mentorlar_id"
        const val TALABA_GURUHLAR_ID = "guruhlar_id"

        const val TABLE_MENTORLAR = "mentorlar"
        const val MENTORLAR_ID = "id"
        const val MENTORLAR_ISM = "name"
        const val MENTORLAR_FAMILYA = "familya"
        const val MENTORLAR_OTASINIISMI = "otasiniismi"
        const val MENTORLAR_TELEPHONE = "telephone"
        const val MENTORLAR_KURSLAR_ID = "mentor_kurslar_id"

        const val TABLE_GURUHLAR = "guruhlar"
        const val GURUHLAR_ID = "id"
        const val GURUHLAR_NOMI = "name"
        const val GURUHLAR_OCHILGAN = "guruppa_ochilgan_payti"
        const val GURUHLAR_OCHILYOTGAN = "ochilyotgan_ochilgan"
        const val GURUHLAR_VAQT = "vaqt"
        const val GURUHLAR_KUN = "kun"
        const val GURUHLAR_KURSLAR_ID = "guruhlar_kurslar_id"
        const val GURUHLAR_MENTOR_ID = "mentor_id"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val kurslarQuery =
            "create table $TABLE_KURSLAR ($KURSLAR_ID integer not null primary key autoincrement unique, $KURSLAR_NAME text not null, $KURSLAR_MALUMOT text not null)"

        //, $TALABA_MENTORLAR_ID integer not null, foreign key ($TALABA_MENTORLAR_ID) references $TABLE_MENTORLAR ($MENTORLAR_ID), $TALABA_GURUHLAR_ID integer not null, foreign key ($TALABA_GURUHLAR_ID) references $TABLE_GURUHLAR ($GURUHLAR_ID)

        val talabaQuery =
            "create table $TABLE_TALABA ($TALABA_ID integer not null primary key autoincrement unique, $TALABA_NAME text not null, $TALABA_FAMILYASI text not null, $TALABA_OTASINI_ISMI text not null, $TALABA_NUMBER text not null, $TALABA_DATA text not null, $TALABA_MENTORLAR_ID text not null, $TALABA_GURUHLAR_ID text not null ,$TALABA_KURSLAR_ID integer not null, foreign key ($TALABA_KURSLAR_ID) references $TABLE_KURSLAR ($KURSLAR_ID))"

        val mentorQuery =
            "create table $TABLE_MENTORLAR ($MENTORLAR_ID integer not null primary key autoincrement unique, $MENTORLAR_ISM text not null, $MENTORLAR_FAMILYA text not null, $MENTORLAR_OTASINIISMI text not null, $MENTORLAR_TELEPHONE text not null, $MENTORLAR_KURSLAR_ID integer not null, foreign key ($MENTORLAR_KURSLAR_ID) references $TABLE_KURSLAR ($KURSLAR_ID))"

        val guruhlarQuery =
            "create table $TABLE_GURUHLAR ($GURUHLAR_ID integer not null primary key autoincrement unique, $GURUHLAR_NOMI text not null unique, $GURUHLAR_OCHILGAN text not null, $GURUHLAR_OCHILYOTGAN text not null, $GURUHLAR_KUN text not null, $GURUHLAR_VAQT text not null, $GURUHLAR_MENTOR_ID text not null, $GURUHLAR_KURSLAR_ID integer not null, foreign key ($GURUHLAR_KURSLAR_ID) references $TABLE_KURSLAR ($KURSLAR_ID))"

        db?.execSQL(kurslarQuery)
        db?.execSQL(talabaQuery)
        db?.execSQL(mentorQuery)
        db?.execSQL(guruhlarQuery)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun addKurslar(kurslar: mrsh.com.a63codialapp.models.Kurslar) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KURSLAR_NAME, kurslar.kursNomi)
        contentValues.put(KURSLAR_MALUMOT, kurslar.kursHaqidaMalumot)
        database.insert(TABLE_KURSLAR, null, contentValues)
        database.close()
    }

    override fun showKurslar(): List<mrsh.com.a63codialapp.models.Kurslar> {
        val list = ArrayList<mrsh.com.a63codialapp.models.Kurslar>()
        val database = this.readableDatabase
        val query = "select * from $TABLE_KURSLAR"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val kurslar = mrsh.com.a63codialapp.models.Kurslar(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
                )
                list.add(kurslar)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getKurslarById(id: Int): mrsh.com.a63codialapp.models.Kurslar {
        val database = this.readableDatabase
        val cursor = database.query(
            TABLE_KURSLAR,
            arrayOf(
                KURSLAR_ID,
                KURSLAR_NAME,
                KURSLAR_MALUMOT
            ),
            "$KURSLAR_ID = ?",
            arrayOf(id.toString()), null, null, null
        )
        cursor.moveToFirst()
        return mrsh.com.a63codialapp.models.Kurslar(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2)
        )
    }

    override fun editKurslar(kurslar: mrsh.com.a63codialapp.models.Kurslar): Int {
        TODO("Not yet implemented")
    }

    override fun deleteKurslar(kurslar: mrsh.com.a63codialapp.models.Kurslar) {
        TODO("Not yet implemented")
    }

    override fun addTalaba(talaba: Talaba) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TALABA_NAME, talaba.name)
        contentValues.put(TALABA_FAMILYASI, talaba.familyasi)
        contentValues.put(TALABA_OTASINI_ISMI, talaba.otasiniIsmi)
        contentValues.put(TALABA_NUMBER, talaba.number)
        contentValues.put(TALABA_DATA, talaba.data)
        contentValues.put(TALABA_MENTORLAR_ID, talaba.mentorlarID)
        contentValues.put(TALABA_GURUHLAR_ID, talaba.guruhlarID)
        contentValues.put(TALABA_KURSLAR_ID, talaba.kurslarID!!.id)

        database.insert(TABLE_TALABA, null, contentValues)
        database.close()

        /**
        const val TALABA_NAME = "name"
        const val TALABA_FAMILYASI = "familyasi"
        const val TALABA_OTASINI_ISMI = "otasini_ismi"
        const val TALABA_NUMBER = "number"
        const val TALABA_DATA = "data"
        const val TALABA_KURSLAR_ID = "kurslar_id"
        const val TALABA_MENTORLAR_ID = "mentorlar_id"
        const val TALABA_GURUHLAR_ID = "guruhlar_id"
         */
    }

    override fun showTalaba(): List<Talaba> {
        val list = ArrayList<mrsh.com.a63codialapp.models.Talaba>()
        val database = this.readableDatabase
        val query = "select * from $TABLE_TALABA"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val talaba = Talaba(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    getKurslarById(cursor.getInt(8))
                )
                list.add(talaba)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getDeleteTalabaByKurslarId(id: Int): Talaba {
        val database = this.readableDatabase
        val cursor = database.query(
            TABLE_TALABA,
            arrayOf(
              TALABA_ID,
                TALABA_NAME,
                TALABA_FAMILYASI,
                TALABA_OTASINI_ISMI,
                TALABA_NUMBER,
                TALABA_DATA,
                TALABA_KURSLAR_ID,
                TALABA_MENTORLAR_ID,
                TALABA_GURUHLAR_ID
            ),
            "$TALABA_ID = ?",
            arrayOf(id.toString()), null, null, null
        )

        /**
        const val TALABA_ID = "id"
        const val TALABA_NAME = "name"
        const val TALABA_FAMILYASI = "familyasi"
        const val TALABA_OTASINI_ISMI = "otasini_ismi"
        const val TALABA_NUMBER = "number"
        const val TALABA_DATA = "data"
        const val TALABA_KURSLAR_ID = "kurslar_id"
        const val TALABA_MENTORLAR_ID = "mentorlar_id"
        const val TALABA_GURUHLAR_ID = "guruhlar_id"
         */

        cursor.moveToFirst()
        return Talaba(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getString(5),
            cursor.getString(6),
            cursor.getString(7),
            getKurslarById(cursor.getInt(8))
        )
    }

    override fun editTalaba(talaba: Talaba): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TALABA_NAME, talaba.name)
        contentValues.put(TALABA_FAMILYASI, talaba.familyasi)
        contentValues.put(TALABA_OTASINI_ISMI, talaba.otasiniIsmi)
        contentValues.put(TALABA_NUMBER, talaba.number)
        contentValues.put(TALABA_DATA, talaba.data)
        contentValues.put(TALABA_KURSLAR_ID, talaba.kurslarID!!.id)
        contentValues.put(TALABA_MENTORLAR_ID,talaba.mentorlarID)
        contentValues.put(TALABA_GURUHLAR_ID,talaba.guruhlarID)


        /**
        const val TALABA_FAMILYASI = "familyasi"
        const val TALABA_OTASINI_ISMI = "otasini_ismi"
        const val TALABA_NUMBER = "number"
        const val TALABA_DATA = "data"
        const val TALABA_KURSLAR_ID = "kurslar_id"
        const val TALABA_MENTORLAR_ID = "mentorlar_id"
        const val TALABA_GURUHLAR_ID = "guruhlar_id"
         */

        return database.update(
            TABLE_TALABA,
            contentValues,
            "$TALABA_ID = ?",
            arrayOf(talaba.id.toString())
        )
    }

    override fun deleteTalaba(talaba: Talaba) {
        val database = this.writableDatabase
        database.delete(TABLE_TALABA,"$TALABA_ID = ?", arrayOf(talaba.id.toString()))
    }

    override fun addGuruhlar(guruhlar: Guruhlar) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(GURUHLAR_NOMI, guruhlar.guruhlarNomi)
        contentValues.put(GURUHLAR_OCHILGAN, guruhlar.ochilganGuruhlar)
        contentValues.put(GURUHLAR_OCHILYOTGAN, guruhlar.ochilyotganGuruhlar)
        contentValues.put(GURUHLAR_KUN, guruhlar.kun)
        contentValues.put(GURUHLAR_VAQT, guruhlar.vaqt)
        contentValues.put(GURUHLAR_MENTOR_ID, guruhlar.mentorId)
        contentValues.put(GURUHLAR_KURSLAR_ID, guruhlar.kurslar!!.id)
        database.insert(TABLE_GURUHLAR, null, contentValues)
        database.close()
    }

    override fun showGuruhlar(): List<Guruhlar> {
        val list = ArrayList<Guruhlar>()
        val database = this.readableDatabase
        val query = "select * from $TABLE_GURUHLAR"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val guruhlar = Guruhlar(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    getKurslarById(cursor.getInt(7))
                )
                list.add(guruhlar)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getGuruhlarById(id: Int): Guruhlar {
        val database = this.readableDatabase
        val cursor = database.query(
            TABLE_GURUHLAR,
            arrayOf(
                GURUHLAR_ID,
                GURUHLAR_NOMI,
                GURUHLAR_OCHILGAN,
                GURUHLAR_OCHILYOTGAN,
                GURUHLAR_VAQT,
                GURUHLAR_KUN,
                GURUHLAR_MENTOR_ID,
                GURUHLAR_KURSLAR_ID
            ),
            "$GURUHLAR_ID = ?",
            arrayOf(id.toString()), null, null, null
        )
        /**
        const val GURUHLAR_ID = "id"
        const val GURUHLAR_NOMI = "name"
        const val GURUHLAR_OCHILGAN = "ochilgan"
        const val GURUHLAR_OCHILYOTGAN = "ochilyotgan"
        const val GURUHLAR_VAQT = "vaqt"
        const val GURUHLAR_KUN  = "kun"
        const val GURUHLAR_KURSLAR_ID = "guruhlar_kurslar_id"
        const val GURUHLAR_MENTOR_ID = "mentor_id"
         */
        cursor.moveToFirst()
        return Guruhlar(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getString(5),
            cursor.getString(6),
            getKurslarById(cursor.getInt(7))
        )
    }

    override fun editGuruhlar(guruhlar: Guruhlar): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(GURUHLAR_NOMI, guruhlar.guruhlarNomi)
        contentValues.put(GURUHLAR_OCHILGAN, guruhlar.ochilganGuruhlar)
        contentValues.put(GURUHLAR_OCHILYOTGAN, guruhlar.ochilyotganGuruhlar)
        contentValues.put(GURUHLAR_KUN, guruhlar.kun)
        contentValues.put(GURUHLAR_VAQT, guruhlar.vaqt)
        contentValues.put(GURUHLAR_MENTOR_ID, guruhlar.mentorId)
        contentValues.put(GURUHLAR_KURSLAR_ID, guruhlar.kurslar!!.id)

        return database.update(
            TABLE_GURUHLAR,
            contentValues,
            "$GURUHLAR_ID = ?",
            arrayOf(guruhlar.id.toString())
        )
    }

    override fun deleteGuruhlar(guruhlar: Guruhlar) {
        val database = this.writableDatabase
        database.delete(TABLE_GURUHLAR,"$GURUHLAR_ID = ?", arrayOf(guruhlar.id.toString()))

    }

    override fun addMentorlar(mentorlar: Mentorlar) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(MENTORLAR_ISM, mentorlar.ism)
        contentValues.put(MENTORLAR_FAMILYA, mentorlar.familya)
        contentValues.put(MENTORLAR_OTASINIISMI, mentorlar.otasiniIsmi)
        contentValues.put(MENTORLAR_KURSLAR_ID, mentorlar.kurslar!!.id)
        contentValues.put(MENTORLAR_TELEPHONE, mentorlar.telephone)
        database.insert(TABLE_MENTORLAR, null, contentValues)
        database.close()
    }

    override fun showMentorlar(): List<Mentorlar> {
        val list = ArrayList<Mentorlar>()
        val database = this.readableDatabase
        val query = "select * from $TABLE_MENTORLAR"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val mentorlar = Mentorlar(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    getKurslarById(cursor.getInt(5))
                )
                list.add(mentorlar)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getMentorlarById(id: Int): Mentorlar {
        val database = this.readableDatabase
        val cursor = database.query(
            TABLE_MENTORLAR,
            arrayOf(
                MENTORLAR_ID, MENTORLAR_ISM, MENTORLAR_FAMILYA, MENTORLAR_OTASINIISMI,
                MENTORLAR_TELEPHONE, MENTORLAR_KURSLAR_ID
            ),
            "$MENTORLAR_ID = ?",
            arrayOf(id.toString()), null, null, null
        )

        cursor.moveToFirst()
        return Mentorlar(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            getKurslarById(cursor.getInt(5))
        )

    }

    override fun editMentorlar(mentorlar: Mentorlar): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(MENTORLAR_ID, mentorlar.id)
        contentValues.put(MENTORLAR_FAMILYA, mentorlar.familya)
        contentValues.put(MENTORLAR_ISM, mentorlar.ism)
        contentValues.put(MENTORLAR_OTASINIISMI, mentorlar.otasiniIsmi)
        contentValues.put(MENTORLAR_TELEPHONE, mentorlar.telephone)

        return database.update(
            TABLE_MENTORLAR,
            contentValues,
            "$MENTORLAR_ID = ?",
            arrayOf(mentorlar.id.toString())
        )
    }

    override fun deleteMentorlar(mentorlar: Mentorlar) {
        val database = this.writableDatabase
        database.delete(TABLE_MENTORLAR,"$MENTORLAR_ID = ?", arrayOf(mentorlar.id.toString()))

    }

}