import bo.*;
import dal.CompteDAO;
import dal.IDAO;
import dal.OperationDAO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class App {


    private static Scanner sc = new Scanner( System.in );
    private static IDAO<Integer, Compte> compteDao = new CompteDAO();

    public static void SegaBankMainMenu() {
        int response;
        boolean first = true;
        do {
            if ( !first ) {
                System.out.println( "Mauvais choix... merci de recommencer !" );
            }
            System.out.println( "======================================" );
            System.out.println( "============== SEGA BANK =============" );
            System.out.println( "======================================" );
            System.out.println( "1 - Se connecter à un compte" );
            System.out.println( "2 - Creer un compte" );
            System.out.println( "3 - Voir la liste des comptes (seulement pour les administrateurs)" );
            System.out.println( "4 - Quitter" );
            System.out.print( "Entrez votre choix : " );
            try {
                response = sc.nextInt();
            } catch ( InputMismatchException e ) {
                response = -1;
            } finally {
                sc.nextLine();
            }
            first = false;
        } while ( response < 1 || response > 4 );

        switch ( response ) {
            case 1:
                connection();
                break;
            case 2:
                creerCompte();
                break;
            case 3:
                voirListeComptes();
                break;
        }
    }

    private static void connection() {
        int id;
        System.out.print( "Entrez votre identifiant : " );
        Compte c;
        try {
            id = sc.nextInt();
            c = compteDao.findBy(id);
            if (c==null) {
                System.out.print( "Identifiant invalide, vous allez etre rediriger vers l'accueil\n" );
                SegaBankMainMenu();
            } else {
                gestionCompte(c);
            }
        } catch ( InputMismatchException e ) {
            id = -1;
            System.out.print( "Identifiant invalide, vous allez etre rediriger vers l'accueil\n" );
            SegaBankMainMenu();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            sc.nextLine();
        }
    }
    /*
    private static Contact.Gender getGenderFromKeyboard( boolean mandatory ) {

        boolean first = true;
        int response;
        do {
            if ( !first ) {
                System.out.println( "Mauvais choix... merci de recommencer !" );
            }
            System.out
                    .printf( "Sélectionner le genre du contact %s...%n", (!mandatory ? "(Tapez Entrée pour passer)" : "") );
            System.out.println( "1 - Masculin" );
            System.out.println( "2 - Feminin" );
            try {
                response = sc.nextInt();
            } catch ( InputMismatchException e ) {
                response = -1;
            } finally {
                sc.nextLine();
            }
            first = false;
        } while ( mandatory && (response < 1 || response > 2) );
        if ( !mandatory && response != 1 && response != 2 ) return null;
        else return Contact.Gender.values()[response - 1];
    }

    private static void createContact() {

        System.out.println( "======================================" );
        System.out.println( "======== CREATION D'UN CONTACT =======" );
        System.out.println( "======================================" );
        Contact contact = new Contact();
        System.out.print( "Entrez le nom : " );
        contact.setName( sc.nextLine() );
        System.out.print( "Entrez l'email : " );
        contact.setEmail( sc.nextLine() );
        contact.setGender( getGenderFromKeyboard( true ) );
        book.getContacts().add( contact );
        System.out.println( "Contact créé avec succès!" );
        dspMainMenu();
    }

    private static void updateContact() {

        System.out.println( "======================================" );
        System.out.println( "====== MODIFICATION D'UN CONTACT =====" );
        System.out.println( "======================================" );
        System.out.println( "Choisissez le contact à modifier ..." );
        boolean first = true;
        int response, size = book.getContacts().size();
        do {
            if ( !first ) {
                System.out.println( "Mauvais choix... merci de recommencer !" );
            }
            dspContacts( false );
            System.out.print( "Votre choix : " );
            try {
                response = sc.nextInt();
            } catch ( InputMismatchException e ) {
                response = -1;
            } finally {
                sc.nextLine();
            }
            first =false;
        } while ( response < 1 || response > size );

        Contact contact = book.getContacts().get( (response - 1) );
        System.out.printf( "======== MODIFICATION DE (%s) %n =======", contact.getName() );

        System.out.printf( "Entrez le nom (%s): ", contact.getName() );
        String name = sc.nextLine();
        if ( name != null && !name.isEmpty() ) {
            contact.setName( name );
        }
        System.out.printf( "Entrez l'email (%s): ", contact.getEmail() );
        String email = sc.nextLine();
        if ( email != null && !email.isEmpty() ) {
            contact.setEmail( email );
        }
        System.out.printf( "Entrez le genre (%s): ", contact.getGender().getLabel() );
        Contact.Gender gender = getGenderFromKeyboard( false );
        if ( gender != null && gender != contact.getGender() ) {
            contact.setGender( gender );
        }
        System.out.println( "Contact modifié avec succès!" );
        dspMainMenu();
    }

    private static void storeCurrentBook() {

        System.out.println( "======================================" );
        System.out.println( "=== SAUVEGARDE DU CARNET D'ADRESSES ==" );
        System.out.println( "======================================" );

        Path bkpPath = Paths.get( BACKUPS_DIR );
        if ( !Files.isDirectory( bkpPath ) ) {
            try {
                Files.createDirectory( bkpPath );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }

        String bkpFileName = new SimpleDateFormat( "yyyy-MM-dd-hh-mm-ss" ).format( new Date() ) + ".ser";
        Path file = Paths.get( BACKUPS_DIR + bkpFileName );
        try ( ObjectOutputStream oos = new ObjectOutputStream( Files.newOutputStream( file ) ) ) {
            oos.writeObject( book );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        System.out.println("Sauvegarde terminée...");
        dspMainMenu();
    }

    private static void restoreBackup() {

        System.out.println( "======================================" );
        System.out.println( "= RESTAURATION D'UN CARNET D'ADRESSES " );
        System.out.println( "======================================" );

        boolean first = true;
        int response;
        List<Path> paths = new ArrayList<>();

        try ( DirectoryStream<Path> ds = Files.newDirectoryStream( Paths.get( BACKUPS_DIR ), "*.ser" ) ) {
            for ( Path path : ds ) {
                paths.add( path );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        int size = paths.size();
        if ( size > 0 ) {
            System.out.println( "Choisissez la sauvegarde à restaurer ..." );
            do {
                if ( !first ) {
                    System.out.println( "Mauvais choix... merci de recommencer !" );
                }
                for ( int i = 0; i < size; ++i ) {
                    System.out.printf(" %d - %s%n", (i+1), paths.get(i).getFileName() );
                }
                System.out.print( "Votre choix : " );
                try {
                    response = sc.nextInt();
                } catch ( InputMismatchException e ) {
                    response = -1;
                }
                first = false;
            } while ( response < 1 || response > size );

            try ( ObjectInputStream ois = new ObjectInputStream( Files.newInputStream( paths.get(response-1) ) ) ) {
                book = (Book) ois.readObject();
            } catch ( IOException e ) {
                e.printStackTrace();
            } catch ( ClassNotFoundException e ) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Pas de sauvegarde trouvée...");
        }
        dspMainMenu();
    }
    private static void dspContacts( boolean dspMenu ) {

        if ( dspMenu ) {
            System.out.println( "======================================" );
            System.out.println( "======== LISTE DE VOS CONTACTS =======" );
            System.out.println( "======================================" );
        }
        List<Contact> list = book.getContacts();
        for ( int i = 0, length = list.size(); i < length; ++i ) {
            System.out.printf( "%d - %s%n", i + 1, list.get( i ) );
        }
        if ( dspMenu ) {
            dspMainMenu();
        }
    }*/
    public static void modifSolde(Compte c, int typeModif){
        //retrait c'est 1 de^pot c'est 2
        IDAO<Integer, Operation> opDAO = new OperationDAO();
        IDAO<Integer, Compte> cDAO = new CompteDAO();
        Operation operation = new Operation();
        if(typeModif == 1){
            System.out.println("Indiquez le montant du retrait sur ce compte :");
        }
        else{
            System.out.println("Indiquez le montant du versement sur ce compte :");
        }
        int montant = sc.nextInt();
        sc.nextLine();
        boolean modifPossible = true;
        operation.setMontant(montant);
        operation.setIdCompte(c.getId());
        operation.setIdAgence(c.getNumAgence());
        operation.setType(typeModif);

        System.out.println(operation);
        if(typeModif == 1){
            if(c.retrait(montant)){
                System.out.println("Le montant va être retiré");
            }
            else{
                modifPossible = false;
                System.out.println("Impossible de retirer ce montant !");
            }
        }
        else{
            if(c.versement(montant)){
                System.out.println("Le montant va être versé");
            }
            else{
                modifPossible =false;
                System.out.println("Impossible de verser ce montant !");
            }
        }
        if(modifPossible){
            try {
                cDAO.update(c);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                opDAO.create(operation);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Opération réussi");
            gestionCompte(c);
        }
        else{
            System.out.println("Voulez-vous tentez à nouveau l'opération ? Y-n");
            String choix = sc.nextLine();
            if(choix.toUpperCase().equals("Y")){
                modifSolde(c,typeModif);
            }
            else{
                gestionCompte(c);
            }
        }





    }

    public static void gestionCompte(Compte c) {
        System.out.println( "======================================" );
        System.out.printf( "===== COMPTE NUMERO %s \n", c.getId() );
        System.out.printf( "===== SOLDE : %s \n", c.getSolde() );
        System.out.printf( "===== AGENCE : %s \n", c.getNumAgence() );
        if(c instanceof CompteSimple) {
            System.out.println( "===== TYPE : Compte simple" );
        } else if(c instanceof CompteEpargne) {
            System.out.println( "===== TYPE : Compte epargne" );
        } else {
            System.out.println( "===== TYPE : Compte payant" );
        }
        System.out.println( "======================================" );
        System.out.println( "1 - Déposer de l'argent" );
        System.out.println( "2 - Retirer de l'argent" );
        System.out.println( "3 - Modifier son compte" );
        System.out.println( "4 - Modifier agence" );
        System.out.println( "5 - Exporter les opérations d'un compte " );
        System.out.println( "6 - Quitter" );
        System.out.print( "Entrez votre choix : " );
        int choix = 0;
        do{
            try {
                choix = sc.nextInt();
                //sc.nextLine(); je sais pas pourquoi mais ce sc.nextLine() empêche d'accéder au menu
            }catch (Exception e){
                System.out.println("Veuillez entrer un chiffre");
            }
        }while(choix <0 && choix >6);

        switch (choix){
            case 1:
                modifSolde(c,2);
                break;
            case 2 :
                modifSolde(c,1);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;

        }

    }

    private static void creerCompte() {
        /*int type;
        System.out.print( "Entrez le type de compte que vous voulez (1.Compte simple, 2.Compte epargne, 3.Compte payant) : " );
        Compte c;
        try {
            type = sc.nextInt();
            if (type!=1 || type!=2 ||type!=3) {
                System.out.print( "Type de compte invalide, vous allez etre rediriger vers l'accueil\n" );
                SegaBankMainMenu();
            } else {
                gestionCompte(c);
            }
        } catch ( InputMismatchException e ) {
            type = -1;
            System.out.print( "Type de compte invalide, vous allez etre rediriger vers l'accueil\n" );
            SegaBankMainMenu();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            sc.nextLine();
        }*/
    }

    public static void voirListeComptes() {
        String mdp;
        System.out.print( "Entrez votre mot de passe administrateur : " );
        try {
            mdp = sc.nextLine();
            if (mdp.equals("admin")) {
                List<Compte> listComptes = compteDao.findAll();
                for (Compte c: listComptes) {
                    if(c instanceof CompteSimple) {
                        System.out.println( "COMPTE : " + c.getId() + " / TYPE : Compte simple / SOLDE : " + c.getSolde() + " / AGENCE : " + c.getNumAgence());
                    } else if(c instanceof CompteEpargne) {
                        System.out.println( "COMPTE : " + c.getId() + " / TYPE : Compte epargne / SOLDE : " + c.getSolde() + " / AGENCE : " +c .getNumAgence());
                    } else {
                        System.out.println( "COMPTE : " + c.getId() + " / TYPE : Compte payant / SOLDE : " + c.getSolde() + " / AGENCE : " + c.getNumAgence());
                    }
                    
                }
                System.out.print( "Appuyez sur entrer pour revenir au menu principal\n" );
            } else {
                System.out.print( "Mot de pass invalide, vous allez etre rediriger vers l'accueil\n" );
                SegaBankMainMenu();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            sc.nextLine();
        }
        SegaBankMainMenu();
    }

    public static void main( String... args ) {
        SegaBankMainMenu();
    }


}