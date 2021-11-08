package com.example.notes_app_fragments

import DATA.Notes
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(application: Application): AndroidViewModel(application) {

    var AllNotes: MutableLiveData<List<Notes>>
    val DBFirebase = Firebase.firestore
    // her we init the array

    init {
        AllNotes = MutableLiveData()
    }

    fun getallnotes(): LiveData<List<Notes>> {
        return AllNotes
    }
    // "AllNotes" is Name of table
    fun getNote() {
        DBFirebase.collection("AllNotes").get()
            // her if we got the list it will show all the data to user
            .addOnSuccessListener { res ->
                val ListOfNotes = arrayListOf<Notes>()
                // her we use map so we have to use (key, value) so we can add the data in map
                for (document in res) {
                    document.data.map { (key, value) -> ListOfNotes.add(Notes(document.id,value.toString())) }

                }
                // her we will post all data
                AllNotes.postValue(ListOfNotes)

            }
            // if we didn't get the data will call this addOnFailureListener
            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)

            }
        getallnotes()
    }

    fun Insertnote(notes: Notes) {
        // we use CoroutineScope because our app will not crash
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.Main) {
            val NewNote = hashMapOf(
                "Note" to notes.Note,
            )
            DBFirebase.collection("AllNotes").add(NewNote)
            getNote()
        }
    }

    fun updatenote(Id:String,notes:String){
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.Main) {

            DBFirebase.collection("AllNotes").get().addOnSuccessListener { res ->
                for (document in res) {
                    // check if document.id == Id it will change the data
                    if (document.id == Id) {
                        DBFirebase.collection("AllNotes").document(Id).update(
                            "notes", notes)

                    }
                }
                getNote()
            }

                .addOnFailureListener { exception ->
                    Log.w("MainActivity", "Error getting documents.", exception) }

        }


    }


    fun deletenote(id:String){
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.Main) {
            DBFirebase.collection("AllNotes").get().addOnSuccessListener { res ->
                for (document in res){
                    if (document.id==id){
                        DBFirebase.collection("AllNotes").document(id).delete()
                    }
                }
                getNote()
            }
                .addOnFailureListener { exception ->
                    Log.w("MainActivity", "Error getting documents.", exception) }
        }
    }


}

