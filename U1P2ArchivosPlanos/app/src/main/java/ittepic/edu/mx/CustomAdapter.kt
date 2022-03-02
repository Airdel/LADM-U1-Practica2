package ittepic.edu.mx

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter(private val lista: ArrayList<Items>,private val listener:OnItemClickListener): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int ):ViewHolder{
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout, viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int){
        val itemactual = lista[position]

        viewHolder.itemTitle.text = itemactual.text1
        // viewHolder.itemDetail.text = details[position]
        viewHolder.itemDetail.text = itemactual.text2
        viewHolder.itemText.text = itemactual.text3
        viewHolder.itemImage.setImageResource(itemactual.imageResource)

    }

    override fun getItemCount() = lista.size


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener{

        var itemText : TextView
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init{
            itemText = itemView.findViewById(R.id.text_view1)
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detal)
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            TODO("codigo con errores, fun placeholder")
        }

/*        override fun onClick(p0: View?) {
            val position = bindingAdapterPosition  //ERror en bindingadapterposition no references
            if(position!=RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }*/
    }
    interface OnItemClickListener {
        fun onItemClick(position:Int)
    }


}
