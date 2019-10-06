import bo.*;
import dal.AgenceDAO;
import dal.CompteDAO;
import dal.IDAO;
import dal.OperationDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {


    private static Scanner sc = new Scanner(System.in);
    private static IDAO<Integer, Compte> compteDao = new CompteDAO();
    private static IDAO<Integer, Operation> opDAO = new OperationDAO();
    private static IDAO<Integer, Agence> agDAO = new AgenceDAO();

    public static void SegaBankMainMenu() {
        int response;
        boolean first = true;
        do {
            if (!first) {
                System.out.println("Mauvais choix... merci de recommencer !");
            }
            System.out.println("======================================");
            System.out.println("============== SEGA BANK =============");
            System.out.println("======================================");
            System.out.println("1 - Se connecter à un compte");
            System.out.println("2 - Creer un compte");
            System.out.println("3 - Voir la liste des comptes (seulement pour les administrateurs)");
            System.out.println("4 - Quitter");
            System.out.print("Entrez votre choix : ");
            try {
                response = sc.nextInt();
            } catch (InputMismatchException e) {
                response = -1;
            } finally {
                sc.nextLine();
            }
            first = false;
        } while (response < 1 || response > 4);

        switch (response) {
            case 1:
                connection();
                break;
            case 2:
                creerCompte();
                break;
            case 3:
                voirListeComptes();
                break;
            case 4 :
                break;
        }
    }

    private static void connection() {
        int id;
        System.out.print("Entrez votre identifiant : ");
        Compte c;
        try {
            id = sc.nextInt();
            c = compteDao.findBy(id);
            if (c == null) {
                System.out.print("Identifiant invalide, vous allez etre rediriger vers l'accueil\n");
                SegaBankMainMenu();
            } else {
                gestionCompte(c);
            }
        } catch (InputMismatchException e) {
            id = -1;
            System.out.print("Identifiant invalide, vous allez etre rediriger vers l'accueil\n");
            SegaBankMainMenu();
        } catch (SQLException| ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } finally {
            sc.nextLine();
        }
    }

    public static void modifSolde(Compte c, int typeModif) {
        //retrait c'est 1 de^pot c'est 2
        Operation operation = new Operation();
        if (typeModif == 1) {
            System.out.println("Indiquez le montant du retrait sur ce compte :");
        } else {
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
        if (typeModif == 1) {
            if (c.retrait(montant)) {
                System.out.println("Le montant va être retiré");
            } else {
                modifPossible = false;
                System.out.println("Impossible de retirer ce montant !");
            }
        } else {
            if (c.versement(montant)) {
                System.out.println("Le montant va être versé");
            } else {
                modifPossible = false;
                System.out.println("Impossible de verser ce montant !");
            }
        }
        if (modifPossible) {
            try {
                compteDao.update(c);
            } catch (ClassNotFoundException | SQLException | IOException e) {
                e.printStackTrace();
            }

            try {
                opDAO.create(operation);
            } catch (ClassNotFoundException | SQLException | IOException e) {
                e.printStackTrace();
            }
            System.out.println("Opération réussi");
            gestionCompte(c);
        } else {
            System.out.println("Voulez-vous tentez à nouveau l'opération ? Y-n");
            String choix = sc.nextLine();
            if (choix.toUpperCase().equals("Y")) {
                modifSolde(c, typeModif);
            } else {
                gestionCompte(c);
            }
        }


    }

    public static void gestionCompte(Compte c) {
        System.out.println("======================================");
        System.out.printf("===== COMPTE NUMERO %s \n", c.getId());
        System.out.printf("===== SOLDE : %s \n", c.getSolde());
        System.out.printf("===== AGENCE : %s \n", c.getNumAgence());
        if (c instanceof CompteSimple) {
            System.out.println("===== TYPE : Compte simple");
        } else if (c instanceof CompteEpargne) {
            System.out.println("===== TYPE : Compte epargne");
        } else {
            System.out.println("===== TYPE : Compte payant");
        }
        System.out.println("======================================");
        System.out.println("1 - Déposer de l'argent");
        System.out.println("2 - Retirer de l'argent");
        System.out.println("3 - Modifier son type de compte");
        System.out.println("4 - Modifier agence");
        System.out.println("5 - Exporter les opérations d'un compte ");
        System.out.println("6 - Deconnexion");
        if(c instanceof  CompteEpargne){
            System.out.println("7 - Imposer un taux d'interet");
        }
        System.out.print("Entrez votre choix : ");
        int choix = 0;
        do {
            try {
                choix = sc.nextInt();
                sc.nextLine(); //je sais pas pourquoi mais ce sc.nextLine() empêche d'accéder au menu
            } catch (Exception e) {
                System.out.println("Veuillez entrer un chiffre");
            }
        } while (choix < 0 && choix > 7);

        switch (choix) {
            case 1:
                modifSolde(c, 2);
                break;
            case 2:
                modifSolde(c, 1);
                break;
            case 3:
                System.out.println("Fonctionnalité non implémenté !");
                gestionCompte(c);
                break;
            case 4:
                System.out.println("Fonctionnalité non implémenté !");
                gestionCompte(c);
                break;
            case 5:
                exportCsv(c);
                break;
            case 6:
                SegaBankMainMenu();
                break;
            case 7:
                if(c instanceof  CompteEpargne){
                    imposerInteret( c);
                }
                else{
                    gestionCompte(c);
                }
                //SegaBankMainMenu();
                break;
        }
    }

    private static void creerCompte() {
        int type;
        System.out.print("Entrez le type de compte que vous voulez (1.Compte simple, 2.Compte epargne, 3.Compte payant) : ");
        Compte c;
        try {
            type = sc.nextInt();
            sc.nextLine();
            if (type == 1 || type == 2 || type == 3) {
                System.out.print("Somme à verser sur le compte : ");
                int somme = sc.nextInt();
                sc.nextLine();
                if (somme<0) {
                    System.out.print("Vous ne pouvez pas avoir un montant négatif sur un nouveau compte, vous allez etre rediriger vers l'accueil\n");
                    SegaBankMainMenu();
                }
                System.out.print("Numéro de l'agence qui gère votre compte : ");
                int agence = sc.nextInt();
                sc.nextLine();
                if (type == 1) {
                    c = new CompteSimple(0, somme, agence);
                } else if (type == 2) {
                    c = new CompteEpargne(0, somme, agence);
                } else {
                    c = new ComptePayant(0, somme, agence);
                }
                compteDao.create(c);
                System.out.print("Bienvenue,\n");
                System.out.print(c.toString() + "\n");
                System.out.print("Vous allez etre rediriger vers l'accueil\n");
                SegaBankMainMenu();
            } else {
                System.out.print("Type de compte invalide, vous allez etre rediriger vers l'accueil\n");
                SegaBankMainMenu();
            }
        } catch (InputMismatchException e) {
            type = -1;
            System.out.print("Type de compte invalide, vous allez etre rediriger vers l'accueil\n");
            SegaBankMainMenu();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void voirListeComptes() {
        String mdp;
        System.out.print("Entrez votre mot de passe administrateur : ");
        try {
            mdp = sc.nextLine();
            if (mdp.equals("admin")) {
                List<Compte> listComptes = compteDao.findAll();
                for (Compte c : listComptes) {
                    if (c instanceof CompteSimple) {
                        System.out.println("COMPTE : " + c.getId() + " / TYPE : Compte simple / SOLDE : " + c.getSolde() + " / AGENCE : " + c.getNumAgence());
                    } else if (c instanceof CompteEpargne) {
                        System.out.println("COMPTE : " + c.getId() + " / TYPE : Compte epargne / SOLDE : " + c.getSolde() + " / AGENCE : " + c.getNumAgence());
                    } else {
                        System.out.println("COMPTE : " + c.getId() + " / TYPE : Compte payant / SOLDE : " + c.getSolde() + " / AGENCE : " + c.getNumAgence());
                    }

                }
                System.out.print("Appuyez sur entrer pour revenir au menu principal\n");
            } else {
                System.out.print("Mot de pass invalide, vous allez etre rediriger vers l'accueil\n");
                SegaBankMainMenu();
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
        SegaBankMainMenu();
    }

    public static void main(String... args) {
        SegaBankMainMenu();
    }


    public static void exportCsv(Compte c){
        int id;
        Operation operation = new Operation();
        try {
            id = c.getId();
            List<Operation> list = opDAO.find(id);
            operation.ExporteCSVOperation(list);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Exportation Réussie !");
        gestionCompte(c);
    }

    public static void imposerInteret(Compte c)  {
        if(c instanceof CompteEpargne){
            System.out.println("Quel est le nouveau taux ?");
            double taux =  sc.nextDouble();
            sc.nextLine();
            ((CompteEpargne) c).setTauxInteret(taux);
            ((CompteEpargne) c).calculInteret();
            try {
                compteDao.update(c);
            } catch (SQLException | ClassNotFoundException | IOException  e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("non autorisé pour votre type de compte");
        }
        gestionCompte(c);
    }
}