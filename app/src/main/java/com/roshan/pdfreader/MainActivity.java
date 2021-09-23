package com.roshan.pdfreader;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.audiofx.Equalizer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.roshan.pdfreader.Adapters.PdfAdapter;

import java.io.File;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnSelectedPdf{

    private PdfAdapter adapter;
    private List<File> pdfList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Documents");

        setRuntimePermission();
    }

    private void setRuntimePermission(){
        Dexter.withContext(MainActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        displayPdf();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        finish();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).onSameThread().check();
    }


    public ArrayList<File> findPDFs(File file){
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        for(File singleFile : files){
            if (singleFile.isDirectory() && !singleFile.isHidden())
                arrayList.addAll(findPDFs(singleFile));
            else if (singleFile.getName().endsWith(".pdf"))
                arrayList.add(singleFile);
        }
        return arrayList;
    }


    public void displayPdf(){
        recyclerView = findViewById(R.id.rv);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        pdfList = new ArrayList<>();
        pdfList.addAll(findPDFs(Environment.getExternalStorageDirectory()));

        adapter = new PdfAdapter(this, pdfList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnSelectedPdf(File file) {
        startActivity(new Intent(MainActivity.this, PdfActivity.class)
                .putExtra("path", file.getAbsolutePath()));
    }
}