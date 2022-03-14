package pimienta.julian.mydigimind.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.recordatorio.view.*
import pimienta.julian.mydigimind.Carrito
import pimienta.julian.mydigimind.R
import pimienta.julian.mydigimind.Recordatorio
import pimienta.julian.mydigimind.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    var carrito: Carrito = Carrito()

    private var _binding: FragmentHomeBinding? = null

    private lateinit var homeViewModel: HomeViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, {})

        loadReminders()

        var remindersAdapter: recordatorioAdapter = recordatorioAdapter(this.requireContext(), carrito.recordatorios)

        var gw = root.findViewById(R.id.gridviewHome) as GridView

        gw.adapter = remindersAdapter

        return root
    }

    fun loadReminders() {
        for (i in 0..8) {
            carrito.agregar(Recordatorio("Sunday", "16:00", "Work"))
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
        return view
    }

}