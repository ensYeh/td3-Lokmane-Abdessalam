package fr.uvsq.cprog.collex;

public class AddCommande implements Commande {
    private final AdresseIP adresse;
    private final NomMachine nomMachine;

    public AddCommande( AdresseIP adresse, NomMachine nomMachine) {
        this.adresse = adresse;
        this.nomMachine = nomMachine;
    }

    @Override
    public void execute() throws Exception {
        Dns dns = new Dns();
        dns.addItem(adresse, nomMachine);
        System.out.println("✅ Ajouté : " + adresse.getFullAddress() + " " + nomMachine.getFullName());
    }
}
