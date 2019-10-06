package bo;

import java.util.ArrayList;
import java.util.List;

public abstract class Compte {
    @Override
    public String toString() {
        return "Compte{" +
                "id=" + id +
                ", solde=" + solde +
                ", Agence=" + numAgence +
                '}';
    }

    private int id;
    private double solde;
    private int numAgence;

    public Compte(){

    }

    public Compte(int id, double solde, int numAgence) {
        this.id = id;
        this.solde = solde;
        numAgence = numAgence;
    }

    public void versement(double montant){
        setSolde(getSolde()+montant);
    }
    public void retrait(double montant){
        setSolde(getSolde()-montant);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public int getNumAgence() {
        return numAgence;
    }

    public void setNumAgence(int numAgence) {
        this.numAgence = numAgence;
    }
}
