package com.getjson;

import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/masukApp")
public class controllerRegister {
    public static boolean isAuthenticated = false;
    private static ArrayList<modelRegister> regList = new ArrayList<>();

    static {
        String[] username = { "admin", "Budi" };
        String[] password = { "default", "cek123" };

        for (int i = 0; i < username.length; i++) {
            modelRegister r = new modelRegister();
            r.setUsername(username[i]);
            r.setPassword(password[i]);
            regList.add(r);
        }
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(modelRegister mdlReg) {
        if (mdlReg.getUsername().equals("") || mdlReg.getPassword().equals("")) {
            String result = "Username / password tidak boleh kosong";
            return Response.status(500).entity(result).build();
        } else {
            boolean authenticated = false;
            for (int i = 0; i < regList.size(); i++) {
                modelRegister user = regList.get(i);
                if (user.getUsername().equals(mdlReg.getUsername())
                        && user.getPassword().equals(mdlReg.getPassword())) {
                    authenticated = true;
                    break;
                }
            }
            if (authenticated) {
                String result = "Login suskes, selamat datang " + mdlReg.getUsername() + "";
                isAuthenticated = true;
                return Response.status(200).entity(result).build();
            } else {
                String result = "Invalid username or password";
                return Response.status(401).entity(result).build();
            }
        }
    }

    @POST
    @Path("/signup")
    @Produces(MediaType.APPLICATION_JSON)
    public Response signUp(modelRegister mdlReg) {
        for (int i = 0; i < regList.size(); i++) {
            modelRegister user = regList.get(i);
            if (user.getUsername().equals(mdlReg.getUsername())) {
                String result = "Username taken";
                return Response.status(500).entity(result).build(); //
            }
        }
        if (mdlReg.getUsername().equals("") || mdlReg.getPassword().equals("")) {
            String result = "Username or password cannot be empty";
            return Response.status(500).entity(result).build(); //
        } else {
            regList.add(mdlReg);
            String result = "Sudah ter registrasi username : " + mdlReg.getUsername() + " password : "
                    + mdlReg.getPassword() + ")";
            return Response.status(201).entity(result).build();
        }
    }

}
