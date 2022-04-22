package com.hbv2.dlf_plus.ui.forumcardlistfragment.view

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.data.model.Forum
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.ui.ForumActivity
import com.hbv2.dlf_plus.ui.MainActivity
import com.hbv2.dlf_plus.ui.MyForumsActivity
import com.hbv2.dlf_plus.ui.forumcardlistfragment.ForumClickListener
import com.hbv2.dlf_plus.ui.forumcardlistfragment.adapter.ForumCardAdapter
import com.hbv2.dlf_plus.ui.forumcardlistfragment.viewmodel.ForumCardListViewModel

private const val TAG = "ForumCardListFragment"

class ForumCardListFragment : Fragment(), ForumClickListener {

    private lateinit var forumRecyclerView: RecyclerView
    private var adapter: ForumCardAdapter? = null

    private lateinit var sessionManager: SessionManager
    private var fetchCondition: Int = -1

    private val forumCardListViewModel: ForumCardListViewModel by lazy {
        ViewModelProvider(this).get(ForumCardListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total forum cards: ${forumCardListViewModel.forums}")

        sessionManager = SessionManager(requireContext())
        when(activity) {
            is MainActivity -> fetchCondition = 0
            is MyForumsActivity -> fetchCondition = 1
        }

        forumCardListViewModel
            .getForumsLiveData()
            .observe(this, Observer<List<Forum>> { forums ->
                updateUI()
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forum_card_list, container, false)
        forumRecyclerView = view.findViewById(R.id.forum_card_recyclerview) as RecyclerView
        forumRecyclerView.layoutManager = GridLayoutManager(context, 2)

        updateUI()

        return view
    }

    private fun updateUI() {
        val forums = forumCardListViewModel.forums
        adapter = ForumCardAdapter(forums, this@ForumCardListFragment)
        forumRecyclerView.adapter = adapter
    }


    override fun onClick(forum: Forum) {
        val intent = Intent(context, ForumActivity::class.java)
        intent.putExtra("FORUM_ID_EXTRA", forum.id)
        intent.putExtra("FORUM_COVER_EXTRA", forum.cover)
        intent.putExtra("FORUM_COURSEID_EXTRA", forum.courseId)
        intent.putExtra("FORUM_NAME_EXTRA", forum.name)
        intent.putExtra("FORUM_DESC_EXTRA", forum.description)
        startActivity(intent)
    }

    fun resetForumList() {
        forumCardListViewModel.resetForumList()
    }

    fun loadForumList() {
        forumCardListViewModel.loadForums(fetchCondition, sessionManager)
    }




    companion object {
        fun newInstance(): ForumCardListFragment {
            return ForumCardListFragment()
        }
    }
}