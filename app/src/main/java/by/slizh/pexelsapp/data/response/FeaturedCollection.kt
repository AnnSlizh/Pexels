package by.slizh.pexelsapp.data.response

data class FeaturedCollection(
    val collections: List<Collection>,
    val page: Int,
    val per_page: Int,
    val total_results: Int
)
