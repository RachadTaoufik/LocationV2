package com.example.location.activities;

import static android.content.ContentValues.TAG;

import static java.lang.Thread.sleep;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.R;
import com.example.location.model.Offre;
import com.example.location.utile.MyAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;

public class ListeOffresActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    LinkedList<Offre> offres;

    RecyclerView myRecycler;
    boolean terminated;
    ProgressDialog progdiag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_offres);


        myRecycler = (RecyclerView) findViewById(R.id.listeOffres);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        System.out.println("Starting activity....");
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        offres = new LinkedList<Offre>();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        getOffres(currentUser);
    }

    void getOffres(FirebaseUser currentUser) {
        terminated=false;
        new AsyncTask() {

            //exécuter des tâches avant le démarrage du thread
            //on a encore le droit d’accéder au thread principal du Gui
            protected void onPreExecute(){
                showDialog();
            }

            protected Object doInBackground(Object[] objects) {
                DocumentReference docRef = db.collection("agents").document(currentUser.getEmail());
                docRef.collection("offres").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Offre offre = new Offre(
                                        document.getString("titre"),
                                        document.getString("description"),
                                        document.get("superficie", Float.class),
                                        document.get("pieces", Integer.class),
                                        document.get("sdb", Integer.class),
                                        document.get("loyer", Float.class)
                                );
                                offres.add(offre);
                                System.out.println(offres);
                                terminated = true;
                            }
                        } else {
                            terminated = true;
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
                while (!terminated)
                    System.out.println(".");
                return null;
            }

            //exécuter des tàches pendant la réalisation de la tâche principale du thread
            //on a encore le droit d’accéder au thread principal du Gui
            protected void onProgressUpdate(Integer... progress) {}

            protected void onPostExecute(Object result) {
                myRecycler.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(ListeOffresActivity.this);
                myRecycler.setLayoutManager(layoutManager);
                MyAdapter myAdapter = new MyAdapter(offres, ListeOffresActivity.this);
                myRecycler.setAdapter(myAdapter);
                System.out.print("finished");
                hideDialog();
            }
        }.execute();

    }


    void showDialog(){
        progdiag = new ProgressDialog(this);
        progdiag.setMessage("Veuillez patienter, les donnees sont en cours de chargement ... ");
        progdiag.setIndeterminate(true);

        progdiag.show();
    }

    void hideDialog(){
        progdiag.dismiss();
    }


}
