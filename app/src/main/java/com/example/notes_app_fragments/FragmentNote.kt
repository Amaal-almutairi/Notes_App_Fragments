package com.example.notes_app_fragments

import DATA.Notes
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FragmentNote : Fragment() {

    lateinit var addNote: EditText
    lateinit var btnsub: Button
    lateinit var Note: List<Notes>
    lateinit var rvadap: RecyclerView
    lateinit var model: MyViewModel
    lateinit var adap: MyAdap
    lateinit var share: SharedPreferences

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_note, container, false)
        addNote = view.findViewById(R.id.ednoteF2)
        btnsub = view.findViewById(R.id.btnsubmitF2)
        rvadap = view.findViewById(R.id.rvNote)
        Note = arrayListOf()
        adap= MyAdap(this,Note)
        rvadap.adapter=adap
        rvadap.layoutManager=LinearLayoutManager(requireContext())
        share = requireActivity().getSharedPreferences("AllNotes",Context.MODE_PRIVATE)
        model = ViewModelProvider(this).get(MyViewModel::class.java)
        model.getallnotes().observe(viewLifecycleOwner,{  Note ->
            adap.updtRC(Note)
        })
        // we create object class so we can access to MyViewModel class


        btnsub.setOnClickListener {
            // her we will add note
            var note=addNote.text.toString()
            if (addNote.text.toString().isNotEmpty()){

                // we call Insertnote function to add the note to database
                model.Insertnote(Notes("",note))
                addNote.text.clear()
                addNote.clearFocus()
            }else{
                Toast.makeText(requireContext(), "Please Enter A value", Toast.LENGTH_SHORT).show()
            } }
        // her we call this function her " MyViewModel class" so we can display the data inside recyclerview
        model.getNote()
        return view

    }

    fun openwendow(){

        Navigation.findNavController(requireView()).navigate(R.id.action_fragmentNote_to_update)

   }

    fun deletedialog(oldnote:Notes){
        val dialog= AlertDialog.Builder(requireContext())
        val id = oldnote.id
        dialog.setMessage( "Are you sure you want to delete?")

        dialog.setCancelable(false).setPositiveButton("Yes", DialogInterface.OnClickListener {
                _, i -> model.deletenote(id)})

        dialog.setNegativeButton("No", DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val Alert = dialog.create()
        Alert.setTitle("Delete Note")
        Alert.show()



    }

}