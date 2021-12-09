package com.pmz.cvsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pmz.cvsapp.R
import com.pmz.cvsapp.adapter.FlickrAdapter
import com.pmz.cvsapp.databinding.FragmentFlickrSearchBinding
import com.pmz.cvsapp.utils.ApiState
import com.pmz.cvsapp.viewmodel.FlickrViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlickrSearchFragment : Fragment() {

    private var _binding: FragmentFlickrSearchBinding? = null
    private val binding: FragmentFlickrSearchBinding get() = _binding!!
    private val viewModel: FlickrViewModel by activityViewModels()

    private lateinit var flickrAdapter: FlickrAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlickrSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flickrAdapter = FlickrAdapter {
            viewModel.selectedImage = it
            findNavController().navigate(R.id.actionSearchFragmentToDetailsFragment)
        }
        setupSearchQueryObserver()
        setupImagesObserver()
        with(binding) {
            rvFlickr.apply {
                adapter = flickrAdapter
            }
            queryAutoTv.apply {
                setOnItemClickListener { adapterView, _, i, _ ->
                    viewModel.query = adapterView.getItemAtPosition(i) as String
                }
                setOnClickListener {
                    viewModel.getSearchQueries()
                }
                addTextChangedListener {
                    if(it.toString().isNotBlank())
                        viewModel.query = it.toString()
                }
            }
            searchBtn.setOnClickListener {
                val input = queryAutoTv.text.toString()
                if (input.isNotBlank()) {
                    viewModel.query = input
                } else {
                    searchTextInput.apply {
                        error = "Search cannot be empty"
                        isErrorEnabled = true
                    }
                }
            }
        }
    }


private fun setupImagesObserver() = with(binding) {
    viewModel.images.observe(viewLifecycleOwner) { state ->
        when (state) {
            is ApiState.Loading -> {
                progressCircular.isVisible = true
                searchTextInput.isErrorEnabled = false
            }
            is ApiState.Success -> {
                progressCircular.isVisible = false
                searchTextInput.isErrorEnabled = false
                flickrAdapter.submitList(state.data.items)
            }
            is ApiState.Failure -> {
                progressCircular.isVisible = false
                searchTextInput.isErrorEnabled = true
                searchTextInput.error = state.errorMsg
            }
        }
    }
}

private fun setupSearchQueryObserver() = with(binding) {
    viewModel.queries.observe(viewLifecycleOwner) { searchQueries ->
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            searchQueries
        )
        queryAutoTv.setAdapter(adapter)
    }
}

override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}

}