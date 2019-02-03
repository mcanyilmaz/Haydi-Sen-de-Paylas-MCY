package com.example.mustafacan.mustafacanyilmazbitirmeprojesi;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends Fragment {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    private Toolbar mToolbar;

    boolean shouldOpenMainActivity = true;

    NavigationView navigationView;
    FragmentTransaction fragmentTransaction;

    String isimAl;

    JSONArray veriListesi;

    String JSON_STRING;
    TextView textView;
    RequestQueue requestQueue;

    public String [] data = {"Haber 1 ","Haber 2","Haber 3","Haber 4 ","Haber 5"};


    String ad, password, email;


    public MainPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View v =  inflater.inflate(R.layout.fragment_main_page, container, false);

        getData();

        EditText etSearch = (EditText)v.findViewById(R.id.et_search);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                filterData(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ad = getActivity().getIntent().getStringExtra("name");
        password = getActivity().getIntent().getStringExtra("password");
        email = getActivity().getIntent().getStringExtra("email");

        //start = (Button) findViewById(R.id.buton_1);
        textView = (TextView) v.findViewById(R.id.textview);
        requestQueue = Volley.newRequestQueue(getActivity());



        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //TextView tx1 = (TextView)getActivity().findViewById(R.id.main_page_textview);
        //tx1.setText(isimAl);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToogle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void processData(String response){


        try {
            JSONObject jsonObject = new JSONObject(response);

            veriListesi = jsonObject.getJSONArray("server_response");
//            veriListesi.get(0);
//            {
//                "name": "1",
//                    "password": "Mustafa",
//                    "email": "aaaaaa"
//            }

//            for(int i = 0 ; i<veriListesi.length; i++){
//
//                veriListesi[i] = jsonArray.getJSONObject(i);
//
//            }
            showData(veriListesi);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

        public void filterData(String key){

        ArrayList<JSONObject> tempList = new ArrayList<>();

        for(int i = 0; i<veriListesi.length(); i++){

            try {
                if (veriListesi.getJSONObject(i).getString("konu").toLowerCase().contains(key.toLowerCase())) {

                    tempList.add(veriListesi.getJSONObject(i));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONObject[] jsonObjects = tempList.toArray(new JSONObject[0]);

        JSONArray jsonArray = new JSONArray();

        for(int i = 0; i<jsonObjects.length; i++){
            jsonArray.put(jsonObjects[i]);

        }

        showData(jsonArray);

    }


    public void showData(JSONArray data){

        String[] veriler = new String[data.length()];
        for (int i = 0; i < data.length(); i++) {
            try {
                veriler[i] = data.getJSONObject(i).getString("konu");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ListView lv = (ListView)getActivity().findViewById(R.id.lv_liste);

        MedipolArrayAdapter adapter = new MedipolArrayAdapter(getActivity(),veriler);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                try {
                    git(veriListesi.getJSONObject(position));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    public void git(JSONObject object){
        try {
            getActivity().getIntent().putExtra("icerik",object.getString("icerik"));
            getActivity().getIntent().putExtra("ekleyen",object.getString("ekleyen"));
            getActivity().getIntent().putExtra("konu",object.getString("konu"));
            // i.putExtra("degerlendirme",object.getString("degerlendirme"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container,new PostDetailsFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void getData(){

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="";
        url = "http://www.mcanyilmaz.hol.es/MYCODE/app/json_get_data2.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }


    public class MedipolArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public MedipolArrayAdapter(Context context, String[] values) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
            TextView txt = (TextView)rowView.findViewById(R.id.txt_rowlayout);
            txt.setText(values[position]);

            return rowView;
        }
    }


}
