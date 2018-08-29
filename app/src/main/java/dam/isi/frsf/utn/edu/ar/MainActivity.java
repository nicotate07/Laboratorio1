package dam.isi.frsf.utn.edu.ar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import dam.isi.frsf.utn.edu.ar.bancolab01.modelo.Cliente;
import dam.isi.frsf.utn.edu.ar.bancolab01.modelo.PlazoFijo;

import static dam.isi.frsf.utn.edu.ar.R.id.btnHacerPF;
import static dam.isi.frsf.utn.edu.ar.R.id.fill;

public class MainActivity extends AppCompatActivity {
    private PlazoFijo pf;
    private Cliente cliente;
    private EditText etdMail;
    private EditText edtCuit;
    private RadioGroup optMoneda;
    private EditText edtMonto;
    private SeekBar seekDias;
    private TextView tvDiasSeleccionados;
    private TextView tvIntereses;
    private Switch swAvisarVencimiento;
    private ToggleButton togAccion;
    private CheckBox chkAceptoTerminos;
    private Button btnHacerPF;
    private TextView edtMensajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SEGUIMOS ACA
        pf = new PlazoFijo(getResources().getStringArray(R.array.tasas));
        cliente = new Cliente();

        etdMail = findViewById(R.id.edtMail);
        edtCuit = findViewById(R.id.edtCuit);
        optMoneda = findViewById(R.id.optMoneda);
        edtMonto = findViewById(R.id.edtMonto);
        seekDias = findViewById(R.id.seekDias);
        tvDiasSeleccionados = findViewById(R.id.tvDiasSeleccionados);
        tvIntereses = findViewById(R.id.tvIntereses);
        swAvisarVencimiento = findViewById(R.id.swAvisarVencimiento);
        togAccion = findViewById(R.id.togAccion);
        chkAceptoTerminos = findViewById(R.id.chkAceptoTerminos);
        btnHacerPF = findViewById(R.id.btnHacerPF);
        edtMensajes = findViewById(R.id.edtMensajes);

    }

}
