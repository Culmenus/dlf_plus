package com.hbv2.dlf_plus.ui.topiccreatefragment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.databinding.FragmentEditTopicBinding
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.ui.ForumActivity
import com.hbv2.dlf_plus.ui.TopicActivity
import com.hbv2.dlf_plus.ui.topiccreatefragment.TopicService

class EditTopicFragment : DialogFragment() {
    private lateinit var topicService: TopicService
    private lateinit var sessionManager: SessionManager
    private lateinit var activity: TopicActivity
    private lateinit var topic: Topic

    private var _binding: FragmentEditTopicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity = getActivity() as TopicActivity
        topicService = activity.getTopicService()
        sessionManager = activity.getSessionManager()
        topic = activity.getTopicInstance()

        _binding = FragmentEditTopicBinding.inflate(inflater, container, false)

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.titleInput.hint = topic.title
        binding.descriptionInput.hint = topic.description

        binding.editButton.setOnClickListener {
            setLoad()
            val title = binding.titleInput.text;
            val desc = binding.descriptionInput.text;

            if (title.toString() == topic.title && desc.toString() == topic.description) {
                // no change
                dismiss()
            } else if (desc?.length?.compareTo(254)?: -1 > 0) {
                Toast.makeText(context, "Description too long", Toast.LENGTH_LONG).show()
                setNotLoad()
            } else {
                if (!title.isNullOrEmpty()) topic.title = title.toString()
                if (!desc.isNullOrEmpty()) topic.description = desc.toString()
                topicService.editTopic(topic, this)
            }
        }

        return binding.root
    }

    companion object {
        fun newInstance(): EditTopicFragment {
            return EditTopicFragment()
        }
    }
    private fun setLoad() {
        binding.loadingSplash.visibility = View.VISIBLE
        binding.editButton.visibility = View.GONE
    }

    private fun setNotLoad() {
        binding.loadingSplash.visibility = View.GONE
        binding.editButton.visibility = View.VISIBLE
    }

    fun onTopicEdited(topic: Topic) {
        activity.onTopicEdited(topic)
        dismiss()
    }

    fun errorEditing(str: String) {
        setNotLoad()
        Toast.makeText(context, str, Toast.LENGTH_LONG).show()
    }
}
