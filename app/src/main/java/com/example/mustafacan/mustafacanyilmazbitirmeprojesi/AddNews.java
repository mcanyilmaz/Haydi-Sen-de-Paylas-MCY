package com.example.mustafacan.mustafacanyilmazbitirmeprojesi;


import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNews extends Fragment {

    EditText HaberiEkleyen, HaberIcerigi,HaberKonu;
    String haberiekleyen, habericerigi,haberkonu;
    FragmentTransaction fragmentTransaction;
    //Context ctx = getActivity();

    public AddNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_add_news, container, false);
        HaberiEkleyen = (EditText) v.findViewById(R.id.et_haber_adsoyad);
        LoginAndRegister activity = new  LoginAndRegister();
        String myDataFromActivity = activity.getMyData();

        //TextView textView1 = (TextView)v.findViewById(R.id.txt_total_degerlendirme);
        //textView1.setText(myDataFromActivity);

        HaberIcerigi = (EditText) v.findViewById(R.id.et_haber_yazi);
        HaberKonu = (EditText) v.findViewById(R.id.et_haber_basligi);
        HaberiEkleyen.setText(myDataFromActivity);


        Button b7 = (Button) v.findViewById(R.id.haber_ekle);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(HaberiEkleyen.getText().toString().trim().equals("") || HaberIcerigi.getText().toString().trim().equals("")
                        || HaberKonu.getText().toString().trim().equals("")){
                    Toast.makeText(getActivity(),"Lütfen Boş Alanları Doldurun",Toast.LENGTH_LONG).show();
                }else{
                    haberiekleyen = HaberiEkleyen.getText().toString();
                    habericerigi = HaberIcerigi.getText().toString();
                    haberkonu = HaberKonu.getText().toString();
                    AddNews.BackGround b = new AddNews.BackGround();
                    b.execute(haberiekleyen, habericerigi, haberkonu);
                }
            }
        });


        return v;
    }


    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String HaberiEkleyen = params[0];
            String HaberIcerigi = params[1];
            String HaberKonu = params[2];
            String data = "";
            int tmp;

            try {
                URL url = new URL("http://www.mustafacanyilmaz.com/MYCODE/haber_ekle_2.php");
                String urlParams = "ekleyen=" + HaberiEkleyen + "&icerik=" + HaberIcerigi + "&konu=" + HaberKonu;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                while ((tmp = is.read()) != -1) {
                    data += (char) tmp;
                }
                is.close();
                httpURLConnection.disconnect();

                return data;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("")) {
                s = "Verileriniz Başarıyla Veritabanına Eklendi.";
                //Intent i = new Intent(getActivity(), Home.class);
                //startActivity(i);
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new MainPageFragment());
                fragmentTransaction.commit();
            }
            //Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
        }
    }

}

