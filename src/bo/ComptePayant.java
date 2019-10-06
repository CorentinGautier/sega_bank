package bo;

public class ComptePayant extends Compte {
    private static final double COMMISSION = 0.05;

    public ComptePayant(){

    }
    public ComptePayant(int id, double solde, int numAgence) {
        super(id, solde, numAgence);
    }

    @Override
    public boolean versement(double montant) {
        setSolde((getSolde()+montant)-montant*COMMISSION);
        return true;
    }

    @Override
    public boolean retrait(double montant) {
        setSolde((getSolde()-montant)-montant*COMMISSION);
        return true;
    }
}
