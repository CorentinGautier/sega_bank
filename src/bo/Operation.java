package bo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Operation implements Serializable {
    private static final String BACKUPS_DIR = "./ressources/exportCSV/";
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

    public void ExporteCSVOperation(List<Operation> listOperation) throws FileNotFoundException {
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
        try (FileOutputStream oss = new FileOutputStream(file.toFile())) {
            oss.write(String.format("%s;%s;%s;%s;%s\n","id de l operation","idAgence","idCompte","Type de l operation","Montant").getBytes());
            for(int i= 0; i<listOperation.size(); i++){
                Operation op = listOperation.get(i);
                String type;
                if(listOperation.get(i).getType() == Integer.parseInt("1")){
                    type = "retrait";
                }else{
                    type = "depot";
                }
                oss.write(String.format("%d;%d;%d;%s;%f\n",op.getId(),op.getIdAgence(),op.getIdCompte(),type,op.getMontant()).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
