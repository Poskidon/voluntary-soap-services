package com.voluntaryapp.test;

import com.voluntaryapp.service.VolunteerService;
import com.voluntaryapp.model.Mission;
import java.util.List;

public class VolunteerServiceTest {
    public static void main(String[] args) {
        VolunteerService volunteerService = new VolunteerService();

        // Test 1: Récupération des missions disponibles
        System.out.println("Test 1: Récupération des missions disponibles");
        List<Mission> availableMissions = volunteerService.getAvailableMissions();
        if (availableMissions != null) {
            System.out.println("✅ Liste des missions disponibles récupérée");
            System.out.println("➡️ Nombre de missions disponibles : " + availableMissions.size());
            for (Mission mission : availableMissions) {
                System.out.println("  - " + mission.getTitle());
            }
        } else {
            System.out.println("❌ Erreur lors de la récupération des missions");
        }

        // Test 2: Acceptation d'une mission (si des missions sont disponibles)
        if (availableMissions != null && !availableMissions.isEmpty()) {
            System.out.println("\nTest 2: Acceptation d'une mission");
            Mission firstMission = availableMissions.get(0);
            boolean accepted = volunteerService.acceptMission(firstMission.getId(), 2); // ID du bénévole = 2
            System.out.println(accepted ? "✅ Mission acceptée avec succès" : "❌ Échec de l'acceptation");
        }

        // Test 3: Récupération des missions d'un bénévole
        System.out.println("\nTest 3: Récupération des missions du bénévole");
        List<Mission> volunteerMissions = volunteerService.getVolunteerMissions(2); // ID du bénévole = 2
        if (volunteerMissions != null) {
            System.out.println("✅ Missions du bénévole récupérées");
            System.out.println("➡️ Nombre de missions : " + volunteerMissions.size());
            for (Mission mission : volunteerMissions) {
                System.out.println("  - " + mission.getTitle() + " (Status: " + mission.getStatus() + ")");
            }
        }

        // Test 4: Compléter une mission
        if (volunteerMissions != null && !volunteerMissions.isEmpty()) {
            System.out.println("\nTest 4: Compléter une mission");
            Mission missionToComplete = volunteerMissions.get(0);
            boolean completed = volunteerService.completeMission(missionToComplete.getId(), 2);
            System.out.println(completed ? "✅ Mission marquée comme complétée" : "❌ Échec de la complétion");
        }
    }
}