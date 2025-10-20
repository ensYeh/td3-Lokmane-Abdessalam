package fr.uvsq.cprog.collex;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Dns {
    public Dns() {
        load_file();
    }
    void load_file(){
        try (InputStream input = Dns.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("⚠️ Fichier config.properties non trouvé !");
                return;
            }

            Properties props = new Properties();
            props.load(input); // Charger le contenu du fichier

            // 🔹 Lire une valeur spécifique
            String filePath = props.getProperty("database.file");
            // Charger le fichier référencé
            try (InputStream dataStream = Dns.class.getClassLoader().getResourceAsStream(filePath)) {
                if (dataStream != null) {
                    System.out.println("Fichier trouvé : " + filePath);
                } else {
                    System.out.println("Fichier introuvable : " + filePath);
                }
                // Lire la première ligne
                BufferedReader reader = new BufferedReader(new InputStreamReader(dataStream));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Récupère un DnsItem correspondant à l'adresse IP donnée.
     */
    public DnsItem getItem(AdresseIP adresseIP) throws Exception {
        String filePath = loadDatabaseFilePath();
        return searchByIP(filePath, adresseIP);
    }
    public DnsItem getItem(NomMachine nomMachine) throws Exception {
        String filePath = loadDatabaseFilePath();
        return searchByMachineName(filePath, nomMachine);
    }

    public List<DnsItem> getItems(String nomDomain) throws Exception {
        String filePath = loadDatabaseFilePath();
        return searchByNomDomain(filePath, nomDomain);
    }

    /**
     * Recherche une adresse IP ou un nom de domaine dans le fichier de base de données.
     */
    private DnsItem searchByIP(String filePath, AdresseIP adresseIP) throws Exception {
        try (BufferedReader reader = openResourceFile(filePath)) {   // ✅ use helper
            String search = adresseIP.getFullAddress();
            String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length == 2) {
                        String ip = parts[0];
                        String domain = parts[1];

                        if (ip.equalsIgnoreCase(search) || domain.equalsIgnoreCase(search)) {
                            System.out.println("✅ Trouvé : " + ip + " " + domain);
                            return new DnsItem(adresseIP, new NomMachine(domain, domain));
                        }
                    }
                }
            }

            throw new Exception("❌ Aucune correspondance trouvée pour : " + adresseIP.getFullAddress());
        }


    /**
     * Recherche une ligne dans le fichier de base de données correspondant au nom de machine.
     */
    private DnsItem searchByMachineName(String filePath, NomMachine nomMachine) throws Exception {
        try (BufferedReader reader = openResourceFile(filePath)) {   // ✅ use helper
            String searchName = nomMachine.getFullName();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 2) {
                    String ip = parts[0];
                    String domain = parts[1];
                    if (domain.equalsIgnoreCase(searchName)) {
                        return new DnsItem(new AdresseIP(ip), nomMachine);
                    }
                }
            }
        }
        throw new Exception("❌ Aucune correspondance trouvée pour : " + nomMachine.getFullName());
    }

    private List<DnsItem> searchByNomDomain(String filePath, String NomDomain) throws Exception {
        List<DnsItem> results = new ArrayList<>();
        try (BufferedReader reader = openResourceFile(filePath)) {   // ✅ use helper

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 2) {
                    String ip = parts[0];
                    String domain = parts[1];
                    // Vérifie si la partie du domaine contient le texte recherché
                    if (domain.toLowerCase().contains(NomDomain.toLowerCase())) {
                        results.add(new DnsItem(new AdresseIP(ip), new NomMachine(domain)));
                    }

                }
            }
            return results;
        }catch (Exception e){
            throw new Exception("❌ Aucune correspondance trouvée pour : " + NomDomain);

        }
    }


    // Reusable method to open a resource file from classpath
    private BufferedReader openResourceFile(String filePath) throws Exception {
        InputStream dataStream = Dns.class.getClassLoader().getResourceAsStream(filePath);
        if (dataStream == null) {
            throw new FileNotFoundException("⚠️ Fichier introuvable : " + filePath);
        }
        return new BufferedReader(new InputStreamReader(dataStream));
    }

    private String loadDatabaseFilePath() throws Exception {
        try (InputStream input = Dns.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new FileNotFoundException("⚠️ Fichier config.properties non trouvé !");
            }

            Properties props = new Properties();
            props.load(input);

            String filePath = props.getProperty("database.file");
            if (filePath == null || filePath.isEmpty()) {
                throw new Exception("⚠️ La clé 'database.file' est absente du fichier config.properties !");
            }

            return filePath;
        }

    }




    // 🔹 Ouvre le fichier défini dans config.properties
    private File openDatabaseFile() throws Exception {
        // Charger le chemin depuis config.properties
        String filePath;
        try (InputStream input = Dns.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new FileNotFoundException("⚠️ Fichier config.properties non trouvé !");
            }

            Properties props = new Properties();
            props.load(input);
            filePath = props.getProperty("database.file");

            if (filePath == null || filePath.isEmpty()) {
                throw new Exception("⚠️ La clé 'database.file' est absente du fichier config.properties !");
            }
        }

        // Crée un objet File à partir du chemin trouvé
        File dbFile = new File(filePath);

        // Si le dossier n’existe pas, le créer
        if (!dbFile.getParentFile().exists()) {
            dbFile.getParentFile().mkdirs();
        }

        // Si le fichier n’existe pas, le créer
        if (!dbFile.exists()) {
            boolean created = dbFile.createNewFile();
            if (!created) {
                throw new IOException("❌ Impossible de créer le fichier de base de données : " + dbFile.getAbsolutePath());
            }
        }

        return dbFile;
    }

    // 🔹 Ajoute une nouvelle entrée IP + NomMachine dans le fichier
    public void addItem(AdresseIP adresseIP, NomMachine nomMachine) throws Exception {
        File dbFile = openDatabaseFile();

        try (FileWriter writer = new FileWriter(dbFile, true)) {
            String newLine = adresseIP.getFullAddress() + " " + nomMachine.getFullName() + "\n";
            writer.write(newLine);
            System.out.println("✅ Ajouté : " + newLine.trim());
        } catch (IOException e) {
            throw new Exception("❌ Erreur lors de l’ajout dans le fichier : " + e.getMessage());
        }
    }

}
