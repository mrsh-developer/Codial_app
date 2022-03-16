package mrsh.com.a63codialapp.yolanishlar

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_malumotlar_guruhlar.view.*
import kotlinx.android.synthetic.main.fragment_ochilayotgan_guruhlar.view.*
import kotlinx.android.synthetic.main.fragment_ochilgan_guruhlar.view.*
import mrsh.com.a63codialapp.R
import mrsh.com.a63codialapp.databinding.ItemCustomDialogGuruhlarBinding
import mrsh.com.a63codialapp.databinding.ItemTalabaOchirishBinding
import mrsh.com.a63codialapp.databinding.RvItemOchilganGuruhlarBinding
import mrsh.com.a63codialapp.db.*
import mrsh.com.a63codialapp.models.Guruhlar
import mrsh.com.a63codialapp.models.Kurslar
import mrsh.com.a63codialapp.models.Talaba


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MalumotlarGuruhlar : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    var key: Int? = null
    lateinit var myDbHelper: MyDbHelper
    lateinit var root: View
    lateinit var list: ArrayList<String>
    lateinit var kurslar: Kurslar

    var tutibOlKunlarniIndexni = 0
    lateinit var kurslarById: Kurslar
    private var kunlar = arrayListOf("D/C/J", "S/P/S")
    var dVaqtlar: String = ""
    var dMentorlar: String = ""
    var dKunlar: String = ""
    lateinit var arrayMentorlar1: ArrayList<String>
    lateinit var rvAdapterOchilgan: RvAdapterOchilgan


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



        root = inflater.inflate(R.layout.fragment_malumotlar_guruhlar, container, false)

        onResume()

        root.orqaga_guruh.setOnClickListener {
            findNavController().popBackStack()
        }

        list = ArrayList()
        list.add("Ochilgan guruhlar")
        list.add("Ochilmagan guruhlar")

        val viewPager: ViewPager = root.findViewById(R.id.view_pager)
        val tabLayout: TabLayout = root.findViewById(R.id.tablayout_guruh)


        tabLayout.setupWithViewPager(viewPager)


        val adapter = MyFragmentPagerAdapter(childFragmentManager)


        adapter.addFrag(OchilganGuruhlar(), "Ochilgan guruhlar")
        adapter.addFrag(OchilayotganGuruhlar(), "Ochilayotgan guruhlar")

        viewPager.adapter = adapter

        //    println(DetailOnPageChangeListener().currentPage)

        root.view_pager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

                if (position == 0) {
                    myDbHelper = MyDbHelper(root.context)
                    val showGuruhlar = myDbHelper.showGuruhlar()
                    val newList = ArrayList<Guruhlar>()
                    for (guruhlar in showGuruhlar) {
                        key = arguments?.getInt("keyIndexGuruh")!!.toInt()
                        myDbHelper = MyDbHelper(root.context)
                        if (key.toString() == guruhlar.kurslar!!.id!!.toString() && guruhlar.ochilyotganGuruhlar == "0") {
                            newList.add(guruhlar)
                        }
                    }



                    val rvAdapterOchilgan = RvAdapterOchilgan(
                        newList,object:RvAdapterOchilgan.RvClick{
                            override fun editGurupalar(guruhlar: Guruhlar) {
                                val dialog = AlertDialog.Builder(root.context).create()
                                val inflate =
                                    ItemCustomDialogGuruhlarBinding.inflate(layoutInflater)
                                dialog.setView(inflate.root)
                                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                                dialog.show()

                                inflate.guruhNomiOzgartiriladigon.setText(guruhlar.guruhlarNomi)


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


                                val spinnerMentorTanlangOzgartiriladigon =
                                    inflate.spinnerMentorTanlangOzgartiriladigon
                                val kurslarById = myDbHelper.getKurslarById(key!!)
                                arrayMentorlar1 = ArrayList()

                                for (mentorlar in myDbHelper.showMentorlar()) {
                                    if (mentorlar.kurslar!!.id == kurslarById.id) {
                                        val ifa =
                                            mentorlar.ism.toString() + " " + mentorlar.familya.toString()
                                        arrayMentorlar1.add(ifa)

                                    }
                                }

                                spinnerMentorTanlangOzgartiriladigon.onItemSelectedListener =
                                    object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            parent: AdapterView<*>?,
                                            view: View?,
                                            position: Int,
                                            id: Long
                                        ) {
                                            dMentorlar = position.toString()

                                        }

                                        override fun onNothingSelected(parent: AdapterView<*>?) {

                                        }
                                    }


                                val c = CustomAdapter(root.context, arrayMentorlar1)
                                spinnerMentorTanlangOzgartiriladigon.adapter = c

                                val spinnerKunlarOzgartiriladigon =
                                    inflate.spinnerKunlarOzgartiriladigon

                                spinnerKunlarOzgartiriladigon.onItemSelectedListener =
                                    object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            parent: AdapterView<*>?,
                                            view: View?,
                                            position: Int,
                                            id: Long
                                        ) {
                                            tutibOlKunlarniIndexni = position
                                            if (position == 0) {

                                                for (guruhlar in showGuruhlar) {
                                                    if (guruhlar.kun == "Dushanba/Chorshanba/Juma") {
                                                        myDbHelper = MyDbHelper(root.context)
                                                        val showGuruhlar1 =
                                                            myDbHelper.showGuruhlar()
                                                        for (guruhlar1 in showGuruhlar1) {
                                                            myDbHelper =
                                                                MyDbHelper(root.context)
                                                            val kurslarById1 =
                                                                myDbHelper.getKurslarById(key!!)

                                                            if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Dushanba/Chorshanba/Juma") {
                                                                //  arrayList.removeAt(arrayList.indexOf(guruhlar1.vaqt))

                                                                arrayList.remove(guruhlar1.vaqt)


                                                                val spinnerDataOzgartiriladigon =
                                                                    inflate.spinnerDataOzgartiriladigon
                                                                val customAdapter1 =
                                                                    CustomAdapter(
                                                                        root.context,
                                                                        arrayList
                                                                    )
                                                                spinnerDataOzgartiriladigon.adapter =
                                                                    customAdapter1
                                                            }

                                                        }

                                                        break
                                                    }
                                                }


                                            } else if (position == 1) {


                                                for (guruhlar in showGuruhlar) {
                                                    if (guruhlar.kun == "Seshanba/Payshanba/Shanba") {

                                                        myDbHelper = MyDbHelper(root.context)
                                                        val showGuruhlar1 =
                                                            myDbHelper.showGuruhlar()
                                                        for (guruhlar1 in showGuruhlar1) {
                                                            myDbHelper =
                                                                MyDbHelper(root.context)
                                                            val kurslarById1 =
                                                                myDbHelper.getKurslarById(key!!)

                                                            if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Seshanba/Payshanba/Shanba") {

                                                                arrayList1.remove(guruhlar1.vaqt)
                                                                println("Kirish kerak emas edi bu yerga")

                                                                println(arrayList1.toString())

                                                                val spinnerDataOzgartiriladigon =
                                                                    inflate.spinnerDataOzgartiriladigon
                                                                val customAdapter1 =
                                                                    CustomAdapter(
                                                                        root.context,
                                                                        arrayList1
                                                                    )
                                                                spinnerDataOzgartiriladigon.adapter =
                                                                    customAdapter1
                                                            }

                                                        }

                                                        break
                                                    }
                                                }


                                            }

                                            dKunlar = kunlar[position]
                                        }


                                        override fun onNothingSelected(parent: AdapterView<*>?) {

                                        }

                                    }

                                val c1 = CustomAdapter(root.context, kunlar)
                                spinnerKunlarOzgartiriladigon.adapter = c1


                                val spinnerDataOzgartiriladigon =
                                    inflate.spinnerDataOzgartiriladigon

                                spinnerDataOzgartiriladigon.onItemSelectedListener =
                                    object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            parent: AdapterView<*>?,
                                            view: View?,
                                            position: Int,
                                            id: Long
                                        ) {
                                            if (tutibOlKunlarniIndexni == 0) {


                                                for (guruhlar in showGuruhlar) {
                                                    if (guruhlar.kun == "Dushanba/Chorshanba/Juma") {
                                                        myDbHelper = MyDbHelper(root.context)
                                                        val showGuruhlar1 =
                                                            myDbHelper.showGuruhlar()
                                                        for (guruhlar1 in showGuruhlar1) {
                                                            myDbHelper =
                                                                MyDbHelper(root.context)
                                                            val kurslarById1 =
                                                                myDbHelper.getKurslarById(key!!)

                                                            if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Dushanba/Chorshanba/Juma") {
                                                                //  arrayList.removeAt(arrayList.indexOf(guruhlar1.vaqt))

                                                                arrayList.remove(guruhlar1.vaqt)

                                                            }

                                                        }

                                                        break
                                                    }
                                                }

                                                if (arrayList.size != 0) {
                                                    dVaqtlar = arrayList[position]
                                                }


                                            } else if (tutibOlKunlarniIndexni == 1) {


                                                for (guruhlar in showGuruhlar) {
                                                    if (guruhlar.kun == "Seshanba/Payshanba/Shanba") {
                                                        myDbHelper = MyDbHelper(root.context)
                                                        val showGuruhlar1 =
                                                            myDbHelper.showGuruhlar()
                                                        for (guruhlar1 in showGuruhlar1) {
                                                            myDbHelper =
                                                                MyDbHelper(root.context)
                                                            val kurslarById1 =
                                                                myDbHelper.getKurslarById(key!!)

                                                            if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Seshanba/Payshanba/Shanba") {

                                                                arrayList1.remove(guruhlar1.vaqt)


                                                            }

                                                        }

                                                        break
                                                    }
                                                }




                                                if (arrayList1.size != 0) {
                                                    dVaqtlar = arrayList1[position]
                                                }

                                            }
                                        }

                                        override fun onNothingSelected(parent: AdapterView<*>?) {

                                        }

                                    }

                                inflate.ozgartirishGuruhlarMalumotlarini.setOnClickListener {

                                    myDbHelper = MyDbHelper(root.context)
                                    val mentorlarById =
                                        myDbHelper.getMentorlarById(dMentorlar.toInt() + 1)


                                    val toString =
                                        inflate.guruhNomiOzgartiriladigon.text.toString()

                                    println(toString)

                                    dKunlar = if (dKunlar == "D/C/J")
                                        "Dushanba/Chorshanba/Juma"
                                    else "Seshanba/Payshanba/Shanba"
                                    println(dKunlar)
                                    println(dVaqtlar)
                                    println(arrayMentorlar1[dMentorlar.toInt()])

                                    myDbHelper = MyDbHelper(root.context)
                                    guruhlar.guruhlarNomi = toString
                                    guruhlar.vaqt = dVaqtlar
                                    guruhlar.kurslar = kurslarById
                                    guruhlar.kun = dKunlar
                                    guruhlar.mentorId = (dMentorlar.toInt() + 1).toString()

                                    myDbHelper.editGuruhlar(guruhlar)

                                    Toast.makeText(
                                        root.context,
                                        "Malumot O'zgardi",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    dialog.dismiss()

                                    updata1()

                                }
                                inflate.yopishOzgarganGuruhlar.setOnClickListener {
                                    dialog.dismiss()
                                }


                            }

                            override fun showGuruhlar(guruhlar: Guruhlar) {
                                root.findNavController().navigate(
                                    R.id.showGruppalar,
                                    bundleOf(
                                        "kursId" to kurslarById.id,
                                        "guruhlar1" to guruhlar.id,
                                        "kalitSoz" to 1
                                    )
                                )
                                println(guruhlar.id)
                            }

                            override fun deleteGuruhlar(guruhlar: Guruhlar) {
                                val dialog = AlertDialog.Builder(root.context).create()
                                val itemTalabaOchirishBinding =
                                    ItemTalabaOchirishBinding.inflate(layoutInflater)
                                dialog.setView(itemTalabaOchirishBinding.root)
                                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                               itemTalabaOchirishBinding.o.text = "Gruppani ochirsangiz undagi talabalar ham o'chib ketadi"
                                dialog.show()

                                itemTalabaOchirishBinding.ha.setOnClickListener {
                                    myDbHelper = MyDbHelper(root.context)

                                    val showTalaba = myDbHelper.showTalaba()
                                    for (talaba in showTalaba) {
                                        if (talaba.mentorlarID.toString() == guruhlar.id.toString()){
                                            myDbHelper.deleteTalaba(talaba)
                                        }
                                    }

                                    myDbHelper.deleteGuruhlar(guruhlar)

                                    updata1()
                                    dialog.dismiss()
                                }
                                itemTalabaOchirishBinding.yoq.setOnClickListener {
                                    dialog.dismiss()
                                }
                            }

                            override fun oquvchilarSoni(
                                itemOchilganGuruhlarBinding: RvItemOchilganGuruhlarBinding,
                                guruhlar: Guruhlar
                            ) {
                                myDbHelper = MyDbHelper(root.context)
                                myDbHelper = MyDbHelper(root.context)
                                val list = ArrayList<Talaba>()
                                val showTalaba = myDbHelper.showTalaba() as ArrayList
                                for (talaba in showTalaba) {
                                    if (kurslarById.id == talaba.kurslarID!!.id && guruhlar.id == talaba.mentorlarID!!.toInt())
                                        list.add(talaba)
                                }

                                itemOchilganGuruhlarBinding.malumot.text =
                                    "O'quvchilar soni ${list.size} ta"
                            }

                        })

                    root.rv_ochilgan_gurupplar.adapter = rvAdapterOchilgan


                }



            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        root.qoshishGuruhlar.visibility = View.INVISIBLE


                    }
                    1 -> {
                        root.qoshishGuruhlar.visibility = View.VISIBLE
                        root.qoshishGuruhlar.setOnClickListener {
                            key = arguments?.getInt("keyIndexGuruh")!!.toInt()
                            myDbHelper = MyDbHelper(root.context)
                            val kurslarById = myDbHelper.getKurslarById(key!!)

                            root.k_g_n.text = kurslarById.kursNomi
                            root.findNavController().navigate(
                                R.id.guruhMalumotOlish,
                                bundleOf("keyIndexGuruhMalumot" to kurslarById.id, "keyValue" to 1)
                            )


                            onResume()
                        }

                        myDbHelper = MyDbHelper(root.context)
                        val showGuruhlar = myDbHelper.showGuruhlar()
                        val newList = ArrayList<Guruhlar>()
                        for (guruhlar in showGuruhlar) {
                            key = arguments?.getInt("keyIndexGuruh")!!.toInt()
                            myDbHelper = MyDbHelper(root.context)
                            if (key.toString() == guruhlar.kurslar!!.id.toString() && guruhlar.ochilyotganGuruhlar == "1") {
                                newList.add(guruhlar)
                                println(guruhlar.ochilyotganGuruhlar)
                            }
                        }


                        val rvAdapterOchilyotgan = RvAdapterOchilyotgan(
                            newList,
                            object : RvAdapterOchilyotgan.RvClick {
                                override fun editGurupalar(guruhlar: Guruhlar) {
                                    val dialog = AlertDialog.Builder(root.context).create()
                                    val inflate =
                                        ItemCustomDialogGuruhlarBinding.inflate(layoutInflater)
                                    dialog.setView(inflate.root)
                                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                                    dialog.show()

                                    inflate.guruhNomiOzgartiriladigon.setText(guruhlar.guruhlarNomi)


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


                                    val spinnerMentorTanlangOzgartiriladigon =
                                        inflate.spinnerMentorTanlangOzgartiriladigon
                                    val kurslarById = myDbHelper.getKurslarById(key!!)
                                    arrayMentorlar1 = ArrayList()

                                    for (mentorlar in myDbHelper.showMentorlar()) {
                                        if (mentorlar.kurslar!!.id == kurslarById.id) {
                                            val ifa =
                                                mentorlar.ism.toString() + " " + mentorlar.familya.toString()
                                            arrayMentorlar1.add(ifa)

                                        }
                                    }

                                    spinnerMentorTanlangOzgartiriladigon.onItemSelectedListener =
                                        object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(
                                                parent: AdapterView<*>?,
                                                view: View?,
                                                position: Int,
                                                id: Long
                                            ) {
                                                dMentorlar = position.toString()

                                            }

                                            override fun onNothingSelected(parent: AdapterView<*>?) {

                                            }
                                        }


                                    val c = CustomAdapter(root.context, arrayMentorlar1)
                                    spinnerMentorTanlangOzgartiriladigon.adapter = c

                                    val spinnerKunlarOzgartiriladigon =
                                        inflate.spinnerKunlarOzgartiriladigon

                                    spinnerKunlarOzgartiriladigon.onItemSelectedListener =
                                        object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(
                                                parent: AdapterView<*>?,
                                                view: View?,
                                                position: Int,
                                                id: Long
                                            ) {
                                                tutibOlKunlarniIndexni = position
                                                if (position == 0) {

                                                    for (guruhlar in showGuruhlar) {
                                                        if (guruhlar.kun == "Dushanba/Chorshanba/Juma") {
                                                            myDbHelper = MyDbHelper(root.context)
                                                            val showGuruhlar1 =
                                                                myDbHelper.showGuruhlar()
                                                            for (guruhlar1 in showGuruhlar1) {
                                                                myDbHelper =
                                                                    MyDbHelper(root.context)
                                                                val kurslarById1 =
                                                                    myDbHelper.getKurslarById(key!!)

                                                                if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Dushanba/Chorshanba/Juma") {
                                                                    //  arrayList.removeAt(arrayList.indexOf(guruhlar1.vaqt))

                                                                    arrayList.remove(guruhlar1.vaqt)


                                                                    val spinnerDataOzgartiriladigon =
                                                                        inflate.spinnerDataOzgartiriladigon
                                                                    val customAdapter1 =
                                                                        CustomAdapter(
                                                                            root.context,
                                                                            arrayList
                                                                        )
                                                                    spinnerDataOzgartiriladigon.adapter =
                                                                        customAdapter1
                                                                }

                                                            }

                                                            break
                                                        }
                                                    }


                                                } else if (position == 1) {


                                                    for (guruhlar in showGuruhlar) {
                                                        if (guruhlar.kun == "Seshanba/Payshanba/Shanba") {

                                                            myDbHelper = MyDbHelper(root.context)
                                                            val showGuruhlar1 =
                                                                myDbHelper.showGuruhlar()
                                                            for (guruhlar1 in showGuruhlar1) {
                                                                myDbHelper =
                                                                    MyDbHelper(root.context)
                                                                val kurslarById1 =
                                                                    myDbHelper.getKurslarById(key!!)

                                                                if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Seshanba/Payshanba/Shanba") {

                                                                    arrayList1.remove(guruhlar1.vaqt)
                                                                    println("Kirish kerak emas edi bu yerga")

                                                                    println(arrayList1.toString())

                                                                    val spinnerDataOzgartiriladigon =
                                                                        inflate.spinnerDataOzgartiriladigon
                                                                    val customAdapter1 =
                                                                        CustomAdapter(
                                                                            root.context,
                                                                            arrayList1
                                                                        )
                                                                    spinnerDataOzgartiriladigon.adapter =
                                                                        customAdapter1
                                                                }

                                                            }

                                                            break
                                                        }
                                                    }


                                                }

                                                dKunlar = kunlar[position]
                                            }


                                            override fun onNothingSelected(parent: AdapterView<*>?) {

                                            }

                                        }

                                    val c1 = CustomAdapter(root.context, kunlar)
                                    spinnerKunlarOzgartiriladigon.adapter = c1


                                    val spinnerDataOzgartiriladigon =
                                        inflate.spinnerDataOzgartiriladigon

                                    spinnerDataOzgartiriladigon.onItemSelectedListener =
                                        object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(
                                                parent: AdapterView<*>?,
                                                view: View?,
                                                position: Int,
                                                id: Long
                                            ) {
                                                if (tutibOlKunlarniIndexni == 0) {


                                                    for (guruhlar in showGuruhlar) {
                                                        if (guruhlar.kun == "Dushanba/Chorshanba/Juma") {
                                                            myDbHelper = MyDbHelper(root.context)
                                                            val showGuruhlar1 =
                                                                myDbHelper.showGuruhlar()
                                                            for (guruhlar1 in showGuruhlar1) {
                                                                myDbHelper =
                                                                    MyDbHelper(root.context)
                                                                val kurslarById1 =
                                                                    myDbHelper.getKurslarById(key!!)

                                                                if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Dushanba/Chorshanba/Juma") {
                                                                    //  arrayList.removeAt(arrayList.indexOf(guruhlar1.vaqt))

                                                                    arrayList.remove(guruhlar1.vaqt)

                                                                }

                                                            }

                                                            break
                                                        }
                                                    }

                                                    if (arrayList.size != 0) {
                                                        dVaqtlar = arrayList[position]
                                                    }


                                                } else if (tutibOlKunlarniIndexni == 1) {


                                                    for (guruhlar in showGuruhlar) {
                                                        if (guruhlar.kun == "Seshanba/Payshanba/Shanba") {
                                                            myDbHelper = MyDbHelper(root.context)
                                                            val showGuruhlar1 =
                                                                myDbHelper.showGuruhlar()
                                                            for (guruhlar1 in showGuruhlar1) {
                                                                myDbHelper =
                                                                    MyDbHelper(root.context)
                                                                val kurslarById1 =
                                                                    myDbHelper.getKurslarById(key!!)

                                                                if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Seshanba/Payshanba/Shanba") {

                                                                    arrayList1.remove(guruhlar1.vaqt)


                                                                }

                                                            }

                                                            break
                                                        }
                                                    }




                                                    if (arrayList1.size != 0) {
                                                        dVaqtlar = arrayList1[position]
                                                    }

                                                }
                                            }

                                            override fun onNothingSelected(parent: AdapterView<*>?) {

                                            }

                                        }

                                    inflate.ozgartirishGuruhlarMalumotlarini.setOnClickListener {

                                        myDbHelper = MyDbHelper(root.context)
                                        val mentorlarById =
                                            myDbHelper.getMentorlarById(dMentorlar.toInt() + 1)


                                        val toString =
                                            inflate.guruhNomiOzgartiriladigon.text.toString()

                                        println(toString)

                                        dKunlar = if (dKunlar == "D/C/J")
                                            "Dushanba/Chorshanba/Juma"
                                        else "Seshanba/Payshanba/Shanba"
                                        println(dKunlar)
                                        println(dVaqtlar)
                                        println(arrayMentorlar1[dMentorlar.toInt()])

                                        myDbHelper = MyDbHelper(root.context)
                                        guruhlar.guruhlarNomi = toString
                                        guruhlar.vaqt = dVaqtlar
                                        guruhlar.kurslar = kurslarById
                                        guruhlar.kun = dKunlar
                                        guruhlar.mentorId = (dMentorlar.toInt() + 1).toString()

                                        myDbHelper.editGuruhlar(guruhlar)

                                        Toast.makeText(
                                            root.context,
                                            "Malumot O'zgardi",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        dialog.dismiss()

                                        update()

                                    }
                                    inflate.yopishOzgarganGuruhlar.setOnClickListener {
                                        dialog.dismiss()
                                    }


                                }

                                override fun showGuruhlar(guruhlar: Guruhlar) {
                                    root.findNavController().navigate(
                                        R.id.showGruppalar,
                                        bundleOf(
                                            "kursId" to kurslarById.id,
                                            "guruhlar1" to guruhlar.id
                                        )
                                    )
                                }

                                override fun deleteGuruhlar(guruhlar: Guruhlar) {
                                    val dialog = AlertDialog.Builder(root.context).create()
                                    val itemTalabaOchirishBinding =
                                        ItemTalabaOchirishBinding.inflate(layoutInflater)
                                    dialog.setView(itemTalabaOchirishBinding.root)
                                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                    itemTalabaOchirishBinding.o.text = "Gruppani ochirsangiz undagi talabalar ham o'chib ketadi"
                                    dialog.show()

                                    itemTalabaOchirishBinding.ha.setOnClickListener {
                                        myDbHelper = MyDbHelper(root.context)

                                        val showTalaba = myDbHelper.showTalaba()
                                        for (talaba in showTalaba) {
                                            if (talaba.mentorlarID.toString() == guruhlar.id.toString()){
                                                myDbHelper.deleteTalaba(talaba)
                                            }
                                        }

                                        myDbHelper.deleteGuruhlar(guruhlar)

                                        update()
                                        dialog.dismiss()
                                    }
                                    itemTalabaOchirishBinding.yoq.setOnClickListener {
                                        dialog.dismiss()
                                    }
                                }

                                override fun oquvchilarSoni(
                                    itemOchilganGuruhlarBinding: RvItemOchilganGuruhlarBinding,
                                    guruhlar: Guruhlar
                                ) {
                                    myDbHelper = MyDbHelper(root.context)
                                    myDbHelper = MyDbHelper(root.context)
                                    val list = ArrayList<Talaba>()
                                    val showTalaba = myDbHelper.showTalaba() as ArrayList
                                    for (talaba in showTalaba) {
                                        if (kurslarById.id == talaba.kurslarID!!.id && guruhlar.id == talaba.guruhlarID!!.toInt())
                                            list.add(talaba)
                                    }

                                    itemOchilganGuruhlarBinding.malumot.text =
                                        "O'quvchilar soni ${list.size} ta"
                                }


                            })
                        root.rv_ochilyatgan_gurupplar.adapter = rvAdapterOchilyotgan


                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        return root
    }

    class DetailOnPageChangeListener : SimpleOnPageChangeListener() {
        var currentPage = 0
            private set

        override fun onPageSelected(position: Int) {
            currentPage = position
        }
    }

    fun update() {
        myDbHelper = MyDbHelper(root.context)
        val showGuruhlar = myDbHelper.showGuruhlar()
        val newList = ArrayList<Guruhlar>()
        for (guruhlar in showGuruhlar) {
            key = arguments?.getInt("keyIndexGuruh")!!.toInt()
            myDbHelper = MyDbHelper(root.context)
            if (key.toString() == guruhlar.kurslar!!.id.toString() && guruhlar.ochilyotganGuruhlar == "1") {
                newList.add(guruhlar)
            }
        }

        val rvAdapterOchilyotgan = RvAdapterOchilyotgan(
            newList,
            object : RvAdapterOchilyotgan.RvClick {
                override fun editGurupalar(guruhlar: Guruhlar) {
                    val dialog = AlertDialog.Builder(root.context).create()
                    val inflate =
                        ItemCustomDialogGuruhlarBinding.inflate(layoutInflater)
                    dialog.setView(inflate.root)
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    dialog.show()

                    inflate.guruhNomiOzgartiriladigon.setText(guruhlar.guruhlarNomi)


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


                    val spinnerMentorTanlangOzgartiriladigon =
                        inflate.spinnerMentorTanlangOzgartiriladigon
                    val kurslarById = myDbHelper.getKurslarById(key!!)
                    arrayMentorlar1 = ArrayList()

                    for (mentorlar in myDbHelper.showMentorlar()) {
                        if (mentorlar.kurslar!!.id == kurslarById.id) {
                            val ifa =
                                mentorlar.ism.toString() + " " + mentorlar.familya.toString()
                            arrayMentorlar1.add(ifa)

                        }
                    }

                    spinnerMentorTanlangOzgartiriladigon.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                dMentorlar = position.toString()

                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }
                        }


                    val c = CustomAdapter(root.context, arrayMentorlar1)
                    spinnerMentorTanlangOzgartiriladigon.adapter = c

                    val spinnerKunlarOzgartiriladigon =
                        inflate.spinnerKunlarOzgartiriladigon

                    spinnerKunlarOzgartiriladigon.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                tutibOlKunlarniIndexni = position
                                if (position == 0) {

                                    for (guruhlar in showGuruhlar) {
                                        if (guruhlar.kun == "Dushanba/Chorshanba/Juma") {
                                            myDbHelper = MyDbHelper(root.context)
                                            val showGuruhlar1 =
                                                myDbHelper.showGuruhlar()
                                            for (guruhlar1 in showGuruhlar1) {
                                                myDbHelper =
                                                    MyDbHelper(root.context)
                                                val kurslarById1 =
                                                    myDbHelper.getKurslarById(key!!)

                                                if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Dushanba/Chorshanba/Juma") {
                                                    //  arrayList.removeAt(arrayList.indexOf(guruhlar1.vaqt))

                                                    arrayList.remove(guruhlar1.vaqt)


                                                    val spinnerDataOzgartiriladigon =
                                                        inflate.spinnerDataOzgartiriladigon
                                                    val customAdapter1 =
                                                        CustomAdapter(
                                                            root.context,
                                                            arrayList
                                                        )
                                                    spinnerDataOzgartiriladigon.adapter =
                                                        customAdapter1
                                                }

                                            }

                                            break
                                        }
                                    }


                                } else if (position == 1) {


                                    for (guruhlar in showGuruhlar) {
                                        if (guruhlar.kun == "Seshanba/Payshanba/Shanba") {

                                            myDbHelper = MyDbHelper(root.context)
                                            val showGuruhlar1 =
                                                myDbHelper.showGuruhlar()
                                            for (guruhlar1 in showGuruhlar1) {
                                                myDbHelper =
                                                    MyDbHelper(root.context)
                                                val kurslarById1 =
                                                    myDbHelper.getKurslarById(key!!)

                                                if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Seshanba/Payshanba/Shanba") {

                                                    arrayList1.remove(guruhlar1.vaqt)
                                                    println("Kirish kerak emas edi bu yerga")

                                                    println(arrayList1.toString())

                                                    val spinnerDataOzgartiriladigon =
                                                        inflate.spinnerDataOzgartiriladigon
                                                    val customAdapter1 =
                                                        CustomAdapter(
                                                            root.context,
                                                            arrayList1
                                                        )
                                                    spinnerDataOzgartiriladigon.adapter =
                                                        customAdapter1
                                                }

                                            }

                                            break
                                        }
                                    }


                                }

                                dKunlar = kunlar[position]
                            }


                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }

                        }

                    val c1 = CustomAdapter(root.context, kunlar)
                    spinnerKunlarOzgartiriladigon.adapter = c1


                    val spinnerDataOzgartiriladigon =
                        inflate.spinnerDataOzgartiriladigon

                    spinnerDataOzgartiriladigon.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (tutibOlKunlarniIndexni == 0) {


                                    for (guruhlar in showGuruhlar) {
                                        if (guruhlar.kun == "Dushanba/Chorshanba/Juma") {
                                            myDbHelper = MyDbHelper(root.context)
                                            val showGuruhlar1 =
                                                myDbHelper.showGuruhlar()
                                            for (guruhlar1 in showGuruhlar1) {
                                                myDbHelper =
                                                    MyDbHelper(root.context)
                                                val kurslarById1 =
                                                    myDbHelper.getKurslarById(key!!)

                                                if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Dushanba/Chorshanba/Juma") {
                                                    //  arrayList.removeAt(arrayList.indexOf(guruhlar1.vaqt))

                                                    arrayList.remove(guruhlar1.vaqt)

                                                }

                                            }

                                            break
                                        }
                                    }

                                    if (arrayList.size != 0) {
                                        dVaqtlar = arrayList[position]
                                    }


                                } else if (tutibOlKunlarniIndexni == 1) {


                                    for (guruhlar in showGuruhlar) {
                                        if (guruhlar.kun == "Seshanba/Payshanba/Shanba") {
                                            myDbHelper = MyDbHelper(root.context)
                                            val showGuruhlar1 =
                                                myDbHelper.showGuruhlar()
                                            for (guruhlar1 in showGuruhlar1) {
                                                myDbHelper =
                                                    MyDbHelper(root.context)
                                                val kurslarById1 =
                                                    myDbHelper.getKurslarById(key!!)

                                                if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Seshanba/Payshanba/Shanba") {

                                                    arrayList1.remove(guruhlar1.vaqt)


                                                }

                                            }

                                            break
                                        }
                                    }




                                    if (arrayList1.size != 0) {
                                        dVaqtlar = arrayList1[position]
                                    }

                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }

                        }

                    inflate.ozgartirishGuruhlarMalumotlarini.setOnClickListener {

                        myDbHelper = MyDbHelper(root.context)
                        val mentorlarById =
                            myDbHelper.getMentorlarById(dMentorlar.toInt() + 1)


                        val toString =
                            inflate.guruhNomiOzgartiriladigon.text.toString()

                        println(toString)

                        dKunlar = if (dKunlar == "D/C/J")
                            "Dushanba/Chorshanba/Juma"
                        else "Seshanba/Payshanba/Shanba"
                        println(dKunlar)
                        println(dVaqtlar)
                        println(arrayMentorlar1[dMentorlar.toInt()])

                        myDbHelper = MyDbHelper(root.context)
                        guruhlar.guruhlarNomi = toString
                        guruhlar.vaqt = dVaqtlar
                        guruhlar.kurslar = kurslarById
                        guruhlar.kun = dKunlar
                        guruhlar.mentorId = (dMentorlar.toInt() + 1).toString()

                        myDbHelper.editGuruhlar(guruhlar)

                        Toast.makeText(
                            root.context,
                            "Malumot O'zgardi",
                            Toast.LENGTH_SHORT
                        ).show()

                        dialog.dismiss()

                        update()

                    }
                    inflate.yopishOzgarganGuruhlar.setOnClickListener {
                        dialog.dismiss()
                    }


                }

                override fun showGuruhlar(guruhlar: Guruhlar) {
                    root.findNavController().navigate(
                        R.id.showGruppalar,
                        bundleOf(
                            "kursId" to kurslarById.id,
                            "guruhlar1" to guruhlar.id
                        )
                    )
                }

                override fun deleteGuruhlar(guruhlar: Guruhlar) {
                    val dialog = AlertDialog.Builder(root.context).create()
                    val itemTalabaOchirishBinding =
                        ItemTalabaOchirishBinding.inflate(layoutInflater)
                    dialog.setView(itemTalabaOchirishBinding.root)
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    itemTalabaOchirishBinding.o.text = "Gruppani ochirsangiz undagi talabalar ham o'chib ketadi"
                    dialog.show()

                    itemTalabaOchirishBinding.ha.setOnClickListener {
                        myDbHelper = MyDbHelper(root.context)

                        val showTalaba = myDbHelper.showTalaba()
                        for (talaba in showTalaba) {
                            if (talaba.mentorlarID.toString() == guruhlar.id.toString()){
                                myDbHelper.deleteTalaba(talaba)
                            }
                        }

                        myDbHelper.deleteGuruhlar(guruhlar)

                        update()
                        dialog.dismiss()
                    }
                    itemTalabaOchirishBinding.yoq.setOnClickListener {
                        dialog.dismiss()
                    }
                }

                override fun oquvchilarSoni(
                    itemOchilganGuruhlarBinding: RvItemOchilganGuruhlarBinding,
                    guruhlar: Guruhlar
                ) {
                    myDbHelper = MyDbHelper(root.context)
                    myDbHelper = MyDbHelper(root.context)
                    val list = ArrayList<Talaba>()
                    val showTalaba = myDbHelper.showTalaba() as ArrayList
                    for (talaba in showTalaba) {
                        if (kurslarById.id == talaba.kurslarID!!.id && guruhlar.id == talaba.guruhlarID!!.toInt())
                            list.add(talaba)
                    }

                    itemOchilganGuruhlarBinding.malumot.text =
                        "O'quvchilar soni ${list.size} ta"
                }



            })
        root.rv_ochilyatgan_gurupplar.adapter = rvAdapterOchilyotgan


    }

    fun updata1() {
        myDbHelper = MyDbHelper(root.context)
        val showGuruhlar = myDbHelper.showGuruhlar()
        val newList = ArrayList<Guruhlar>()
        for (guruhlar in showGuruhlar) {
            key = arguments?.getInt("keyIndexGuruh")!!.toInt()
            myDbHelper = MyDbHelper(root.context)
            if (key.toString() == guruhlar.mentorId.toString() && guruhlar.ochilyotganGuruhlar == "0") {
                newList.add(guruhlar)
            }
        }

        val rvAdapterOchilyotgan = RvAdapterOchilgan(
            newList,
            object : RvAdapterOchilgan.RvClick {
                override fun editGurupalar(guruhlar: Guruhlar) {
                    val dialog = AlertDialog.Builder(root.context).create()
                    val inflate =
                        ItemCustomDialogGuruhlarBinding.inflate(layoutInflater)
                    dialog.setView(inflate.root)
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    dialog.show()

                    inflate.guruhNomiOzgartiriladigon.setText(guruhlar.guruhlarNomi)


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


                    val spinnerMentorTanlangOzgartiriladigon =
                        inflate.spinnerMentorTanlangOzgartiriladigon
                    val kurslarById = myDbHelper.getKurslarById(key!!)
                    arrayMentorlar1 = ArrayList()

                    for (mentorlar in myDbHelper.showMentorlar()) {
                        if (mentorlar.kurslar!!.id == kurslarById.id) {
                            val ifa =
                                mentorlar.ism.toString() + " " + mentorlar.familya.toString()
                            arrayMentorlar1.add(ifa)

                        }
                    }

                    spinnerMentorTanlangOzgartiriladigon.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                dMentorlar = position.toString()

                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }
                        }


                    val c = CustomAdapter(root.context, arrayMentorlar1)
                    spinnerMentorTanlangOzgartiriladigon.adapter = c

                    val spinnerKunlarOzgartiriladigon =
                        inflate.spinnerKunlarOzgartiriladigon

                    spinnerKunlarOzgartiriladigon.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                tutibOlKunlarniIndexni = position
                                if (position == 0) {

                                    for (guruhlar in showGuruhlar) {
                                        if (guruhlar.kun == "Dushanba/Chorshanba/Juma") {
                                            myDbHelper = MyDbHelper(root.context)
                                            val showGuruhlar1 =
                                                myDbHelper.showGuruhlar()
                                            for (guruhlar1 in showGuruhlar1) {
                                                myDbHelper =
                                                    MyDbHelper(root.context)
                                                val kurslarById1 =
                                                    myDbHelper.getKurslarById(key!!)

                                                if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Dushanba/Chorshanba/Juma") {
                                                    //  arrayList.removeAt(arrayList.indexOf(guruhlar1.vaqt))

                                                    arrayList.remove(guruhlar1.vaqt)


                                                    val spinnerDataOzgartiriladigon =
                                                        inflate.spinnerDataOzgartiriladigon
                                                    val customAdapter1 =
                                                        CustomAdapter(
                                                            root.context,
                                                            arrayList
                                                        )
                                                    spinnerDataOzgartiriladigon.adapter =
                                                        customAdapter1
                                                }

                                            }

                                            break
                                        }
                                    }


                                } else if (position == 1) {


                                    for (guruhlar in showGuruhlar) {
                                        if (guruhlar.kun == "Seshanba/Payshanba/Shanba") {

                                            myDbHelper = MyDbHelper(root.context)
                                            val showGuruhlar1 =
                                                myDbHelper.showGuruhlar()
                                            for (guruhlar1 in showGuruhlar1) {
                                                myDbHelper =
                                                    MyDbHelper(root.context)
                                                val kurslarById1 =
                                                    myDbHelper.getKurslarById(key!!)

                                                if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Seshanba/Payshanba/Shanba") {

                                                    arrayList1.remove(guruhlar1.vaqt)
                                                    println("Kirish kerak emas edi bu yerga")

                                                    println(arrayList1.toString())

                                                    val spinnerDataOzgartiriladigon =
                                                        inflate.spinnerDataOzgartiriladigon
                                                    val customAdapter1 =
                                                        CustomAdapter(
                                                            root.context,
                                                            arrayList1
                                                        )
                                                    spinnerDataOzgartiriladigon.adapter =
                                                        customAdapter1
                                                }

                                            }

                                            break
                                        }
                                    }


                                }

                                dKunlar = kunlar[position]
                            }


                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }

                        }

                    val c1 = CustomAdapter(root.context, kunlar)
                    spinnerKunlarOzgartiriladigon.adapter = c1


                    val spinnerDataOzgartiriladigon =
                        inflate.spinnerDataOzgartiriladigon

                    spinnerDataOzgartiriladigon.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (tutibOlKunlarniIndexni == 0) {


                                    for (guruhlar in showGuruhlar) {
                                        if (guruhlar.kun == "Dushanba/Chorshanba/Juma") {
                                            myDbHelper = MyDbHelper(root.context)
                                            val showGuruhlar1 =
                                                myDbHelper.showGuruhlar()
                                            for (guruhlar1 in showGuruhlar1) {
                                                myDbHelper =
                                                    MyDbHelper(root.context)
                                                val kurslarById1 =
                                                    myDbHelper.getKurslarById(key!!)

                                                if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Dushanba/Chorshanba/Juma") {
                                                    //  arrayList.removeAt(arrayList.indexOf(guruhlar1.vaqt))

                                                    arrayList.remove(guruhlar1.vaqt)

                                                }

                                            }

                                            break
                                        }
                                    }

                                    if (arrayList.size != 0) {
                                        dVaqtlar = arrayList[position]
                                    }


                                } else if (tutibOlKunlarniIndexni == 1) {


                                    for (guruhlar in showGuruhlar) {
                                        if (guruhlar.kun == "Seshanba/Payshanba/Shanba") {
                                            myDbHelper = MyDbHelper(root.context)
                                            val showGuruhlar1 =
                                                myDbHelper.showGuruhlar()
                                            for (guruhlar1 in showGuruhlar1) {
                                                myDbHelper =
                                                    MyDbHelper(root.context)
                                                val kurslarById1 =
                                                    myDbHelper.getKurslarById(key!!)

                                                if (kurslarById1.id!!.toInt() == guruhlar1.kurslar!!.id!!.toInt() && guruhlar1.kun == "Seshanba/Payshanba/Shanba") {

                                                    arrayList1.remove(guruhlar1.vaqt)


                                                }

                                            }

                                            break
                                        }
                                    }




                                    if (arrayList1.size != 0) {
                                        dVaqtlar = arrayList1[position]
                                    }

                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }

                        }

                    inflate.ozgartirishGuruhlarMalumotlarini.setOnClickListener {

                        myDbHelper = MyDbHelper(root.context)
                        val mentorlarById =
                            myDbHelper.getMentorlarById(dMentorlar.toInt() + 1)


                        val toString =
                            inflate.guruhNomiOzgartiriladigon.text.toString()

                        println(toString)

                        dKunlar = if (dKunlar == "D/C/J")
                            "Dushanba/Chorshanba/Juma"
                        else "Seshanba/Payshanba/Shanba"
                        println(dKunlar)
                        println(dVaqtlar)
                        println(arrayMentorlar1[dMentorlar.toInt()])

                        myDbHelper = MyDbHelper(root.context)
                        guruhlar.guruhlarNomi = toString
                        guruhlar.vaqt = dVaqtlar
                        guruhlar.kurslar = kurslarById
                        guruhlar.kun = dKunlar
                        guruhlar.mentorId = (dMentorlar.toInt() + 1).toString()

                        myDbHelper.editGuruhlar(guruhlar)

                        Toast.makeText(
                            root.context,
                            "Malumot O'zgardi",
                            Toast.LENGTH_SHORT
                        ).show()

                        dialog.dismiss()

                        updata1()

                    }
                    inflate.yopishOzgarganGuruhlar.setOnClickListener {
                        dialog.dismiss()
                    }


                }

                override fun showGuruhlar(guruhlar: Guruhlar) {
                    root.findNavController().navigate(
                        R.id.showGruppalar,
                        bundleOf(
                            "kursId" to kurslarById.id,
                            "guruhlar1" to guruhlar.id,
                            "kalitSoz" to 1
                        )
                    )
                }

                override fun deleteGuruhlar(guruhlar: Guruhlar) {
                    val dialog = AlertDialog.Builder(root.context).create()
                    val itemTalabaOchirishBinding =
                        ItemTalabaOchirishBinding.inflate(layoutInflater)
                    dialog.setView(itemTalabaOchirishBinding.root)
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    itemTalabaOchirishBinding.o.text = "Gruppani ochirsangiz undagi talabalar ham o'chib ketadi"
                    dialog.show()

                    itemTalabaOchirishBinding.ha.setOnClickListener {
                        myDbHelper = MyDbHelper(root.context)

                        val showTalaba = myDbHelper.showTalaba()
                        for (talaba in showTalaba) {
                            if (talaba.mentorlarID.toString() == guruhlar.id.toString()){
                                myDbHelper.deleteTalaba(talaba)
                            }
                        }

                        myDbHelper.deleteGuruhlar(guruhlar)

                        updata1()
                        dialog.dismiss()
                    }
                    itemTalabaOchirishBinding.yoq.setOnClickListener {
                        dialog.dismiss()
                    }
                }

                override fun oquvchilarSoni(
                    itemOchilganGuruhlarBinding: RvItemOchilganGuruhlarBinding,
                    guruhlar: Guruhlar
                ) {
                    myDbHelper = MyDbHelper(root.context)
                    myDbHelper = MyDbHelper(root.context)
                    val list = ArrayList<Talaba>()
                    val showTalaba = myDbHelper.showTalaba() as ArrayList
                    for (talaba in showTalaba) {
                        if (kurslarById.id == talaba.kurslarID!!.id && guruhlar.id == talaba.guruhlarID!!.toInt())
                            list.add(talaba)
                    }

                    itemOchilganGuruhlarBinding.malumot.text =
                        "O'quvchilar soni ${list.size} ta"
                }


            })
        root.rv_ochilgan_gurupplar.adapter = rvAdapterOchilyotgan

    }

    override fun onResume() {
        super.onResume()

        key = arguments?.getInt("keyIndexGuruh")!!.toInt()
        myDbHelper = MyDbHelper(root.context)
        kurslarById = myDbHelper.getKurslarById(key!!)

        kurslar = kurslarById
        root.k_g_n.text = kurslarById.kursNomi



        /*     root.view_pager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                 override fun onPageScrolled(
                     position: Int,
                     positionOffset: Float,
                     positionOffsetPixels: Int
                 ) {

                 }

                 override fun onPageSelected(position: Int) {
                     when (position) {
                         0 -> {
                             root.qoshishGuruhlar.visibility = View.INVISIBLE
                         }
                         1 -> {
                             root.qoshishGuruhlar.visibility = View.VISIBLE
                             root.qoshishGuruhlar.setOnClickListener {
                                 key = arguments?.getInt("keyIndexGuruh")!!.toInt()
                                 myDbHelper = MyDbHelper(root.context)
                                 val kurslarById = myDbHelper.getKurslarById(key!!)

                                 root.k_g_n.text = kurslarById.kursNomi
                                 root.findNavController().navigate(
                                     R.id.guruhMalumotOlish,
                                     bundleOf("keyIndexGuruhMalumot" to kurslarById.id)
                                 )

                                 onResume()
                             }

                             myDbHelper = MyDbHelper(root.context)
                             val showGuruhlar = myDbHelper.showGuruhlar()
                             val newList = ArrayList<Guruhlar>()
                             for (guruhlar in showGuruhlar) {
                                 if (guruhlar.mentorId!!.toString() == kurslarById.id.toString()){
                                     newList.add(guruhlar)
                                 }
                             }

                             val rvAdapterOchilyotgan = RvAdapterOchilyotgan(
                                 newList,
                                 object : RvAdapterOchilyotgan.RvClick {
                                     override fun editMentorlar(guruhlar: Guruhlar) {

                                     }

                                 })
                             root.rv_ochilyatgan_gurupplar.adapter = rvAdapterOchilyotgan

                         }
                     }
                 }

                 override fun onPageScrollStateChanged(state: Int) {

                 }

             })
     */

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MalumotlarGuruhlar().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
