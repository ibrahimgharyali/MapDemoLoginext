package com.demo.mapdemologinext

import android.R
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.demo.mapdemologinext.databinding.ActivitySignatureBinding
import com.demo.mapdemologinext.helper.Utils.showPathAndImage
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class SignatureActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignatureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivitySignatureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveSignature.setOnClickListener {
            val file = saveImageToSDCard(binding.signatureView.bitmap)

            showPathAndImage(this, file.toUri())
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.home) {
            onBackPressed()
            return true
        }
        return true
    }

    private fun saveImageToSDCard(bitmap: Bitmap): File {
        val directory = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!, "eSignature")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val file = File(directory, "${Date().time}.jpg")
        if (!file.exists()) {
            file.createNewFile()
            Log.d("path", file.toString())
            val fos: FileOutputStream?
            try {
                fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return file
    }
}