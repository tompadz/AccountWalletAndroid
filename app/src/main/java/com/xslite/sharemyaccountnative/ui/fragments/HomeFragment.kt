package com.xslite.sharemyaccountnative.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import com.google.gson.Gson
import com.xslite.sharemyaccountnative.R
import com.xslite.sharemyaccountnative.data.adapters.HomePageAdapter
import com.xslite.sharemyaccountnative.data.models.AccountModel
import com.xslite.sharemyaccountnative.databinding.FragmentHomeBinding
import com.xslite.sharemyaccountnative.ui.view_models.AccountViewModel
import com.xslite.sharemyaccountnative.ui.sheets.SheetAccountActions
import com.xslite.sharemyaccountnative.util.listeners.OnAccountActionSheetListener
import com.xslite.sharemyaccountnative.util.listeners.OnAccountClickListener
import com.xslite.sharemyaccountnative.util.listeners.OnHomeFooterClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnAccountClickListener, OnHomeFooterClickListener {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding !!

    private val viewModel : AccountViewModel by viewModels()
    private lateinit var adapter : HomePageAdapter

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
    }

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        binding.apply {
            toolbar.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menu_item_search -> {
                        val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
                        findNavController().navigate(action)
                        true
                    }
                    else -> {false}
                }
            }
        }

        binding.apply {
            adapter = HomePageAdapter(this@HomeFragment, this@HomeFragment)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
        }

        viewModel.accounts.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.apply {
                    recyclerView.isVisible = false
                    collapsingToolbar.setScrollEnable(false)
                    emptyMessage.isVisible = true
                }
            } else {
                binding.apply {
                    recyclerView.isVisible = true
                    collapsingToolbar.setScrollEnable(true)
                    emptyMessage.isVisible = false
                }
                adapter.setData(viewModel.sortAccountsToSection(it))
            }
        }

        binding.emptyMessage.addCircleClickListener { navigateToAddNewAccount() }

        binding.toolbar.setNavigationOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
            findNavController().navigate(action)
        }

        return view
    }

    private fun navigateToAddNewAccount() {
        val action = HomeFragmentDirections.actionHomeFragmentToAddAccountFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAccountClick(account : AccountModel) {
        val json = Gson().toJson(account)
        val action = HomeFragmentDirections.actionHomeFragmentToAccountFragment(json)
        findNavController().navigate(action)
    }

    override fun onAccountLongClick(account : AccountModel) {
        val modalAction = SheetAccountActions(
            account = account,
            listener = object : OnAccountActionSheetListener {
                override fun onDeleteClick() {
                    viewModel.deleteAccount(account)
                }

                override fun onCopyClick() {
                    super.onCopyClick()
                    Snackbar.make(binding.root, getString(R.string.snackbar_link_copy), Snackbar.LENGTH_SHORT).show()
                }
            })
        modalAction.show(parentFragmentManager, SheetAccountActions.TAG)
    }

    override fun onCreateAccountClick() {
        navigateToAddNewAccount()
    }
}