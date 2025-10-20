package fr.uvsq.cprog.collex;

import fr.uvsq.cprog.collex.Commande;

public class DnsApp {

    private final DnsTUI tui;

    public DnsApp() {
        this.tui = new DnsTUI();
    }

    /**
     * 🔹 Boucle principale : lire la commande, exécuter et afficher le résultat
     */
    public void run() {
        tui.affiche("=== Bienvenue dans l'application DNS ===");
        while (true) {
            Commande cmd = tui.nextCommande();
            if (cmd != null) {
                try {
                    cmd.execute();
                } catch (Exception e) {
                    tui.affiche("⚠️ Erreur : " + e.getMessage());
                }
            }
        }
    }

    /**
     * 🔹 Point d'entrée
     */
    public static void main(String[] args) {
        new DnsApp().run();
    }
}
