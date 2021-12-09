package com.pmz.cvsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pmz.cvsapp.R
import com.pmz.cvsapp.databinding.FragmentFlickrDetailsBinding
import com.pmz.cvsapp.utils.ApiState
import com.pmz.cvsapp.utils.loadImage
import com.pmz.cvsapp.viewmodel.FlickrViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlickrDetailsFragment : Fragment() {

    private var _binding: FragmentFlickrDetailsBinding? = null
    private val binding: FragmentFlickrDetailsBinding get() = _binding!!
    private val viewModel: FlickrViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlickrDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewModel.selectedImage?.let { item ->
                titleTv.text = getString(R.string.title_image).format(item.title)
                val description = (viewModel.images.value as ApiState.Success).data.description
                descriptionTv.text = getString(R.string.description_image).format(
                    if (description?.isNotBlank() == true) description else root.context.getString(R.string.none)
                )
                item.getDecodedDescription()?.let {
                    widthTv.text = getString(R.string.width_image).format(
                        it["width"] ?: root.context.getString(R.string.none)
                    )
                    heightTv.text = getString(R.string.width_image).format(
                        it["height"] ?: root.context.getString(R.string.none)
                    )
                    flickrIv.loadImage(it["link"] ?: "")
                    flickrIv.contentDescription = it["alt"] ?: item.title
                }
                authorTv.text = getString(R.string.author_image).format(item.author)

            } ?: {
                findNavController().navigateUp()
                Toast.makeText(
                    requireContext(),
                    getString(R.string.invalid_image),
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}