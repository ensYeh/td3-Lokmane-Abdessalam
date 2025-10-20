package fr.uvsq.cprog.collex;

public class FindByIPCommande implements Commande {
    private final AdresseIP adresse;

    public FindByIPCommande( AdresseIP adresse) {
        this.adresse = adresse;
    }

    @Override
    public void execute() throws Exception {
        Dns dns = new Dns();
        DnsItem item = dns.getItem(adresse);
        if (item != null) {
            System.out.println("✅ Trouvé : " + item.returnInfo());
        } else {
            System.out.println("❌ Aucune entrée trouvée pour " + adresse.getFullAddress());
        }
    }
}
