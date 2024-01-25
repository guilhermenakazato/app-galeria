package com.example.galeria.controllers

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.example.frontend.model.Video
import com.example.galeria.utils.Date

class VideoController {
    companion object {
        fun getVideos(context: Context) : Map<Long, Video> {
            val videoList = mutableMapOf<Long, Video>()

            val collection =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Video.Media.getContentUri(
                        MediaStore.VOLUME_EXTERNAL
                    )
                } else {
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                }

            val projection = arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.DURATION
            )

            val sortOrder = "${MediaStore.Video.Media.DATE_MODIFIED} DESC"
            val query = context.contentResolver.query(
                collection,
                projection,
                null,
                null,
                sortOrder
            )
            query?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                val durationColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
                val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val duration = cursor.getInt(durationColumn)
                    val size = cursor.getInt(sizeColumn)
                    val date = Date.convertEpoch(cursor.getLong(dateColumn))

                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    videoList[id] = Video(id, contentUri, name, size, duration, date)
                }

                cursor.close()
            }

            return videoList
        }
    }
}