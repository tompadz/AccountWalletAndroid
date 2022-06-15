package com.xslite.sharemyaccountnative.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialSharedAxis
import com.google.gson.Gson
import com.xslite.sharemyaccountnative.data.adapters.AccountCreatedAdapter
import com.xslite.sharemyaccountnative.data.models.AccountModel
import com.xslite.sharemyaccountnative.databinding.FragmentSearchBinding
import com.xslite.sharemyaccountnative.ui.view_models.SearchViewModel
import com.xslite.sharemyaccountnative.util.AndroidUtil
import com.xslite.sharemyaccountnative.util.AppTextWatcher
import com.xslite.sharemyaccountnative.util.listeners.OnAccountClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel : SearchViewModel by viewModels()

    private lateinit var adapter:AccountCreatedAdapter

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y,false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
    }

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        binding.apply {
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }

        binding.apply {
            adapter = AccountCreatedAdapter(
                listener = object : OnAccountClickListener {
                    override fun onAccountClick(account : AccountModel) {
                        val item = Gson().toJson(account)
                        val action = SearchFragmentDirections.actionSearchFragmentToAccountFragment(item)
                        findNavController().navigate(action)
                    }
                }
            )
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }

        viewModel.accounts.observe(viewLifecycleOwner){
            it.let {
                adapter.setData(it)
            }
        }

        binding.apply {
            fieldSearch.addTextChangedListener(object : AppTextWatcher() {
                override fun onTextChanged(p0 : CharSequence?, p1 : Int, p2 : Int, p3 : Int) {
                    val query = "%$p0%"
                    viewModel.getAccountQuery(query = query).observe(viewLifecycleOwner) { items ->
                        items.let {
                            adapter.setData(items)
                        }
                    }
                }
            })
        }


        return view
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidUtil().showKeyboard(binding.fieldSearch)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}