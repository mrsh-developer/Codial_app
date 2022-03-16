package mrsh.com.a63codialapp.yolanishlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_mentorlar.view.*
import mrsh.com.a63codialapp.R
import mrsh.com.a63codialapp.db.MyDbHelper
import mrsh.com.a63codialapp.db.RvAdapter
import mrsh.com.a63codialapp.models.Kurslar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Mentorlar.newInstance] factory method to
 * create an instance of this fragment.
 */
class Mentorlar : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var root:View

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
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_mentorlar, container, false)

        onResume()

        root.orqaga_mentor_bir.setOnClickListener {
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
                root.findNavController().navigate(R.id.malumotlarMentorlar, bundleOf("keyIndexMentorlar" to kurslar.id))
            }
        })
        root.rv_mentorlar.adapter = rvAdapter

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Mentorlar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Mentorlar().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}