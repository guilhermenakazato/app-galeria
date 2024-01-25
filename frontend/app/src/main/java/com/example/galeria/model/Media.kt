package com.example.frontend.model

import android.net.Uri
import com.example.galeria.utils.Date

abstract class Media {
    abstract var id: Long
    abstract var uri: Uri
    abstract var name: String
    abstract var size: Int
    abstract var date: Date
}