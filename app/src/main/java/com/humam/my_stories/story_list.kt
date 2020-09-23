package com.humam.my_stories

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.messaging.RemoteMessage
import kotlinx.android.synthetic.main.activity_story_list.*


class story_list : AppCompatActivity() {
    private val TAG = "MainActivity"

    private var mAdapter: mAdapter? = null

    private var firestoreDB: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null


    lateinit var recyclerview:RecyclerView
    var linearLayoutManager: LinearLayoutManager? = null
    var arr=null

    var db = FirebaseFirestore.getInstance()


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setContentView(R.layout.activity_story_list)

        MobileAds.initialize(this) {}


        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        recyclerview=findViewById(R.id.rv)

        firestoreDB = FirebaseFirestore.getInstance()

        loadNotesList()

        firestoreListener = firestoreDB!!.collection("story").orderBy("date")
            .addSnapshotListener(EventListener { documentSnapshots, e ->
                if (e != null) {
                    Log.e(TAG, "Listen failed!", e)
                    return@EventListener
                }

                val notesList = mutableListOf<Model>()

                if (documentSnapshots != null) {
                    for (doc in documentSnapshots) {
                        Log.d("doc res", doc.get("name").toString() )
                        var na=Model(doc.id,doc.get("name").toString())
                       // na.name = doc.get("name").toString()
                        //note.id = doc.id

                        notesList.add(na)
                        Log.d("lsls", na.name)
                    }
                }

                mAdapter = mAdapter(notesList, applicationContext, firestoreDB!!)
                recyclerview.adapter = mAdapter
            })
       //get list of item from db
//        val docRef = db.collection("story")
//        docRef.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    for (doc in document) {
//                        Log.d("humma", "${doc.id} => ${doc.get("name")}")
//                    }
//                    Log.d("sss", "DocumentSnapshot data: ")
//                } else {
//                    Log.d("deadfull", "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("sss", "get failed with ", exception)
//            }





    }



    private fun loadNotesList() {

        firestoreDB!!.collection("story").orderBy("date")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val notesList = mutableListOf<Model>()

                    for (doc in task.result!!) {

                        var note=Model(doc.id.toString(),doc.get("name").toString())
                        //note.id = doc.id
                        notesList.add(note)
                        Log.e("hass",notesList[0].toString())
                    }

                    mAdapter = mAdapter(notesList, applicationContext, firestoreDB!!)
                    val mLayoutManager = LinearLayoutManager(applicationContext)
                    recyclerview.layoutManager = mLayoutManager
                    recyclerview.itemAnimator = DefaultItemAnimator()
                    recyclerview.adapter = mAdapter
                } else {
                    Log.d(TAG, "Error getting documents: ", task.exception)
                }
            }
    }

}
