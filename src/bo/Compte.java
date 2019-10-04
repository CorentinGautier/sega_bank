package bo;

public abstract class Compte {
    private int id;
    private double solde;

    public Compte(int id, double solde) {
        this.id = id;
        this.solde = solde;
    }

    public void versement(double montant){

    }
    public void retrait(double montant){

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

    public void setSolde(float solde) {
        this.solde = solde;
    }
}
