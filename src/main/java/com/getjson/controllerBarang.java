package com.getjson;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/barang")
public class controllerBarang {
    // Shared list to store modelBarang objects
    boolean isAuthenticated = controllerRegister.isAuthenticated;

    public static ArrayList<modelBarang> mbrgList = new ArrayList<>();

    static {
        int[] kode = { 1, 2, 3, 4, 5, 6 };
        int[] harga = { 49000, 112000, 5000, 2000, 3000, 280000 };
        String[] kategori = { "Aksesoris Komputer", "Aksesoris Komputer",
                "Alat Tulis", "Alat Tulis", "Alat Tulis", "Hardisk & SSD" };
        String[] barang = { "Mouse", "Keyboard", "Pulpen Tizo 0.8",
                "Pensi 2B Stedler", "Pulpen Standard", "Adata SU800" };
        int[] stok = { 5, 11, 32, 25, 7, 5 };

        for (int i = 0; i < barang.length; i++) {
            modelBarang m = new modelBarang();
            m.setKodeBarang(kode[i]);
            m.setNamaBarang(barang[i]);
            m.setHargaBarang(harga[i]);
            m.setStokBarang(stok[i]);
            m.setKategoriBarang(kategori[i]);

            mbrgList.add(m);
        }
    }

    public modelBarang getBarangById(int id) {
        for (modelBarang barang : mbrgList) {
            if (barang.getKodeBarang() == id) {
                return barang;
            }
        }
        return null; // Return null if the product with the given ID is not found
    }

    @GET
    @Path("/cekData")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDataBarang() throws ClassCastException {
        return Response.status(200).entity(mbrgList).build();
    }

    @POST
    @Path("/addData")
    @Produces(MediaType.APPLICATION_JSON)
    public Response inputData(modelBarang mbrg) {
        if (!isAuthenticated) {
            return Response.status(401).entity("Authentication required").build();
        }
        if (!(mbrg.getNamaBarang() instanceof String)) {
            String result = "Tipe data harus String";
            return Response.status(415).entity(result).build();
        }

        if (mbrg.getNamaBarang().equals("") || mbrg.getKategoriBarang().equals("")
                || mbrg.getHargaBarang() == 0 || mbrg.getKodeBarang() == 0 || mbrg.getStokBarang() < 0) {
            String result = "Data Kosong kudu di isi";
            return Response.status(500).entity(result).build();
        } else {
            mbrgList.add(mbrg);
            String result = "Data Barang sudah masuk \nID       : " + mbrg.getKodeBarang()
                    + "\nNama     : " + mbrg.getNamaBarang()
                    + "\nHarga    : " + mbrg.getHargaBarang()
                    + "\nStok     : " + mbrg.getStokBarang()
                    + "\nKategori : " + mbrg.getKategoriBarang();
            return Response.status(201).entity(result).build();
        }
    }

    @POST
    @Path("/editData/{kodeBarang}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editData(@PathParam("kodeBarang") int kodeBarang, modelBarang mbrg) {
        if (!isAuthenticated) {
            return Response.status(401).entity("Authentication required").build();
        }
        if (mbrg.getNamaBarang().equals("") || mbrg.getKategoriBarang().equals("")
                || mbrg.getHargaBarang() == 0 || mbrg.getStokBarang() == 0) {
            String result = "Data Kosong kudu di isi";
            return Response.status(500).entity(result).build();
        }

        modelBarang brgKode = null;
        for (int i = 0; i < mbrgList.size(); i++) {
            modelBarang brg = mbrgList.get(i);
            if (brg.getKodeBarang() == kodeBarang) {
                brgKode = brg;
                break;
            }
        }

        if (brgKode != null) {
            brgKode.setNamaBarang(mbrg.getNamaBarang());
            brgKode.setHargaBarang(mbrg.getHargaBarang());
            brgKode.setKategoriBarang(mbrg.getKategoriBarang());

            String result = "Data id-" + kodeBarang + " Berhasil di ubah"
                    + "\nNama     : " + mbrg.getNamaBarang()
                    + "\nHarga    : " + mbrg.getHargaBarang()
                    + "\nStok     : " + mbrg.getStokBarang()
                    + "\nKategori : " + mbrg.getKategoriBarang();
            return Response.status(200).entity(result).build();
        } else {
            String result = "Data dengan kodebarang " + kodeBarang + " tidak ditemukan";
            return Response.status(404).entity(result).build();
        }
    }

    @DELETE
    @Path("/deleteData/{kodeBarang}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteData(@PathParam("kodeBarang") int kodeBarang) {
        if (!isAuthenticated) {
            return Response.status(401).entity("Authentication required").build();
        }
        if (kodeBarang == 0) {
            String result = "Kode barang tidak boleh kosong";
            return Response.status(400).entity(result).build();
        }

        for (int i = 0; i < mbrgList.size(); i++) {
            modelBarang brg = mbrgList.get(i);
            if (brg.getKodeBarang() == kodeBarang) {
                mbrgList.remove(i);
                String result = "Data dengan kode barang " + kodeBarang + " berhasil dihapus";
                return Response.status(200).entity(result).build();
            }
        }

        String result = "Data dengan kode barang " + kodeBarang + " tidak ditemukan";
        return Response.status(404).entity(result).build();
    }

    @GET
    @Path("/cariData/{barang}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<modelBarang> cariData(@PathParam("barang") String barang) {
        ArrayList<modelBarang> dataSearched = new ArrayList<>();

        Pattern pattern = Pattern.compile(barang, Pattern.CASE_INSENSITIVE);

        for (int i = 0; i < mbrgList.size(); i++) {
            modelBarang brg = mbrgList.get(i);
            Matcher matcher = pattern.matcher(brg.getNamaBarang());
            if (matcher.find()) {
                dataSearched.add(brg);
            }
        }

        return dataSearched;
    }

    @GET
    @Path("/filter/harga/{value1}/{value2}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<modelBarang> filterHarga(@PathParam("value1") int value1, @PathParam("value2") int value2) {
        ArrayList<modelBarang> dataSearched = new ArrayList<>();

        for (int i = 0; i < mbrgList.size(); i++) {
            modelBarang brg = mbrgList.get(i);
            if (brg.getHargaBarang() >= value1 && brg.getHargaBarang() <= value2) {
                dataSearched.add(brg);
            }
        }

        return dataSearched;
    }

}
