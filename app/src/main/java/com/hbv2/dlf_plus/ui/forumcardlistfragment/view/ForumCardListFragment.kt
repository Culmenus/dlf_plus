package com.hbv2.dlf_plus.ui.forumcardlistfragment.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.data.model.Forum
import com.hbv2.dlf_plus.ui.ForumActivity
import com.hbv2.dlf_plus.ui.forumcardlistfragment.ForumClickListener
import com.hbv2.dlf_plus.ui.forumcardlistfragment.adapter.ForumCardAdapter
import com.hbv2.dlf_plus.ui.forumcardlistfragment.viewmodel.ForumCardListViewModel

private const val TAG = "ForumCardListFragment"

class ForumCardListFragment : Fragment(), ForumClickListener {

    private lateinit var forumRecyclerView: RecyclerView
    private var adapter: ForumCardAdapter? = null

    private val forumCardListViewModel: ForumCardListViewModel by lazy {
        ViewModelProvider(this).get(ForumCardListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total forum cards: ${forumCardListViewModel.forums.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO
        val view = inflater.inflate(R.layout.fragment_forum_card_list, container, false)
        forumRecyclerView = view.findViewById(R.id.forum_card_recyclerview) as RecyclerView
        //application context?? setti context sem hofix
        forumRecyclerView.layoutManager = GridLayoutManager(context, 2)

        updateUI()

        return view
    }

    private fun updateUI() {
        val forums = forumCardListViewModel.forums
        adapter = ForumCardAdapter(forums, this@ForumCardListFragment)
        forumRecyclerView.adapter = adapter
    }

    /* Til að geta gert interactive test
    override fun onClick(forum: Forum) {
        Toast.makeText(context, "${forum.name} pressed! id: ${forum.id}", Toast.LENGTH_SHORT).show()
    }
     */

    override fun onClick(forum: Forum) {
        val intent = Intent(context, ForumActivity::class.java)
        //laga string value
        intent.putExtra("FORUM_ID_EXTRA", forum.id)
        startActivity(intent)
    }


    companion object {
        fun newInstance(): ForumCardListFragment {
            return ForumCardListFragment()
        }
    }
}