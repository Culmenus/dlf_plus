package com.hbv2.dlf_plus.ui.topiclistfragment.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.ui.TopicActivity
import com.hbv2.dlf_plus.ui.topiclistfragment.TopicClickListener
import com.hbv2.dlf_plus.ui.topiclistfragment.adapter.TopicAdapter
import com.hbv2.dlf_plus.ui.topiclistfragment.viewmodel.TopicListViewModel

// Sbr. bls. 178

private const val TAG = "TopicListFragment"

class TopicListFragment : Fragment(), TopicClickListener {

    private lateinit var topicRecyclerView: RecyclerView
    private var adapter: TopicAdapter? = null

    private val topicListViewModel: TopicListViewModel by lazy {
        ViewModelProvider(this).get(TopicListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total topics: ${topicListViewModel.topics.size}")
        topicListViewModel
            .getTopicsLiveData()
            .observe(this, Observer<List<Topic>> { topic ->
                updateUI()
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_topic_list, container, false)
        topicRecyclerView = view.findViewById(R.id.topic_recycler_view) as RecyclerView
        topicRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

    override fun onClick(topic: Topic) {
        val intent = Intent(context, TopicActivity()::class.java)
        // todo redirect to this forum
        intent.putExtra("TOPIC_ID", topic.id)
        startActivity(intent)
    }


    private fun updateUI() {
        val topics = topicListViewModel.topics
        adapter = TopicAdapter(topics, this@TopicListFragment)
        topicRecyclerView.adapter = adapter
    }

    fun addTopicToListView(topic: Topic) {
        topicListViewModel.createTopic(topic)
    }

    fun getViewModel(): TopicListViewModel {
        return topicListViewModel
    }

    fun resetTopicList() {
        topicListViewModel.resetTopicList()
    }

    companion object {
        fun newInstance(): TopicListFragment {
            return TopicListFragment()
        }
    }
}

