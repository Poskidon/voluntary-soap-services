package com.voluntaryapp;

import com.voluntaryapp.service.AuthenticationService;
import com.voluntaryapp.service.RequesterService;
import com.voluntaryapp.service.VolunteerService;

// Important : utilisation de l'import correct pour Endpoint
import javax.xml.ws.Endpoint;

public class Main {
    public static void main(String[] args) {
        try {
            // Publier le service d'authentification
            String authAddress = "http://localhost:8080/auth";
            Endpoint.publish(authAddress, new AuthenticationService());
            System.out.println("Service d'authentification démarré à : " + authAddress + "?wsdl");

            // Publier le service des demandeurs
            String requesterAddress = "http://localhost:8081/requester";
            Endpoint.publish(requesterAddress, new RequesterService());
            System.out.println("Service des demandeurs démarré à : " + requesterAddress + "?wsdl");

            // Publier le service des bénévoles
            String volunteerAddress = "http://localhost:8082/volunteer";
            Endpoint.publish(volunteerAddress, new VolunteerService());
            System.out.println("Service des bénévoles démarré à : " + volunteerAddress + "?wsdl");

            System.out.println("Tous les services sont démarrés et prêts à être utilisés.");
            System.out.println("Appuyez sur Ctrl+C pour arrêter les services.");
        } catch (Exception e) {
            System.out.println("Erreur lors du démarrage des services : " + e.getMessage());
            e.printStackTrace();
        }
    }
}