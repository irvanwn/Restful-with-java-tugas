package com.getjson;

import java.util.List;

public class modelTransaksi {
    private int kodeTransaksi;
    private List<modelBarang> listBarang;
    private int totalTransaksi;

    public int getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(int kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public List<modelBarang> getListBarang() {
        return listBarang;
    }

    public void setListBarang(List<modelBarang> listBarang) {
        this.listBarang = listBarang;
    }

    public int getTotalTransaksi() {
        return totalTransaksi;
    }

    public void setTotalTransaksi(int totalTransaksi) {
        this.totalTransaksi = totalTransaksi;
    }

}
