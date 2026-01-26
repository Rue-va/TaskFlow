package hr.algebra.to_doapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TaskEntity::class], version = 2)
@TypeConverters(LocalDateConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile private var instance: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "tasks.db"
                ).fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
    }
}