/*
 *   Copyright © 2020-2023 PSPDFKit GmbH. All rights reserved.
 *
 *   The PSPDFKit Sample applications are licensed with a modified BSD license.
 *   Please see License for details. This notice may not be removed from this file.
 */

package com.pspdfkit.catalog.examples.kotlin

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.pspdfkit.catalog.PSPDFExample
import com.pspdfkit.catalog.R
import com.pspdfkit.configuration.activity.PdfActivityConfiguration
import com.pspdfkit.document.DocumentSource
import com.pspdfkit.document.PdfDocumentLoader
import com.pspdfkit.document.providers.AssetDataProvider
import com.pspdfkit.signatures.SignerOptions
import com.pspdfkit.signatures.SigningManager
import com.pspdfkit.ui.PdfActivityIntentBuilder
import com.pspdfkit.utils.PdfLog
import java.io.File
import java.io.IOException
import java.security.GeneralSecurityException
import java.security.KeyStore

/**
 * An example that shows how to digitally sign a PDF document using [SigningManager].
 * This is a Simple implementation where user provides Private key in [SignerOptions].
 */
class DigitalSignatureExample(context: Context) : PSPDFExample(context, R.string.digitalSignatureExampleTitle, R.string.digitalSignatureExampleDescription) {

    override fun launchExample(context: Context, configuration: PdfActivityConfiguration.Builder) {
        val assetName = "Form_example.pdf"

        val unsignedDocument = PdfDocumentLoader.openDocument(context, DocumentSource(AssetDataProvider(assetName)))
        val key = getPrivateKeyEntry(context)
        val signatureFormFields = unsignedDocument.documentSignatureInfo.signatureFormFields
        val outputFile = File(context.filesDir, "signedDocument.pdf")
        val signerOptions = SignerOptions.Builder(signatureFormFields[0], Uri.fromFile(outputFile))
            .setPrivateKey(key)
            .build()

        /** [SignerOptions] contains all the required configuration for [SigningManager]*/
        SigningManager.signDocument(
            context = context,
            signerOptions = signerOptions,
            type = digitalSignatureType,
            onFailure = { e ->
                Toast.makeText(context, "Error launching example. See logcat for details.", Toast.LENGTH_SHORT).show()
                PdfLog.e("ContainedSignatures", e, "Error while launching example.")
            }
        ) {
            val signedDocument = Uri.fromFile(outputFile)
            // Load and show the signed document.
            val intent = PdfActivityIntentBuilder.fromUri(context, signedDocument)
                .configuration(configuration.build())
                .build()
            context.startActivity(intent)
        }
    }

    /**
     * Loads the [KeyStore.PrivateKeyEntry] that will be used by our [SigningManager].
     */
    @Throws(IOException::class, GeneralSecurityException::class)
    private fun getPrivateKeyEntry(context: Context): KeyStore.PrivateKeyEntry {
        val keystoreFile = context.assets.open("JohnAppleseed.p12")
        // Inside a p12 we have both the certificate and private key used for signing. We just need the certificate here.
        // Proper signatures should have a root CA approved certificate making this step unnecessary.
        return SigningManager.loadPrivateKeyPairFromStream(keystoreFile, "test", null, null)
    }
}
