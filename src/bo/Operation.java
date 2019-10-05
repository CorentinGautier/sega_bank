package bo;

public class Operation {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int idAgence;
    private int idCompte;
    private int type;
    private double montant;

    public Operation() {
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", idAgence=" + idAgence +
                ", idCompte=" + idCompte +
                ", type=" + type +
                ", montant=" + montant +
                '}';
    }

    public int getIdAgence() {
        return idAgence;
    }

    public void setIdAgence(int idAgence) {
        this.idAgence = idAgence;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public Operation(int idAgence, int idCompte, int type, double montant) {
        this.idAgence = idAgence;
        this.idCompte = idCompte;
        this.type = type;
        this.montant = montant;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
