@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package org.halo.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDeviceInput
import platform.AVFoundation.AVCaptureSession
import platform.AVFoundation.AVCaptureSessionPresetHigh
import platform.AVFoundation.AVCaptureVideoDataOutput
import platform.AVFoundation.AVCaptureVideoPreviewLayer
import platform.AVFoundation.AVLayerVideoGravity
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVMediaTypeVideo
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSError
import platform.UIKit.UIColor
import platform.UIKit.UIView
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

actual class CameraController {
    private val session: AVCaptureSession = AVCaptureSession().apply {
        sessionPreset = AVCaptureSessionPresetHigh
    }
    private val previewLayer: AVCaptureVideoPreviewLayer =
        AVCaptureVideoPreviewLayer().also { layer ->
            layer.session = session
            layer.videoGravity = AVLayerVideoGravityResizeAspectFill
        }
    internal val previewView: UIView = object : UIView(frame = CGRectMake(0.0, 0.0, 0.0, 0.0)) {
        override fun layoutSubviews() {
            super.layoutSubviews()
            previewLayer.frame = bounds
        }
    }.apply {
        layer.addSublayer(previewLayer)
        backgroundColor = UIColor.blackColor
    }

    private var started = false

    fun startSession() {
        if (started) return
        started = true

        dispatch_async(dispatch_get_main_queue()) {
            configureSession()
            session.startRunning()
        }
    }

    private fun configureSession() = memScoped {
        val device = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo) ?: return@memScoped

        val errorPtr = alloc<ObjCObjectVar<NSError?>>()
        val input = AVCaptureDeviceInput.deviceInputWithDevice(device, errorPtr.ptr) as? AVCaptureDeviceInput
        if (input != null && session.canAddInput(input)) {
            session.addInput(input)
        }

        val output = AVCaptureVideoDataOutput()
        if (session.canAddOutput(output)) {
            session.addOutput(output)
        }
    }
}

@Composable
actual fun rememberCameraController(): CameraController = remember { CameraController() }

@Composable
actual fun PlatformCameraPreview(
    controller: CameraController,
    modifier: Modifier
) {
    LaunchedEffect(controller) {
        controller.startSession()
    }

    UIKitView(
        factory = { controller.previewView },
        modifier = modifier
    )
}
