package com.voluntaryapp.test;

import com.voluntaryapp.service.RequesterService;
import com.voluntaryapp.model.Mission;
import java.util.List;

public class RequesterServiceTest {
    public static void main(String[] args) {
        RequesterService requesterService = new RequesterService();

        // Test 1: Création d'une mission
        System.out.println("Test 1: Création d'une nouvelle mission");
        boolean missionCreated = requesterService.createMission(
                1, // ID du demandeur (doit exister dans la base)
                "Aide a sortir",
                "Besoin d'aide "
        );
        System.out.println(missionCreated ? "✅ Mission créée avec succès" : "❌ Échec de création de la mission");

        // Test 2: Récupération des missions d'un demandeur
        System.out.println("\nTest 2: Récupération des missions du demandeur");
        List<Mission> missions = requesterService.getRequesterMissions(1);
        if (missions != null && !missions.isEmpty()) {
            System.out.println("✅ Missions récupérées avec succès");
            System.out.println("➡️ Nombre de missions : " + missions.size());
            for (Mission mission : missions) {
                System.out.println("  - " + mission.getTitle() + " (Status: " + mission.getStatus() + ")");
            }
        } else {
            System.out.println("❌ Aucune mission trouvée ou erreur");
        }
    }
}