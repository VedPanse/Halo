package org.halo.camera

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CameraPreview(modifier: Modifier = Modifier) {
    val controller = rememberCameraController()
    PlatformCameraPreview(controller = controller, modifier = modifier)
}

@Composable
fun CameraScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(modifier = Modifier.fillMaxSize())
        // TODO: overlay AR content here when available
    }
}

@Composable
expect fun rememberCameraController(): CameraController

@Composable
internal expect fun PlatformCameraPreview(
    controller: CameraController,
    modifier: Modifier = Modifier
)
