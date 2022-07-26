package com.example.nojotoui.activity

import android.R.attr.bitmap
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.nojotoui.databinding.ActivityCameraBinding
import com.google.common.util.concurrent.ListenableFuture
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer


class CameraActivity : AppCompatActivity() {
    var binding: ActivityCameraBinding? = null
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    var imageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            binding?.viewFinder?.post{
                binding?.ivCapture?.visibility = View.VISIBLE
                bindPreview(cameraProvider)
            }
        }, ContextCompat.getMainExecutor(this))

        binding?.ivCapture?.setOnClickListener {
            val file = File(filesDir, "test.png")
            imageCapture?.takePicture(ContextCompat.getMainExecutor(this),object : ImageCapture.OnImageCapturedCallback(){
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    //Log.d(TAG, "onCaptureSuccess: ")
                    val imageBitmap = imageProxyToBitmap(image)
                    imageBitmap?.let {
                        binding?.ivCapturedImage?.visibility = View.VISIBLE
                        binding?.ivCapturedImage?.setImageBitmap(imageBitmap)
                        val bos = ByteArrayOutputStream()
                        it.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
                        val bitmapdata: ByteArray = bos.toByteArray()
                        val fos = FileOutputStream(file)
                        fos.write(bitmapdata)
                        fos.flush()
                        fos.close()
                        startActivity(Intent(this@CameraActivity, UploadActivity::class.java).putExtra("FILE", file.toString()))
                    }


                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Toast.makeText(this@CameraActivity, "Error in capturing photo", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider?) {
        cameraProvider?.unbindAll()
        val preview: Preview = Preview.Builder()
            .build()

        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
            .build()

        preview.setSurfaceProvider(binding?.viewFinder?.surfaceProvider)

        imageCapture = ImageCapture.Builder()
            .setTargetRotation(binding?.viewFinder?.display?.rotation!!)
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()

        val camera = cameraProvider?.bindToLifecycle(
            this as LifecycleOwner,
            cameraSelector,
            imageCapture,
            preview
        )
    }


    private fun imageProxyToBitmap(image: ImageProxy): Bitmap? {
        val planeProxy = image.planes[0]
        val buffer: ByteBuffer = planeProxy.buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}