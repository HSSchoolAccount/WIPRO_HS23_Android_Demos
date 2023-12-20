/*
 *   Copyright © 2023 PSPDFKit GmbH. All rights reserved.
 *
 *   The PSPDFKit Sample applications are licensed with a modified BSD license.
 *   Please see License for details. This notice may not be removed from this file.
 */
package com.pspdfkit.catalog.examples.kotlin.instant.activities

import android.Manifest.permission
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.pspdfkit.catalog.R
import com.pspdfkit.catalog.utils.Utils
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.util.Arrays

/**
 * Provides live QR code scanning. Start this activity for result and it will retrieve first
 * recognized encoded data with the BARCODE_ENCODED_KEY. Requires Manifest.permission.CAMERA.
 */
class BarcodeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    /** Indicates that we are waiting for onRequestPermissionsResult.  */
    private var waitingForPermissions = false
    private val scannerView by lazy { ZXingScannerView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(scannerView)
        initScannerView()
        if (checkRequiredPermissions()) {
            waitingForPermissions = false
        }
    }

    private fun initScannerView() {
        scannerView.setFormats(Arrays.asList(BarcodeFormat.AZTEC, BarcodeFormat.QR_CODE))
        scannerView.setLaserEnabled(false)
        scannerView.setMaskColor(Color.TRANSPARENT)
        scannerView.setBorderColor(Utils.getThemeColor(this, R.attr.colorPrimary, R.color.color_primary))
        scannerView.setSquareViewFinder(true)
        scannerView.setBorderLineLength(Utils.dpToPx(this, BORDER_LINE_LENGTH_DP))
        scannerView.setBorderStrokeWidth(Utils.dpToPx(this, BORDER_STROKE_WIDTH_DP))
        scannerView.setIsBorderCornerRounded(true)
        scannerView.setBorderCornerRadius(Utils.dpToPx(this, BORDER_CORNER_RADIUS_DP))
    }

    /**
     * Returns True when permission required for handling request with code `requestCode` has
     * already been granted.
     */
    private fun checkRequiredPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            ContextCompat.checkSelfPermission(this, permission.CAMERA) == PackageManager.PERMISSION_DENIED
        ) {
            if (!waitingForPermissions) {
                waitingForPermissions = true
                requestPermissions(arrayOf(permission.CAMERA), ASK_FOR_CAMERA_PERMISSION_REQUEST_CODE)
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        waitingForPermissions = false
        if (requestCode == ASK_FOR_CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("Camera Permission Granted")
            } else {
                showToast("Camera Permission Denied")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(permission.CAMERA)) {
                        showPermissionRequiredDialog()
                    } else {
                        setResultCanceledAndFinish()
                    }
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun showPermissionRequiredDialog() {
        AlertDialog.Builder(this)
            .setMessage("You need to grant Camera Permission to scan QR code")
            .setPositiveButton(
                getString(android.R.string.ok)
            ) { _: DialogInterface?, _: Int ->
                requestPermissions(
                    arrayOf(permission.CAMERA),
                    ASK_FOR_CAMERA_PERMISSION_REQUEST_CODE
                )
            }
            .setNegativeButton(android.R.string.cancel) { _: DialogInterface?, _: Int -> setResultCanceledAndFinish() }
            .create()
            .show()
    }

    private fun showToast(s: String) {
        Toast.makeText(applicationContext, s, Toast.LENGTH_LONG).show()
    }

    private fun setResultCanceledAndFinish() {
        setResult(RESULT_CANCELED, Intent())
        finish()
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onStop() {
        super.onStop()
        scannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result) {
        val returnIntent = Intent()
        returnIntent.putExtra(BARCODE_ENCODED_KEY, rawResult.text)
        setResult(RESULT_OK, returnIntent)
        finish()
    }

    companion object {
        /**
         * This code should be used for receiving result of ActivityCompat.requestPermissions for
         * Manifest.permission.CAMERA.
         */
        private const val ASK_FOR_CAMERA_PERMISSION_REQUEST_CODE = 1
        const val BARCODE_RESULT_REQUEST_CODE = 2
        const val BARCODE_ENCODED_KEY = "BARCODE_ENCODED_KEY"

        /** UI parameters for barcode scanner input window (to hover over the QR code)  */
        private const val BORDER_LINE_LENGTH_DP = 60
        private const val BORDER_STROKE_WIDTH_DP = 5
        private const val BORDER_CORNER_RADIUS_DP = 5
    }
}
