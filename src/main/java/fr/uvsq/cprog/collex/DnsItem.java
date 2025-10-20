package fr.uvsq.cprog.collex;

public class DnsItem {
    private AdresseIP adresseIP;
    private NomMachine  nomMachine;

    public DnsItem(AdresseIP adresseIP, NomMachine nomMachine) {
        this.adresseIP = adresseIP;
        this.nomMachine = nomMachine;
    }
    public void getInfo(){
        System.out.println(this.adresseIP.getFullAddress() + " " + this.nomMachine.getFullName());
    }
    public String returnInfo(){
       return this.adresseIP.getFullAddress() + " " + this.nomMachine.getFullName();
    }

    public AdresseIP getAdresseIP() {
        return adresseIP;
    }

    public NomMachine getNomMachine() {
        return nomMachine;
    }
}
