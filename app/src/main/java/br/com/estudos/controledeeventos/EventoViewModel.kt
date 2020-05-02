package br.com.estudos.controledeeventos
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import br.com.estudos.controleeventos.database.Evento
import br.com.estudos.controleeventos.database.EventoDao
import kotlinx.coroutines.*

class EventoViewModel(
    val database: EventoDao,
    application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun onSave() {
        uiScope.launch {
            insert()
        }
    }

    private val events = database.getAllEventos()

    val eventoString = Transformations.map(events){ eventos ->
        val sb = StringBuilder()
        sb.apply {
            append("\n EVENTOS")
            eventos.forEach {
                append("\n")
                append("Nome evento: ${it.nome} | Local: ${it.local} | Quantidade de vagas: ${it.vagas}")
            }
        }


        sb.toString()
    }

    private suspend  fun insert() {
        Log.d("save", "salvar")
        withContext(Dispatchers.IO) {
            database.insert(Evento())
        }
    }

    private fun salvarEvento(view: View, evento: EventoDto) {
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(view.windowToken, 0)
        Log.d("nome", evento.nome)
//        Toast.makeText(this, evento.nome, Toast.LENGTH_LONG).show()
    }
}