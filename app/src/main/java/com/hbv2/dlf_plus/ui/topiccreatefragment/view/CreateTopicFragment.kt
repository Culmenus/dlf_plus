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
import com.hbv2.dlf_plus.databinding.FragmentCreateTopicBinding
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.ui.topiccreatefragment.CreateTopicService
import com.hbv2.dlf_plus.ui.topiccreatefragment.OnTopicCreated
import com.hbv2.dlf_plus.ui.ForumActivity

class CreateTopicFragment : DialogFragment() {
    private lateinit var createTopicService: CreateTopicService
    private lateinit var sessionManager: SessionManager
    private lateinit var listener: OnTopicCreated

    private var _binding: FragmentCreateTopicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        createTopicService = CreateTopicService(context, this)
        sessionManager = SessionManager(requireContext())

        _binding = FragmentCreateTopicBinding.inflate(inflater, container, false)

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.createButton.setOnClickListener {
            setLoad()
            val title = binding.titleInput.text;
            val desc = binding.descriptionInput.text;

            val user: User? = sessionManager?.fetchAuthedUserDetails()?.user

            if (title?.isEmpty() == true) {
                Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_LONG).show()
                Log.d("create Topic", "Title cannot be empty")
                setNotLoad()
            } else if (desc?.length?.compareTo(254)?: -1 > 0) {
                Toast.makeText(context, "Description too long", Toast.LENGTH_LONG).show()
                Log.d("create Topic", "Description too long")
                setNotLoad()
            } else if (user == null) {
                Toast.makeText(context, "Not logged in", Toast.LENGTH_LONG).show()
                Log.d("create Topic", "Not logged in")
                setNotLoad()
            } else {

                val topic = Topic(
                    creator = user,
                    title = title.toString(),
                    description = desc.toString()
                )
                val forumId = activity?.intent?.getIntExtra("FORUM_ID_EXTRA", -1).toString()
                if (forumId != null && forumId != "-1") {
                    createTopicService.createTopic(topic, forumId)
                } else {
                    Toast.makeText(context, "Creation failed, no forum id.", Toast.LENGTH_SHORT).show()
                    Log.d("Create Topic", "Creation failed, no forum id.")
                }
            }
        }

        return binding.root
    }

    fun topicCreated(topic: Topic) {
        Log.d("CreateTopicFragment", "received topic: " + topic.toString())
        binding.titleInput.setText("(Avada Kedavra")
        (activity as ForumActivity).onTopicCreated(topic)
        dismiss()
    }

    fun errorFetching(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        binding.loadingSplash.visibility = View.GONE
        binding.createButton.visibility = View.VISIBLE
    }

    fun setLoad() {
        binding.loadingSplash.visibility = View.VISIBLE
        binding.createButton.visibility = View.GONE
    }

    fun setNotLoad() {
        binding.loadingSplash.visibility = View.GONE
        binding.createButton.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance(): CreateTopicFragment {
            return CreateTopicFragment()
        }
    }
}
