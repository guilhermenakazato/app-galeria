package com.example.frontend.model

import android.net.Uri
import com.example.galeria.utils.Date

class Image(imageId: Long, imageUri: Uri, imageName: String, imageSize: Int, imageDate: Date) : Media() {
    override var id: Long = imageId
        set(value) {}
    override var name: String = imageName
        set(value) {}
    override var size: Int = imageSize
        set(value) {}
    override var uri: Uri = imageUri
        set(value) {}
    override var date: Date = imageDate
        set(value) {}

    override fun toString(): String {
        return "Uri: ${this.uri}, Name: ${this.name}, ${this.date}"
    }
}