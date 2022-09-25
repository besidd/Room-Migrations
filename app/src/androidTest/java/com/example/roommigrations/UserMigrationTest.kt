package com.example.roommigrations

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.roommigrations.room.UserDataBase
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val DB_NAME = "test.db"

@RunWith(AndroidJUnit4::class)
class UserMigrationTest {

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        UserDataBase::class.java,
        listOf(UserDataBase.Migration2To3()),
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
     fun migration_1_2_containsCorrectData() {
          var db = helper.createDatabase(DB_NAME, 1).apply {
              execSQL("INSERT INTO user VALUES('test@test.com', 'Siddharth')")
              close()
          }

            db = helper.runMigrationsAndValidate(DB_NAME, 2, true)

        db.query("SELECT * FROM user").apply {
            assertThat(moveToFirst()).isTrue()
            assertThat(getLong(getColumnIndex("createdAt"))).isEqualTo(0)
        }
     }

    @Test
    fun testAllMigrations() {
        helper.createDatabase(DB_NAME, 1).apply {
            close()
        }

        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            UserDataBase::class.java,
            DB_NAME
        ).addMigrations(UserDataBase.migration3To4).build().apply {
            openHelper.writableDatabase.close()
        }



    }

}