package com.example.user.myapplication;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBDataSource {

    //inisialiasi SQLite Database
    private SQLiteDatabase database;

    //inisialisasi kelas DBHelper
    private DBHelper dbHelper;

    //ambil semua nama kolom
    private String[] allColumns = { DBHelper.COLUMN_ID,
            DBHelper.COLUMN_NAME, DBHelper.COLUMN_MERK,DBHelper.COLUMN_HARGA};

    //DBHelper diinstantiasi pada constructor
    public DBDataSource(Context context)
    {
        dbHelper = new DBHelper(context);
    }

    //membuka/membuat sambungan baru ke database
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    //menutup sambungan ke database
    public void close() {
        dbHelper.close();
    }

    //method untuk create/insert barang ke database
    public Mahasiswa createBarang(String nama, String merk, String harga) {

        // membuat sebuah ContentValues, yang berfungsi
        // untuk memasangkan data dengan nama-nama
        // kolom pada database
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, nama);
        values.put(DBHelper.COLUMN_MERK, merk);
        values.put(DBHelper.COLUMN_HARGA, harga);

        // mengeksekusi perintah SQL insert data
        // yang akan mengembalikan sebuah insert ID
        long insertId = database.insert(DBHelper.TABLE_NAME, null,
                values);

        // setelah data dimasukkan, memanggil
        // perintah SQL Select menggunakan Cursor untuk
        // melihat apakah data tadi benar2 sudah masuk
        // dengan menyesuaikan ID = insertID
        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                allColumns, DBHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        // pindah ke data paling pertama
        cursor.moveToFirst();

        // mengubah objek pada kursor pertama tadi
        // ke dalam objek barang
        Mahasiswa newMahasiswa = cursorToMahasiswa(cursor);

        //close cursor
        cursor.close();

        //mengembalikan barang baru
        return newMahasiswa;
    }

    private Mahasiswa cursorToMahasiswa(Cursor cursor)
    {
        // buat objek barang baru
        Mahasiswa mahasiswa = new Mahasiswa();
        // debug LOGCAT
        //Log.v("info", "The getLONG "+cursor.getLong(0));
        //Log.v("info", "The setLatLng "+cursor.getString(1)+","+cursor.getString(2));

        // Set atribut pada objek barang dengan
        // data kursor yang diambil dari database
        mahasiswa.setId(cursor.getLong(0));
        mahasiswa.setNama_barang(cursor.getString(1));
        mahasiswa.setMerk_barang(cursor.getString(2));
        mahasiswa.setHarga_barang(cursor.getString(3));

        //kembalikan sebagai objek barang
        return mahasiswa;
    }

    //mengambil semua data barang
    public ArrayList<Mahasiswa> getAllMahasiswa() {
        ArrayList<Mahasiswa> daftarBarang = new ArrayList<Mahasiswa>();

        // select all SQL query
        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        // pindah ke data paling pertama
        cursor.moveToFirst();
        // jika masih ada data, masukkan data barang ke
        // daftar barang
        while (!cursor.isAfterLast()) {
            Mahasiswa barang = cursorToMahasiswa(cursor);
            daftarBarang.add(barang);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return daftarBarang;
    }

    //ambil satu barang sesuai id
    public Mahasiswa getMahasiswa(long id)
    {
        Mahasiswa mahasiswa = new Mahasiswa();

        Cursor cursor = database.query(DBHelper.TABLE_NAME, allColumns, "_id ="+id, null, null, null, null);

        cursor.moveToFirst();

        mahasiswa = cursorToMahasiswa(cursor);

        cursor.close();

        return mahasiswa;
    }

    //update barang yang diedit
    public void updateBarang(Mahasiswa b)
    {
        //ambil id barang
        String strFilter = "_id=" + b.getId();
        //memasukkan ke content values
        ContentValues args = new ContentValues();
        //masukkan data sesuai dengan kolom pada database
        args.put(DBHelper.COLUMN_NAME, b.getNama_barang());
        args.put(DBHelper.COLUMN_MERK, b.getMerk_barang());
        args.put(DBHelper.COLUMN_HARGA, b.getHarga_barang() );
        //update query
        database.update(DBHelper.TABLE_NAME, args, strFilter, null);
    }

    // delete barang sesuai ID
    public void deleteBarang(long id)
    {
        String strFilter = "_id=" + id;
        database.delete(DBHelper.TABLE_NAME, strFilter, null);
    }
}