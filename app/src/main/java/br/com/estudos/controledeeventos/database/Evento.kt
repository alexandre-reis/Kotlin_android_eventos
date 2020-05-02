package br.com.estudos.controleeventos.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "evento")
class Evento (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "name")
    var nome: String = "",

    @ColumnInfo(name = "description")
    var descricao: String = "",

    @ColumnInfo(name = "value")
    var valor: Double = 0.00,

    @ColumnInfo(name = "vacancies")
    var vagas: Int = 0,

    @ColumnInfo(name = "location")
    var local: String = "",

    @ColumnInfo(name = "date")
    var data: String = ""
)