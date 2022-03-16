package mrsh.com.a63codialapp.yolanishlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_ochilgan_guruhlar.view.*
import mrsh.com.a63codialapp.R
import mrsh.com.a63codialapp.databinding.RvItemOchilganGuruhlarBinding
import mrsh.com.a63codialapp.db.MyDbHelper
import mrsh.com.a63codialapp.db.RvAdapterOchilgan
import mrsh.com.a63codialapp.models.Guruhlar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OchilganGuruhlar.newInstance] factory method to
 * create an instance of this fragment.
 */
class OchilganGuruhlar : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var root:View
    lateinit var myDbHelper:MyDbHelper
     var key:Int? = null

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
        root = inflater.inflate(R.layout.fragment_ochilgan_guruhlar, container, false)

        onResume()

        return root

    }

    override fun onResume() {
        super.onResume()

        val toString = arguments?.getString("keyValue").toString()

        if (toString == "1"){

            key = arguments?.getInt("keyIndexGuruhMalumot")!!.toInt()
            myDbHelper = MyDbHelper(root.context)
            val kurslarById = myDbHelper.getKurslarById(key!!)
            myDbHelper = MyDbHelper(root.context)
            val showGuruhlar = myDbHelper.showGuruhlar()
            val newList = ArrayList<mrsh.com.a63codialapp.models.Guruhlar>()
            for (guruhlar in showGuruhlar) {
                key = arguments?.getInt("keyIndexGuruhMalumot")!!.toInt()
                myDbHelper = MyDbHelper(root.context)
                if (key.toString() == kurslarById.id.toString() && guruhlar.ochilyotganGuruhlar == "0") {
                    newList.add(guruhlar)
                }
            }

            val rvAdapterOchilyotgan = RvAdapterOchilgan(
                newList,
                object : RvAdapterOchilgan.RvClick {
                    override fun editGurupalar(guruhlar: Guruhlar) {

                    }

                    override fun showGuruhlar(guruhlar: Guruhlar) {

                    }

                    override fun deleteGuruhlar(guruhlar: Guruhlar) {

                    }

                    override fun oquvchilarSoni(
                        itemOchilganGuruhlarBinding: RvItemOchilganGuruhlarBinding,
                        guruhlar: Guruhlar
                    ) {

                    }


                })
            root.rv_ochilgan_gurupplar.adapter = rvAdapterOchilyotgan

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OchilganGuruhlar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OchilganGuruhlar().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}