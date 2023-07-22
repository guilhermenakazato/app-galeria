package com.example.frontend.view

import android.Manifest
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import com.example.frontend.R
import com.example.frontend.model.Image
import com.example.frontend.model.Video
import com.example.frontend.utils.isPermissionGranted
import com.example.frontend.utils.requestPermission

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mediaQuantityText: TextView = findViewById(R.id.mediaQuantityText)
        checkPermissions()
        var mediaQuantity = getVideosQuantity() + getImagesQuantity()
        mediaQuantityText.text = mediaQuantity.toString()
    }

    private fun checkPermissions() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            (!isPermissionGranted(Manifest.permission.READ_MEDIA_VIDEO) ||
                    !isPermissionGranted(Manifest.permission.READ_MEDIA_IMAGES))) {
            requestPermission(NEW_PERMISSIONS_STORAGE, REQUEST_STORAGE)
        } else if (!isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE) ||
            !isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            requestPermission(OLD_PERMISSIONS_STORAGE, REQUEST_STORAGE)
        }
    }

    private fun getVideosQuantity() : Int {
        val videoList = mutableListOf<Video>()

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
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE
        )

        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

        val query = this.contentResolver.query(
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

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getInt(durationColumn)
                val size = cursor.getInt(sizeColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                videoList += Video(contentUri, name, size, duration)
            }

            cursor.close()
        }

        return videoList.size
    }

    private fun getImagesQuantity() : Int {
        val imageList = mutableListOf<Image>()

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
            MediaStore.Images.Media.SIZE
        )

        val sortOrder = "${MediaStore.Images.Media.DISPLAY_NAME} ASC"

        val query = this.contentResolver.query(
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

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getInt(sizeColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                imageList += Image(contentUri, name, size)
            }

            cursor.close()
        }

        return imageList.size
    }

    companion object {
        const val REQUEST_STORAGE = 0
        val OLD_PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val NEW_PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_VIDEO
        )
    }
}