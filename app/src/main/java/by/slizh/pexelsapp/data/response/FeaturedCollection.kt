package by.slizh.pexelsapp.data.response

data class FeaturedCollection(
    val id: Int,
    val title: String,
    val description: String,
    val private: Boolean,
    val media_count: Int,
    val photos_count: Int,
    val videos_count: Int
)
