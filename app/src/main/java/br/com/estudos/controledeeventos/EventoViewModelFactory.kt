package br.com.estudos.controleeventos

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.estudos.controledeeventos.EventoViewModel
import br.com.estudos.controleeventos.database.EventoDao

class EventoViewModelFactory(
    private val dataSource: EventoDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventoViewModel::class.java)) {
            return EventoViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("View model não informado")
    }
}