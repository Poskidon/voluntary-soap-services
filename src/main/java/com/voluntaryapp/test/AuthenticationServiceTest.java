package com.voluntaryapp.test;

import com.voluntaryapp.service.AuthenticationService;
import com.voluntaryapp.model.User;

public class AuthenticationServiceTest {
    public static void main(String[] args) {
        AuthenticationService authService = new AuthenticationService();

        // Test 1: Inscription d'un utilisateur
        System.out.println("Test 1: Inscription d'un nouveau bénévole");
        boolean registerResult = authService.register(
                "Ayman@buiji.com",
                "password123",
                "Ayamn Bouiji",
                "VOLUNTEER"
        );
        System.out.println(registerResult ? "✅ Inscription réussie" : "❌ Échec de l'inscription");

        // Test 2: Connexion avec les identifiants créés
        System.out.println("\nTest 2: Tentative de connexion");
        User loggedUser = authService.login("Ayman@buiji.com", "password123");
        if (loggedUser != null) {
            System.out.println("✅ Connexion réussie");
            System.out.println("➡️ Utilisateur connecté : " + loggedUser.getName());
            System.out.println("➡️ Rôle : " + loggedUser.getRole());
        } else {
            System.out.println("❌ Échec de la connexion");
        }

        // Test 3: Tentative de connexion avec des identifiants incorrects
        System.out.println("\nTest 3: Tentative de connexion avec mauvais mot de passe");
        User failedLogin = authService.login("Ayman@buiji.com", "wrongpassword");
        System.out.println(failedLogin == null ? "✅ Échec attendu" : "❌ La connexion n'aurait pas dû réussir");
    }
}