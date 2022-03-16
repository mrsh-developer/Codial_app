package mrsh.com.a63codialapp.yolanishlar

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_show_gruppalar.view.*
import kotlinx.android.synthetic.main.fragment_talaba_qoshish.view.*
import mrsh.com.a63codialapp.R
import mrsh.com.a63codialapp.db.MyDbHelper
import mrsh.com.a63codialapp.db.RvAdapterTalabalar
import mrsh.com.a63codialapp.models.Talaba
import java.time.LocalDateTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TalabaQoshish.newInstance] factory method to
 * create an instance of this fragment.
 */
class TalabaQoshish : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var root: View
    lateinit var rvAdapterTalabalar: RvAdapterTalabalar
    lateinit var myDbHelper: MyDbHelper
    lateinit var list: ArrayList<Talaba>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_talaba_qoshish, container, false)



            root.saqlash_talaba.setOnClickListener {
                val toString = root.talaba_familyasi.text.toString()
                val toString1 = root.talaba_ismi.text.toString()
                val toString2 = root.talaba_otasini_ismi.text.toString()
                val toString3 = root.talaba_tel_raqami.text.toString()
                val int = arguments?.getInt("k")
                val int1 = arguments?.getInt("g")
                myDbHelper = MyDbHelper(root.context)
                val kurslarById = myDbHelper.getKurslarById(int!!.toInt())
                val guruhlarById = myDbHelper.getGuruhlarById(int1!!.toInt())
                val mentorlarById = myDbHelper.getMentorlarById(guruhlarById.mentorId!!.toInt())

                println(guruhlarById.id)
                val now = LocalDateTime.now()
                myDbHelper.addTalaba(
                    Talaba(
                        toString,
                        toString1,
                        toString2,
                        toString3,
                        now.toString(),

                        mentorlarById.id.toString(),
                        guruhlarById.id.toString(),
                        kurslarById
                    )
                )


                findNavController().popBackStack()


            }
            root.orqaga_talaba_qoshish.setOnClickListener {
                findNavController().popBackStack()
            }




        return root
    }

    override fun onResume() {
        super.onResume()


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TalabaQoshish.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TalabaQoshish().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}