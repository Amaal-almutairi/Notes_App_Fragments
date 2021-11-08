package com.example.notes_app_fragments

import DATA.Notes
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController


class update : Fragment() {
    lateinit var  updatemodel:MyViewModel
    lateinit var  share:SharedPreferences
    lateinit var  ednote:EditText
    lateinit var btnupdate:Button
    lateinit var tvNote:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.fragment_update, container, false)
         share = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key),MODE_PRIVATE)
         updatemodel=ViewModelProvider(this).get(MyViewModel::class.java)
        ednote = view.findViewById(R.id.edUpdateNote)
         btnupdate = view.findViewById(R.id.btnNupdate)
        tvNote=view.findViewById(R.id.tvF1)

        // Inflate the layout for this fragment

        btnupdate.setOnClickListener {
            var ID = share.getString("NoteID","")
            var newNote = ednote.text.toString()
            ednote.text.clear()
            ednote.clearFocus()
            updatemodel.updatenote(ID.toString(),newNote)
            tvNote.text=newNote



         //   findNavController().navigate(R.id.action_update_to_fragmentNote)
        }
        tvNote.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_update_to_fragmentNote)
        }


        return view
    }






}