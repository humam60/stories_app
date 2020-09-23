package com.humam.my_stories.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val  db = FirebaseFirestore.getInstance()
    var adapter: FirestoreRecyclerAdapter<*, *>? = null
    lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
        getFriendList()
    }
    private fun getFriendList() {
        val query: Query = db.collection("story")
        val response: FirestoreRecyclerOptions<Model> =
            FirestoreRecyclerOptions.Builder<Model>()
                .setQuery(query, Model::class.java)
                .build()

        adapter = object : FirestoreRecyclerAdapter<Model, FriendsHolder>(response) {
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
                    .inflate(R.layout.card, group, false)
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


    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}









