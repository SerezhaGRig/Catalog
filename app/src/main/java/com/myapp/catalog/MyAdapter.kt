package com.myapp.catalog

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class MyAdapter(private val myDataset: Products,val context: Context,val categ:String) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val holderLey = LayoutInflater.from(parent.context).inflate(R.layout.product_view,parent,false)

        val displayMetrics = context.resources.displayMetrics


        val dpWidth = displayMetrics.widthPixels /// displayMetrics.density


        val imageView = holderLey.findViewById<ImageView>(R.id.imageView)
        val params = imageView.layoutParams as LinearLayout.LayoutParams
        params.height = (dpWidth*2/3).toInt()
        imageView.layoutParams = params
        return MyViewHolder(holderLey)

    }

    override fun getItemCount()= myDataset.prList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.name.text = myDataset.prList[position].name
        holder.cost.text = myDataset.prList[position].cost
        Product.setupImage(myDataset.prList[position].image1,holder.image,400,225,context)
        //Product.bitmapRes(it) })
        holder.button.setOnClickListener{

            val intent = Intent(context,ScrollingActivity::class.java)
            intent.putExtra("index",position)
            intent.putExtra("category",categ)
            context.startActivity(intent)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.prName)!!
        val cost = itemView.findViewById<TextView>(R.id.prCost)!!
        val image = itemView.findViewById<ImageView>(R.id.imageView)!!
        val button = itemView.findViewById<LinearLayout>(R.id.lin_lay2)
    }
}