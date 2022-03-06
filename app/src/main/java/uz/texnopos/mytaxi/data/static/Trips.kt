package uz.texnopos.mytaxi.data.static

import com.google.android.gms.maps.model.LatLng
import uz.texnopos.mytaxi.data.model.Destination
import uz.texnopos.mytaxi.data.model.Trip

val trips= listOf(
    Trip(
        "6 Июля, Вторник",
        "11:54",
        12900,
        1,
        Destination(
            start = LatLng(41.277108, 69.298304),
            end = LatLng(41.312494, 69.238777),
            whereFrom = "улица Sharof Rashidov, Ташкент",
            whereTo = "5a улица Асадуллы Ходжаева",
        )
    ),Trip(
        "6 Июля, Вторник",
        "21:36",
        25500,
        2,
        Destination(
            start = LatLng(41.296887, 69.294047),
            end = LatLng(41.365623, 69.313833),
            whereFrom = "Яшнабадский район, улица Sharof Rashidov, Ташкент",
            whereTo = "Юнусабадский район, м-в юнусабад-19, ул. дехканабад, 17",
        )
    ),Trip(
        "5 Июля, Понедельник",
        "03:45",
        36000,
        3,
        Destination(
            start = LatLng(41.322112, 69.209830),
            end = LatLng(41.310843, 69.193082),
            whereFrom = "Шайхантахурский район",
            whereTo = "Малая кольцевая дорога",
        )
    ),Trip(
        "4 Июля, Воскресенье",
        "09:16",
        15200,
        1,
        Destination(
            start = LatLng(41.274789, 69.203716),
            end = LatLng(41.270852, 69.169814),
            whereFrom = "Чиланзар-16 Ташкент",
            whereTo = "улица Катта Каъни Ташкент",
        )
    ),Trip(
        "4 Июля, Воскресенье",
        "15:52",
        22800,
        3,
        Destination(
            start = LatLng(41.274789, 69.203716),
            end = LatLng(41.292166, 69.127197),
            whereFrom = "Зангиатинский район Ташкент",
            whereTo = "Назарбек Ташкент",
        )
    ),
    Trip(
        "3 Июля, Суббота",
        "11:54",
        12900,
        1,
        Destination(
            start = LatLng(41.277108, 69.298304),
            end = LatLng(41.312494, 69.238777),
            whereFrom = "улица Sharof Rashidov, Ташкент",
            whereTo = "5a улица Асадуллы Ходжаева",
        )
    ),Trip(
        "2 Июля, Пятница",
        "21:36",
        25500,
        2,
        Destination(
            start = LatLng(41.296887, 69.294047),
            end = LatLng(41.365623, 69.313833),
            whereFrom = "Яшнабадский район, улица Sharof Rashidov, Ташкент",
            whereTo = "Юнусабадский район, м-в юнусабад-19, ул. дехканабад, 17",
        )
    ),Trip(
        "1 Июля, Четверг",
        "03:45",
        36000,
        3,
        Destination(
            start = LatLng(41.322112, 69.209830),
            end = LatLng(41.310843, 69.193082),
            whereFrom = "Шайхантахурский район",
            whereTo = "Малая кольцевая дорога",
        )
    ),Trip(
        "1 Июля, Четверг",
        "09:16",
        15200,
        1,
        Destination(
            start = LatLng(41.274789, 69.203716),
            end = LatLng(41.270852, 69.169814),
            whereFrom = "Чиланзар-16 Ташкент",
            whereTo = "улица Катта Каъни Ташкент",
        )
    ),Trip(
        "30 Июня, Среда",
        "15:52",
        22800,
        3,
        Destination(
            start = LatLng(41.274789, 69.203716),
            end = LatLng(41.292166, 69.127197),
            whereFrom = "Зангиатинский район Ташкент",
            whereTo = "Назарбек Ташкент",
        )
    ),
)