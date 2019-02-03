package com.example.mustafacan.mustafacanyilmazbitirmeprojesi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class LoginAndRegister extends Activity implements TextWatcher,
        CompoundButton.OnCheckedChangeListener {

    FragmentTransaction fragmentTransaction;
    EditText login_username, login_password;
    String Name, Password;
    Context ctx = this;
    String NAME = null, PASSWORD = null, EMAIL = null;

    public static EditText et_Username , et_Password;
    private CheckBox rem_userpass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);
        login_username = (EditText) findViewById(R.id.main_name);
        login_password = (EditText) findViewById(R.id.main_password);

        final EditText etUsername = (EditText) findViewById(R.id.main_name);
        final EditText etPassword = (EditText) findViewById(R.id.main_password);
        final Button bLogin = (Button) findViewById(R.id.main_login);

//        Fragment frag = new Fragment();
//        String isimAl = login_username.getText().toString();
//        Bundle bundle = new Bundle();
//        bundle.putString("name",isimAl);
//        frag.setArguments(bundle);

        //MainPageFragment frag = new MainPageFragment();
        //String isimAlici = etUsername.getText().toString();
        //Bundle bundle = new Bundle();
        //bundle.putString("name",isimAlici);
        //frag.setArguments(bundle);

        //String isimAl;

        //isimAl= getArguments().getString("name");

        //TextView txt123 = (TextView)findViewById(R.id.profile_name);
        //txt123.setText(isimAlici);


        Bundle bundle = new Bundle();
        bundle.putString("et", "From Activity");
        // set Fragmentclass Arguments
        PostDetailsFragment fragobj = new PostDetailsFragment();
        fragobj.setArguments(bundle);



        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(login_username.getText().toString().trim().equals("") ||
                                    login_password.getText().toString().trim().equals("")){
                                Toast.makeText(LoginAndRegister.this,"Lütfen Boş Alanları Eksiksiz Doldurun ! ",Toast.LENGTH_LONG).show();
                            }

                             else if (success) {
                                //String name = jsonResponse.getString("name");
                                Intent intent = new Intent(LoginAndRegister.this, Home.class);

                                intent.putExtra("name", username);
                                LoginAndRegister.this.startActivity(intent);
                                Toast.makeText(LoginAndRegister.this," Hoşgeldin " + username,Toast.LENGTH_LONG).show();
                                finish();


                            }

                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginAndRegister.this);
                                builder.setMessage("Kullanıcı Adı Ya da Şifre Yanlış.")
                                        .setNegativeButton("Tekrar Deneyiniz ! ", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginAndRegister.this);
                queue.add(loginRequest);
            }
        });

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        et_Username = (EditText)findViewById(R.id.main_name);
        et_Password = (EditText)findViewById(R.id.main_password);
        rem_userpass = (CheckBox)findViewById(R.id.remember_me);

        if(sharedPreferences.getBoolean(KEY_REMEMBER, false))
            rem_userpass.setChecked(true);
        else
            rem_userpass.setChecked(false);

        et_Username.setText(sharedPreferences.getString(KEY_USERNAME,""));
        et_Password.setText(sharedPreferences.getString(KEY_PASS,""));

        et_Username.addTextChangedListener(this);
        et_Password.addTextChangedListener(this);
        rem_userpass.setOnCheckedChangeListener(this);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
    }

    private void managePrefs(){
        if(rem_userpass.isChecked()){
            editor.putString(KEY_USERNAME, et_Username.getText().toString().trim());
            editor.putString(KEY_PASS, et_Password.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        }else{
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);
            editor.remove(KEY_USERNAME);
            editor.apply();
        }
    }

    public void main_register(View v){
        startActivity(new Intent(this,Register.class));

    }

    public void main_login(View v){

        Name = login_username.getText().toString();
        Password = login_password.getText().toString();
        BackGround b = new BackGround();
        b.execute(Name, Password);

        Toast.makeText(this,"Hoşgeldiniz : " + Name, Toast.LENGTH_SHORT).show();

    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String password = params[1];
            String data="";
            int tmp;

            try {
                URL url = new URL("http://www.mustafacanyilmaz.com/MYCODE/login3.php");
                String urlParams = "name="+name+"&password="+password;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                while((tmp=is.read())!=-1){
                    data+= (char)tmp;
                }

                is.close();
                httpURLConnection.disconnect();

                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String err=null;
            try {
                JSONObject root = new JSONObject(s);
                JSONObject user_data = root.getJSONObject("user_data");
                NAME = user_data.getString("name");
                PASSWORD = user_data.getString("password");
                EMAIL = user_data.getString("email");
            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: "+e.getMessage();
            }

            Intent i = new Intent(ctx, Home.class);
            i.putExtra("name", NAME);
            i.putExtra("password", PASSWORD);
            i.putExtra("email", EMAIL);
            i.putExtra("err", err);
            startActivity(i);

        }
    }
    public void continue_without(View v){

        Intent i = new Intent(LoginAndRegister.this,ContinueWithoutMember.class);
        startActivity(i);
        finish();
    }

    public String getMyData() {
        String uName = et_Username.getText().toString();
        return uName;
    }


}