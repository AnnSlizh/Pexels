package by.slizh.pexelsapp.data.converter

import by.slizh.pexelsapp.data.local.entity.PhotoEntity
import by.slizh.pexelsapp.data.local.entity.SrcEntity
import by.slizh.pexelsapp.data.response.Photo
import by.slizh.pexelsapp.data.response.Src


fun photoEntityToPhoto(photoEntity: PhotoEntity): Photo {
    return Photo(
        id = photoEntity.id,
        width = photoEntity.width,
        height = photoEntity.height,
        url = photoEntity.url,
        photographer = photoEntity.photographer,
        photographer_url = photoEntity.photographer_url,
        photographer_id = photoEntity.photographer_id,
        avg_color = photoEntity.avg_color,
        src = convertSrcEntityToSrc(photoEntity.src),
        liked = photoEntity.liked,
        alt = photoEntity.alt
    )
}

fun photoToPhotoEntity(photo: Photo): PhotoEntity {
    return PhotoEntity(
        id = photo.id,
        width = photo.width,
        height = photo.height,
        url = photo.url,
        photographer = photo.photographer,
        photographer_url = photo.photographer_url,
        photographer_id = photo.photographer_id,
        avg_color = photo.avg_color,
        src = convertSrcToSrcEntity(photo.src),
        liked = photo.liked,
        alt = photo.alt
    )
}

private fun convertSrcToSrcEntity(src: Src): SrcEntity {
    return SrcEntity(
        original = src.original,
        large = src.large,
        large2x = src.large2x,
        medium = src.medium,
        small = src.small,
        portrait = src.portrait,
        landscape = src.landscape,
        tiny = src.tiny
    )
}

private fun convertSrcEntityToSrc(srcEntity: SrcEntity): Src {
    return Src(
        original = srcEntity.original,
        large = srcEntity.large,
        large2x = srcEntity.large2x,
        medium = srcEntity.medium,
        small = srcEntity.small,
        portrait = srcEntity.portrait,
        landscape = srcEntity.landscape,
        tiny = srcEntity.tiny
    )
}
