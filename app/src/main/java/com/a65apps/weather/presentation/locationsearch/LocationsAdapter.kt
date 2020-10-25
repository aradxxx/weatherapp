package com.a65apps.weather.presentation.locationsearch

import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.a65apps.weather.databinding.ItemLocationBinding
import com.a65apps.weather.domain.location.Location
import com.a65apps.weather.presentation.util.itemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun locationAdapterDelegate(clickListener: (Int) -> Unit) =
    adapterDelegateViewBinding<Location, Location, ItemLocationBinding>(
        { layoutInflater, root -> ItemLocationBinding.inflate(layoutInflater, root, false) }
    ) {
        itemView.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                clickListener(position)
            }
        }
        bind {
            binding.locationName.text = item.name
            binding.savedIcon.isInvisible = item.savedTimestamp == null
        }
    }

val diffCallback = itemCallback<Location>(
    areItemsTheSame = { oldItem, newItem ->
        oldItem.id == newItem.id
    }
)

class LocationsAdapter(clickListener: (Int) -> Unit) :
    AsyncListDifferDelegationAdapter<Location>(diffCallback) {
    init {
        delegatesManager.addDelegate(
            locationAdapterDelegate(clickListener)
        )
    }
}
