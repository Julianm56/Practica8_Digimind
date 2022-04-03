package pimienta.julian.mydigimind.ui.home

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.recordatorio.view.*
import pimienta.julian.mydigimind.Carrito
import pimienta.julian.mydigimind.R
import pimienta.julian.mydigimind.Recordatorio
import pimienta.julian.mydigimind.databinding.FragmentHomeBinding
import pimienta.julian.mydigimind.ui.AdaptadorRecordatorio


class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null

    private lateinit var homeViewModel: HomeViewModel

    private val binding get() = _binding!!


    companion object {
        var carrito: Carrito = Carrito()
        lateinit var remindersAdapter: recordatorioAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, {})


       // loadTestReminders()
        cargar_tareas()

         remindersAdapter = recordatorioAdapter(this.requireContext(), carrito.recordatorios)

        var gw = root.findViewById(R.id.gridviewHome) as GridView

        gw.adapter = remindersAdapter


        return root
    }
//    fun loadTestReminders() {
//        for (i in 0..8) {
//            carrito.agregar(Recordatorio("Sunday", "16:00", "Work"))
//        }
//    }


fun cargar_tareas(){
    val preferencias = context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
    val gson : Gson = Gson()

    var json = preferencias?.getString("Reminders",null)

    val type = object : TypeToken<Carrito?>(){}.type

    if (json == null){
        carrito =  Carrito()
    }else{
        carrito = gson.fromJson(json,type)
    }

}

override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}

}

class recordatorioAdapter: BaseAdapter {

    var reminders = ArrayList<Recordatorio>()
    var context: Context? = null

    constructor(context: Context, movies: ArrayList<Recordatorio>) {
        this.context = context
        this.reminders = movies
    }

    override fun getCount(): Int {
        return reminders.size
    }

    override fun getItem(p0: Int): Any {
        return reminders[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var reminder = reminders[p0]
        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = inflator.inflate(R.layout.recordatorio, null)
        view.textDiasRecordatorio.setText(reminder.dias)
        view.txtNombreRecordatorio.setText(reminder.nombre)
        view.textTiempoRecordatorio.setText(reminder.tiempo)


        view.setOnClickListener {
            elimnar( reminder as Recordatorio)
        }

        return view
    }

    fun elimnar(recordatorio: Recordatorio) {
        val alertDialog: AlertDialog? = context?.let{
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok_button,
                    DialogInterface.OnClickListener{dialog, id->
                        HomeFragment.carrito.remove(recordatorio)
                        guardar_json()
                        HomeFragment.remindersAdapter.notifyDataSetChanged()
                        Toast.makeText(context, R.string.msg_deleted, Toast.LENGTH_SHORT).show()

                    })
                setNegativeButton(R.string.cancel_button,
                    DialogInterface.OnClickListener{dialog, id->
                        //User cancelled
                    })
            }

            builder?.setMessage(R.string.msg).setTitle(R.string.title)

            builder.create()
        }
        alertDialog?.show()
    }


    fun guardar_json(){
        val preferencias = context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val editor = preferencias?.edit()
        val gson : Gson = Gson()

        var json = gson.toJson(HomeFragment.carrito)

        editor?.putString("Reminders",json)

        editor?.apply()

    }


}