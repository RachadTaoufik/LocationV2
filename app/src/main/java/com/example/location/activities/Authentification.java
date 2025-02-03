package com.example.location.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.location.R;

public class Authentification extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layout1;
    LinearLayout layout2;
    Button bttauth;
    Button bttacc;
    EditText login;
    EditText passwod;
    EditText nom;
    EditText prenom;
    EditText pays;
    EditText adresse;
    EditText phone;
    EditText email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authentification);

        layout1=(LinearLayout) findViewById(R.id.Layout1);
        layout2=(LinearLayout)findViewById(R.id.Layout2);
        bttauth=(Button) findViewById(R.id.authButton);
        bttauth.setOnClickListener(this);
        bttacc.setOnClickListener(this);
        // instancier les autres elements graphiques

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    @Override
    public void onClick(View view) {
        if(view.getId()==bttacc.getId()){
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
        }
    }
}