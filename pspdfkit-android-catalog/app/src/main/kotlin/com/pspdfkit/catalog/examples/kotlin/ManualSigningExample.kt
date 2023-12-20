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
import com.pspdfkit.signatures.DigitalSignatureMetadata
import com.pspdfkit.signatures.SignerOptions
import com.pspdfkit.signatures.SigningManager
import com.pspdfkit.signatures.TimestampData
import com.pspdfkit.signatures.getX509Certificates
import com.pspdfkit.ui.PdfActivityIntentBuilder
import com.pspdfkit.utils.PdfLog
import java.io.File
import java.io.IOException
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.Signature

/**
 * An example showing how to leverage customSigning functionality in [SigningManager] to digitally sign document manually.
 * */
class ManualSigningExample(context: Context) : PSPDFExample(context, R.string.manualSigningExampleTitle, R.string.manualSigningExampleDescription) {

    override fun launchExample(context: Context, configuration: PdfActivityConfiguration.Builder) {
        val assetName = "Form_example.pdf"

        val unsignedDocument = PdfDocumentLoader.openDocument(context, DocumentSource(AssetDataProvider(assetName)))
        val key = getPrivateKeyEntry(context)
        val signatureFormFields = unsignedDocument.documentSignatureInfo.signatureFormFields
        val outputFile = File(context.filesDir, "signedDocument.pdf")

        /** [SignerOptions] contains all the required configuration for [SigningManager]*/
        val signerOptions = SignerOptions.Builder(signatureFormFields[0], Uri.fromFile(outputFile))
            .setCertificates(key.getX509Certificates())
            .setSignatureMetadata(DigitalSignatureMetadata(timestampData = TimestampData("https://freetsa.org/tsr")))
            .build()

        /** [SignerOptions] contains all the required configuration for [SigningManager]*/
        SigningManager.signDocument(
            context = context,
            signerOptions = signerOptions,
            type = digitalSignatureType,
            customSigning = { data, hashAlgorithm ->
                /** Here we are manually signing ByteArray with provided hashAlgorithm and private key, this is a mandatory step if
                 * customer doesn't provide private key in [SignerOptions] */
                data.signData(key, hashAlgorithm)
            },
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

    fun ByteArray.signData(privateKey: KeyStore.PrivateKeyEntry, hashAlgorithm: String?): ByteArray = try {
        /** We are using the [Signature] class from java.security package, to sign the byte arrays.*/

        val key = privateKey.privateKey ?: throw RuntimeException("Private key in needed for signing")

        /** The signature algorithm can be, among others, the NIST standard DSA, using DSA and SHA-256.
         *  The DSA algorithm using the SHA-256 message digest algorithm can be specified as SHA256withDSA.
         *  In the case of RSA the signing algorithm could be specified as, for example, SHA256withRSA.
         *  The algorithm name must be specified, as there is no default.
         *
         *  Here 'hashAlgorithm' is SHA256 and 'key.algorithm' is RSA
         *  providing algorithm as SHA256withRSA
         *  for more details visit https://docs.oracle.com/javase/8/docs/api/java/security/Signature.html
         **/
        val algorithm = "${hashAlgorithm}with${key.algorithm}"

        Signature.getInstance(algorithm).run {
            initSign(key) // Initialize this object for signing.
            update(this@signData) // Updates the data to be signed or verified, using the specified array of bytes
            sign() // Returns the signature bytes of all the data updated
        }
    } catch (e: NoSuchAlgorithmException) {
        throw RuntimeException(e)
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
