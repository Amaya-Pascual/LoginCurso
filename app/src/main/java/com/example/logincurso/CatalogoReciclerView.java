package com.example.logincurso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.logincurso.POJOS.Lote;
import com.example.logincurso.adapters.AdapterLotes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CatalogoReciclerView extends AppCompatActivity {
    //url de la obtencion del json
    private static final String urlListaLotes="http://194.30.35.183/subasta/listarLotes.php";
    //array para depositar los datos obtenidos
    ArrayList<Lote> listLotes;
    //declarar el recycler view
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_recicler_view);
        recycler=findViewById(R.id.recyclerId);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        //recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        //recycler.setLayoutManager(new GridLayoutManager(this,3));
        listLotes = new ArrayList<>();
        llenarDatos();
        AdapterLotes adapterLotes= new AdapterLotes(this,listLotes);
        adapterLotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("has hecho click");
                Toast.makeText(getApplicationContext(),
                        "texto",
                        Toast.LENGTH_SHORT).show();
            }
        });
        recycler.setAdapter(adapterLotes);
    }

    private void llenarDatos() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlListaLotes,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getApplicationContext(), response ,Toast.LENGTH_LONG).show();
                        try {
                            JSONArray array = new JSONArray(response);
                            //Toast.makeText(getApplicationContext(), response ,Toast.LENGTH_LONG).show();
                            //Toast.makeText(getApplicationContext(), array.length()+"",Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < array.length(); i++) {
                                //Toast.makeText(getApplicationContext(), i+"",Toast.LENGTH_SHORT).show();
                                JSONObject loteSon = array.getJSONObject(i);
                                listLotes.add(new Lote(
                                        loteSon.getInt("idLote"),
                                        loteSon.getInt("refLote"),
                                        (float) loteSon.getDouble("salida"),
                                        loteSon.getString("descripcion"),
                                        loteSon.getString("imgLote")
                                ));
                            }
                            AdapterLotes adapterLotes = new AdapterLotes(CatalogoReciclerView.this, listLotes);
                            recycler.setAdapter(adapterLotes);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        );
        //Toast.makeText(getApplicationContext(), stringRequest +"",Toast.LENGTH_LONG).show();
        Volley.newRequestQueue(this).add(stringRequest);
    }
}