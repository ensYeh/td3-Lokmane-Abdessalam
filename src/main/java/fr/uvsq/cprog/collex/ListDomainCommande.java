package fr.uvsq.cprog.collex;

import java.util.Comparator;
import java.util.List;

public class ListDomainCommande implements Commande {
    private final String domain;
    private final boolean sortByIP;

    public ListDomainCommande(String domain, boolean sortByIP) {
        this.domain = domain;
        this.sortByIP = sortByIP;
    }

    @Override
    public void execute() throws Exception {
        Dns dns = new Dns();
        List<DnsItem> items = dns.getItems(domain);

        if (sortByIP) {
            items.sort(Comparator.comparing(item -> item.getAdresseIP().getFullAddress()));
        } else {
            items.sort(Comparator.comparing(item -> item.getNomMachine().getFullName()));
        }

        for (DnsItem item : items) {
            System.out.println(item.getAdresseIP().getFullAddress() + " " + item.getNomMachine().getFullName());
        }
    }
}
