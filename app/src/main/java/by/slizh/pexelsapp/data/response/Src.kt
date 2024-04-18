package by.slizh.pexelsapp.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Src(
    val original: String,
    val large: String,
    val large2x: String,
    val medium:String,
    val small: String,
    val portrait: String,
    val landscape:String,
    val tiny: String
): Parcelable
