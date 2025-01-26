package com.voluntaryapp.service;

// Importations JAX-WS
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

// Autres importations nécessaires
import com.voluntaryapp.database.DatabaseConnection;
import com.voluntaryapp.model.Mission;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebService(serviceName = "VolunteerService")
public class VolunteerService {

    @WebMethod(operationName = "getAvailableMissions")
    public List<Mission> getAvailableMissions() {
        List<Mission> missions = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM missions WHERE volunteer_id IS NULL AND status = 'PENDING'";
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Mission mission = new Mission();
                mission.setId(rs.getInt("id"));
                mission.setRequesterId(rs.getInt("requester_id"));
                mission.setTitle(rs.getString("title"));
                mission.setDescription(rs.getString("description"));
                mission.setStatus(rs.getString("status"));
                mission.setCreatedAt(rs.getString("created_at"));
                missions.add(mission);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return missions;
    }

    @WebMethod(operationName = "acceptMission")
    public boolean acceptMission(
            @WebParam(name = "missionId") int missionId,
            @WebParam(name = "volunteerId") int volunteerId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE missions SET volunteer_id = ?, status = 'IN_PROGRESS' WHERE id = ? AND volunteer_id IS NULL";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, volunteerId);
            stmt.setInt(2, missionId);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @WebMethod(operationName = "completeMission")
    public boolean completeMission(
            @WebParam(name = "missionId") int missionId,
            @WebParam(name = "volunteerId") int volunteerId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE missions SET status = 'COMPLETED' WHERE id = ? AND volunteer_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, missionId);
            stmt.setInt(2, volunteerId);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @WebMethod(operationName = "getVolunteerMissions")
    public List<Mission> getVolunteerMissions(
            @WebParam(name = "volunteerId") int volunteerId) {
        List<Mission> missions = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM missions WHERE volunteer_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, volunteerId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Mission mission = new Mission();
                mission.setId(rs.getInt("id"));
                mission.setRequesterId(rs.getInt("requester_id"));
                mission.setVolunteerId(rs.getInt("volunteer_id"));
                mission.setTitle(rs.getString("title"));
                mission.setDescription(rs.getString("description"));
                mission.setStatus(rs.getString("status"));
                mission.setCreatedAt(rs.getString("created_at"));
                missions.add(mission);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return missions;
    }
}