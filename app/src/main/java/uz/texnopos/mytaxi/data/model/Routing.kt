package uz.texnopos.mytaxi.data.model

import com.directions.route.Route

data class Routing(
    val route: ArrayList<Route>?,
    val shortestRouteIndex: Int,
)
