package by.slizh.pexelsapp.data.response

data class PhotoList (
    val total_results: Int,
    val page: Int,
    val per_page: Int,
    val photos: List<Photo>,
    val next_page: String
)