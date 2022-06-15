package com.xslite.sharemyaccountnative.ui.fragments

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import com.xslite.sharemyaccountnative.R
import com.xslite.sharemyaccountnative.databinding.FragmentSettingsBinding
import com.xslite.sharemyaccountnative.ui.sheets.SheetSettingsTheme
import com.xslite.sharemyaccountnative.ui.view_models.SettingsViewModel
import com.xslite.sharemyaccountnative.util.listeners.OnToggleValueChangeListener
import com.xslite.sharemyaccountnative.util.listeners.SheetSettingsThemeListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding !!
    private val viewModel : SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
    }

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        binding.apply {
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
            //Disable the button if the android version is below 12
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                buttonSettingsToggle.isEnable = viewModel.getPrefDynamicColors()
            } else {
                buttonSettingsToggle.isClickable = false
                buttonSettingsToggle.alpha = 0.5f
            }
            updateThemeText(viewModel.getPrefTheme())
        }

        binding.apply {
            buttonSettingsToggle.addOnChangeListener(object : OnToggleValueChangeListener {
                override fun onChange(value : Boolean) {
                    viewModel.setPrefDynamicColors(
                        value = value,
                        onSuccesses = {
                            Snackbar.make(
                                root,
                                getString(R.string.snackbar_restart),
                                Snackbar.LENGTH_LONG
                            ).show()
                        },
                        onError = {
                            buttonSettingsToggle.isEnable = ! value
                        }
                    )
                }
            })
            buttonSettingsDarkMode.addOnClickListener {
                showThemeSettings()
            }
        }

        return view
    }

    private fun updateThemeText(theme : Int) {
        binding.apply {
            buttonSettingsDarkMode.itemText = when (theme) {
                AppCompatDelegate.MODE_NIGHT_NO -> getString(R.string.button_theme_light)
                AppCompatDelegate.MODE_NIGHT_YES -> getString(R.string.button_theme_dark)
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> getString(R.string.button_theme_system)
                else -> getString(R.string.button_theme_system)
            }
        }
    }

    private fun showThemeSettings() {
        val modalThemeSettings = SheetSettingsTheme(
            listener = object : SheetSettingsThemeListener {
                override fun onThemeChange(theme : Int) {
                    viewModel.setTheme(theme) {
                        //To make the animation start more smoothly, we need to delay the theme change for a short time.
                        //During testing, I took this time as 100
                        Handler(Looper.getMainLooper()).postDelayed({
                            AppCompatDelegate.setDefaultNightMode(theme)
                        }, 100)
                    }
                }
            }
        )
        modalThemeSettings.show(parentFragmentManager, SheetSettingsTheme.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}