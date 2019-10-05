import bo.*;
/*
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class App {


    private static Scanner sc = new Scanner( System.in );

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
            System.out.println( "2 - Quitter" );
            System.out.print( "Identifiant : " );
            try {
                response = sc.nextInt();
            } catch ( InputMismatchException e ) {
                response = -1;
            } finally {
                sc.nextLine();
            }
            first = false;
        } while ( response < 1 || response > 2 );

        switch ( response ) {
            case 1:
                createContact();
                break;
            case 2:
                updateContact();
                break;
        }
    }

    private static void sortContacts(boolean naturalSort) {

        if ( naturalSort ) {
            Collections.sort( book.getContacts() );
        } else {
            Collections.sort(book.getContacts(), new ContactEmailComparator() );
        }
        dspContacts( true );
    }

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
    }

    public static void main( String... args ) {

        SegaBankMainMenu();
    }
}
*/