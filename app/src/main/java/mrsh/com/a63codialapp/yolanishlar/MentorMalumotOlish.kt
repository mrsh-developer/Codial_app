package mrsh.com.a63codialapp.yolanishlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_mentor_malumot_olish.view.*
import mrsh.com.a63codialapp.R
import mrsh.com.a63codialapp.db.MyDbHelper
import mrsh.com.a63codialapp.models.Mentorlar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MentorMalumotOlish.newInstance] factory method to
 * create an instance of this fragment.
 */
class MentorMalumotOlish : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var root:View
    var key: Int? = null
    lateinit var myDbHelper: MyDbHelper
    lateinit var list:ArrayList<Mentorlar>
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
        root = inflater.inflate(R.layout.fragment_mentor_malumot_olish, container, false)


        root.qoshish_mentorlar.setOnClickListener {
            val ismi = root.mentor_ismi.text.toString()
            val familya = root.mentor_familyasi.text.toString()
            val otasiniIsmi = root.mentor_otasini_ismi.text.toString()
            val telepone = root.mentor_tel_raqami.text.toString()

            key = arguments?.getInt("keyKursMalumotMentorlar")!!.toInt()
            myDbHelper = MyDbHelper(root.context)
            val kurslarById = myDbHelper.getKurslarById(key!!)
            myDbHelper.addMentorlar(Mentorlar(ismi,familya,otasiniIsmi,telepone,kurslarById))

            Toast.makeText(root.context, "Malumot Saqlandi", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()

        }
        root.yopish_mentorlar.setOnClickListener {
            findNavController().popBackStack()
        }

        root.orqaga_mentor_saqlash.setOnClickListener {
            findNavController().popBackStack()

        }


        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MentorMalumotOlish.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MentorMalumotOlish().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}