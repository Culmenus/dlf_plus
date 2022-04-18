package com.hbv2.dlf_plus.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.data.model.User
import com.hbv2.dlf_plus.databinding.FragmentCreateTopicBinding
import com.hbv2.dlf_plus.services.ForumService
import com.hbv2.dlf_plus.ui.topiclistfragment.view.TopicListFragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val FORUM_SERVICE = "forumservice"
private const val ARG_PARAM2 = "param2"



class CreateTopicFragment : DialogFragment() {
//    // TODO: Rename and change types of parameters
    private var param2: String? = null
    private var param1: String? = null
    private lateinit var listener: OnTopicCreated

    // assign the _binding variable initially to null and
    // also when the view is destroyed again it has to be set to null
    private var _binding: FragmentCreateTopicBinding? = null

    // with the backing property of the kotlin we extract
    // the non null value of the _binding
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context != null) {
            
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateTopicBinding.inflate(inflater, container, false)

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.createButton.setOnClickListener {
            // Toast.makeText(context, "create now", Toast.LENGTH_SHORT).show()
            val title = binding.titleInput.text;
            val desc = binding.descriptionInput.text;

            if (title?.isEmpty() == true) {
                Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                val topic = Topic(
                    // todo get authed user
                    creator = User( "danni@hi.is", username = "Danni", id = 2),
                    title = title.toString(),
                    description = desc.toString()
                )
                // todo koma þessu topic í topics í TopicListFragment og posta því á bakenda

                Toast.makeText(context, "make it", Toast.LENGTH_SHORT).show()
                dismiss()
                // todo Redirect to created topic i staðinn fyrir dismiss()?
            }
        }

        return binding.root
    }


    companion object {
        fun newInstance(): CreateTopicFragment {
            return CreateTopicFragment()
        }
    }
}

interface OnTopicCreated {
    fun onTopicCreated(topic: Topic)
}