package com.oksik.okmap.ui.dialog_information

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

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
        viewModel = ViewModelProviders.of(this).get(InformationDialogViewModel::class.java)
        viewModel.selectedPlant.value = InformationDialogFragmentArgs.fromBundle(arguments!!).selectedPlant

        val binding = InformationDialogFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent);

        Log.i("pppp", viewModel.selectedPlant.value?.name)
        return binding.root
    }
}
