package bo;

import java.util.ArrayList;
import java.util.List;

public abstract class Compte {
    private int id;
    private double solde;
    List<Operation> operations;

    public Compte(){

    }

    public Compte(int id, double solde) {
        this.id = id;
        this.solde = solde;
        operations = new ArrayList();
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

    @Override
    public String toString() {
        return "Compte{" +
                "id=" + id +
                ", solde=" + solde +
                '}';
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }
}
