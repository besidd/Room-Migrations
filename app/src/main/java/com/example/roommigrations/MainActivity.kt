package com.example.roommigrations

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.roommigrations.room.School
import com.example.roommigrations.room.UserDataBase
import com.example.roommigrations.ui.theme.RoomMigrationsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            UserDataBase::class.java,
            "users.db"
        )
            .addMigrations(UserDataBase.migration3To4)
            .build()

        (0..10).forEach {
            lifecycleScope.launchWhenCreated {
                db.dao.upsertSchool(
                    School(
                    "School$it"
                )
                )
            }
        }

        lifecycleScope.launch {
            db.dao.getAllSchools().forEach(::println)
        }

        setContent {
            RoomMigrationsTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text(text = "No Ui", modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}
