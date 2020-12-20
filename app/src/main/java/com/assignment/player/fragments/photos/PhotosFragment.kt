package com.assignment.player.fragments.photos

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import com.assignment.player.R
import com.assignment.player.room.photos.Photo
import com.assignment.player.viewmodel.PhotosViewModel

class PhotosFragment : Fragment() {

    val photosViewModel by activityViewModels<PhotosViewModel>()
    lateinit var viewPager: ViewPager

    var photosData: List<Photo> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_photos, container, false)

        viewPager = view.findViewById(R.id.viewPager) as ViewPager

       /* photosViewModel.getAllPhotosAsLiveData().observe(
            viewLifecycleOwner,
            {
                val adapter = PhotosAdapter(requireContext(), it)
                viewPager.adapter = adapter
                autoSwitcher()
            }
        )*/


        photosViewModel.getAllPhotosAsLiveData().observe(
            viewLifecycleOwner,
            { photos ->
                if (!photos.isNullOrEmpty()) {
                    photosData = photos
                    activity?.runOnUiThread {
                        val adapter = PhotosAdapter(requireContext(), photos)
                        viewPager.adapter = adapter
                        autoSwitcher()
                    }
                }
                else {
                    photosViewModel.callPhotosApi()
                }
            }
        )


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }



    override fun onResume() {
        super.onResume()

      //  if(!photosViewModel.photosDataAlreadExist())
       // photosViewModel.callPhotosApi()
    }

    fun autoSwitcher(){

        Handler().postDelayed({
            viewPager.currentItem += 1
            autoSwitcher()
        }, 5000)

    }



}