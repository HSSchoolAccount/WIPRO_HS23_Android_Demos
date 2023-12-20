package com.example.mediumpdf

import android.os.Bundle
import android.os.ParcelFileDescriptor
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mediumpdf.ui.theme.MediumPDFTheme
import java.io.File
import android.content.Context
import android.graphics.pdf.PdfRenderer.Page
import java.io.IOException
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MediumPDFTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uri = Uri.parse("//android_asset/1.pdf")
                    val file = uri.toFile()
                    PDFReader2(file)
                }
            }
        }
    }
}





@Composable
fun PDFReader2(file: File) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val pdfRender = PdfRender(
            fileDescriptor = ParcelFileDescriptor.open(
                file,
                ParcelFileDescriptor.MODE_READ_ONLY
            )
        )
        DisposableEffect(key1 = Unit) {
            onDispose {
                pdfRender.close()
            }
        }
        LazyColumn {
            items(count = pdfRender.pageCount) { index ->
                BoxWithConstraints(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val page = pdfRender.pageLists[index]
                    DisposableEffect(key1 = Unit) {
                        page.load()
                        onDispose {
                            page.recycle()
                        }
                    }
                    page.pageContent.collectAsState().value?.asImageBitmap()?.let {
                        Image(
                            bitmap = it,
                            contentDescription = "Pdf page number: $index",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                    } ?: Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(
                                page.heightByWidth(constraints.maxWidth).dp
                            )
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MediumPDFTheme {
        val uri = Uri.parse("//android_asset/1.pdf")
        val file = uri.toFile()
        PDFReader2(file)
    }
}