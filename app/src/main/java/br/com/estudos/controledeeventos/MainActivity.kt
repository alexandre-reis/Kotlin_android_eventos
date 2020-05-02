package br.com.estudos.controledeeventos

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProviders
import br.com.estudos.controledeeventos.databinding.ActivityMainBinding
import br.com.estudos.controleeventos.EventoViewModelFactory
import br.com.estudos.controleeventos.database.Evento
import br.com.estudos.controleeventos.database.EventoDao
import br.com.estudos.controleeventos.database.EventoDatabase
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val sb = StringBuilder("Eventos")

    var eventoDto: EventoDto = EventoDto()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.eventoDto = eventoDto;

        val application = requireNotNull(this).application
        val dataSource = EventoDatabase.getInstance(application).eventoDao

        val viewModelFactory = EventoViewModelFactory(dataSource, application)
        var eventoViewModel = ViewModelProviders.of(this, viewModelFactory).get(EventoViewModel::class.java)

        val events = eventoViewModel.database.getAllEventos()

        Transformations.map(events) { eventos ->
                sb.apply {
                    eventos.forEach {
                        append("\n")
                        Log.d("eventos", "Nome evento> ${it.nome} + Local> ${it.local}")
                    }
                }
            }

        binding.eventoString = sb.toString()

        binding.eventoMVVM = eventoViewModel

        binding.btnSalvar.setOnClickListener {
            uiScope.launch {
                salvarEvento(it, eventoDto)
            }
        }

        binding.btnBuscar.setOnClickListener {
            uiScope.launch {
                getAllEventos()
            }
        }
    }

    private suspend fun getAllEventos() {
        Log.d("aaa", sb.toString())
        withContext(Dispatchers.IO) {

//            val events =  binding.eventoMVVM.database.getAllEventos()
//            val eventos = Transformations.map(events) { eventos ->
//                val sb = StringBuilder()
//                sb.apply {
//                    append("\n EVENTOS")
//                    eventos.forEach {
//                        append("\n")
//                        Log.d("eventos", "Nome evento> ${it.nome} + Local> ${it.local}")
//                    }
//                }
//                sb.toString()
//            }
        }



    }

    private suspend fun salvarEvento(view: View, dto: EventoDto) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        Log.d("nome", dto.nome)
        val evento = Evento()
        evento.nome = dto.nome
        evento.descricao = dto.descricao
//        evento.vagas = Integer.valueOf(dto.vagas)
        withContext(Dispatchers.IO) {
            binding.eventoMVVM?.database?.insert(evento)
        }

        Toast.makeText(this, evento.nome, Toast.LENGTH_LONG).show()
    }


}
