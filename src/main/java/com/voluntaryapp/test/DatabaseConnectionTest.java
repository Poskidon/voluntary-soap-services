package com.voluntaryapp.test;

import com.voluntaryapp.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        System.out.println("Test de connexion à la base de données...");

        try {
            // Test 1: Tenter d'établir une connexion
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("✅ Connexion établie avec succès!");

            // Test 2: Vérifier si on peut exécuter une requête simple
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1");
            if (rs.next()) {
                System.out.println("✅ Exécution de requête réussie!");
            }

            // Test 3: Vérifier si les tables existent
            ResultSet tables = conn.getMetaData().getTables(null, null, "users", null);
            if (tables.next()) {
                System.out.println("✅ La table 'users' existe!");
            } else {
                System.out.println("❌ La table 'users' n'existe pas encore.");
                System.out.println("➡️ Vous devez exécuter le script SQL pour créer les tables.");
            }

            // Fermeture des ressources
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("✅ Test terminé avec succès!");

        } catch (Exception e) {
            System.out.println("❌ Erreur lors du test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}