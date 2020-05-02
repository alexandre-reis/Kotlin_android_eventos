package br.com.estudos.controleeventos.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EventoDao {

    @Insert
    fun insert(event: Evento)

    @Update
    fun update(event: Evento)

    @Query("select * from evento where id = :key")
    fun get(key: Long): Evento

    @Delete
    fun delete(event: Evento)

    @Query("delete from evento")
    fun clear()

    @Query("SELECT * FROM evento ORDER BY id DESC")
    fun getAllEventos(): LiveData<List<Evento>>

    @Query("SELECT * from evento WHERE id = :key")
    fun getEventoById(key: Long): LiveData<Evento>

}
