package com.makassar.newsapp.ui.activities.objectDetection

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.makassar.newsapp.data.repository.ObjectDetectionRepository
import org.tensorflow.lite.task.vision.detector.Detection

class ObjectDetectionViewModel(private val objectDetectionRepository: ObjectDetectionRepository): ViewModel() {

    var result: List<Detection>? = null

    fun detect(bitmap: Bitmap) = objectDetectionRepository.detect(bitmap)

}