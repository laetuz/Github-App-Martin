package com.neotica.submissiondicodingawal.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.neotica.submissiondicodingawal.databinding.ThemeFragmentBinding
import com.neotica.submissiondicodingawal.presentation.viewmodel.GithubViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ThemeFragment : Fragment() {
    private var _binding: ThemeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GithubViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ThemeFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTheme()
        theme()
    }


    private fun theme() {
        binding.apply {
            btnTheme.setOnClickListener {
                val currentTheme = when (AppCompatDelegate.getDefaultNightMode()) {
                    AppCompatDelegate.MODE_NIGHT_NO -> "LIGHT"
                    AppCompatDelegate.MODE_NIGHT_YES -> "DARK"
                    else -> "UNKNOWN"

                }
                viewModel.saveThemeSetting(currentTheme)
            }
        }
    }

    private fun setTheme() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getThemeSettings().collect{
                if (it == "DARK") {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}