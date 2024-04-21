package com.example.myapplication


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.model.DataResponseItem
import com.example.myapplication.databinding.ItemPhotoGridBinding
import com.example.myapplication.utils.ImageLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotoListAdapter(
    private var mPhotoList: ArrayList<DataResponseItem?> = arrayListOf()
) : BaseAdapter<DataResponseItem?>(mPhotoList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PhotoListViewHolder(
            ItemPhotoGridBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        when (holder) {
            is PhotoListViewHolder -> {
                holder.binding.apply {
                    val media = mPhotoList[position]
                    val imgUrl = media?.thumbnail?.domain+"/"+media?.thumbnail?.basePath+"/"+media?.thumbnail?.qualities?.get(0)+"/"+media?.thumbnail?.key

                   CoroutineScope(Dispatchers.IO).launch {
                       val bitmap = ImageLoader.getInstance(holder.binding.ivPhoto.context).loadImage(imgUrl,media?.thumbnail?.id)
                       withContext(Dispatchers.Main) {
                           if (bitmap == null) {
                               holder.binding.ivPhoto.setImageResource(R.drawable.baseline_error_24)
                           } else {
                               holder.binding.ivPhoto.setImageBitmap(bitmap)
                           }
                       }
                   }
                }
            }
        }
    }


    inner class PhotoListViewHolder(val binding: ItemPhotoGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}