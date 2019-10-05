package bo;

public class CompteSimple extends Compte{
    private int decouvert;

    public CompteSimple(){

    }
    public CompteSimple(int id, double solde, int numAgence) {
        super(id, solde, numAgence);
        this.decouvert = decouvert;
    }

    @Override
    public void retrait(double montant) {
        if((getSolde() - montant) < decouvert){
            //gérer le problème
        }
        else{
            setSolde(getSolde()-montant);
        }
    }

    public int getDecouvert() {
        return decouvert;
    }

    public void setDecouvert(int decouvert) {
        this.decouvert = decouvert;
    }
}
