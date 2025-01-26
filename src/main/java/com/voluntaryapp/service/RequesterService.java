package com.voluntaryapp.service;

import com.voluntaryapp.database.DatabaseConnection;
import com.voluntaryapp.model.Mission;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebService(serviceName = "RequesterService")
public class RequesterService {

    @WebMethod(operationName = "createMission")
    public boolean createMission(@WebParam(name = "requesterId") int requesterId,
                                 @WebParam(name = "title") String title,
                                 @WebParam(name = "description") String description) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO missions (requester_id, title, description, status) VALUES (?, ?, ?, 'PENDING')";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, requesterId);
            stmt.setString(2, title);
            stmt.setString(3, description);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @WebMethod(operationName = "getRequesterMissions")
    public List<Mission> getRequesterMissions(@WebParam(name = "requesterId") int requesterId) {
        List<Mission> missions = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM missions WHERE requester_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, requesterId);

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