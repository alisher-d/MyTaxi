package uz.texnopos.mytaxi.data.model

data class Trip(
    val tripDate: String,
    val whereFrom: String,
    val whereTo: String,
    val tripTime: String,
    val tripPrice: Long,
    val carType: Int
)