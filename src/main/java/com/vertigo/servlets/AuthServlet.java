package com.vertigo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vertigo.web.dtos.Credentials;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json");

        Credentials creds = mapper.readValue(req.getInputStream(), Credentials.class);

        resp.getWriter().write(creds.toString());

    }
}
