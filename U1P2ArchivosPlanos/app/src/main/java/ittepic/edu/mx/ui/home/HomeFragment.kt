package ittepic.edu.mx.ui.home

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ittepic.edu.mx.CustomAdapter
import ittepic.edu.mx.Items
import ittepic.edu.mx.R
import ittepic.edu.mx.databinding.FragmentHomeBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter


class HomeFragment : Fragment(), CustomAdapter.OnItemClickListener {

    private val lista = generarLista(0)
   private val adapter = CustomAdapter(lista,this)
    private var _binding: FragmentHomeBinding? = null

    var vector = ArrayList<String>()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val recyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val buttoninsertar = binding.btnInsertar.setOnClickListener {
            val tareas = binding.tareas.text.toString()
            val idtarea = binding.tareaID.text.toString()
            var indice = 0
            indice = lista.size
         //  var indicemostrado = lista.size+1
            val nuevoitem = Items(
                R.drawable.todoicono, "Tarea: $indice",  tareas, idtarea
            )
            lista.add(nuevoitem)
            adapter.notifyItemInserted(indice)

            archivo()
            binding.tareas.setText("")
            binding.tareaID.setText("")

        }

        val buttonborrar = binding.btnBorrar.setOnClickListener {
            val indice = lista.size
//            lista.removeAt(indice)

            val inputborrar = EditText(requireContext())
            inputborrar.inputType = InputType.TYPE_CLASS_NUMBER
            AlertDialog.Builder(requireContext())
                .setTitle("Atencion")
                .setMessage("Tarea a borrar")
                .setView(inputborrar)
                .setPositiveButton("Borrar"){d, i->

                    lista.removeAt(inputborrar.text.toString().toInt())
                //    actualizarLista()
                    adapter.notifyItemRemoved(indice)

                    }

                .setNegativeButton("Cancelar"){d,i->d.cancel()}
                .show()

        }
        return root
    }



    private fun generarLista(size:Int): ArrayList<Items>{
        val list = ArrayList<Items>()
        for(i in 0 until size){
            val drawable = when (i%3){
                0-> R.drawable.todoicono
                1-> R.drawable.todoicono
                else-> R.drawable.todoicono
            }
            val item = Items(drawable, ""," ", "")
            list+=item

        }
       return list
    }
   private fun archivo(){

        val outputStreamWriter = OutputStreamWriter(
            requireContext().openFileOutput("archivo.txt", MODE_PRIVATE)
        )
        var cadena = binding.tareas.text.toString()+"\n" + binding.tareaID.text.toString()
        outputStreamWriter.write(cadena)
        outputStreamWriter.flush()
        outputStreamWriter.close()
        Toast.makeText(requireContext(), "Guardado",Toast.LENGTH_SHORT).show()
    }

    private fun leerArchivo(){
        try{
            val archivo = BufferedReader(InputStreamReader(requireContext().openFileInput("archivo.txt")))
            var cadena = ""

            archivo.forEachLine {
                cadena+= archivo.readLine()
            }

            archivo.close()

            AlertDialog.Builder(requireContext()).setMessage(cadena).show()
        }catch (e:Exception){
            AlertDialog.Builder(requireContext()).setMessage(e.message).show()

        }

    }

            override  fun onItemClick(position: Int) {
        Toast.makeText(requireContext(),"Seleccionaste la tarea",Toast.LENGTH_SHORT).show()
        val clickedItem =  lista[position]
        clickedItem.text1 = "Tarea $position entregada"
        adapter.notifyItemChanged(position)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}