package fr.uvsq.cprog.collex;

public class AdresseIP {
    private final short pre_partie;
    private final short deux_partie;
    private final short tro_partie;
    private final short quatr_partie;

    public AdresseIP(short pre_partie, short deux_partie, short tro_partie, short quatr_partie) {
        this.pre_partie = pre_partie;
        this.deux_partie = deux_partie;
        this.tro_partie = tro_partie;
        this.quatr_partie = quatr_partie;
    }

    public AdresseIP(String ipString) {
        // Vérifier si l'adresse IP est valide
        if (ipString == null || ipString.isEmpty()) {
            throw new IllegalArgumentException("L'adresse IP ne peut pas être vide ou null");
        }

        // Diviser la chaîne par les points
        String[] parties = ipString.split("\\.");

        // Vérifier qu'il y a exactement 4 parties
        if (parties.length != 4) {
            throw new IllegalArgumentException("L'adresse IP doit contenir exactement 4 parties");
        }

        // Créer un tableau de shorts pour stocker les 4 parties
        short[] resultat = new short[4];

        // Convertir chaque partie en short
        for (int i = 0; i < 4; i++) {
            try {
                short valeur = Short.parseShort(parties[i]);
                // Vérifier que la valeur est dans la plage valide (0-255)
                verifierShortValid(valeur);
                resultat[i] = valeur;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Partie invalide dans l'adresse IP: " + parties[i]);
            }
        }

        this.pre_partie = resultat[0];
        this.deux_partie = resultat[1];
        this.tro_partie = resultat[2];
        this.quatr_partie = resultat[3];
    }

    public String getFullAddress() {
        return this.pre_partie + "." + this.deux_partie + "." + this.tro_partie + "." + this.quatr_partie;
    }

    private void verifierShortValid(short short_verifier) {
        if (short_verifier < 0 || short_verifier > 255) {
            throw new IllegalArgumentException("Chaque partie doit être entre 0 et 255");
        }
    }
}