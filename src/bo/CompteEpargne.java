package bo;

public class CompteEpargne extends Compte{
    private double tauxInteret;

    public  CompteEpargne(){

    }
    public CompteEpargne(int id, double solde, int numAgence) {
        super(id, solde, numAgence);
        this.tauxInteret = tauxInteret;
    }

    public double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    public void calculInteret(){
        setSolde(getSolde() + (getSolde() * tauxInteret));
    }
}
