package com.example.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.canvas.ui.theme.CanvasTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CanvasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    //MyCustomDrawing("/assets/1.bmp")
                    loadImage()
                }
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

@Composable
fun MyCustomDrawing(assetPath: String) {
    val context = LocalContext.current
    val painter = AssetPainter(assetPath, context)

    Image(painter = painter, contentDescription = "My Image")
}

@Composable
fun loadImage(){
    val dogImage = ImageBitmap.imageResource(id = R.drawable.erste_etage)

    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        drawImage(dogImage)
    })
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CanvasTheme {
        //Greeting("Android")
        //MyCustomDrawing("/assets/1.bmp")
        loadImage()
    }
}