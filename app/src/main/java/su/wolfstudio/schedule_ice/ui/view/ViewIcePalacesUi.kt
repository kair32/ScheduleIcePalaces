package su.wolfstudio.schedule_ice.ui.view

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import su.wolfstudio.schedule_ice.R
import su.wolfstudio.schedule_ice.ui.theme.SkateColor
import androidx.lifecycle.viewmodel.compose.viewModel
import su.wolfstudio.schedule_ice.navigation.Screen
import su.wolfstudio.schedule_ice.ui.base.ViewIcePalacesViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewIcePalacesUi(
    modifier: Modifier,
    navigation: NavController,
    palaceId: Long,
    isSchedule: Boolean,
    viewModel: ViewIcePalacesViewModel = viewModel(factory = ViewIcePalacesViewModelFactory(palaceId, isSchedule))
){
    val palace by viewModel.palace.collectAsState()
    Column(
        modifier = modifier
    ) {
        TopAppBar(
            title = {
                Text(text = palace.name)
            },
            colors = TopAppBarDefaults.topAppBarColors(SkateColor),
            navigationIcon = {
                IconButton(onClick = { navigation.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    navigation.navigate(Screen.AddSchedule.screenName + "/" + palace.id)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_calendar),
                        contentDescription = null
                    )
                }
            }
        )
        WebViewContent(
            if (viewModel.isSchedule) palace.urlSchedule
            else palace.url
        )
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewContent(url: String){
    var isLoad by remember { mutableStateOf(true) }
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    loadsImagesAutomatically = true
                }
                webChromeClient = object : WebChromeClient(){
                    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                        Log.d("tag","error WebViewContent = ${consoleMessage?.message()}")
                        return super.onConsoleMessage(consoleMessage)
                    }
                }
                webViewClient = object : WebViewClient(){

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        isLoad = true
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        isLoad = false
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        Log.d("tag","error WebViewContent = ${error?.description}")
                    }
                }
                loadUrl(url)
            }
        }, update = {
            it.loadUrl(url)
        })
        if (isLoad)
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
    }
}