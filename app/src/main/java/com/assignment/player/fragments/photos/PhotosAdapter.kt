package com.assignment.player.fragments.photos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.assignment.player.R
import com.assignment.player.room.photos.Photo
import com.squareup.picasso.Picasso

class PhotosAdapter(var context: Context, var photos: List<Photo>) : PagerAdapter() {

    var layoutInflater: LayoutInflater
    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return photos.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val itemView: View = layoutInflater.inflate(R.layout.item_photo, container, false)
        val photo: ImageView = itemView.findViewById(R.id.imageView)
        val photoTitle: TextView = itemView.findViewById(R.id.textView)
        val photoId: TextView = itemView.findViewById(R.id.tvId)
        container.addView(itemView)


        var photoUrl : String? = photos[position].url
        var thumbUrl : String? = photos[position].thumbnailUrl
        photoId.text = photos[position].id.toString()
        photoTitle.text = "Title:" + photos[position].title
        Picasso.get().load(photoUrl).placeholder(R.drawable.ic_launcher_background).into(photo)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}