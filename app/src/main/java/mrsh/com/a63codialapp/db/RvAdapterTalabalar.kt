package mrsh.com.a63codialapp.db

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mrsh.com.a63codialapp.databinding.ItemMentorlarBinding
import mrsh.com.a63codialapp.databinding.ItemTalabalarBinding
import mrsh.com.a63codialapp.models.Mentorlar
import mrsh.com.a63codialapp.models.Talaba


class RvAdapterTalabalar (val list:ArrayList<Talaba>, var rvClick: RvClick): RecyclerView.Adapter<RvAdapterTalabalar.VH>(){
    inner class VH(var talabalarBinding: ItemTalabalarBinding): RecyclerView.ViewHolder(talabalarBinding.root){
        fun onBind(talaba: Talaba){
           talabalarBinding.talabNomi.text = talaba.name + talaba.familyasi

            val data = talaba.data
            val s = data!!.substring(0,10)

            talabalarBinding.kirganPayti.text = s
            talabalarBinding.showTalaba.setOnClickListener {
                rvClick.showTalaba(talaba)
            }
            talabalarBinding.ozgartirishTalaba.setOnClickListener {
                rvClick.editTalaba(talaba)
            }
            talabalarBinding.ochirishTalaba.setOnClickListener {
                rvClick.deleteTalaba(talaba)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemTalabalarBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface RvClick{

      fun editTalaba(talaba: Talaba)
      fun deleteTalaba(talaba: Talaba)
      fun showTalaba(talaba: Talaba)
    }
}