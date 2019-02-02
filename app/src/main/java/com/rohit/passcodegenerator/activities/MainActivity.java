package com.rohit.passcodegenerator.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rohit.passcodegenerator.R;
import com.rohit.passcodegenerator.adapters.UserDataAdapter;
import com.rohit.passcodegenerator.database.RealmHelper;
import com.rohit.passcodegenerator.models.PassCode;
import com.rohit.passcodegenerator.models.UserDataModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LinearLayout addUserBtn;
    private RecyclerView userDatarv;
    private UserDataAdapter userDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        addUserBtn=(LinearLayout)findViewById(R.id.addUserBtn);
        userDatarv=(RecyclerView)findViewById(R.id.userDatarv);
        
        setAdapter();
        
        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        
    }
    private static final int REQUEST_CAPTURE_IMAGE = 100;



    private void setAdapter() {
        ArrayList<UserDataModel> userDataModels= RealmHelper.getUserDatas();
        if(userDataModels!=null && userDataModels.size()>0){
            
            if(userDataAdapter==null){
                userDataAdapter=new UserDataAdapter(MainActivity.this,userDataModels);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
                userDatarv.setLayoutManager(mLayoutManager);
                userDatarv.setItemAnimator(new DefaultItemAnimator());
                userDatarv.setAdapter(userDataAdapter);
            }else{
                userDataAdapter.updateList(userDataModels);
            }
        }
    }

    private void openCamera() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE
        );
        if(pictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pictureIntent,
                    REQUEST_CAPTURE_IMAGE);
        }
    }
    int localCount=0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_CAPTURE_IMAGE &&
                resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                createNewUser(encoded);

            }
        }
    }

    private void createNewUser(String absolutePath) {
        localCount=0;
        int count = RealmHelper.getUsersCount()+1;
        UserDataModel userDataModel = new UserDataModel();
        userDataModel.setId(count);
        userDataModel.setUsername("User "+count);
        userDataModel.setImagePath(absolutePath);
        String passCode=getUniquePasscode(localCount++);
        if(passCode.equalsIgnoreCase("Error")){
            Toast.makeText(this, "User cannot be added !", Toast.LENGTH_SHORT).show();
        }else{
            userDataModel.setPasscode(passCode);
            RealmHelper.addorUpdateUserData(userDataModel);

            setAdapter();
        }

    }

    private String getUniquePasscode(int count) {
        if(count<10000) {
            Random rnd = new Random();
            int n = 100000 + rnd.nextInt(900000);
            if(RealmHelper.isPassCodeFound(n)){
                return getUniquePasscode(localCount);
            }else{
                PassCode passCode=new PassCode();
                passCode.setPassC(n);
                RealmHelper.addorUpdatePassCode(passCode);
                return n+"";
            }
        }else{
            return "Error";
        }
    }
}
