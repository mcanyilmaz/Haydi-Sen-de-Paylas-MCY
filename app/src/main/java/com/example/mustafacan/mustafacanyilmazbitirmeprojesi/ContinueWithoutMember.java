package com.example.mustafacan.mustafacanyilmazbitirmeprojesi;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;

public class ContinueWithoutMember extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    private Toolbar mToolbar;

    NavigationView navigationView;
    FragmentTransaction fragmentTransaction;


    JSONArray veriListesi;

    String JSON_STRING;
    TextView textView;
    RequestQueue requestQueue;

    public String [] data = {"Haber 1 ","Haber 2","Haber 3","Haber 4 ","Haber 5"};


    String ad, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_without_member);

        getData();

        EditText etSearch = (EditText)findViewById(R.id.et_search);

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

        ad = getIntent().getStringExtra("name");
        password = getIntent().getStringExtra("password");
        email = getIntent().getStringExtra("email");

        textView = (TextView) findViewById(R.id.textview);
        requestQueue = Volley.newRequestQueue(this);

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


        ListView lv = (ListView)findViewById(R.id.lv_liste);

        MedipolArrayAdapter adapter = new MedipolArrayAdapter(this,veriler);
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
        Intent i = new Intent(ContinueWithoutMember.this,PostDetailsActivity.class);
        try {
            i.putExtra("icerik",object.getString("icerik"));
            i.putExtra("ekleyen",object.getString("ekleyen"));
            i.putExtra("konu",object.getString("konu"));
            // i.putExtra("degerlendirme",object.getString("degerlendirme"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(i);

    }

    public void getData(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="";
        url = "http://www.mustafacanyilmaz.com/MYCODE/json_get_data2.php";

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
