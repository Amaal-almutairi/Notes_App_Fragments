package com.example.notes_app_fragments

import DATA.Notes
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes_app_fragments.databinding.ItemRowBinding

class MyAdap(val mainfragment: FragmentNote, var Notes:List<Notes>): RecyclerView.Adapter<MyAdap.ItemViewHolder>()
    {

    class ItemViewHolder(val binding:ItemRowBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            (ItemRowBinding.inflate(
               LayoutInflater.from(parent.context),parent,false
            ))
        )



    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val NOTE = Notes[position]
        holder.binding.apply {
            tvNote.text = NOTE.Note
            iconEdit.setOnClickListener {
                with(mainfragment.share.edit()){
                    putString("NoteID",NOTE.id)
                    apply()
                }
               mainfragment.openwendow()
            }


            iconDelete.setOnClickListener {
           mainfragment.deletedialog(NOTE)


            }

        }
    }

    override fun getItemCount() = Notes.size

    fun updtRC(notes: List<Notes>){
            // her notes will display in recyclerview
           this.Notes = notes
          notifyDataSetChanged()

    }


}



