package mrsh.com.a63codialapp.yolanishlar

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_show_gruppalar.view.*
import mrsh.com.a63codialapp.R
import mrsh.com.a63codialapp.databinding.ItemCustomDialogMentorlarBinding
import mrsh.com.a63codialapp.databinding.ItemTalabaOchirishBinding
import mrsh.com.a63codialapp.db.MyDbHelper
import mrsh.com.a63codialapp.db.RvAdapterTalabalar
import mrsh.com.a63codialapp.models.Guruhlar
import mrsh.com.a63codialapp.models.Kurslar
import mrsh.com.a63codialapp.models.Talaba

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShowGruppalar.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowGruppalar : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var root:View
    lateinit var myDbHelper: MyDbHelper
    private lateinit var rvAdapterTalabalar:RvAdapterTalabalar

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
        root =  inflater.inflate(R.layout.fragment_show_gruppalar, container, false)

        onResume()

        return root
    }

    override fun onResume() {
        super.onResume()
        myDbHelper = MyDbHelper(root.context)
        val kurslar = arguments?.getInt("kursId")
        val guruhlar = arguments?.getInt("guruhlar1")

        println(guruhlar)

        val kurslarById = myDbHelper.getKurslarById(kurslar!!)
        val guruhlarById = myDbHelper.getGuruhlarById(guruhlar!!)



        root.guruh_malumotlari_show.text = guruhlarById.guruhlarNomi
        root.guruh_nomi_show.text = "Guruh Nomi: ${guruhlarById.guruhlarNomi}"

        val int = arguments?.getInt("kalitSoz")
if (int == 1)
    root.guruh_vaqti_show.text = guruhlarById.vaqt
else
        root.guruh_vaqti_show.text = guruhlarById.kun

        root.qoshishTalabalar_show.setOnClickListener {
                       root.findNavController().navigate(R.id.talabaQoshish, bundleOf("k" to kurslarById.id,"g" to guruhlarById.id))
        }

        root.orqagaqa_show.setOnClickListener {
            findNavController().popBackStack()
        }

        root.darsBoshlash.setOnClickListener {
            myDbHelper = MyDbHelper(root.context)
            guruhlarById.ochilyotganGuruhlar = "0"
            myDbHelper.editGuruhlar(guruhlarById)
            findNavController().popBackStack()
        }

        myDbHelper = MyDbHelper(root.context)
        val list = ArrayList<Talaba>()
        val showTalaba = myDbHelper.showTalaba() as ArrayList

        for (talaba in showTalaba) {
            if (kurslarById.id == talaba.kurslarID!!.id && guruhlarById.id == talaba.guruhlarID!!.toInt())
                list.add(talaba)


        }

        root.oquvchilar_soni_show.text = "O'quvchilar soni: ${list.size} ta"

        rvAdapterTalabalar = RvAdapterTalabalar(list,object: RvAdapterTalabalar.RvClick{
            override fun editTalaba(talaba: Talaba) {
                root.findNavController().navigate(R.id.talabaOzgartirish, bundleOf("talabaId" to talaba.id))
            }

            override fun deleteTalaba(talaba: Talaba) {
                val dialog = AlertDialog.Builder(root.context).create()
                val itemTalabaOchirishBinding =
                    ItemTalabaOchirishBinding.inflate(layoutInflater)
                dialog.setView(itemTalabaOchirishBinding.root)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                dialog.show()

                itemTalabaOchirishBinding.ha.setOnClickListener {
                    myDbHelper = MyDbHelper(root.context)
                    myDbHelper.deleteTalaba(talaba)

                    update()
                    dialog.dismiss()
                }
                itemTalabaOchirishBinding.yoq.setOnClickListener {
                    dialog.dismiss()
                }
            }

            override fun showTalaba(talaba: Talaba) {

            }

        })

        root.rv_talabalar.adapter = rvAdapterTalabalar
    }

    fun update(){
        myDbHelper = MyDbHelper(root.context)
        val kurslar = arguments?.getInt("kursId")
        val guruhlar = arguments?.getInt("guruhlar1")
        val kurslarById = myDbHelper.getKurslarById(kurslar!!.toInt())
        val guruhlarById = myDbHelper.getGuruhlarById(guruhlar!!.toInt())
        myDbHelper = MyDbHelper(root.context)
        val list = ArrayList<Talaba>()
        val showTalaba = myDbHelper.showTalaba() as ArrayList

        for (talaba in showTalaba) {
            if (kurslarById.id == talaba.kurslarID!!.id && guruhlarById.id == talaba.guruhlarID!!.toInt())
                list.add(talaba)
        }

        root.oquvchilar_soni_show.text = "O'quvchilar soni: ${list.size} ta"

        rvAdapterTalabalar = RvAdapterTalabalar(list,object: RvAdapterTalabalar.RvClick{
            override fun editTalaba(talaba: Talaba) {
                root.findNavController().navigate(R.id.talabaOzgartirish, bundleOf("talabaId" to talaba.id))
            }

            override fun deleteTalaba(talaba: Talaba) {
                val dialog = AlertDialog.Builder(root.context).create()
                val itemTalabaOchirishBinding =
                    ItemTalabaOchirishBinding.inflate(layoutInflater)
                dialog.setView(itemTalabaOchirishBinding.root)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                dialog.show()

                itemTalabaOchirishBinding.ha.setOnClickListener {
                    myDbHelper = MyDbHelper(root.context)
                    myDbHelper.deleteTalaba(talaba)

                    update()
                    dialog.dismiss()
                }
                itemTalabaOchirishBinding.yoq.setOnClickListener {
                    dialog.dismiss()
                }
            }

            override fun showTalaba(talaba: Talaba) {

            }

        })

        root.rv_talabalar.adapter = rvAdapterTalabalar
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShowGruppalar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShowGruppalar().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}