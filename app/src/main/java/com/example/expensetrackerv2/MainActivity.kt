package com.example.expensetrackerv2

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.expensetrackerv2.navigation.NavHostComposable
import com.example.expensetrackerv2.ui.theme.ExpenseTrackerV2Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var navControllerState: MutableState<NavHostController?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerV2Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.surface) {
                    val navController = rememberNavController()
                    navControllerState.value = navController

                    NavHostComposable(navController = navController)
                }
            }
        }
    }
}
