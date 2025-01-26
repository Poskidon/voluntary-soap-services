package com.voluntaryapp.service;

import com.voluntaryapp.database.DatabaseConnection;
import com.voluntaryapp.model.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebService(serviceName = "AuthenticationService")
public class AuthenticationService {

    @WebMethod(operationName = "login")
    public User login(@WebParam(name = "email") String email,
                      @WebParam(name = "password") String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setRole(rs.getString("role"));
                user.setCreatedAt(rs.getString("created_at"));
                return user;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod(operationName = "register")
    public boolean register(@WebParam(name = "email") String email,
                            @WebParam(name = "password") String password,
                            @WebParam(name = "name") String name,
                            @WebParam(name = "role") String role) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (email, password, name, role) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, name);
            stmt.setString(4, role);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}