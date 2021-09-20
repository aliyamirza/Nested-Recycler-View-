package com.smartheard.recyclerviewusingnested

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout.HORIZONTAL
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_list.view.*
import kotlin.coroutines.coroutineContext

class Adapter(val clickListener: onItemClickListener):RecyclerView.Adapter<Adapter.ViewHolder>() {
    var locListData = mutableListOf<LocationData>()

    inner class ViewHolder(itemView: View,val clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val imageview = itemView.imageview
        val textViewName = itemView.textName
        val textViewAddress = itemView.textAddress
        val tvChildView = itemView.tvChild
        val childRecycler = itemView.childRecyclerView
        fun bind(data: LocationData) {
            textViewName.text = data.locationName
            var address = ""

            data.address?.let {
                address += it + ","
            }
            data.city?.let {
                address += it + ","
            }
            data.state?.let {
                address += it + ","
            }
            data.zip?.let {
                address += it + ","
            }
            data.country?.let {
                address += it
            }
            textViewAddress.text = address

            Glide.with(imageview).load(data.url).into(imageview)

            if (data.childLocations != null && data.childLocations.size > 0) {
                tvChildView.visibility = VISIBLE
                childRecycler.visibility = VISIBLE
                childRecycler.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    val decoration =
                        DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
                    addItemDecoration(decoration)
                    val recyclerAdapter = Adapter(clickListener)
                    recyclerAdapter.locListData = data.childLocations.toMutableList()
                    adapter = recyclerAdapter
                }
                tvChildView.text = "Child Location" + data.childLocations.size
            } else {
                tvChildView.visibility = GONE
                childRecycler.visibility = GONE
            }
        }
    }
        interface onItemClickListener {
            fun onClickListener(data: LocationData)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list,parent,false)
        return ViewHolder(view,clickListener)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(locListData[position])
        holder.itemView.setOnClickListener {
        clickListener.onClickListener(locListData[position])
        }
    }

    override fun getItemCount(): Int {
        return locListData.size
    }
}