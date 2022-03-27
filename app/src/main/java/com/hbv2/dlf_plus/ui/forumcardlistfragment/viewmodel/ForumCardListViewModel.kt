package com.hbv2.dlf_plus.ui.forumcardlistfragment.viewmodel

import androidx.lifecycle.ViewModel
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.data.model.Forum

class ForumCardListViewModel : ViewModel() {
    // todo check kafli 11
    val forums = mutableListOf<Forum>()

    init {
        // temp mock data
        val forum1 = Forum(
            1,
            R.drawable.pallas,
            "Tol999",
            "Forritun",
        )
        forums += forum1

        val forum2 = Forum(
            2,
            R.drawable.pallasblue,
            "Stæ999",
            "Stærðfræði",
        )
        forums += forum2
        val forum3 = Forum(
            3,
            R.drawable.img,
            "Cov19",
            "Veikur",
        )
        forums += forum3

    }
}