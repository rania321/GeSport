package tn.Gesport.test;

import tn.Gesport.models.Reclamation;
import tn.Gesport.models.Response;
import tn.Gesport.services.ServiceReclamation;
import tn.Gesport.services.ServiceResponse;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class MainResponse {
    public static void main(String[] args) {
        // Create a service for Reclamation and Response
        ServiceReclamation reclamationService = new ServiceReclamation();
        ServiceResponse responseService = new ServiceResponse();

        // Choose a valid reclamation ID for testing
        int reclamationIdForTesting = 31; // Update with a valid ID from your database

        // Retrieve the reclamation from the database
        Reclamation savedReclamation = reclamationService.getById(reclamationIdForTesting);

        if (savedReclamation != null) {
            System.out.println("Retrieved Reclamation: " + savedReclamation);

            // Create a sample response associated with the saved reclamation
            Response response = new Response();
            response.setIdRec(savedReclamation.getIdRec());
            response.setDateRep(Timestamp.valueOf(LocalDateTime.now()));
            response.setContenuRep("This is a test response");

            // Add the response to the database
            responseService.add(response);

            // Retrieve the response from the database
            Response savedResponse = responseService.getById(response.getIdRep());
            System.out.println("Retrieved Response: " + savedResponse);

            // Clean up: delete the response and reclamation from the database
            // responseService.delete(savedResponse.getIdRep());
            // reclamationService.delete(savedReclamation);
        } else {
            System.out.println("Reclamation with ID " + reclamationIdForTesting + " not found.");
        }
    }
}
