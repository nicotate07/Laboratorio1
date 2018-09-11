package dam.isi.frsf.utn.edu.ar;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import dam.isi.frsf.utn.edu.ar.bancolab01.modelo.Cliente;
import dam.isi.frsf.utn.edu.ar.bancolab01.modelo.Moneda;
import dam.isi.frsf.utn.edu.ar.bancolab01.modelo.PlazoFijo;

import static dam.isi.frsf.utn.edu.ar.R.id.btnHacerPF;
import static dam.isi.frsf.utn.edu.ar.R.id.edtMail;
import static dam.isi.frsf.utn.edu.ar.R.id.fill;
import static dam.isi.frsf.utn.edu.ar.R.id.radio;

public class MainActivity extends AppCompatActivity {
    private PlazoFijo pf;
    private Cliente cliente;
    private EditText edtMail;
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
    private Integer defaultBackgroundColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pf = new PlazoFijo(getResources().getStringArray(R.array.tasas));
        cliente = new Cliente();

        edtMail = findViewById(R.id.edtMail);
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

        defaultBackgroundColor = edtMail.getSolidColor();
        edtMensajes.setTextColor(getResources().getColor(R.color.colorPrimary));

        btnHacerPF.setEnabled(false);

        final Integer seekMinValue = 10;
        final Integer seekMaxValue = 170;
        seekDias.setMax(seekMaxValue);
        seekDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seek,int n,boolean b)
            {
                Integer dias = n + seekMinValue;
                tvDiasSeleccionados.setText(dias.toString());
                pf.setDias(dias);
                String m = edtMonto.getText().toString();
                if(!m.isEmpty())
                {
                    pf.setMonto(new Double(m));
                }
                tvIntereses.setText("$"+pf.intereses().toString());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        seekDias.setProgress(0);
        tvDiasSeleccionados.setText(seekMinValue.toString());
        pf.setDias(seekMinValue);
        tvIntereses.setText(pf.intereses().toString());
        //TODO: El calculo de intereses esta mal?, Ver ejemplo del tp, da distinto...

        chkAceptoTerminos.setOnCheckedChangeListener(
                new CheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        btnHacerPF.setEnabled(b);
                        if(!b){
                            Context context = getApplicationContext();
                            CharSequence text = "Es obligatorio aceptar las condiciones";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
        });

        optMoneda.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        Integer idSeleccion = radioGroup.getCheckedRadioButtonId();
                        if (idSeleccion == R.id.optPesos){
                            pf.setMoneda(Moneda.PESO);
                        }else{
                            pf.setMoneda(Moneda.DOLAR);
                        }
                    }
                }
        );

        swAvisarVencimiento.setOnCheckedChangeListener(
                new Switch.OnCheckedChangeListener(){
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        pf.setAvisarVencimiento(b);
                    }
                }
        );

        btnHacerPF.setOnClickListener(
                new Button.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Double monto = 0.0;
                        boolean mail = edtMail.getText().toString().isEmpty();
                        boolean cuit = edtCuit.getText().toString().isEmpty();
                        if( !edtMonto.getText().toString().isEmpty()){
                            monto = new Double(edtMonto.getText().toString());
                        }
                        Integer barra = seekDias.getProgress() + seekMinValue;

                        String eMonto = "El monto debe ser mayor a 0\n";
                        String eMail = "Mail vacio\n";
                        String eCuit = "CUIT/CUIL vacio\n";
                        String eDias = "Dias debe ser mayor a 10\n";

                        String errores = "";
                        if(monto == 0.0) {
                            edtMonto.setError(eMonto);
                            errores += eMonto;
                        }
                        if(mail) {
                            edtMail.setError(eMail);
                            errores += eMail;
                        }
                        if(cuit) {
                            edtCuit.setError(eCuit);
                            errores += eCuit;
                        }
                        if(barra<=10) {
                            seekDias.setBackgroundColor(getResources().getColor(R.color.colorError));
                            errores += eDias;
                        }else{
                            seekDias.setBackgroundColor(defaultBackgroundColor);
                        }

                        if(!errores.isEmpty()){
                            Context context = getApplicationContext();
                            CharSequence text = errores;
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }else{
                            edtMensajes.setText("El plazo fijo se realizó exitosamente\n"+pf.toStringSuccess());
                        }

                    }
                }
        );

    }

}
