package com.example.frontend.views

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.frontend.R
import com.example.frontend.model.Image
import com.example.frontend.model.Video
import com.example.frontend.utils.containsOnly
import com.example.frontend.utils.isPermissionGranted
import com.example.frontend.utils.requestPermission
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_container
        ) as NavHostFragment

        navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navbar)
        bottomNavigationView.setupWithNavController(navController)

        checkAndRequestPermissions()
    }

    private fun checkAndRequestPermissions() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            (!isPermissionGranted(Manifest.permission.READ_MEDIA_VIDEO) ||
                    !isPermissionGranted(Manifest.permission.READ_MEDIA_IMAGES))) {
            requestPermission(NEW_PERMISSIONS_STORAGE, REQUEST_STORAGE)
        } else if (!isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE) ||
            !isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            requestPermission(OLD_PERMISSIONS_STORAGE, REQUEST_STORAGE)
        } else {
            val mediaQuantity = getVideosQuantity() + getImagesQuantity()
            Log.i("quantidade", mediaQuantity.toString())
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        val mainLayout: View = findViewById(R.id.mainLayout)

        if (requestCode == REQUEST_STORAGE) {
            if (grantResults.containsOnly(PackageManager.PERMISSION_GRANTED)) {
                val mediaQuantity = getVideosQuantity() + getImagesQuantity()
                Log.i("quantidade", mediaQuantity.toString())
            } else {
                Snackbar.make(
                    mainLayout,
                    R.string.permission_not_granted,
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(R.string.try_permission_again) {
                    checkAndRequestPermissions()
                }.show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
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