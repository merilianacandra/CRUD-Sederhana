package com.example.user.myapplication;


import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateData extends Activity implements OnClickListener{

    private Button buttonSubmit;
    private EditText edNama;
    private EditText edJrsn;
    private EditText edNim;
    private DBDataSource dataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_data);

        buttonSubmit = (Button) findViewById(R.id.buttom_submit);
        buttonSubmit.setOnClickListener(this);
        edNama = (EditText) findViewById(R.id.nama_mhs);
        edJrsn = (EditText) findViewById(R.id.jrsn_mhs);
        edNim = (EditText) findViewById(R.id.nim_mhs);

        dataSource = new DBDataSource(this);

        dataSource.open();
    }

    @Override
    public void onClick(View v) {

        String nama = null;
        String jrsn = null;
        String nim = null;
        @SuppressWarnings("unused")

                Mahasiswa mahasiswa = null;
        if(edNama.getText()!=null && edJrsn.getText()!=null && edNim.getText()!=null)
        {
            nama = edNama.getText().toString();
            jrsn = edJrsn.getText().toString();
            nim = edNim.getText().toString();
        }

        switch(v.getId())
        {
            case R.id.buttom_submit:
                mahasiswa = dataSource.createMahasiswa(nama, jrsn, nim);
                Toast.makeText(this, "Data masuk dengan \n" +
                        "Nama : " + mahasiswa.getNama_mhs() +
                        "\nJurusan :" + mahasiswa.getJrsn_mhs() +
                        "\nNIM : " + mahasiswa.getNim_mhs(), Toast.LENGTH_LONG).show();
                break;
        }
    }
}