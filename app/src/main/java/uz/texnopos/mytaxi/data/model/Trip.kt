package uz.texnopos.mytaxi.data.model

data class Trip(
    val tripDate: String,
    val tripTime: String,
    val tripPrice: Long,
    val carType: Int,
    val destination: Destination,
    var dateVisibility: Boolean = true,
)