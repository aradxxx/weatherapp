package com.a65apps.weather.presentation.citysearch

import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.a65apps.weather.R
import com.a65apps.weather.domain.city.City
import com.a65apps.weather.presentation.util.itemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_city.*

fun cityItemAdapterDelegate(clickListener: (Int) -> Unit) =
    adapterDelegateLayoutContainer<City, City>(R.layout.item_city) {
        itemView.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                clickListener(position)
            }
        }
        bind {
            cityName.text = item.name
            savedIcon.isInvisible = !item.saved
        }
    }

val diffCallback = itemCallback<City>(
    areItemsTheSame = { oldItem, newItem ->
        oldItem.id == newItem.id
    }
)

class CitiesAdapter(clickListener: (Int) -> Unit) :
    AsyncListDifferDelegationAdapter<City>(diffCallback) {
    init {
        delegatesManager.addDelegate(
            cityItemAdapterDelegate(clickListener)
        )
    }
}
