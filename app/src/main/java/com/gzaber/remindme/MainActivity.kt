package com.gzaber.remindme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gzaber.remindme.ui.addedit.AddEditScreen
import com.gzaber.remindme.ui.theme.RemindMeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RemindMeTheme {
                AddEditScreen(title = R.string.add_edit_app_bar_create_title)
            }
        }
    }
}