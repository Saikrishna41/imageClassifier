package com.techskfortyonefortyone.imageclassification

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val img = findViewById<ImageView>(R.id.imageToLabel)
        val txtOutput : TextView = findViewById<TextView>(R.id.txtOutput)
        val btn: Button = findViewById(R.id.btnTest)
        val filename = "insect.jpg"
        val bitmap : Bitmap? = assetsToBitmap(filename)
        bitmap?.apply {
            img.setImageBitmap(this)
        }
        btn.setOnClickListener {
            val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
            val image = InputImage.fromBitmap(bitmap!!, 0)
            var outputText = ""
            labeler.process(image).addOnSuccessListener { labels ->
                for (label in labels) {
                    val text = label.text
                    val confidence = label.confidence
                    outputText += "$text : $confidence"
                }
                txtOutput.text = outputText
            }
                .addOnFailureListener {
                    e ->
                }
        }
    }

    fun Context.assetsToBitmap(filename: String): Bitmap? {
        return try {
            with(assets.open(filename)) {
                BitmapFactory.decodeStream(this)
            }
        } catch (e: Exception) {
            null
        }
    }
}