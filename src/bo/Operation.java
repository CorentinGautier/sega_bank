package bo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Operation implements Serializable {
private static final String BACKUPS_DIR = "./ressources/backups/";
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

    public void ExporteCSVOperation(ArrayList ArrayListOperation) throws FileNotFoundException {
        System.out.println(" ------ sauvegarde ------- ");
        System.out.println(ArrayListOperation);
        Path BkpPath = Paths.get(BACKUPS_DIR);
        if (!Files.isDirectory(BkpPath)) {
            try {
                Files.createDirectory(BkpPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String bkpFileName = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date()) + ".csv";
        Path file = Paths.get(BACKUPS_DIR + "Operations_"+bkpFileName);
        try (ObjectOutputStream oss = new ObjectOutputStream(Files.newOutputStream(file))) {
            for(int i= 0; i<ArrayListOperation.size(); i++){
                System.out.println(ArrayListOperation.size()); // retourne 2
                for(var item : ArrayListOperation){
                }
           //     oss.writeObject(ArrayListOperation.get(i).toString()); // affiche toutes les valeurs dans une même case
                    oss.writeObject(ArrayListOperation.get(i).toString());


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("sauvegarde terminée");

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
