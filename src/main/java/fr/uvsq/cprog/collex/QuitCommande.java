package fr.uvsq.cprog.collex;

public class QuitCommande implements Commande {

    @Override
    public void execute() {
        System.out.println("👋 Fin du programme. Au revoir !");
        System.exit(0);
    }
}
