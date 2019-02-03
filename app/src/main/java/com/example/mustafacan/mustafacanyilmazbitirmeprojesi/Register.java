package com.example.mustafacan.mustafacanyilmazbitirmeprojesi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mustafacan.mustafacanyilmazbitirmeprojesi.BenRobotDegilim.MainCaptcha;
import com.example.mustafacan.mustafacanyilmazbitirmeprojesi.BenRobotDegilim.OperationCaptcha;
import com.example.mustafacan.mustafacanyilmazbitirmeprojesi.BenRobotDegilim.TextCaptcha;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends Activity {


    ImageView captchaImage;
    Button refreshBtn;
    RadioButton numRB;
    EditText userCaptcha;
    TextView result;
    String answer;

    EditText name, password, email;
    String Name, Password, Email;
    Context ctx=this;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.register_name);
        password = (EditText) findViewById(R.id.register_password);
        email = (EditText) findViewById(R.id.register_email);


        final EditText et = (EditText) findViewById(R.id.register_password);
        CheckBox c = (CheckBox) findViewById(R.id.show_password);

        c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });

        numRB = (RadioButton)findViewById(R.id.aritmatika_rb);
        //cekBtn = (Button)findViewById(R.id.cek_btn);
        refreshBtn = (Button) findViewById(R.id.refresh_btn);
        captchaImage = (ImageView) findViewById(R.id.captcha_im);
        userCaptcha = (EditText) findViewById(R.id.insertedCaptca_et);
        result = (TextView) findViewById(R.id.result_tv);


        //Initializing our first captcha....

        MainCaptcha c1 = new OperationCaptcha(300, 100, OperationCaptcha.MathOptions.PLUS_MINUS_MULTIPLY);
        captchaImage.setImageBitmap(c1.image);
        answer = c1.answer;


        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numRB.isChecked())
                {
                    MainCaptcha c = new OperationCaptcha(300, 100, OperationCaptcha.MathOptions.PLUS_MINUS_MULTIPLY);
                    captchaImage.setImageBitmap(c.image);
                    answer = c.answer;
                }else
                {
                    MainCaptcha c = new TextCaptcha(300, 100, 5, TextCaptcha.TextOptions.NUMBERS_AND_LETTERS);
                    captchaImage.setImageBitmap(c.image);
                    answer = c.answer;
                }
            }
        });

        /*

        cekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = userCaptcha.getText().toString();
                if(userAnswer.equalsIgnoreCase(answer))
                {
                    result.setText("Tebrikler " + "-->" + answer);
                }else
                {
                    result.setText("Tekrar Deneyiniz");
                    if(numRB.isChecked())
                    {
                        MainCaptcha c = new OperationCaptcha(300, 100, OperationCaptcha.MathOptions.PLUS_MINUS_MULTIPLY);
                        captchaImage.setImageBitmap(c.image);
                        answer = c.answer;
                    }else
                    {
                        MainCaptcha c = new TextCaptcha(300, 100, 5, TextCaptcha.TextOptions.NUMBERS_AND_LETTERS);
                        captchaImage.setImageBitmap(c.image);
                        answer = c.answer;
                    }
                }

            }
        });

        */
    }

    public void register_register(View v){

        if(name.getText().toString().trim().equals("") ||
                password.getText().toString().trim().equals("") ||
                email.getText().toString().trim().equals("") ||
                userCaptcha.getText().toString().trim().equals("")) {

            Toast.makeText(this,"Lütfen Boş Alanları Eksiksiz Doldurun ! ",Toast.LENGTH_LONG).show();
        }else{
            String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +

                    "\\@" +

                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +

                    "(" +

                    "\\." +

                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +

                    ")+";


            String email1 = email.getText().toString();

            Matcher matcher= Pattern.compile(validemail).matcher(email1);

            if (matcher.matches()){

                String userAnswer = userCaptcha.getText().toString();
                if(userAnswer.equalsIgnoreCase(answer))
                {
                    result.setText("Tebrikler " + "-->" + answer);
                    Intent i = new Intent(Register.this,LoginAndRegister.class);
                    startActivity(i);
                    finish();
                    s = "Kayıt Başarılı ";

                    Name = name.getText().toString();
                    Password = password.getText().toString();
                    Email = email.getText().toString();

                    BackGround b = new BackGround();
                    b.execute(Name, Password, Email);

                    Toast.makeText(ctx,s, Toast.LENGTH_LONG).show();
                    Toast.makeText(this,"Kullanıcı Adınız : " + Name + " Şifreniz : " + Password  +" Email Adresiniz : " + Email ,Toast.LENGTH_LONG).show();
                }else
                {
                    result.setText("Tekrar Deneyiniz");
                    if(numRB.isChecked())
                    {
                        MainCaptcha c = new OperationCaptcha(300, 100, OperationCaptcha.MathOptions.PLUS_MINUS_MULTIPLY);
                        captchaImage.setImageBitmap(c.image);
                        answer = c.answer;
                    }else
                    {
                        MainCaptcha c = new TextCaptcha(300, 100, 5, TextCaptcha.TextOptions.NUMBERS_AND_LETTERS);
                        captchaImage.setImageBitmap(c.image);
                        answer = c.answer;
                    }
                }
            }
            else{
                    Toast.makeText(getApplicationContext(),"e-Mail Formatına Uygun Giriniz",Toast.LENGTH_LONG).show();
            }
        }
    }
    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String password = params[1];
            String email = params[2];

            String data="";
            int tmp;

            try {
                URL url = new URL("http://www.mustafacanyilmaz.com/MYCODE/register.php");
                String urlParams = "name=" + name + "&password=" +password + "&email=" + email;

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

       /* @Override
        protected void onPostExecute(String s) {
            if(s.equals("")){
                s="Kaydınız Başarıyla Veritabanına Eklendi.";

                Intent i = new Intent(Register.this,Main.class);
                startActivity(i);

                //Toast.makeText(this,"Kullanıcı Adınız : " + name + " Şifreniz : " + password + " Email Adresiniz : " + email ,Toast.LENGTH_SHORT).show();

            }
            Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
        }
        */



    }


}