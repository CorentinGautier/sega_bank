package bo;

public class CompteSimple extends Compte{
    private int decouvert = -150;

    public CompteSimple(){

    }
    public CompteSimple(int id, double solde, int numAgence) {
        super(id, solde, numAgence);
    }

    @Override
    public boolean retrait(double montant) {
        if((getSolde() - montant) < decouvert){
            return false;
        }
        else{
            setSolde(getSolde()-montant);
            return true;
        }
    }

    public int getDecouvert() {
        return decouvert;
    }

    public void setDecouvert(int decouvert) {
        this.decouvert = decouvert;
    }
}
