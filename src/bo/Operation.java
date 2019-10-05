package bo;

public class Operation {


    public enum Type {
        RETRAIT (1),DEPOT(2);

        private int val;

        Type(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
        public void setVal(int val) {
            this.val = val;
        }
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Agence agence;
    private Compte compte;
    private Type type;
    private double montant;

    public Operation() {
    }

    public Operation(Agence agence, Compte compte, Type type, double montant) {
        this.agence = agence;
        this.compte = compte;
        this.type = type;
        this.montant = montant;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
