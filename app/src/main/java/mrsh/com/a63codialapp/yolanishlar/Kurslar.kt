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
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_kurslar.*
import kotlinx.android.synthetic.main.fragment_kurslar.view.*
import kotlinx.android.synthetic.main.item_custom_dialog_kurslar.*
import mrsh.com.a63codialapp.R
import mrsh.com.a63codialapp.db.MyDbHelper
import mrsh.com.a63codialapp.models.Kurslar
import mrsh.com.a63codialapp.db.RvAdapter
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Kurslar.newInstance] factory method to
 * create an instance of this fragment.
 */
class Kurslar : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var root: View
    lateinit var list: ArrayList<Kurslar>
    lateinit var rvAdapter: RvAdapter
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

        root = inflater.inflate(R.layout.fragment_kurslar, container, false)

        root.qoshishKurslar.setOnClickListener {

            val dialog = AlertDialog.Builder(root.context).create()
            val dialogView =
                layoutInflater.inflate(R.layout.item_custom_dialog_kurslar, null, false)
            dialog.setView(dialogView)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val kHaqida = dialog.findViewById<EditText>(R.id.edt_kh)
            val kNomi = dialog.findViewById<EditText>(R.id.edt_kn)

            dialog.qoshish.setOnClickListener {

                val a1 = kHaqida?.text.toString()
                val a2 = kNomi?.text.toString()


                list = ArrayList()
                list.add(Kurslar(a2, a1))
                myDbHelper = MyDbHelper(root.context)
                myDbHelper.addKurslar(Kurslar(a2, a1))

                Toast.makeText(root.context, "Kurs Qo'shildi", Toast.LENGTH_SHORT).show()

                dialog.dismiss()

                onResume()

            }
            dialog.yopish.setOnClickListener {
                Toast.makeText(root.context, "Kurs Qo'shilmadi", Toast.LENGTH_SHORT).show()

                dialog.dismiss()

            }

            onResume()

        }

        root.orqaga_kurslar.setOnClickListener {
            findNavController().popBackStack()
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        val myDbHelper = MyDbHelper(root.context)
        list = myDbHelper.showKurslar() as ArrayList<Kurslar>
        rvAdapter = RvAdapter(list, object : RvAdapter.RvClick {
            override fun onClick(kurslar: Kurslar) {
                root.findNavController().navigate(R.id.malumotKurslar, bundleOf("keyIndex" to kurslar.id))
            }
        })
        rv.adapter = rvAdapter

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Kurslar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Kurslar().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}