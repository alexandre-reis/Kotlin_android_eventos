package br.com.estudos.controleeventos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Evento::class], version = 1, exportSchema = false)
abstract class EventoDatabase : RoomDatabase() {

    abstract val eventoDao: EventoDao

    companion object {

        @Volatile
        private var INSTANCE: EventoDatabase? = null

        fun getInstance(context: Context): EventoDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EventoDatabase::class.java,
                        "evento_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance

            }
        }
    }
}