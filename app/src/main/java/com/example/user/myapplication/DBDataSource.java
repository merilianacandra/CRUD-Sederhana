package com.example.user.myapplication;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBDataSource {

    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = { DBHelper.COLUMN_ID,
            DBHelper.COLUMN_NAME, DBHelper.COLUMN_MERK,DBHelper.COLUMN_HARGA};


    public DBDataSource(Context context)
    {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Mahasiswa createMahasiswa(String nama, String jrsn, String nim) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, nama);
        values.put(DBHelper.COLUMN_MERK, jrsn);
        values.put(DBHelper.COLUMN_HARGA, nim);

        long insertId = database.insert(DBHelper.TABLE_NAME, null,
                values);

        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                allColumns, DBHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);


        Mahasiswa newMahasiswa = cursorToMahasiswa(cursor);
        cursor.close();

        return newMahasiswa;
    }

    private Mahasiswa cursorToMahasiswa(Cursor cursor)
    {

        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setId(cursor.getLong(0));
        mahasiswa.setNama_mhs(cursor.getString(1));
        mahasiswa.setJrsn_mhs(cursor.getString(2));
        mahasiswa.setNim_mhs(cursor.getString(3));

        return mahasiswa;
    }

    public ArrayList<Mahasiswa> getAllMahasiswa() {
        ArrayList<Mahasiswa> daftarMahasiswa = new ArrayList<Mahasiswa>();

        // select all SQL query
        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Mahasiswa barang = cursorToMahasiswa(cursor);
            daftarMahasiswa.add(barang);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return daftarMahasiswa;
    }

    public Mahasiswa getMahasiswa(long id)
    {
        Mahasiswa mahasiswa = new Mahasiswa();
        Cursor cursor = database.query(DBHelper.TABLE_NAME, allColumns, "_id ="+id,
                null, null, null, null);
        cursor.moveToFirst();
        mahasiswa = cursorToMahasiswa(cursor);
        cursor.close();

        return mahasiswa;
    }

    public void updateBarang(Mahasiswa b)
    {

        String strFilter = "_id=" + b.getId();
        ContentValues args = new ContentValues();
        args.put(DBHelper.COLUMN_NAME, b.getNama_mhs());
        args.put(DBHelper.COLUMN_MERK, b.getJrsn_mhs());
        args.put(DBHelper.COLUMN_HARGA, b.getNim_mhs() );
        database.update(DBHelper.TABLE_NAME, args, strFilter, null);
    }

    public void deleteMahasiswa(long id)
    {
        String strFilter = "_id=" + id;
        database.delete(DBHelper.TABLE_NAME, strFilter, null);
    }
}