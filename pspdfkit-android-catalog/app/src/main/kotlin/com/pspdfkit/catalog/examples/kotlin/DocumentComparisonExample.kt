/*
 *   Copyright © 2018-2023 PSPDFKit GmbH. All rights reserved.
 *
 *   The PSPDFKit Sample applications are licensed with a modified BSD license.
 *   Please see License for details. This notice may not be removed from this file.
 */

@file:Suppress("DEPRECATION")

package com.pspdfkit.catalog.examples.kotlin

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.net.Uri
import com.pspdfkit.annotations.BlendMode
import com.pspdfkit.catalog.PSPDFExample
import com.pspdfkit.catalog.R
import com.pspdfkit.catalog.examples.kotlin.activities.DocumentComparisonActivity
import com.pspdfkit.configuration.activity.PdfActivityConfiguration
import com.pspdfkit.configuration.activity.ThumbnailBarMode
import com.pspdfkit.configuration.sharing.ShareFeatures
import com.pspdfkit.document.DocumentSource
import com.pspdfkit.document.PdfDocumentLoader
import com.pspdfkit.document.processor.PagePdf
import com.pspdfkit.document.processor.PdfProcessor
import com.pspdfkit.document.processor.PdfProcessorTask
import com.pspdfkit.document.providers.AssetDataProvider
import com.pspdfkit.ui.DocumentDescriptor
import com.pspdfkit.ui.PdfActivityIntentBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.util.EnumSet

/**
 * This example shows how to use [PdfProcessor] for comparing PDF pages using a different
 * stroke color for each and blending these colored pages into a single document. Furthermore, it shows how to integrate the
 * {@link com.pspdfkit.document.processor.DocumentComparisonDialog} to align two documents for better comparison results.
 */
class DocumentComparisonExample(context: Context) : PSPDFExample(
    context.getString(R.string.documentComparisonExampleTitle),
    context.getString(R.string.documentComparisonExampleDescription)
) {

    @SuppressLint("CheckResult")
    override fun launchExample(context: Context, configuration: PdfActivityConfiguration.Builder) {
        // We'll show progress dialog while processing documents with PdfProcessor.
        val progressDialog = createProgressDialog(context)
        progressDialog.show()

        configuration.apply {
            // The example uses a custom activity with a floating action button to start document alignment.
            layout(R.layout.activity_document_comparison)
            // Disable features that are not important for this example.
            setThumbnailBarMode(ThumbnailBarMode.THUMBNAIL_BAR_MODE_NONE)
            disableAnnotationEditing()
            disableOutline()
            disableBookmarkEditing()
            hideSettingsMenu()
            disableBookmarkList()
            hideThumbnailGrid()
            disableAnnotationList()
            disableDocumentInfoView()
            disableSearch()
            disablePrinting()
            disableContentEditing()
            setEnabledShareFeatures(EnumSet.noneOf(ShareFeatures::class.java))
            invertColors(false)
        }

        // Comparison process consists from 2 steps:
        // 1. Color strokes from both documents in different colors.
        // 2. Merge 2 pages from these documents with colored strokes together.
        val processDocumentsForComparison = Single.defer {
            // Color strokes in the old document to GREEN.
            val greenDocumentUri = changeStrokeColorForDocumentFromAssets(
                context,
                "comparison/Document-A.pdf",
                oldDocumentColor,
                "Document-A"
            )

            // Color strokes in the new document to RED.
            val redDocumentUri = changeStrokeColorForDocumentFromAssets(
                context,
                "comparison/Document-B.pdf",
                newDocumentColor,
                "Document-B"
            )

            // Now generate document by merging both colored pages.
            val mergedDocumentUri = generateComparisonDocument(context, greenDocumentUri, redDocumentUri, "Comparison")

            // Return Single emitting triple of red, green and merged documents.
            return@defer Single.just(Triple(greenDocumentUri, redDocumentUri, mergedDocumentUri))
        }

        processDocumentsForComparison.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                // Hide the progress dialog when processing finishes.
                progressDialog.hide()
            }
            .subscribe(
                Consumer {
                    // Start the PdfActivity with all 3 documents loaded in tabs. This will show documents A and B, as well as the
                    // comparison result.
                    val documentTabs = arrayOf(
                        DocumentDescriptor.fromUri(it.first),
                        DocumentDescriptor.fromUri(it.second),
                        DocumentDescriptor.fromUri(it.third).apply {
                            setTitle("Comparison")
                        }
                    )
                    val intent = PdfActivityIntentBuilder.fromDocumentDescriptor(context, *documentTabs)
                        .configuration(configuration.build())
                        // Make the tab with comparison document visible after starting the activity.
                        .visibleDocument(2)
                        .activityClass(DocumentComparisonActivity::class.java)
                        .build()
                    context.startActivity(intent)
                }
            )
    }

    private fun changeStrokeColorForDocumentFromAssets(context: Context, documentAsset: String, color: Int, outputFileName: String): Uri {
        val outputFile = File(context.filesDir, "$outputFileName.pdf")

        val sourceDocument = PdfDocumentLoader.openDocument(context, DocumentSource(AssetDataProvider(documentAsset)))
        val task = PdfProcessorTask.fromDocument(sourceDocument).changeStrokeColorOnPage(0, color)
        PdfProcessor.processDocument(task, outputFile)

        return Uri.fromFile(outputFile)
    }

    private fun generateComparisonDocument(context: Context, oldDocumentUri: Uri, newDocumentUri: Uri, outputFileName: String): Uri {
        val outputFile = File(context.filesDir, "$outputFileName.pdf")

        val oldDocument = PdfDocumentLoader.openDocument(context, oldDocumentUri)
        val task = PdfProcessorTask.fromDocument(oldDocument)
            .mergePage(PagePdf(context, newDocumentUri), 0, BlendMode.DARKEN)
        PdfProcessor.processDocument(task, outputFile)

        return Uri.fromFile(outputFile)
    }

    private fun createProgressDialog(context: Context): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Comparing documents")
        progressDialog.setProgressNumberFormat(null)
        progressDialog.setProgressPercentFormat(null)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        return progressDialog
    }

    companion object {
        /** Tint color used for the old document. */
        val oldDocumentColor = Color.parseColor("#F5281B")

        /** Tint color used for the new document. */
        val newDocumentColor = Color.parseColor("#31C1FF")
    }
}
