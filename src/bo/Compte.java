package bo;

public abstract class Compte {
    @Override
    public String toString() {
        return "Compte : " +
                "id=" + id +
                ", solde=" + solde +
                ", Agence=" + numAgence;
    }

    private int id;
    private double solde;
    private int numAgence;

    public Compte(){

    }

    public Compte(int id, double solde, int numAgence) {
        this.id = id;
        this.solde = solde;
        this.numAgence = numAgence;
    }

    public boolean versement(double montant){
        setSolde(getSolde()+montant);
        return true;
    }
    public boolean retrait(double montant){
        setSolde(getSolde()-montant);
        return true;
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
