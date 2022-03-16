package mrsh.com.a63codialapp.yolanishlar

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_guruh_malumot_olish.view.*
import kotlinx.android.synthetic.main.fragment_ochilayotgan_guruhlar.view.*
import mrsh.com.a63codialapp.R
import mrsh.com.a63codialapp.db.CustomAdapter
import mrsh.com.a63codialapp.db.MyDbHelper
import mrsh.com.a63codialapp.db.RvAdapterOchilyotgan
import mrsh.com.a63codialapp.models.Guruhlar
import mrsh.com.a63codialapp.models.Mentorlar
import java.time.LocalDateTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GuruhMalumotOlish.newInstance] factory method to
 * create an instance of this fragment.
 */
class GuruhMalumotOlish : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var key: Int? = null
    lateinit var myDbHelper: MyDbHelper
    lateinit var root: View
    lateinit var list: ArrayList<String>
    private var kunlar = arrayListOf("Dushanba/Chorshanba/Juma", "Seshanba/Payshanba/Shanba")
    internal var vaqtlar =
        arrayListOf("08:00/10:00", "10:00/12:00", "14:00/16:00", "16:00/18:00", "18:00/20:00")
    lateinit var arrayMentorlar: ArrayList<String>
    var dKunlar: String = ""
    var dVaqtlar: String = ""
    var dMentorlar: String = ""
    var tutibOlKunlarniIndexni = 0
    lateinit var hashMapSpiner: HashMap<String, ArrayList<String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        root = inflater.inflate(R.layout.fragment_guruh_malumot_olish, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            onResume()
        }

        return root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

        key = arguments?.getInt("keyIndexGuruhMalumot")!!.toInt()
        myDbHelper = MyDbHelper(root.context)

        hashMapSpiner = HashMap()
        val arrayList = ArrayList<String>()
        arrayList.add("08:00/10:00")
        arrayList.add("10:00/12:00")
        arrayList.add("14:00/16:00")
        arrayList.add("16:00/18:00")
        arrayList.add("18:00/20:00")

        val arrayList1 = ArrayList<String>()
        arrayList1.add("08:00/10:00")
        arrayList1.add("10:00/12:00")
        arrayList1.add("14:00/16:00")
        arrayList1.add("16:00/18:00")
        arrayList1.add("18:00/20:00")


        val showGuruhlar = myDbHelper.showGuruhlar() as ArrayList


        val spinerKUnlar = root.spinner_kunlar
        val customAdapter = CustomAdapter(root.context, kunlar)
        spinerKUnlar.adapter = customAdapter


        val spinnerMentorlar = root.spinner_mentor_tanlang
        val kurslarById = myDbHelper.getKurslarById(key!!)

        arrayMentorlar = ArrayList()

        for (mentorlar in myDbHelper.showMentorlar()) {
            if (mentorlar.kurslar!!.id == kurslarById.id) {
                val ifa = mentorlar.ism.toString() + " " + mentorlar.familya.toString()
                arrayMentorlar.add(ifa)
            }
        }

        val customAdapter2 = CustomAdapter(root.context, arrayMentorlar)
        spinnerMentorlar.adapter = customAdapter2


        val spinerKUnlar1 = root.spinner_kunlar


        spinerKUnlar1.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?, arg1: View?,
                arg2: Int, arg3: Long
            ) {

                tutibOlKunlarniIndexni = arg2

                if (arg2 == 0) {

                    for (guruhlar in showGuruhlar) {
                        if (guruhlar.kun == "Dushanba/Chorshanba/Juma") {
                            myDbHelper = MyDbHelper(root.context)
                            val showGuruhlar1 = myDbHelper.showGuruhlar()
                            for (guruhlar1 in showGuruhlar1) {
                                myDbHelper = MyDbHelper(root.context)
                                val kurslarById1 = myDbHelper.getKurslarById(key!!)

                                if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Dushanba/Chorshanba/Juma") {
                                    //  arrayList.removeAt(arrayList.indexOf(guruhlar1.vaqt))

                                    arrayList.remove(guruhlar1.vaqt)


                                    val spinnerData = root.spinner_data
                                    val customAdapter1 = CustomAdapter(root.context, arrayList)
                                    spinnerData.adapter = customAdapter1
                                }

                            }

                            break
                        }
                    }



                }
                else if (arg2 == 1) {


                    for (guruhlar in showGuruhlar) {
                        if (guruhlar.kun == "Seshanba/Payshanba/Shanba") {

                            myDbHelper = MyDbHelper(root.context)
                            val showGuruhlar1 = myDbHelper.showGuruhlar()
                            for (guruhlar1 in showGuruhlar1) {
                                myDbHelper = MyDbHelper(root.context)
                                val kurslarById1 = myDbHelper.getKurslarById(key!!)

                                if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Seshanba/Payshanba/Shanba") {

                                    arrayList1.remove(guruhlar1.vaqt)
                                    println("Kirish kerak emas edi bu yerga")

                                    println(arrayList1.toString())
                                    val spinnerData = root.spinner_data
                                    val customAdapter1 = CustomAdapter(root.context, arrayList1)
                                    spinnerData.adapter = customAdapter1
                                }

                            }

                            break
                        }
                    }



                }

                dKunlar = kunlar[arg2]
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {

            }
        }

        val customAdapterS = CustomAdapter(root.context, kunlar)
        spinerKUnlar1.adapter = customAdapterS


        val spinnerData1 = root.spinner_data

        spinnerData1.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?, arg1: View?,
                arg2: Int, arg3: Long
            ) {



                if (tutibOlKunlarniIndexni == 0){


                        for (guruhlar in showGuruhlar) {
                            if (guruhlar.kun == "Dushanba/Chorshanba/Juma") {
                                myDbHelper = MyDbHelper(root.context)
                                val showGuruhlar1 = myDbHelper.showGuruhlar()
                                for (guruhlar1 in showGuruhlar1) {
                                    myDbHelper = MyDbHelper(root.context)
                                    val kurslarById1 = myDbHelper.getKurslarById(key!!)

                                    if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Dushanba/Chorshanba/Juma") {
                                        //  arrayList.removeAt(arrayList.indexOf(guruhlar1.vaqt))

                                        arrayList.remove(guruhlar1.vaqt)

                                    }

                                }

                                break
                            }
                        }

                    if (arrayList.size != 0){
                        dVaqtlar = arrayList[arg2]
                    }


                }
                else if(tutibOlKunlarniIndexni == 1){


                        for (guruhlar in showGuruhlar) {
                            if (guruhlar.kun == "Seshanba/Payshanba/Shanba") {
                                myDbHelper = MyDbHelper(root.context)
                                val showGuruhlar1 = myDbHelper.showGuruhlar()
                                for (guruhlar1 in showGuruhlar1) {
                                    myDbHelper = MyDbHelper(root.context)
                                    val kurslarById1 = myDbHelper.getKurslarById(key!!)

                                    if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Seshanba/Payshanba/Shanba") {

                                        arrayList1.remove(guruhlar1.vaqt)


                                    }

                                }

                                break
                            }
                        }




                    if (arrayList1.size != 0){
                        dVaqtlar = arrayList1[arg2]
                    }

                }

            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {

            }
        }
        val customAdapterD = CustomAdapter(root.context, vaqtlar)
        spinnerData1.adapter = customAdapterD


        val spinnerMentorlar23 = root.spinner_mentor_tanlang

        spinnerMentorlar23.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?, arg1: View?,
                arg2: Int, arg3: Long
            ) {


                dMentorlar = arg2.toString()
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {

            }
        }

        val customAdapter23 = CustomAdapter(root.context, arrayMentorlar)
        spinnerMentorlar23.adapter = customAdapter23

        root.orqaga_mentor_saqlash.setOnClickListener {

            findNavController().popBackStack()
        }



            root.saqlash_guruhlar.setOnClickListener {
                val guruhNomi = root.guruh_nomi.text.toString()

                myDbHelper = MyDbHelper(root.context)
                var l = 0
                val showMentorlar = myDbHelper.showMentorlar()

                for (mentorlar in showMentorlar) {
                    if (kurslarById.id.toString() == mentorlar.kurslar!!.id.toString()) {
                        l = (mentorlar.id!!.toInt())
break
                    }
                }
                val mentorlarById = myDbHelper.getMentorlarById(dMentorlar.toInt() + l)


                myDbHelper = MyDbHelper(root.context)
                val kurslar = myDbHelper.getKurslarById(key!!.toInt())

                if (dVaqtlar == ""){
                    println("Kirdita")
                    Toast.makeText(context, "Vaqt ni kiriting", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }else {

                    val now = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        java.time.LocalDateTime.now()
                    } else {

                    }

                    val guruhlar = Guruhlar(
                        guruhNomi,
                        "$now",
                        "1",
                        dKunlar,
                        dVaqtlar,
                        mentorlarById.id.toString(),
                        kurslar
                    )

                    println(kurslar.id)

                    myDbHelper.addGuruhlar(guruhlar)
                    findNavController().popBackStack()
                }


            }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GuruhMalumotOlish.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GuruhMalumotOlish().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
