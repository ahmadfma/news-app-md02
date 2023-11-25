package com.makassar.newsapp.data.repository

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.makassar.newsapp.data.Result
import kotlinx.coroutines.Dispatchers
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.Detection
import org.tensorflow.lite.task.vision.detector.ObjectDetector

class ObjectDetectionRepository(private val context: Context) {

    private val modelFilePath = "sehatin_modelV5b.tflite"

    fun detect(bitmap: Bitmap): LiveData<Result<List<Detection>>> = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            val image = TensorImage.fromBitmap(bitmap)
            val options = ObjectDetector.ObjectDetectorOptions.builder()
                .setMaxResults(8)
                .setScoreThreshold(0.3f)
                .build()
            val detector = ObjectDetector.createFromFileAndOptions(context, modelFilePath, options)
            val results = detector.detect(image)
            emitSource(MutableLiveData(Result.Success(results)))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

}