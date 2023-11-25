package com.makassar.newsapp.ui.activities.objectDetection

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.makassar.newsapp.R
import com.makassar.newsapp.data.Result
import com.makassar.newsapp.databinding.ActivityObjectDetectionBinding
import com.makassar.newsapp.utils.FileHelper
import com.makassar.newsapp.utils.ViewModelFactory
import java.io.File

class ObjectDetectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityObjectDetectionBinding
    private lateinit var objectDetectionViewModel: ObjectDetectionViewModel

    private var selectedImageFile: File? = null
    private lateinit var currentPhotoPath: String
    private var image: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityObjectDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        objectDetectionViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this))[ObjectDetectionViewModel::class.java]
        binding.cameraBtn.setOnClickListener {
            startIntentCamera()
        }
    }

    private fun startIntentCamera() {
        if (!allCameraPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS_CAMERA,
                REQUEST_CODE_CAMERA
            )
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.resolveActivity(packageManager)
            FileHelper.createTempFile(this).also {
                val photoURI: Uri = FileProvider.getUriForFile(this, AUTHOR, it)
                currentPhotoPath = it.absolutePath
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                launcherIntentCamera.launch(intent)
            }
        }
    }

    private val launcherIntentCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            selectedImageFile = FileHelper.reduceFileImage(File(currentPhotoPath))
            if (selectedImageFile != null) {
                image = BitmapFactory.decodeFile(selectedImageFile!!.path)
                binding.resultIv.setImageBitmap(image)
                detect(image)
            } else {
                Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun detect(result: Bitmap?) {
        objectDetectionViewModel.detect(result!!).observe(this) {
            when(it) {
                is Result.Loading -> {}
                is Result.Error -> {}
                is Result.Success -> {
                    Log.d("ObjectDetection", "resut: ${it.data}")
                    objectDetectionViewModel.result = it.data
                    onSuccessDetect()
                }
            }
        }
    }

    private fun onSuccessDetect() {
        val result = objectDetectionViewModel.result
        if(!result.isNullOrEmpty()) {
            val resultToDisplay = result.map {
                DetectionResult(it.boundingBox, it.categories.first().label)
            }
            val imageResult = drawDetectionResult(image!!, resultToDisplay)
            binding.resultIv.setImageBitmap(imageResult)
        } else {
            Toast.makeText(this, "Tidak terdeteki", Toast.LENGTH_SHORT).show()
        }
    }

    private fun drawDetectionResult(bitmap: Bitmap, detectionResults: List<DetectionResult>): Bitmap {
        val outputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(outputBitmap)
        val pen = Paint()
        pen.textAlign = Paint.Align.LEFT

        detectionResults.forEach {
            // draw bounding box
            pen.color = Color.RED
            pen.strokeWidth = 8F
            pen.style = Paint.Style.STROKE
            val box = it.boundingBox
            canvas.drawRect(box, pen)
            val tagSize = Rect(0, 0, 0, 0)
            // calculate the right font size
            pen.style = Paint.Style.FILL_AND_STROKE
            pen.color = Color.RED
            pen.strokeWidth = 2F
            pen.textSize = 50F
            pen.getTextBounds(it.text, 0, it.text.length, tagSize)
//            val fontSize: Float = pen.textSize * box.width() / tagSize.width()
//            // adjust the font size so texts are inside the bounding box
//            if (fontSize < pen.textSize) pen.textSize = fontSize
            var margin = (box.width() - tagSize.width()) / 2.0F
            if (margin < 0F) margin = 0F
            canvas.drawText(
                it.text, box.left + margin,
                box.top + tagSize.height().times(1F),
                pen
            )
        }
        return outputBitmap
    }

    private fun allCameraPermissionsGranted() = REQUIRED_PERMISSIONS_CAMERA.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        val REQUIRED_PERMISSIONS_CAMERA = arrayOf(Manifest.permission.CAMERA)
        const val REQUEST_CODE_CAMERA = 10
        const val AUTHOR = "com.makassar.newsapp"
    }

}