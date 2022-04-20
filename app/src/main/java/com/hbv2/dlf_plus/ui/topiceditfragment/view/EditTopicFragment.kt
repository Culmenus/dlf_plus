package com.hbv2.dlf_plus.ui.topiccreatefragment.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.data.model.User
import com.hbv2.dlf_plus.databinding.FragmentEditTopicBinding
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.ui.topiccreatefragment.EditTopicService
import com.hbv2.dlf_plus.ui.ForumActivity
import com.hbv2.dlf_plus.ui.topiccreatefragment.OnTopicEdited

class EditTopicFragment : DialogFragment() {
    private lateinit var editTopicService: EditTopicService
    private lateinit var sessionManager: SessionManager
    private lateinit var listener: OnTopicEdited

    private var _binding: FragmentEditTopicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        editTopicService = EditTopicService(context, this)
        sessionManager = SessionManager(requireContext())

        _binding = FragmentEditTopicBinding.inflate(inflater, container, false)

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        // todo endurskrifa thetta vel (dha)
        binding.editButton.setOnClickListener {
            val title = binding.titleInput.text;
            val desc = binding.descriptionInput.text;

            if (title?.isEmpty() == true) {
                Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_LONG).show()
            } else if (desc?.length?.compareTo(254)?: -1 > 0) {
                Toast.makeText(context, "Description too long", Toast.LENGTH_LONG).show()
            } else {
                binding.loadingSplash.visibility = View.VISIBLE
                binding.editButton.visibility = View.GONE
                val user: User? = sessionManager?.fetchAuthedUserDetails()?.user
                val topic = Topic(
                    creator = user,
                    title = title.toString(),
                    description = desc.toString()
                )
                val forumId = activity?.intent?.getIntExtra("FORUM_ID_EXTRA", -1).toString()
                if (forumId != null && forumId != "-1") {
                    editTopicService.editTopic(topic, forumId)
                } else {
                    Toast.makeText(context, "Edit failed, no forum id.", Toast.LENGTH_SHORT).show()
                    Log.d("Edit Topic", "Edit failed, no forum id.")
                }
            }
        }

        return binding.root
    }

    fun topicEdited(topic: Topic) {
        Log.d("EditTopicFragment", "received updated topic: " + topic.toString())
        (activity as ForumActivity).onTopicEdited(topic)
        dismiss()
    }

    fun errorFetching(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        binding.loadingSplash.visibility = View.GONE
        binding.editButton.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance(): EditTopicFragment {
            return EditTopicFragment()
        }
    }
}
