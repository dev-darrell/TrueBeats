package com.adnahcodes.truebeats.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adnahcodes.truebeats.databinding.FragmentTrackListBinding

class TrackList : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mBinding: FragmentTrackListBinding = FragmentTrackListBinding.inflate(inflater,
            container, false)
        return mBinding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}