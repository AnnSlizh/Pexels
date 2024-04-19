package by.slizh.pexelsapp.util.downloader

interface Downloader {
    fun downloadFile(url: String): Long
}