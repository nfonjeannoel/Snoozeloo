package com.camgist.snoozeloo.utils

import android.util.Log
import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun rememberKeyboardVisibility(): State<Boolean> {
    val keyboardState = remember { mutableStateOf(false) }

    val view = LocalView.current
    val viewTreeObserver = view.viewTreeObserver

    DisposableEffect(viewTreeObserver) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            keyboardState.value = ViewCompat
                .getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
        }
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            /* Try to fix crash:
            https://console.firebase.google.com/u/2/project/yayolauncher/crashlytics/app/android:io.bleta.simplelauncher/issues/248bcb734b5634fb393c3a673e5fc6df?time=1725062400000:1727395199000&versions=3.6.1%20Google%20Play%20(3060100)&sessionEventKey=66F5483E032700010E66AC4F08C00F94_1997405698720104408
             */
            try {
                if (viewTreeObserver.isAlive)
                    viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
            } catch (e: Exception) {
                Log.w("rememberKeyboardVisibility", "Error on removeOnGlobalLayoutListener", e)
                e.printStackTrace()
            }
        }
    }

    return keyboardState
}