package my.jcu.edu.au.cp3406.spacelearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import my.jcu.edu.au.cp3406.spacelearn.ui.theme.SpaceLearnTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)


        setContent {
            SpaceLearnTheme {
                SpaceLearnApp()
            }
        }
    }
}