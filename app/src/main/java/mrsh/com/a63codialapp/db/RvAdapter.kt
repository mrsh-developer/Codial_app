package mrsh.com.a63codialapp.db

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mrsh.com.a63codialapp.databinding.RvItemKurslarBinding
import mrsh.com.a63codialapp.models.Kurslar

class RvAdapter (val list:List<Kurslar>, var rvClick: RvClick):RecyclerView.Adapter<RvAdapter.VH>(){
    inner class VH(var rvItemKurslarBinding: RvItemKurslarBinding):RecyclerView.ViewHolder(rvItemKurslarBinding.root){
        fun onBind(kurslar: Kurslar){
             rvItemKurslarBinding.kursNomi.text = kurslar.kursNomi

            rvItemKurslarBinding.strelkaKirish.setOnClickListener {
                rvClick.onClick(kurslar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
       return VH(RvItemKurslarBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
       holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface RvClick{
        fun onClick(kurslar: Kurslar)
    }
}