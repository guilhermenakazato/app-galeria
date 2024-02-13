package com.example.frontend.model

import android.net.Uri
import com.example.galeria.utils.Date

class Video(orderedId: Long, videoId: Long, videoUri: Uri, videoName: String, videoSize: Int, videoDuration: Int, videoDate: Date) : Media(), Playable {
    override var secondaryId: Long = orderedId
        set(value) {}
    override var primaryId: Long = videoId
        set(value) {}
    override var duration: Int = videoDuration
        set(value) {}
    override var name: String = videoName
        set(value) {}
    override var uri: Uri = videoUri
        set(value) {}
    override var size: Int = videoSize
        set(value) {}
    override var date: Date = videoDate
        set(value) {}

    override fun toString(): String {
        return "Uri: ${this.uri}, Name: ${this.name}, Date: ${this.date}"
    }
}