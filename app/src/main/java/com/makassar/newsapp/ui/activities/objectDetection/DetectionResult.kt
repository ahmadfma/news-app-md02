package com.makassar.newsapp.ui.activities.objectDetection

import android.graphics.RectF

data class DetectionResult(
    val boundingBox: RectF,
    val text: String
)