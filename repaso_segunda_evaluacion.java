package com.example.repaso_segunda_evaluacion;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static java.lang.Boolean.FALSE;


public class repaso_segunda_evaluacion extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repaso_segunda_evaluacion);

        //VARIABLES PARA EL SPINNER
        final Spinner spinner = findViewById(R.id.spinner);
        final TextView infoSpinner = findViewById(R.id.textoSpinner);
        //VARIABLES PARA LA SEEKBAR
        final SeekBar seekBar = findViewById(R.id.seekbar);
        final TextView infoSeekBar = findViewById(R.id.tituloSeekBar);
        final Button btnIntroSeekBar = findViewById(R.id.btnSeekBar);
        final EditText introValor =findViewById(R.id.escribirValorSeekBar);
        //VARIABLES PARA LA PROGRESSBAR
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final Button botonProgress= findViewById(R.id.btnProgressBar);
        final TextView textoProgressBar= findViewById(R.id.tituloProgressBar);

        //--------------------------------SPINNER---------------------------------------------------
        //EL ARRAY CON LAS OPCIONES LO HACEMOS EN EL XML QUE ASÍ LO PODEMOS CONTROLR MEJOR
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.opciones,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);//AQUI CONECTAMOS EL SPINNER CON EL ADAPTADOR ESTE, QUE LE METE EL MODELO DEL SPINNER Y LA INFO

        //AQUI PODEMOS HACERLO DE ESTE MODO O IMPLEMENTANDOLO EN LA CLASE PRINCIPAL CON IMPLEMENTS Y UNICAMENTE NOS PONDRIA LOS METODOS OITEMSELECTED Y ONNOTHINGSELECTED
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                if(position==0)
                {
                    infoSpinner.setText("Seleccione al menos un elemento del Spinner.".toUpperCase());
                    infoSpinner.setTextColor(infoSpinner.getContext().getResources().getColor(R.color.colorError));
                }
                else if(position==1)
                {
                    Uri uri = Uri.parse("http://www.google.es/");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    Toast mensaje = Toast.makeText(getApplicationContext(), "Abriendo Google",Toast.LENGTH_SHORT);
                    mensaje.show();

                    String opcion = parent.getItemAtPosition(position).toString();//CREAMOS UNA VARIABLE DE TIPO STRING EN LA QUE ALMACENAMOS LA OPCION SELECCIONADA QUE NOS INDICA EL POSITION DEL ITEM SELECCIONADO
                    infoSpinner.setText("Ha seleccionado el elemento "+opcion);
                    infoSpinner.setTextColor(infoSpinner.getContext().getResources().getColor(R.color.colorTitulo));
                }
                else if(position==2)
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                    startActivity(intent);
                    Toast mensaje = Toast.makeText(getApplicationContext(), "Abriendo Cámara",Toast.LENGTH_SHORT);
                    mensaje.show();

                    String opcion = parent.getItemAtPosition(position).toString();//CREAMOS UNA VARIABLE DE TIPO STRING EN LA QUE ALMACENAMOS LA OPCION SELECCIONADA QUE NOS INDICA EL POSITION DEL ITEM SELECCIONADO
                    infoSpinner.setText("Ha seleccionado el elemento "+opcion);
                    infoSpinner.setTextColor(infoSpinner.getContext().getResources().getColor(R.color.colorTitulo));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                infoSpinner.setText("Hola majo, no has seleccionado nada xdddd.");

            }
        });

        //----------------------------------------SEEKBAR-------------------------------------------
        seekBar.setProgress(0);
        infoSeekBar.setText("Seleccione un valor deslizando la barra:");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                infoSeekBar.setText("El valor seleccionado es: "+progress);//VA MOSTRANDO EL VALOR SELECCIONADO

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                Toast.makeText(getApplicationContext(),"SeekBarSelected",Toast.LENGTH_LONG).show();//APARENTEMENTE NO SALE EL TOAST
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                Toast.makeText(getApplicationContext(),"SeekBarDeselected",Toast.LENGTH_SHORT).show();//APARENTEMENTE NO SALE EL TOAST
            }
        });

        //BOTÓN INTRODUCIR DATOS DE LA SELECCION DEL SEEKBAR
        btnIntroSeekBar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(),"Valor Seleccionado Correctamente.",Toast.LENGTH_LONG).show();
                btnIntroSeekBar.setText("El valor es:"+seekBar.getProgress());
            }
        });

        //EDITEXT QUE AL INTRODUCIR UN VALOR LA SEEKBAR SE POSICIONA EN ÉL
        //PEGA FALLO AL BORRAR EL NUMERO Y QUEDARSE VACÍO
        introValor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                if(introValor.getText().toString().isEmpty())//metemos esta comprobacion para que al borrar el editext no pete el programa
                {
                    seekBar.setProgress(0);

                }
                else if(Integer.parseInt(s.toString()) >= 100)//si el numero que metemos es mayor o igual que cien, mostrar un toast de que 100 es el max
                {

                    Toast.makeText(getApplicationContext(),"(El máximo valor a introducir es 100)",Toast.LENGTH_LONG).show();

                }
                else
                {
                    seekBar.setProgress(Integer.parseInt(s.toString()));//LE METEMOS EL VALOR QUE INTORDUCIMOS CON EL EDITEXT EN EL SEEKBAR
                }

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //-------------------------------------------PROGRESSBAR-------------------------------------------------------
            progressBar.setVisibility(View.INVISIBLE);

        botonProgress.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);
                botonProgress.setEnabled(FALSE);

                switch (v.getId())
                {
                    case R.id.btnProgressBar:
                        new MyAsyncTask().execute(100);
                        break;
                }


            }
        });

    }//FIN DEL ONCREATE
    //NOS CREAMOS UNA CLASE PARA EJECUTAR LA PROGRESSBAR
    protected class MyAsyncTask extends AsyncTask <Integer,Integer,String>
    {
        //VARIABLES PARA LA PROGRESSBAR DECLARADAS DE NUEVO EN ESTA CLASE APARTE, QUE LO SUYO SERÍA HACERLO DE UNA Y YASTA BUT ES LO QUE HAY
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final Button botonProgress= findViewById(R.id.btnProgressBar);
        final TextView textoProgressBar= findViewById(R.id.tituloProgressBar);

        //MÉTODO QUE SE EJECUTA POR DETRÁS Y DEVUELVE UN STRING AL ACABAR INDICANDO QUE SE HA FINALIZADO
        @Override
        protected String doInBackground(Integer... integers)
        {
            for (int counter = 0; counter <= integers[0]; counter+=7)
            {
                try {
                    Thread.sleep(800);
                    publishProgress(counter);
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }

        //MÉTODO QUE NOS PERMITE IR VIENDO EL PROGRESO DE LA PROGRESSBAR
        @Override
        protected void onProgressUpdate (Integer... values)
        {
            botonProgress.setText("ESPERE...");
            botonProgress.setEnabled(false);
            textoProgressBar.setText("En ejecución..."+ values[0]);
            progressBar.setProgress(values[0]);
            if(values[0] > 10)
            {
                Toast.makeText(getApplicationContext(),"Cambiar color(AMARILLO)",Toast.LENGTH_SHORT).show();
               // progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
                //NO SÉ COMO CAMBIAR EL COLOOOOOOOOR

            }
            else if(values[0] >50)
            {
                Toast.makeText(getApplicationContext(),"Cambiar color(VERDE)",Toast.LENGTH_SHORT).show();

            }
            else if(values[0]==100)
            {
                Toast.makeText(getApplicationContext(),"Cambiar color(ROJO)",Toast.LENGTH_SHORT).show();
            }
        }

        //MÉTODO QUE SE EJECUTA DESPUÉS DE LA EJECUCIÓN DE LA PROGRESSBAR
        @Override
        protected void onPostExecute(String s) {
            textoProgressBar.setText("Ejecución correcta...");
            botonProgress.setText("REINICIAR");
            botonProgress.setEnabled(true);
        }

    }
}
