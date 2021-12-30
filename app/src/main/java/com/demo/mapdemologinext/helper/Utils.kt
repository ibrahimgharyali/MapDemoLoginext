package com.demo.mapdemologinext.helper

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import com.github.dhaval2404.imagepicker.util.IntentUtils

object Utils {
    fun showPathAndImage(context: Context, uri: Uri) {
        // Show path and open Image
        AlertDialog.Builder(context)
            .setTitle("Image saved")
            .setMessage( "path: $uri")
            .setPositiveButton("Ok") { _, _ ->
                context.startActivity(IntentUtils.getUriViewIntent(context, uri))
            }
            .show()
    }
}