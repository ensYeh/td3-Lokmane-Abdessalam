package fr.uvsq.cprog.collex;

import java.util.List;

public class FindByDomainCommande implements Commande {
    private final String domain;

    public FindByDomainCommande(String domain) {
        this.domain = domain;
    }

    @Override
    public void execute() throws Exception {
        Dns dns = new Dns();
        List<DnsItem> results = dns.getItems(domain);
        if (results.isEmpty()) {
            System.out.println("❌ Aucun résultat pour le domaine : " + domain);
        } else {
            System.out.println("✅ Machines trouvées pour le domaine " + domain + " :");
            for (DnsItem item : results) {
                System.out.println("   - " + item);
            }
        }
    }
}
