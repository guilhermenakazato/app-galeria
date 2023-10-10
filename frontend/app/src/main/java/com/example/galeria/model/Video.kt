package com.example.frontend.model

import android.net.Uri

class Video(videoUri: Uri, videoName: String, videoSize: Int, videoDuration: Int) : Media(), Playable {
    override val duration: Int = videoDuration
    override var name: String = videoName
        set(value) {}
    override var uri: Uri = videoUri
        set(value) {}
    override var size: Int = videoSize
        set(value) {}
}