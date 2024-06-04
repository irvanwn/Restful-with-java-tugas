package com.getjson;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
    
@Path("/transaksi")
public class controllerTransaksi {
    boolean isAuthenticated = controllerRegister.isAuthenticated;

    private static ArrayList<modelTransaksi> mdlTransaksi = new ArrayList<>();
    private controllerBarang barangController;

    // Constructor
    public controllerTransaksi() {
        this.barangController = new controllerBarang();
        initializeTransaksi();
    }

    // Method to initialize transactions
    private void initializeTransaksi() {
        List<modelBarang> mbrgList = barangController.mbrgList;
        int[] kodeTransaksi = { 1, 2 };
        int[] totalTransaksi = { 49000, 112000 };

        for (int i = 0; i < kodeTransaksi.length; i++) {
            modelTransaksi m = new modelTransaksi();
            m.setKodeTransaksi(kodeTransaksi[i]);

            List<modelBarang> barangList = new ArrayList<>();
            if (i == 0) {
                barangList.add(mbrgList.get(0));
                barangList.add(mbrgList.get(5));
            } else if (i == 1) {
                barangList.add(mbrgList.get(3));
                barangList.add(mbrgList.get(4));
            }

            m.setListBarang(barangList);
            m.setTotalTransaksi(totalTransaksi[i]);
            mdlTransaksi.add(m);
        }
    }

    @GET
    @Path("/cek/{kodeTransaksi}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cariData(@PathParam("kodeTransaksi") int kodeTransaksi) {
        if (!isAuthenticated) {
            return Response.status(401).entity("Authentication required").build();
        }
        try {
            modelTransaksi transaksi = null;
            for (modelTransaksi t : mdlTransaksi) {
                if (t.getKodeTransaksi() == kodeTransaksi) {
                    transaksi = t;
                    break;
                }
            }

            if (transaksi != null) {
                return Response.status(Response.Status.OK).entity(transaksi).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("transkasi kodeTransaksi " + kodeTransaksi + " tidak ditemukan")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("gagal melakukan transaksi").build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    // @Produces(MediaType.APPLICATION_JSON)
    public Response addTransaksi(modelTransaksi request) {
        if (!isAuthenticated) {
            return Response.status(401).entity("Authentication required").build();
        }
        try {
            mdlTransaksi.add(request);
            return Response.status(Response.Status.CREATED).entity("transaksi berhasil").build();

        } catch (Exception e) {

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("gagal menambah transaksi").build();
        }
    }
}
