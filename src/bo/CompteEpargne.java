package bo;

public class CompteEpargne extends Compte{

    private double tauxInteret;

    public CompteEpargne(int id, double solde, double tauxInteret) {
        super(id, solde);
        this.tauxInteret = tauxInteret;
    }

    public double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }
}
