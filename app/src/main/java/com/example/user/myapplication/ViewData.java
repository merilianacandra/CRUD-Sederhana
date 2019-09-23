package com.example.user.myapplication;

import java.util.ArrayList;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ViewData extends ListActivity implements OnItemLongClickListener {

    private DBDataSource dataSource;
    private ArrayList<Mahasiswa> values;
    private Button editButton;
    private Button delButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdata);
        dataSource = new DBDataSource(this);
        dataSource.open();
        values = dataSource.getAllMahasiswa();

        ArrayAdapter<Mahasiswa> adapter = new ArrayAdapter<Mahasiswa>(this,
                android.R.layout.simple_list_item_1, values);

        setListAdapter(adapter);

        ListView lv = (ListView) findViewById(android.R.id.list);
        lv.setOnItemLongClickListener(this);

    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> adapter, View v, int pos,
                                   final long id) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_view);
        dialog.setTitle("Pilih Aksi");
        dialog.show();
        final Mahasiswa b = (Mahasiswa) getListAdapter().getItem(pos);
        editButton = (Button) dialog.findViewById(R.id.button_edit_data);
        delButton = (Button) dialog.findViewById(R.id.button_delete_data);

        editButton.setOnClickListener(
                new OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        switchToEdit(b.getId());
                        dialog.dismiss();
                    }
                }
        );

        delButton.setOnClickListener(
                new OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        dataSource.deleteMahasiswa(b.getId());
                        dialog.dismiss();
                        finish();
                        startActivity(getIntent());
                    }
                }
        );

        return true;

    }

    public void switchToEdit(long id)
    {
        Mahasiswa b = dataSource.getMahasiswa(id);
        Intent i = new Intent(this, EditData.class);
        Bundle bun = new Bundle();
        bun.putLong("id", b.getId());
        bun.putString("nama", b.getNama_mhs());
        bun.putString("jrsn", b.getJrsn_mhs());
        bun.putString("nim", b.getNim_mhs());
        i.putExtras(bun);
        finale();
        startActivity(i);
    }

    public void finale()
    {
        ViewData.this.finish();
        dataSource.close();
    }
    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

}