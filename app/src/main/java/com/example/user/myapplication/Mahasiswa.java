package com.example.user.myapplication;

public class Mahasiswa {

    private long id;
    private String nama_mhs;
    private String jrsn_mhs;
    private String nim_mhs;

    public Mahasiswa()
    {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNama_mhs() {
        return nama_mhs;
    }

    public void setNama_mhs(String nama_barang) {
        this.nama_mhs = nama_barang;
    }

    public String getJrsn_mhs() {
        return jrsn_mhs;
    }

    public void setJrsn_mhs(String merk_barang) {
        this.jrsn_mhs = merk_barang;
    }

    public String getNim_mhs() {
        return nim_mhs;
    }

    public void setNim_mhs(String harga_barang) {
        this.nim_mhs = harga_barang;
    }

    @Override
    public String toString()
    {
        return "\nNama      : " + nama_mhs +"\nJurusan  :" + jrsn_mhs + "\nNIM         : " + nim_mhs;
    }
}

