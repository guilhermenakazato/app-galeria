package com.example.galeria.controllers

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.Composable
import com.example.frontend.model.Image
import com.example.galeria.utils.Date

class ImageController {
    companion object {
        @Composable
        fun getImages(context: Context): MutableMap<Long, Image> {
            val imageMap = mutableMapOf<Long, Image>()
            var byteSize: Long = 0

            val collection =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Images.Media.getContentUri(
                        MediaStore.VOLUME_EXTERNAL
                    )
                } else {
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                }

            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_ADDED
            )

            val sortOrder = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
            val query = context.contentResolver.query(
                collection,
                projection,
                null,
                null,
                sortOrder
            )

            query?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val size = cursor.getInt(sizeColumn)
                    val date = Date.convertEpoch(cursor.getLong(dateColumn))

                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    byteSize += size
                    imageMap[id] = Image(id, contentUri, name, size, date)
                }

                cursor.close()
            }

            return imageMap
        }
    }
}