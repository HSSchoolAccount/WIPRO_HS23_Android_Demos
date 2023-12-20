package com.example.psppdfkit_demo


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pspdfkit.PSPDFKit
import com.pspdfkit.configuration.activity.PdfActivityConfiguration
import com.pspdfkit.ui.PdfActivity

const val PICK_PDF_FILE = 1001

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        PSPDFKit.VERSION
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val uri = Uri.parse("file:///android_asset/1.pdf")
        val config = PdfActivityConfiguration.Builder(this).build()
        PdfActivity.showDocument(this, uri, config)

        //val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            //addCategory(Intent.CATEGORY_OPENABLE)
            //type = "application/pdf"
         //}

        //startActivityForResult(intent, PICK_PDF_FILE)
    }

    //override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
    // super.onActivityResult(requestCode, resultCode, resultData)
    // if(requestCode == PICK_PDF_FILE && resultCode == Activity.RESULT_OK){
    // resultData?.data?.also { uri ->
    // val documentUri = Uri.parse(uri.toString())
    // val config = PdfActivityConfiguration.Builder(this).build()
    //PdfActivity.showDocument(this, documentUri, config)
    // }
    // }
    // }

}

