package mrsh.com.a63codialapp.yolanishlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_talaba_ozgartirish.view.*
import mrsh.com.a63codialapp.R
import mrsh.com.a63codialapp.db.MyDbHelper
import mrsh.com.a63codialapp.models.Talaba

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TalabaOzgartirish.newInstance] factory method to
 * create an instance of this fragment.
 */
class TalabaOzgartirish : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var root: View
    lateinit var myDbHelper: MyDbHelper

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
        root = inflater.inflate(R.layout.fragment_talaba_ozgartirish, container, false)

        myDbHelper = MyDbHelper(root.context)

        val int = arguments?.getInt("talabaId")
        val deleteTalabaByKurslarId = myDbHelper.getDeleteTalabaByKurslarId(int!!)

        val findViewById = root.findViewById<EditText>(R.id.ozgartirish_talaba_familyasi)
        val findViewById1 = root.findViewById<EditText>(R.id.ozgartirish_talaba_ismi)
        val findViewById2 = root.findViewById<EditText>(R.id.ozgartirish_talaba_otasini_ismi)
        val findViewById3 = root.findViewById<EditText>(R.id.ozgartirish_talaba_tel_raqami)

        findViewById.setText(deleteTalabaByKurslarId.familyasi)
        findViewById1.setText(deleteTalabaByKurslarId.name)
        findViewById2.setText(deleteTalabaByKurslarId.otasiniIsmi)
        findViewById3.setText(deleteTalabaByKurslarId.number)

        root.ozgartirish_saqlash_talaba.setOnClickListener {

            deleteTalabaByKurslarId.familyasi = root.ozgartirish_talaba_familyasi.text.toString()
            deleteTalabaByKurslarId.name = root.ozgartirish_talaba_ismi.text.toString()
            deleteTalabaByKurslarId.otasiniIsmi =
                root.ozgartirish_talaba_otasini_ismi.text.toString()
            deleteTalabaByKurslarId.number = root.ozgartirish_talaba_tel_raqami.text.toString()

            myDbHelper = MyDbHelper(root.context)
            myDbHelper.editTalaba(deleteTalabaByKurslarId)
            findNavController().popBackStack()
        }

        root.ozgartirish_orqaga_talaba_qoshish.setOnClickListener {
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
         * @return A new instance of fragment TalabaOzgartirish.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TalabaOzgartirish().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}