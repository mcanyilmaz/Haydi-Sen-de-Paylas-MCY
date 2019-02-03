package com.example.mustafacan.mustafacanyilmazbitirmeprojesi;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostDetailsFragment extends Fragment {

    private RatingBar ratingBar;
    private TextView txtRatingValue;
    private Button btnSubmit;
    private TextView ratingBarValue2;

    static FragmentTransaction fragmentTransaction;


    public PostDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_post_details, container, false);

        //String strtext = getArguments().getString("et");




        addListenerOnRatingBar(v);
        addListenerOnButton(v);


        TextView txtTitle1 = (TextView)v.findViewById(R.id.txt_name2);
        String titleString1 = getActivity().getIntent().getStringExtra("icerik");
        txtTitle1.setText("İçerik : "+titleString1);

        TextView txtTitle2 = (TextView)v.findViewById(R.id.txt_ekleyen);
        String titleString2 = getActivity().getIntent().getStringExtra("ekleyen");
        txtTitle2.setText("Ekleyen : "+titleString2);


        TextView txtTitle3 = (TextView)v.findViewById(R.id.txt_haber_basligi);
        String titleString3 = getActivity().getIntent().getStringExtra("konu");
        txtTitle3.setText("Konu : "+titleString3);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void addListenerOnRatingBar(View v) {

        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        txtRatingValue = (TextView) v.findViewById(R.id.txtRatingValue);
        //ratingBarValue2 = (TextView)v.findViewById(R.id.txt_total_degerlendirme);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                txtRatingValue.setText(String.valueOf(rating));
                //ratingBarValue = txtRatingValue.getText().toString();
            }
        });
    }

    public void addListenerOnButton(View v) {

        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        btnSubmit = (Button) v.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT).show();
            }

        });

    }




}
