package com.example.user.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditData extends Activity implements OnClickListener{

    private DBDataSource dataSource;

    private long id;
    private String nim;
    private String jrsn;
    private String nama;

    private EditText edNama;
    private EditText edNim;
    private EditText edJrsn;

    private TextView txId;

    private Button btnSave;
    private Button btnCancel;

    private Mahasiswa mahasiswa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data);

        edNama = (EditText) findViewById(R.id.editText_nama);
        edNim = (EditText) findViewById(R.id.editText_nim);
        edJrsn = (EditText) findViewById(R.id.editText_jrsn);
        txId = (TextView) findViewById(R.id.text_id_mhs);

        dataSource = new DBDataSource(this);
        dataSource.open();

        Bundle bun = this.getIntent().getExtras();
        id = bun.getLong("id");
        nim = bun.getString("nim");
        jrsn = bun.getString("jrsn");
        nama = bun.getString("nama");

        txId.append(String.valueOf(id));
        edNama.setText(nama);
        edNim.setText(nim);
        edJrsn.setText(jrsn);

        //set listener pada tombol
        btnSave = (Button) findViewById(R.id.button_save_update);
        btnSave.setOnClickListener(this);
        btnCancel = (Button) findViewById(R.id.button_cancel_update);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {

            case R.id.button_save_update :
                mahasiswa = new Mahasiswa();
                mahasiswa.setNim_mhs(edNim.getText().toString());
                mahasiswa.setNama_mhs(edNama.getText().toString());
                mahasiswa.setJrsn_mhs(edJrsn.getText().toString());
                mahasiswa.setId(id);
                dataSource.updateBarang(mahasiswa);
                Intent i = new Intent(this, ViewData.class);
                startActivity(i);
                EditData.this.finish();
                dataSource.close();
                break;

            case R.id.button_cancel_update :
                finish();
                dataSource.close();
                break;
        }
    }
}
