package com.example.canvas

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.drawscope.DrawScope
import java.io.IOException
import android.util.Log


class AssetPainter(private val assetPath: String, private val context: Context) : Painter() {
    private var imageBitmap: ImageBitmap? = null

    init {
        try {
            context.assets.open(assetPath).use { inputStream ->
                BitmapFactory.decodeStream(inputStream)?.asImageBitmap()?.let {
                    imageBitmap = it
                }
            }
        } catch (e: IOException) {
            // Handle the exception, e.g., log or set a placeholder image
            Log.e("Error occurred:$e", "Error message")
        }
    }

    override val intrinsicSize: Size
        get() = imageBitmap?.let {
            Size(it.width.toFloat(), it.height.toFloat())
        } ?: Size.Unspecified

    override fun DrawScope.onDraw() {
        imageBitmap?.let { image ->
            drawImage(image)
        }
    }
}
