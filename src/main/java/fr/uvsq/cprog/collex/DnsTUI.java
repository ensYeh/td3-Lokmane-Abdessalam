package fr.uvsq.cprog.collex;

import java.util.Scanner;

public class DnsTUI {
    private final Scanner scanner;

    public DnsTUI() {
        this.scanner = new Scanner(System.in);
    }

    public Commande nextCommande() {
        System.out.print("> ");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit")) {
            return new QuitCommande();
        }

        try {
            // add IP nom.machine
            if (input.startsWith("add ")) {
                String[] parts = input.split("\\s+");
                return new AddCommande(new AdresseIP(parts[1]), new NomMachine(parts[2]));
            }

            // ls [-a] domaine
            if (input.startsWith("ls")) {
                String[] parts = input.split("\\s+");
                boolean sortByIP = false;
                String domain;

                if (parts.length == 2) {
                    domain = parts[1];
                } else if (parts.length == 3 && parts[1].equals("-a")) {
                    sortByIP = true;
                    domain = parts[2];
                } else {
                    System.out.println("❌ Utilisation : ls [-a] <domaine>");
                    return null;
                }

                return new ListDomainCommande(domain, sortByIP);
            }

            // vérifier si c’est une IP ou un nom
            if (input.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
                return new FindByIPCommande( new AdresseIP(input));
            } else {
                return new FindByNameCommande( new NomMachine(input));
            }

        } catch (Exception e) {
            System.out.println("⚠️ Erreur : " + e.getMessage());
            return null;
        }
    }

    public void affiche(String message) {
        System.out.println(message);
    }
}



