package mrsh.com.a63codialapp.db

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mrsh.com.a63codialapp.databinding.ItemMentorlarBinding
import mrsh.com.a63codialapp.databinding.RvItemKurslarBinding
import mrsh.com.a63codialapp.models.Kurslar
import mrsh.com.a63codialapp.models.Mentorlar

class RvAdapterMentorlar (val list:List<Mentorlar>, var rvClick: RvClick): RecyclerView.Adapter<RvAdapterMentorlar.VH>(){
    inner class VH(var itemMentorlarBinding: ItemMentorlarBinding): RecyclerView.ViewHolder(itemMentorlarBinding.root){
        fun onBind(mentorlar: Mentorlar){
          itemMentorlarBinding.ustozNomi.text = "${mentorlar.ism} ${mentorlar.familya}"

            itemMentorlarBinding.ozgartirishMentorlar.setOnClickListener {
                rvClick.editMentorlar(mentorlar)
            }
            itemMentorlarBinding.ochirishMentorlar.setOnClickListener {
                rvClick.deleteMentorlar(mentorlar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemMentorlarBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface RvClick{

        fun editMentorlar(mentorlar: Mentorlar)
        fun deleteMentorlar(mentorlar: Mentorlar)
    }
}