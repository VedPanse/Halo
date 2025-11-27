package org.halo.camera

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executor

actual class CameraController(private val context: Context) {
    private val cameraExecutor: Executor = ContextCompat.getMainExecutor(context)

    internal val previewView: PreviewView = PreviewView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        scaleType = PreviewView.ScaleType.FILL_CENTER
    }

    fun startCamera(lifecycleOwner: LifecycleOwner) {
        if (!hasCameraPermission()) {
            // TODO: request camera permission before attempting to start preview.
            Log.w("CameraController", "Camera permission not granted.")
            return
        }

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            try {
                cameraProvider.unbindAll()

                val preview = Preview.Builder().build().apply {
                    setSurfaceProvider(previewView.surfaceProvider)
                }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
            } catch (e: Exception) {
                Log.e("CameraController", "Unable to bind camera preview", e)
            }
        }, cameraExecutor)
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }
}

@Composable
actual fun rememberCameraController(): CameraController {
    val context = LocalContext.current
    return remember(context) { CameraController(context) }
}

@Composable
actual fun PlatformCameraPreview(
    controller: CameraController,
    modifier: Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        controller.startCamera(lifecycleOwner)
    }

    AndroidView(
        factory = { controller.previewView },
        modifier = modifier
    )
}
