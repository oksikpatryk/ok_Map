package com.oksik.okmap.ui.dialog_information

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2

import com.oksik.okmap.databinding.InformationDialogFragmentBinding

class InformationDialogFragment : DialogFragment() {

    companion object {
        fun newInstance() = InformationDialogFragment()
    }

    private lateinit var viewModel: InformationDialogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = InformationDialogViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(InformationDialogViewModel::class.java)
        viewModel.selectedPlant.value =
            InformationDialogFragmentArgs.fromBundle(requireArguments()).selectedPlant

        val adapter = PicturesAdapter()


        val binding = InformationDialogFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent);

        binding.imageView2.adapter = adapter
        viewModel.getSelectedPlantPictures.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })

        binding.imageView2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setImagePosition(position)
            }
        })

        return binding.root
    }
}
