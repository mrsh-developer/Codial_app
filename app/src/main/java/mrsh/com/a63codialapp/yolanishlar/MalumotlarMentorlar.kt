package mrsh.com.a63codialapp.yolanishlar

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_malumotlar_guruhlar.view.*
import kotlinx.android.synthetic.main.fragment_malumotlar_mentorlar.view.*
import kotlinx.android.synthetic.main.fragment_malumotlar_mentorlar.view.k_m_n
import kotlinx.android.synthetic.main.fragment_malumotlar_mentorlar.view.qoshishMentorlar
import kotlinx.android.synthetic.main.fragment_mentor_malumot_olish.view.*
import kotlinx.android.synthetic.main.item_custom_dialog_mentorlar.*
import mrsh.com.a63codialapp.R
import mrsh.com.a63codialapp.databinding.ItemCustomDialogMentorlarBinding
import mrsh.com.a63codialapp.databinding.ItemTalabaOchirishBinding
import mrsh.com.a63codialapp.db.MyDbHelper
import mrsh.com.a63codialapp.db.RvAdapterMentorlar
import mrsh.com.a63codialapp.models.Kurslar
import mrsh.com.a63codialapp.models.Mentorlar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MalumotlarMentorlar.newInstance] factory method to
 * create an instance of this fragment.
 */
class MalumotlarMentorlar : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var key: Int? = null
    lateinit var myDbHelper: MyDbHelper
    lateinit var root: View
    lateinit var list: ArrayList<Mentorlar>
    lateinit var rvAdapterMentorlar: RvAdapterMentorlar

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
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_malumotlar_mentorlar, container, false)

        onResume()


        return root
    }

    override fun onResume() {
        super.onResume()

        key = arguments?.getInt("keyIndexMentorlar")!!.toInt()
        myDbHelper = MyDbHelper(root.context)
        val kurslarById = myDbHelper.getKurslarById(key!!)

        root.k_m_n.text = kurslarById.kursNomi



        root.qoshishMentorlar.setOnClickListener {
            root.findNavController().navigate(
                R.id.mentorMalumotOlish,
                bundleOf("keyKursMalumotMentorlar" to kurslarById.id)
            )
        }


        val showGuruhlar = myDbHelper.showMentorlar() as ArrayList
        val newList = ArrayList<Mentorlar>()
        for (mentorlar in showGuruhlar) {
            if (kurslarById.id == mentorlar.kurslar?.id)
                newList.add(mentorlar)
        }

        rvAdapterMentorlar = RvAdapterMentorlar(newList, object : RvAdapterMentorlar.RvClick {
            override fun editMentorlar(mentorlar: Mentorlar) {
                val dialog = AlertDialog.Builder(root.context).create()
                val itemCustomDialogMentorlarBinding =
                    ItemCustomDialogMentorlarBinding.inflate(layoutInflater)
//                val dialogView =
//                    layoutInflater.inflate(R.layout.item_custom_dialog_mentorlar, null, false)
                dialog.setView(itemCustomDialogMentorlarBinding.root)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


                dialog.show()

                val ismi = itemCustomDialogMentorlarBinding.mentorIsmi.setText(mentorlar.ism)
                val familya =
                    itemCustomDialogMentorlarBinding.mentorFamilyasi.setText(mentorlar.familya)
                val otasiniIsmi =
                    itemCustomDialogMentorlarBinding.mentorOtasiniIsmi.setText(mentorlar.otasiniIsmi)
                val telepone =
                    itemCustomDialogMentorlarBinding.mentorTelRaqami.setText(mentorlar.telephone)

                itemCustomDialogMentorlarBinding.yopishOzgarganMentorlar.setOnClickListener {
                    dialog.dismiss()
                }

                itemCustomDialogMentorlarBinding.ozgartirishMentorlarniMalumotlarini.setOnClickListener {
                    mentorlar.ism = itemCustomDialogMentorlarBinding.mentorIsmi.text.toString()
                    mentorlar.familya =
                        itemCustomDialogMentorlarBinding.mentorFamilyasi.text.toString()
                    mentorlar.otasiniIsmi =
                        itemCustomDialogMentorlarBinding.mentorOtasiniIsmi.text.toString()
                    mentorlar.telephone =
                        itemCustomDialogMentorlarBinding.mentorTelRaqami.text.toString()
                    Toast.makeText(root.context, "Malumot O'zgartirildi", Toast.LENGTH_SHORT).show()

                    myDbHelper = MyDbHelper(root.context)
                    myDbHelper.editMentorlar(mentorlar)
                    onResume()
                    dialog.dismiss()
                }


            }

            override fun deleteMentorlar(mentorlar: Mentorlar) {
                val dialog = AlertDialog.Builder(root.context).create()
                val itemTalabaOchirishBinding =
                    ItemTalabaOchirishBinding.inflate(layoutInflater)
                dialog.setView(itemTalabaOchirishBinding.root)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                itemTalabaOchirishBinding.o.text = "Mentorni O'chirsangiz unga bog'liq barcha narsa o'chadi"
                dialog.show()

                itemTalabaOchirishBinding.ha.setOnClickListener {
                    myDbHelper = MyDbHelper(root.context)

                    val showTalaba = myDbHelper.showTalaba()
                    val showGuruhlar1 = myDbHelper.showGuruhlar()


                        for (guruhlar in showGuruhlar1) {
                            for (talaba in showTalaba) {
                                if (talaba.kurslarID!!.id.toString() == kurslarById.id.toString() &&
                                        talaba.guruhlarID == guruhlar.id.toString() &&
                                        talaba.mentorlarID == mentorlar.id.toString()){
                                    myDbHelper.deleteTalaba(talaba)
                                }
                            }
                        }
                        for (guruhlar in showGuruhlar1) {

                                if (guruhlar.mentorId == mentorlar.id.toString()
                                    && guruhlar.kurslar!!.id.toString() == kurslarById.id.toString()){
                                    myDbHelper.deleteGuruhlar(guruhlar)
                                }

                        }

                    myDbHelper.deleteMentorlar(mentorlar)
                    update()
                    dialog.dismiss()
                }
                itemTalabaOchirishBinding.yoq.setOnClickListener {
                    dialog.dismiss()
                }
            }


        })

        root.rv_mentorlar_ustozlar.adapter = rvAdapterMentorlar

        root.orqaga_mentorlar_kurslardan.setOnClickListener {
            findNavController().popBackStack()
        }


        // root.k_i_t_m.text = kurslarById.kursHaqidaMalumot

    }

    fun update(){
        key = arguments?.getInt("keyIndexMentorlar")!!.toInt()
        myDbHelper = MyDbHelper(root.context)
        val kurslarById = myDbHelper.getKurslarById(key!!)

        root.k_m_n.text = kurslarById.kursNomi



        root.qoshishMentorlar.setOnClickListener {
            root.findNavController().navigate(
                R.id.mentorMalumotOlish,
                bundleOf("keyKursMalumotMentorlar" to kurslarById.id)
            )
        }


        val showGuruhlar = myDbHelper.showMentorlar() as ArrayList
        val newList = ArrayList<Mentorlar>()
        for (mentorlar in showGuruhlar) {
            if (kurslarById.id == mentorlar.kurslar?.id)
                newList.add(mentorlar)
        }

        rvAdapterMentorlar = RvAdapterMentorlar(newList, object : RvAdapterMentorlar.RvClick {
            override fun editMentorlar(mentorlar: Mentorlar) {
                val dialog = AlertDialog.Builder(root.context).create()
                val itemCustomDialogMentorlarBinding =
                    ItemCustomDialogMentorlarBinding.inflate(layoutInflater)
//                val dialogView =
//                    layoutInflater.inflate(R.layout.item_custom_dialog_mentorlar, null, false)
                dialog.setView(itemCustomDialogMentorlarBinding.root)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


                dialog.show()

                val ismi = itemCustomDialogMentorlarBinding.mentorIsmi.setText(mentorlar.ism)
                val familya =
                    itemCustomDialogMentorlarBinding.mentorFamilyasi.setText(mentorlar.familya)
                val otasiniIsmi =
                    itemCustomDialogMentorlarBinding.mentorOtasiniIsmi.setText(mentorlar.otasiniIsmi)
                val telepone =
                    itemCustomDialogMentorlarBinding.mentorTelRaqami.setText(mentorlar.telephone)

                itemCustomDialogMentorlarBinding.yopishOzgarganMentorlar.setOnClickListener {
                    dialog.dismiss()
                }

                itemCustomDialogMentorlarBinding.ozgartirishMentorlarniMalumotlarini.setOnClickListener {
                    mentorlar.ism = itemCustomDialogMentorlarBinding.mentorIsmi.text.toString()
                    mentorlar.familya =
                        itemCustomDialogMentorlarBinding.mentorFamilyasi.text.toString()
                    mentorlar.otasiniIsmi =
                        itemCustomDialogMentorlarBinding.mentorOtasiniIsmi.text.toString()
                    mentorlar.telephone =
                        itemCustomDialogMentorlarBinding.mentorTelRaqami.text.toString()
                    Toast.makeText(root.context, "Malumot O'zgartirildi", Toast.LENGTH_SHORT).show()

                    myDbHelper = MyDbHelper(root.context)
                    myDbHelper.editMentorlar(mentorlar)
                    onResume()
                    dialog.dismiss()
                }


            }

            override fun deleteMentorlar(mentorlar: Mentorlar) {
                val dialog = AlertDialog.Builder(root.context).create()
                val itemTalabaOchirishBinding =
                    ItemTalabaOchirishBinding.inflate(layoutInflater)
                dialog.setView(itemTalabaOchirishBinding.root)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                itemTalabaOchirishBinding.o.text = "Mentorni O'chirsangiz unga bog'liq barcha narsa o'chadi"
                dialog.show()

                itemTalabaOchirishBinding.ha.setOnClickListener {
                    myDbHelper = MyDbHelper(root.context)

                    val showTalaba = myDbHelper.showTalaba()
                    val showGuruhlar1 = myDbHelper.showGuruhlar()


                    for (guruhlar in showGuruhlar1) {
                        for (talaba in showTalaba) {
                            if (talaba.kurslarID!!.id.toString() == kurslarById.id.toString() &&
                                talaba.guruhlarID == guruhlar.id.toString() &&
                                talaba.mentorlarID == mentorlar.id.toString()){
                                myDbHelper.deleteTalaba(talaba)
                            }
                        }
                    }
                    for (guruhlar in showGuruhlar1) {

                        if (guruhlar.mentorId == mentorlar.id.toString() && guruhlar.kurslar!!.id.toString() == kurslarById.id.toString()){
                            myDbHelper.deleteGuruhlar(guruhlar)
                        }

                    }

                    myDbHelper.deleteMentorlar(mentorlar)

                    dialog.dismiss()
                }
                itemTalabaOchirishBinding.yoq.setOnClickListener {
                    dialog.dismiss()
                }
            }


        })

        root.rv_mentorlar_ustozlar.adapter = rvAdapterMentorlar

        root.orqaga_mentorlar_kurslardan.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MalumotlarMentorlar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MalumotlarMentorlar().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}