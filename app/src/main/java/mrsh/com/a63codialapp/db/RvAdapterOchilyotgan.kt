package mrsh.com.a63codialapp.db

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mrsh.com.a63codialapp.databinding.ItemMentorlarBinding
import mrsh.com.a63codialapp.databinding.RvItemOchilganGuruhlarBinding
import mrsh.com.a63codialapp.models.Guruhlar
import mrsh.com.a63codialapp.models.Mentorlar
import mrsh.com.a63codialapp.models.Talaba


class RvAdapterOchilyotgan(val list:List<Guruhlar>, var rvClick: RvClick): RecyclerView.Adapter<RvAdapterOchilyotgan.VH>(){
    inner class VH(var itemOchilganGuruhlarBinding: RvItemOchilganGuruhlarBinding): RecyclerView.ViewHolder(itemOchilganGuruhlarBinding.root){
        fun onBind(guruhlar: Guruhlar){
            itemOchilganGuruhlarBinding.guruhNomiText.text = guruhlar.guruhlarNomi


            itemOchilganGuruhlarBinding.ozgartirishGuruhlarni.setOnClickListener {
                rvClick.editGurupalar(guruhlar)
            }
            itemOchilganGuruhlarBinding.korishGuruhlarni.setOnClickListener {
                rvClick.showGuruhlar(guruhlar)
            }
            itemOchilganGuruhlarBinding.ochirishGuruhni.setOnClickListener {
                rvClick.deleteGuruhlar(guruhlar)
            }

            rvClick.oquvchilarSoni(itemOchilganGuruhlarBinding,guruhlar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RvItemOchilganGuruhlarBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface RvClick{
        fun editGurupalar(guruhlar: Guruhlar)
        fun showGuruhlar(guruhlar: Guruhlar)
        fun deleteGuruhlar(guruhlar: Guruhlar)
        fun oquvchilarSoni(itemOchilganGuruhlarBinding: RvItemOchilganGuruhlarBinding,guruhlar: Guruhlar)
    }
}