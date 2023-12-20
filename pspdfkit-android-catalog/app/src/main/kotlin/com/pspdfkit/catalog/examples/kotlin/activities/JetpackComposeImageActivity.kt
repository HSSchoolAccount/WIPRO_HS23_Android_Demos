/*
 *   Copyright © 2019-2023 PSPDFKit GmbH. All rights reserved.
 *
 *   The PSPDFKit Sample applications are licensed with a modified BSD license.
 *   Please see License for details. This notice may not be removed from this file.
 */

package com.pspdfkit.catalog.examples.kotlin.activities

import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.pspdfkit.document.ImageDocumentLoader
import com.pspdfkit.jetpack.compose.ExperimentalPSPDFKitApi
import com.pspdfkit.jetpack.compose.ImageDocumentView
import com.pspdfkit.jetpack.compose.rememberDocumentState
import com.pspdfkit.ui.PdfUiFragment
import com.pspdfkit.utils.getSupportParcelableExtra

/**
 * This example shows you how to use the [PdfUiFragment] to display PDFs in your activities.
 */
class JetpackComposeImageActivity : AppCompatActivity() {

    @OptIn(ExperimentalPSPDFKitApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uri = intent.getSupportParcelableExtra(EXTRA_URI, Uri::class.java)!!

        setContent {
            Scaffold { paddingValues ->
                val imageDocumentConfiguration = ImageDocumentLoader.getDefaultImageDocumentActivityConfiguration(this)

                val documentState = rememberDocumentState(uri, imageDocumentConfiguration)
                val currentPage by remember(documentState.currentPage) { mutableStateOf(documentState.currentPage) }

                Box(Modifier.padding(paddingValues)) {
                    ImageDocumentView(
                        documentState = documentState,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Loading via uri is also supported, and the default pdfActivityConfiguration is being used
                    // ImageDocumentView(
                    //     imageUri = uri,
                    //     modifier = Modifier.fillMaxSize()
                    // )
                }

                LaunchedEffect(currentPage) {
                    documentState.scrollToPage(currentPage)
                }
            }
        }
    }

    companion object {
        const val EXTRA_URI = "JetpackComposeImageActivity.DocumentUri"
    }
}
