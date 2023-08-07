package com.neotica.submissiondicodingawal.view.fragment.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neotica.submissiondicodingawal.view.fragment.FollowersFragment
import com.neotica.submissiondicodingawal.view.fragment.FollowingFragment
import com.neotica.submissiondicodingawal.view.fragment.UserProfileFragment

class TabAdapter(activity: UserProfileFragment) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowersFragment()
            else -> FollowingFragment()
        }
    }
}