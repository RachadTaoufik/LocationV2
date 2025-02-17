package com.example.location.activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.location.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AuthentificationActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    Button bttauth;
    Button bttacc;
    Button bttconfAcc;
    Button bttAnnuler;
    Button bttDeconnecxion;
    Button bttOffres;
    Button bttDemandes;
    Button bttProfil;
    TextView userinfo;
    EditText login;
    EditText password;
    EditText email;
    EditText nom;
    EditText prenom;
    EditText pays;
    EditText adresse;
    EditText phone;
    EditText ville;
    RadioButton agentRadio;
    RadioButton clientRadio;
    RadioGroup radioGroup;

    boolean typeUser=true; //true for agent and false for client


    private FirebaseAuth mAuth;
    FirebaseFirestore db ;

    String nomUser;
    String prenomUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authentification);

        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();


        layout1=(LinearLayout) findViewById(R.id.Layout1);
        layout2=(LinearLayout)findViewById(R.id.Layout2);
        layout3=(LinearLayout)findViewById(R.id.Layout3);

        userinfo=(TextView) findViewById(R.id.userTextView);
        email=(EditText) findViewById(R.id.emailEditText);
        nom=(EditText) findViewById(R.id.nomEditText);
        prenom=(EditText) findViewById(R.id.prenomEditText);
        ville=(EditText) findViewById(R.id.villeEditText);
        pays=(EditText) findViewById(R.id.paysEditText);
        phone=(EditText) findViewById(R.id.phoneEditText);

        bttauth=(Button) findViewById(R.id.authButton);
        bttacc=(Button) findViewById(R.id.creerCompteButton);
        bttconfAcc=(Button) findViewById(R.id.confirmeButton);
        bttAnnuler=(Button) findViewById(R.id.annulerButton);

        bttDeconnecxion= (Button) findViewById(R.id.deconnexionButton);
        bttOffres= (Button) findViewById(R.id.offresButton);
        bttDemandes= (Button) findViewById(R.id.demandesButton);
        bttProfil= (Button) findViewById(R.id.profilButton);

        bttauth.setOnClickListener(this);
        bttacc.setOnClickListener(this);
        bttconfAcc.setOnClickListener(this);
        bttAnnuler.setOnClickListener(this);
        bttOffres.setOnClickListener(this);
        bttDemandes.setOnClickListener(this);
        bttProfil.setOnClickListener(this);
        bttDeconnecxion.setOnClickListener(this);

        login=(EditText) findViewById(R.id.loginEditText);
        password=(EditText) findViewById(R.id.passwordEditText);
        agentRadio=(RadioButton) findViewById(R.id.agentRadio);
        agentRadio.setChecked(true);
        clientRadio=(RadioButton)findViewById(R.id.clientRadio) ;
        radioGroup=(RadioGroup) findViewById(R.id.typeuserRadioGroup);
        radioGroup.setOnCheckedChangeListener(
                (group, checkedId) -> {

                    if (clientRadio.isChecked())
                        typeUser=false;
                    else if (agentRadio.isChecked())
                        typeUser=true;
                });


        // instancier les autres elements graphiques

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==bttacc.getId()){
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.GONE);
        }
        else if (view.getId()==bttauth.getId()) {
            SignIn();
        }
        else if (view.getId()==bttconfAcc.getId()) {
            signUp();
        }
        else if (view.getId()==bttAnnuler.getId()){
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);
        }
        else if (view.getId()==bttDeconnecxion.getId()){
            mAuth.signOut();
            updateUI(null);
        }
        else if (view.getId()==bttOffres.getId()){
           Intent intent= new Intent(this, ListeOffresActivity.class);
           startActivity(intent);
        }
        else if (view.getId()==bttDemandes.getId()){

        }
        else if (view.getId()==bttProfil.getId()){

        }
    }

    private void SignIn() {
        mAuth.signInWithEmailAndPassword(login.getText().toString(),password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCustomToken:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            //Toast.makeText(CustomAuthActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signUp() {
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), "123456")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCustomToken:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            //Toast.makeText(CustomAuthActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });


        //creation de l'utilisateur
        Map<String, Object> user = new HashMap<>();
        user.put("nom", nom.getText().toString());
        user.put("prenom", prenom.getText().toString());
        //user.put("adresse", adresse.getText().toString());
        user.put("ville", ville.getText().toString());
        user.put("pays", pays.getText().toString());
        user.put("email", email.getText().toString());
        user.put("telephone", phone.getText().toString());


        if (typeUser)
        {
            // Add a new document with a generated ID
            db.collection("agents").document(email.getText().toString())
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
        }
        else {
            // Add a new document with a generated ID
            db.collection("clients").document(email.getText().toString())
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
        }

    }

    void updateUI(FirebaseUser currentUser){

        System.out.println("Update");
        if (currentUser!=null) {
            if(typeUser){
                DocumentReference docRef = db.collection("agents").document(currentUser.getEmail());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                nomUser=document.getString("nom");
                                prenomUser=document.getString("prenom");
                                userinfo.setText("L'utilisateur "+nomUser+ " "+prenomUser+" est authentifié correctement");

                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

            }

            else {

                    DocumentReference docRef = db.collection("clients").document(currentUser.getEmail());
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    nomUser=document.getString("nom");
                                    prenomUser=document.getString("prenom");
                                    userinfo.setText("L'utilisateur "+nomUser+ " "+prenomUser+" est authentifié correctement");
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

            }

            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.VISIBLE);
        }else {
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);
        }
    }

}