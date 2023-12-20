package com.example.pdfrenderer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pdfrenderer.ui.theme.PDFRendererTheme
import android.content.Context
import android.os.ParcelFileDescriptor
import java.io.File
import android.graphics.pdf.PdfRenderer
import java.io.IOException
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PDFRendererTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PdfPageView("1.pdf")

                }
            }

            val fileName = "1.pdf"
            val pdf = openFileDescriptor(this, fileName)
            renderPdf(pdf)
        }
    }
}

fun openFileDescriptor(context: Context, fileName: String): ParcelFileDescriptor {
    val file = File(context.filesDir, fileName)
    return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
}

fun renderPdf(pdf: ParcelFileDescriptor) {
    val pdfRenderer = PdfRenderer(pdf)
    // Use pdfRenderer to render PDF pages
    // Remember to handle exceptions and close the PdfRenderer and ParcelFileDescriptor
}

@Composable
fun PdfPageView(fileName: String) {
    val context = LocalContext.current
    val pfd = openFileDescriptor(context, fileName)

    val pageBitmap = try {
        val pdfRenderer = PdfRenderer(pfd)
        val page = pdfRenderer.openPage(0) // Open the first page

        val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        page.close()
        pdfRenderer.close()
        bitmap
    } catch (e: IOException) {
        null
    } finally {
        closeDescriptor(pfd)
    }

    pageBitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = "PDF Page",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f) // Adjust aspect ratio as needed
        )
    }
}

fun closeDescriptor(pdf: ParcelFileDescriptor) {
    try {
        pdf.close()
    } catch (e: IOException) {
        // Handle exception
    }
}

@Composable
fun PDFReader(file: File) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val pdfReader = PdfRender(
            fileDescriptor = ParcelFileDescriptor.open(
                file,
                ParcelFileDescriptor.MODE_READ_ONLY
            )
        )
        LazyColumn {
            items(count = pdfRender.pageCount) {
                Image(
                    bitmap = pdfRender.pageLists[index].pageContent.asImageBitmap(),
                    contentDescription = "Psd page number: $index"
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PDFRendererTheme {
       PdfPageView(fileName = "1.pdf")
    }
}