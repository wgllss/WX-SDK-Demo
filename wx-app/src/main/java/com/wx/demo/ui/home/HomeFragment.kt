package com.wx.demo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wx.demo.SampleApplication
import com.wx.demo.databinding.FragmentHomeBinding
import com.wx.iml.XLogLoaderImpl
import com.wx.sdk5.impl.SDK5Impl

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        textView.setOnClickListener {
            SampleApplication.xLogLoader.getWXLog().e("HomeFragment", "AAAAAAAAAAAA")
        }

        binding.textSdk5.setOnClickListener {
            SDK5Impl.instance.basicTypes(1, 2L, true, 3.0f, 4.4, "AAA")
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}