package com.hbv2.dlf_plus.ui.topicdeletefragment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.databinding.FragmentDeleteTopicBinding
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.ui.TopicActivity
import com.hbv2.dlf_plus.services.TopicService

class DeleteTopicFragment : DialogFragment() {
    private lateinit var topicService: TopicService
    private lateinit var sessionManager: SessionManager
    private lateinit var activity: TopicActivity
    private lateinit var topic: Topic

    private var _binding: FragmentDeleteTopicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity = getActivity() as TopicActivity
        topicService = activity.getTopicService()
        sessionManager = activity.getSessionManager()
        topic = activity.getTopicInstance()

        _binding = FragmentDeleteTopicBinding.inflate(inflater, container, false)

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.confirmButton.setOnClickListener {
            setLoad()

            topicService.deleteTopic(topic, this)

        }

        return binding.root
    }

    companion object {
        fun newInstance(): DeleteTopicFragment {
            return DeleteTopicFragment()
        }
    }
    private fun setLoad() {
        binding.loadingSplash.visibility = View.VISIBLE
        binding.confirmButton.visibility = View.GONE
    }

    private fun setNotLoad() {
        binding.loadingSplash.visibility = View.GONE
        binding.confirmButton.visibility = View.VISIBLE
    }

    fun onTopicDeleted() {
        activity.onTopicDeleted()
        dismiss()
    }

    fun errorDeleting(str: String) {
        setNotLoad()
        Toast.makeText(context, str, Toast.LENGTH_LONG).show()
    }
}
