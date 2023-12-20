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
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pspdfkit.configuration.activity.PdfActivityConfiguration
import com.pspdfkit.configuration.activity.UserInterfaceViewMode
import com.pspdfkit.jetpack.compose.DocumentView
import com.pspdfkit.jetpack.compose.ExperimentalPSPDFKitApi
import com.pspdfkit.jetpack.compose.rememberDocumentState
import com.pspdfkit.ui.PdfUiFragment
import com.pspdfkit.utils.getSupportParcelableExtra

/**
 * This example shows you how to use the [PdfUiFragment] to display PDFs in your activities.
 */
class JetpackComposeActivity : AppCompatActivity() {

    @OptIn(ExperimentalPSPDFKitApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uri = intent.getSupportParcelableExtra(EXTRA_URI, Uri::class.java)!!

        setContent {
            Scaffold { paddingValues ->
                val pdfActivityConfiguration = PdfActivityConfiguration
                    .Builder(this)
                    .setUserInterfaceViewMode(UserInterfaceViewMode.USER_INTERFACE_VIEW_MODE_HIDDEN)
                    .build()

                val documentState = rememberDocumentState(uri, pdfActivityConfiguration)
                var currentPage by remember(documentState.currentPage) { mutableStateOf(documentState.currentPage) }

                Box(Modifier.padding(paddingValues)) {
                    DocumentView(
                        documentState = documentState,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Loading via uri is also supported, and the default pdfActivityConfiguration is being used
                    // DocumentView(
                    //     documentUri = uri,
                    //     modifier = Modifier.fillMaxSize()
                    // )

                    // As well as loading images, using image URI
                    // ImageDocumentView(
                    //     imageUri = uri,
                    //     modifier = Modifier.fillMaxSize()
                    // )

                    // Or using documentState
                    // ImageDocumentView(
                    //     documentState = documentState,
                    //     modifier = Modifier.fillMaxSize()
                    // )

                    Button(
                        onClick = { currentPage = (currentPage + 1).coerceAtMost(17) },
                        modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)
                    ) {
                        Text("Go to next page")
                    }

                    Button(
                        onClick = { currentPage = (currentPage - 1).coerceAtLeast(0) },
                        modifier = Modifier.align(Alignment.BottomStart).padding(8.dp)
                    ) {
                        Text("Go to Previous page")
                    }
                }

                LaunchedEffect(currentPage) {
                    documentState.scrollToPage(currentPage)
                }
            }
        }
    }

    companion object {
        const val EXTRA_URI = "JetpackComposeActivity.DocumentUri"
    }
}
