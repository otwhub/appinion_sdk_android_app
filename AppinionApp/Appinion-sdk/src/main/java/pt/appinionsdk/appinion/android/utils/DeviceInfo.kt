package pt.appinionsdk.appinion.android.utils

import android.os.Build

data class DeviceInfo(val manufacturer: String,
                      val model: String,
                      val osVersion: String,
                      val sdkInt: Int)
fun getDeviceInfo(): DeviceInfo {
    return DeviceInfo(
        manufacturer = Build.MANUFACTURER,
        model = Build.MODEL,
        osVersion = Build.VERSION.RELEASE,
        sdkInt = Build.VERSION.SDK_INT
    )
}