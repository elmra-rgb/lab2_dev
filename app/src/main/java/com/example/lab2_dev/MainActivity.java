package com.example.lab2_dev;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Déclaration des composants UI
    private EditText champSuperficie;
    private EditText champNbPieces;
    private CheckBox casePiscine;
    private TextView zoneResultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        initialiserVues();

        // Configuration du bouton
        configurerBoutonCalcul();
    }

    private void initialiserVues() {
        champSuperficie = findViewById(R.id.edt_superficie);
        champNbPieces = findViewById(R.id.edt_nb_pieces);
        casePiscine = findViewById(R.id.chk_bassin);
        zoneResultat = findViewById(R.id.txt_affichage_resultat);
    }

    private void configurerBoutonCalcul() {
        Button boutonCalcul = findViewById(R.id.btn_calculer_impot);
        boutonCalcul.setOnClickListener(v -> executerCalculImpot());
    }

    private void executerCalculImpot() {
        try {
            // Récupération des valeurs saisies
            double valeurSuperficie = recupererValeurSuperficie();
            int valeurNbPieces = recupererValeurNbPieces();
            boolean presencePiscine = casePiscine.isChecked();

            // Calcul de l'impôt local
            double impotLocal = calculerImpotLocal(valeurSuperficie, valeurNbPieces, presencePiscine);

            // Affichage du résultat
            afficherResultat(impotLocal);

        } catch (NumberFormatException exception) {
            zoneResultat.setText("Erreur : Veuillez saisir des nombres valides");
        }
    }

    private double recupererValeurSuperficie() throws NumberFormatException {
        String texteSuperficie = champSuperficie.getText().toString();
        if (texteSuperficie.isEmpty()) {
            throw new NumberFormatException();
        }
        return Double.parseDouble(texteSuperficie);
    }

    private int recupererValeurNbPieces() throws NumberFormatException {
        String texteNbPieces = champNbPieces.getText().toString();
        if (texteNbPieces.isEmpty()) {
            throw new NumberFormatException();
        }
        return Integer.parseInt(texteNbPieces);
    }

    private double calculerImpotLocal(double superficie, int nbPieces, boolean aPiscine) {
        // Taux de base : 2 DH par m²
        double impotFoncierBase = superficie * 2;

        // Suppléments
        double supplementPieces = nbPieces * 50;
        double supplementPiscine = aPiscine ? 100 : 0;

        // Impôt total
        double impotTotal = impotFoncierBase + supplementPieces + supplementPiscine;

        return impotTotal;
    }

    private void afficherResultat(double impotTotal) {
        String messageResultat = String.format("Montant total des impôts locaux : %.2f DH", impotTotal);
        zoneResultat.setText(messageResultat);
    }
}