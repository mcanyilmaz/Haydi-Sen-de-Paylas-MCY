package com.example.mustafacan.mustafacanyilmazbitirmeprojesi;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PostDetailsActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private TextView txtRatingValue;
    private Button btnSubmit;
    private TextView ratingBarValue2;


    //String ratingBarValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

       // ratingBar = (RatingBar)findViewById(R.id.ratingBar);

        addListenerOnRatingBar();
        addListenerOnButton();


        TextView txtTitle1 = (TextView)findViewById(R.id.txt_name2);
        String titleString1 = getIntent().getStringExtra("icerik");
        txtTitle1.setText("İçerik : "+titleString1);

        TextView txtTitle2 = (TextView)findViewById(R.id.txt_ekleyen);
        String titleString2 = getIntent().getStringExtra("ekleyen");
        txtTitle2.setText("Ekleyen : "+titleString2);


        TextView txtTitle3 = (TextView)findViewById(R.id.txt_haber_basligi);
        String titleString3 = getIntent().getStringExtra("konu");
        txtTitle3.setText("Konu : "+titleString3);

        //TextView txtTitle4 = (TextView)findViewById(R.id.txt_total_degerlendirme);
        //String titleString4 = getIntent().getStringExtra("degerlendirme");
        //txtTitle4.setText("Toplam Değerlendirme : "+titleString4);

    }

    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);
        // ratingBarValue2 = (TextView)findViewById(R.id.txt_total_degerlendirme);

        ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                txtRatingValue.setText(String.valueOf(rating));
                //ratingBarValue = txtRatingValue.getText().toString();
            }
        });
    }

    public void addListenerOnButton() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //PostDetailsActivity.BackGround b = new PostDetailsActivity.BackGround();
               // b.execute(ratingBarValue);
                Toast.makeText(PostDetailsActivity.this,
                        String.valueOf(ratingBar.getRating()),
                        Toast.LENGTH_SHORT).show();

            }

        });

    }

}
