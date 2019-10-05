package bo;

public class ComptePayant extends Compte{
    private static final double COMMISSION = 0.05;

    public ComptePayant(){

    }
    public ComptePayant(int id, double solde, int numAgence) {
        super(id, solde, numAgence);
    }

    @Override
    public void versement(double montant) {
        setSolde((getSolde()+montant)-montant*COMMISSION);
    }

    @Override
    public void retrait(double montant) {
        setSolde((getSolde()-montant)-montant*COMMISSION);
    }
}
