package com.humam.my_stories.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.humam.my_stories.Model
import com.humam.my_stories.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    val  db = FirebaseFirestore.getInstance()

    lateinit var recyclerview:RecyclerView

    //1
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
         recyclerview=root.findViewById(R.id.rv)

        homeViewModel
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
           // textView.text = it
            //getFriendList()
        })
        getFriendList()
        return root
    }


    private fun getFriendList() {
        val query: Query = db.collection("story")
        val response: FirestoreRecyclerOptions<Model> =
            FirestoreRecyclerOptions.Builder<Model>()
                .setQuery(query, Model::class.java)
                .build()

      var  adapter = object : FirestoreRecyclerAdapter<Model, FriendsHolder>(response) {
            override fun onBindViewHolder(
                holder: FriendsHolder,
                position: Int,
                model: Model
            ) { Log.d("error", "zrbn1")
                // progressBar.setVisibility(View.GONE)
                holder.mTitleView?.setText(model.name)
                Log.d("error", "hello i am not woerk"+model.name)
                /// holder.textTitle.setText(model.getTitle())
                // holder.textCompany.setText(model.getCompany())
//                Glide.with(applicationContext)
//                    .load(model.getImage())
//                    .into(holder.imageView)
                holder.itemView.setOnClickListener { v ->
                    Snackbar.make(

                            recyclerview,
                            model.name
                                .toString() + ", ",
                            Snackbar.LENGTH_LONG
                        )
                        .setAction("Action", null).show()
                }
            }

            override fun onCreateViewHolder(group: ViewGroup, i: Int): FriendsHolder {
                val view: View = LayoutInflater.from(group.context)
                    .inflate(R.layout.hmm, group, false)
                return FriendsHolder(view)
            }

            override fun onError(e: FirebaseFirestoreException) {

                Log.e("error", "hello i am not woerk"+e.message)
            }
        }
        adapter?.notifyDataSetChanged()
        rv?.adapter = adapter
    }

    //holders :)
    class FriendsHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var mTitleView: TextView? = null
        init {
            mTitleView=itemView?.findViewById(R.id.textView)

        }
    }


//    override fun onStart() {
//        super.onStart()
//        adapter!!.startListening()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        adapter!!.stopListening()
//    }
}
