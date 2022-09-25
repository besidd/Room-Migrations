package com.example.roommigrations.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [User::class, School::class], version = 4,
autoMigrations = [
    AutoMigration(from = 1, to = 2),
    AutoMigration(from = 2, to = 3, spec = UserDataBase.Migration2To3::class)
]
)
abstract class UserDataBase: RoomDatabase() {
    abstract val dao: UserDao

    //    @DeleteColumn @DeleteTable : for references
    @RenameColumn(tableName = "User", fromColumnName = "createdAt", toColumnName = "created")
    class Migration2To3: AutoMigrationSpec

    companion object {
        val migration3To4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS school (name CHAR NOT NULL PRIMARY KEY)")
            }
        }
    }
}