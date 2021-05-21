package com.maad.bankticket;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.maad.bankticket.model.HomePhoto;
import com.maad.bankticket.model.HomePhotoAdapter;

import java.util.ArrayList;
import java.util.List;

public class Home extends ParentActivity {

    TextView title;
    ImageView image;
    RecyclerView recyclerView;
    List<HomePhoto> data;
    HomePhotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle(R.string.home);

        title = findViewById(R.id.title);
        image = findViewById(R.id.image);
        recyclerView = findViewById(R.id.recycle_view);
        data = createAdapterData();
        adapter = new HomePhotoAdapter(this, data);
        recyclerView.setAdapter(adapter);
    }


    private List<HomePhoto> createAdapterData() {
        List<HomePhoto> data = new ArrayList<>();
        data.add(new HomePhoto(getString(R.string.taketurn), R.drawable.taketurn));
        data.add(new HomePhoto(getString(R.string.viewticket), R.drawable.ticket));
        data.add(new HomePhoto(getString(R.string.neareastbranch), R.drawable.nearstbranch));
        data.add(new HomePhoto(getString(R.string.settings), R.drawable.setting));
        return data;
    }

}