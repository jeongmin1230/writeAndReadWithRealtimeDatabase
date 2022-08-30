package com.example.writeandreadwithrealtimedatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_layout.view.*

class ListAdapter(val itemList: ArrayList<ListLayout>): RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        holder.word.text = itemList[position].word

        val item = itemList[position]
        val listener = View.OnClickListener { it ->
            Toast.makeText(it.context, "Clicked -> WORD : ${item.word}, MEAN : ${item.mean}", Toast.LENGTH_SHORT).show()
        }
        holder.apply {
            bind(listener, item)
            itemView.tag=item
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView
        val word: TextView = itemView.findViewById(R.id.listName)

        fun bind(listener: View.OnClickListener, item: ListLayout) {
            view.listName.text = item.word
            view.setOnClickListener(listener)
        }
    }
}