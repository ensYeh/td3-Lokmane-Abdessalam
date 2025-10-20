package fr.uvsq.cprog.collex;


public class FindByNameCommande implements Commande {
    private final NomMachine nomMachine;

    public FindByNameCommande(NomMachine nomMachine) {
        this.nomMachine = nomMachine;
    }

    @Override
    public void execute() throws Exception {
        Dns dns = new Dns();
        DnsItem item = dns.getItem(nomMachine);
        if (item != null) {
            System.out.println("✅ Trouvé : " + item.returnInfo() );
        } else {
            System.out.println("❌ Aucune machine trouvée avec le nom : " + nomMachine.getFullName());
        }
    }
}
