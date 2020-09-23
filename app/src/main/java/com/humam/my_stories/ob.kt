package com.humam.my_stories

import android.content.Context
import android.content.Intent
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd

import com.google.firebase.firestore.FirebaseFirestore

class mAdapter(
    private val notesList: MutableList<Model>,
    private val context: Context,
    private val firestoreDB: FirebaseFirestore) : RecyclerView.Adapter<mAdapter.ViewHolder>() {

    private lateinit var mInterstitialAd: InterstitialAd


    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var title: TextView = view.findViewById(R.id.textView)
       internal var card_view:CardView=view.findViewById(R.id.card_view)

        // internal var content: TextView


        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hmm, parent, false)
        mInterstitialAd = InterstitialAd(parent.context)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]

        holder.title.text = note.name

        holder.card_view.setOnClickListener {
            openme(note)}
    }



    private fun openme(note: Model) {
            var content:String
        //var content=open_story(note.id)


        val docRef = firestoreDB.collection("story").document(note.id)
      docRef.get()
          .addOnSuccessListener { document ->
              if (document != null) {
               content= document.get("content").toString()
                  val intent = Intent(context, Story_opend::class.java)
                  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                  intent.putExtra("UpdateNoteId", note.id)
                  intent.putExtra("UpdateNoteTitle", note.name)
                  intent.putExtra("UpdateNoteContent", content)
                  //    Toast.makeText(context,content,Toast.LENGTH_SHORT).show()
                  if (mInterstitialAd.isLoaded) {
                      mInterstitialAd.show()

                      mInterstitialAd.adListener = object : AdListener() {
                          override fun onAdClosed() {
                              super.onAdClosed()
                              mInterstitialAd.loadAd(AdRequest.Builder().build())
                              context.startActivity(intent)
                          }
                      }
                  }else {
                      context.startActivity(intent)
                          Log.d("TAG", "The interstitial wasn't loaded yet.")
                      }

                  // Toast.makeText(context,content,Toast.LENGTH_SHORT).show()

              } else {
                  Toast.makeText(context,"عذرا هناك مشكله في الاتصال",Toast.LENGTH_SHORT).show()

              }
          }
//            .addOnFailureListener { exception ->
//                //Log.d(TAG, "get failed with ", exception)
//                m="not work"
//            }


    }




}