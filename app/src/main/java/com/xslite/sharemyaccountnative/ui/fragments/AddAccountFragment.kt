package com.xslite.sharemyaccountnative.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialSharedAxis
import com.google.gson.Gson
import com.xslite.sharemyaccountnative.data.adapters.NewAccountAdapter
import com.xslite.sharemyaccountnative.data.models.AccountModel
import com.xslite.sharemyaccountnative.databinding.FragmentAddAccountBinding
import com.xslite.sharemyaccountnative.ui.view_models.AddAccountViewModel
import com.xslite.sharemyaccountnative.util.listeners.OnAccountClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddAccountFragment : Fragment(), OnAccountClickListener {

    private var _binding : FragmentAddAccountBinding? = null
    private val binding get() = _binding !!

    private val viewModel : AddAccountViewModel by viewModels()
    private lateinit var adapter:NewAccountAdapter

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * material motion
         * https://material.io/develop/android/theming/motion
         */
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y,false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
    }

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        _binding = FragmentAddAccountBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        binding.apply {
            adapter = NewAccountAdapter(this@AddAccountFragment)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
        }

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        lifecycleScope.launch {
            viewModel.uiState
                .map { it.sections }
                .distinctUntilChanged()
                .collect {
                    adapter.setData(it)
                }
        }

        return view
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.requestApplyInsets(binding.root);
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAccountClick(account : AccountModel) {
        val json = Gson().toJson(account)
        val action = AddAccountFragmentDirections.actionAddAccountFragmentToCreateAccountFragment(json)
        findNavController().navigate(action)
    }
}