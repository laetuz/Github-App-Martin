package com.neotica.submissiondicodingawal.main.fragment.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neotica.submissiondicodingawal.main.fragment.FollowersFragment
import com.neotica.submissiondicodingawal.main.fragment.FollowingFragment
import com.neotica.submissiondicodingawal.main.fragment.UserProfileFragment

class TabAdapter(activity: UserProfileFragment): FragmentStateAdapter(activity) {
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