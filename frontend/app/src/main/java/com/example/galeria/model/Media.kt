package com.example.frontend.model

import android.net.Uri

abstract class Media {
    abstract var uri: Uri
    abstract var name: String
    abstract var size: Int
}