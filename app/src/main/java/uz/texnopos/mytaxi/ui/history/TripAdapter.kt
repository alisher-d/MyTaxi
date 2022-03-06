package uz.texnopos.mytaxi.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.mytaxi.R
import uz.texnopos.mytaxi.data.model.Destination
import uz.texnopos.mytaxi.data.model.Trip
import uz.texnopos.mytaxi.databinding.ItemTripBinding
import uz.texnopos.mytaxi.utils.changeFormat
import uz.texnopos.mytaxi.utils.onClick

class TripAdapter : RecyclerView.Adapter<TripAdapter.TripItemViewHolder>() {
    private var lastDate = "qwerty"

    inner class TripItemViewHolder(private val binding: ItemTripBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(trip: Trip) {
            binding.apply {
                tvTripDate.isVisible = trip.dateVisibility
                tvTripDate.text = trip.tripDate
                tvFrom.text = trip.destination.whereFrom
                tvTo.text = trip.destination.whereTo
                tvTripTime.text = trip.tripTime
                tvTripPrice.text = trip.tripPrice.changeFormat()
                when (trip.carType) {
                    1 -> imgCarType.setImageResource(R.drawable.ic_car_1)
                    2 -> imgCarType.setImageResource(R.drawable.ic_car_2)
                    3 -> imgCarType.setImageResource(R.drawable.ic_car_3)
                }
                tripCard.onClick {
                    onItemClick.invoke(trip.destination)
                }

            }
        }
    }

    private var onItemClick: (destination: Destination) -> Unit = {}
    fun onItemClickListener(onItemClick: (destination: Destination) -> Unit) {
        this.onItemClick = onItemClick
    }

    private val models = mutableListOf<Trip>()
    fun setData(data: List<Trip>) {
        data.forEach {
            if (!models.contains(it)) {
                models.add(it)
                if (it.tripDate != lastDate) lastDate = it.tripDate
                else it.dateVisibility = false
                notifyItemInserted(models.lastIndex)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripItemViewHolder {
        val binding = ItemTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TripItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TripItemViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount() = models.size
}