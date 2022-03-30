package pimienta.julian.mydigimind.ui.dashboard

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import pimienta.julian.mydigimind.R
import pimienta.julian.mydigimind.Recordatorio
import pimienta.julian.mydigimind.databinding.FragmentDashboardBinding
import pimienta.julian.mydigimind.ui.home.HomeFragment
import java.text.SimpleDateFormat
import java.util.*


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    private var _binding: FragmentDashboardBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       dashboardViewModel =
           ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard,container,false)
        dashboardViewModel.text.observe(viewLifecycleOwner,{})


        val btnTime = root.findViewById(R.id.btn_time) as Button
        val btnSave = root.findViewById(R.id.btn_save) as Button


        btnTime.setOnClickListener {

            set_time(root)
        }

        btnSave.setOnClickListener {
          guardar(root)
        }




        return root
    }


    fun guardar(view: View){

        val name = view.findViewById(R.id.etTask) as EditText
        var titulo = name.getText().toString()

        val time = view.findViewById(R.id.btn_time) as Button
        var tiempo : String = time.getText().toString()




        val Mon = view.findViewById(R.id.Mon) as CheckBox
        val Tues = view.findViewById(R.id.Tues) as CheckBox
        val Wed = view.findViewById(R.id.Wed) as CheckBox
        val Thur = view.findViewById(R.id.Thur) as CheckBox
        val Fri = view.findViewById(R.id.Fri) as CheckBox
        val Sat = view.findViewById(R.id.Sat) as CheckBox
        val Sun = view.findViewById(R.id.Sun) as CheckBox
    var dia: String = ""


        if (Mon.isChecked) dia = getString(R.string.Monday)
        if (Tues.isChecked) dia = getString(R.string.Tuesday)
        if (Wed.isChecked) dia = getString(R.string.Wednesday)
        if (Thur.isChecked) dia = getString(R.string.Thursday)
        if (Fri.isChecked) dia = getString(R.string.Friday)
        if (Sat.isChecked) dia = getString(R.string.Saturday)
        if (Sun.isChecked) dia = getString(R.string.Sunday)

        var tarea = Recordatorio(dia,tiempo,titulo)

        HomeFragment.carrito.agregar(tarea)

        Toast.makeText(context,"The reminder was added",Toast.LENGTH_SHORT).show()
    }

    fun set_time(view: View){
       val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener{timerPicker, hour, minute->
            cal.set(Calendar.HOUR_OF_DAY,hour)
            cal.set(Calendar.MINUTE,minute)

            val btnTime = view.findViewById(R.id.btn_time) as Button

           btnTime.text = SimpleDateFormat("HH:mm").format(cal.time)

        }

        TimePickerDialog(context,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()


    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}