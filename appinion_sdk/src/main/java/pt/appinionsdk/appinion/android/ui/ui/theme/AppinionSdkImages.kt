package pt.appinionsdk.appinion.android.ui.ui.theme

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

data class AppinionSdkImages(val sdkFirstImage: Int, val sdkSecondImage: Int, val sdkThridImage: Int)

val LocalSdkImages = staticCompositionLocalOf{ DefaultAppinionImages }

val DefaultAppinionImages = AppinionSdkImages(
    sdkFirstImage = pt.appinionsdk.appinion.android.R.drawable.sdk_first,
    sdkSecondImage = pt.appinionsdk.appinion.android.R.drawable.sdk_second,
    sdkThridImage = pt.appinionsdk.appinion.android.R.drawable.sdk_third
)


internal fun getAppIcon(context: Context): ImageBitmap? {
    val packageManager = context.packageManager
    return try {
        // 1. Obtém o Drawable do ícone da App hospedeira
        val drawable: Drawable = packageManager.getApplicationIcon(context.packageName)

        // 2. Se já for um Bitmap, basta converter
        if (drawable is BitmapDrawable) {
            return drawable.bitmap.asImageBitmap()
        }

        // 3. Se for AdaptiveIcon (XML), desenhamos num Canvas para criar um Bitmap
        val width = if (drawable.intrinsicWidth > 0) drawable.intrinsicWidth else 512
        val height = if (drawable.intrinsicHeight > 0) drawable.intrinsicHeight else 512

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        bitmap.asImageBitmap()
    } catch (e: Exception) {
        null // Fallback caso algo falhe
    }
}
