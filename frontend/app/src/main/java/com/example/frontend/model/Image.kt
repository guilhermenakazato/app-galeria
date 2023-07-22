package com.example.frontend.model

import android.net.Uri

class Image(imageUri: Uri, imageName: String, imageSize: Int) : Media() {
    override var name: String = imageName
        set(value) {}
    override var size: Int = imageSize
        set(value) {}
    override var uri: Uri = imageUri
        set(value) {}
}