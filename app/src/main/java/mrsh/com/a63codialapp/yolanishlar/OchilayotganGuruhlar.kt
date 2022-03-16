package mrsh.com.a63codialapp.yolanishlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_ochilayotgan_guruhlar.view.*
import mrsh.com.a63codialapp.R
import mrsh.com.a63codialapp.databinding.RvItemOchilganGuruhlarBinding
import mrsh.com.a63codialapp.db.MyDbHelper
import mrsh.com.a63codialapp.db.RvAdapterOchilyotgan

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OchilayotganGuruhlar.newInstance] factory method to
 * create an instance of this fragment.
 */
class OchilayotganGuruhlar() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var root: View
    lateinit var myDbHelper: MyDbHelper
    lateinit var list: ArrayList<Guruhlar>
    var key: Int? = null

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
        root = inflater.inflate(R.layout.fragment_ochilayotgan_guruhlar, container, false)



     //   onResume()

        return root
    }

  /*  override fun onResume() {
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
                if (key.toString() == kurslarById.id.toString() && guruhlar.ochilyotganGuruhlar == "1") {
                    newList.add(guruhlar)
                }
            }

            val rvAdapterOchilyotgan = RvAdapterOchilyotgan(
                newList,
                object : RvAdapterOchilyotgan.RvClick {
                    override fun editGurupalar(guruhlar: mrsh.com.a63codialapp.models.Guruhlar) {

                    }

                    override fun showGuruhlar(guruhlar: mrsh.com.a63codialapp.models.Guruhlar) {

                    }

                    override fun oquvchilarSoni(
                        itemOchilganGuruhlarBinding: RvItemOchilganGuruhlarBinding,
                        guruhlar: mrsh.com.a63codialapp.models.Guruhlar
                    ) {

                    }


                })
            root.rv_ochilyatgan_gurupplar.adapter = rvAdapterOchilyotgan

        }
    }
*/

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OchilayotganGuruhlar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OchilayotganGuruhlar().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}