package com.hbv2.dlf_plus.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.databinding.FragmentCreateTopicBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CreateTopicFragment : DialogFragment() {
//    // TODO: Rename and change types of parameters
    private var param2: String? = null
    private var param1: String? = null

    // assign the _binding variable initially to null and
    // also when the view is destroyed again it has to be set to null
    private var _binding: FragmentCreateTopicBinding? = null

    // with the backing property of the kotlin we extract
    // the non null value of the _binding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateTopicBinding.inflate(inflater, container, false)

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.createButton.setOnClickListener {
            Toast.makeText(context, "create now", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateTopicFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateTopicFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}