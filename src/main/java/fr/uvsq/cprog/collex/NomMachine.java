package fr.uvsq.cprog.collex;

public class NomMachine {
    private final String nom_machine;
    private final String nom_domain;

    public NomMachine(String nomMachine, String nomDomain) {
        nom_machine = nomMachine;
        nom_domain = nomDomain;
    }
    public NomMachine(String fullNomMachine) throws Exception{
        // Vérifier si fullNomMachine est valide
        if (fullNomMachine == null || fullNomMachine.isEmpty()) {
            throw new IllegalArgumentException("full Nom Machine ne peut pas être vide ou null");
        }
        int idx = fullNomMachine.indexOf('.');
        if (idx == -1) throw new Exception("full Nom Machine n'ai pas valid");

        this.nom_machine = fullNomMachine.substring(0, idx);
        this.nom_domain =  fullNomMachine.substring(idx + 1);;
    }

    String getFullName() {
        return this.nom_machine + "." + this.nom_domain;
    }
}
//193.51.25.12