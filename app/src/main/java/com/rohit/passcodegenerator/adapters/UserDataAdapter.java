package com.rohit.passcodegenerator.adapters;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rohit.passcodegenerator.R;
import com.rohit.passcodegenerator.models.UserDataModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oust on 2/2/19.
 */

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.MyViewHolder> {

    private ArrayList<UserDataModel> userDataModels;
    private Context context;


    public UserDataAdapter(Context context,ArrayList<UserDataModel> userDataModels) {
        this.context=context;
        this.userDataModels=userDataModels;
    }

    public void updateList(ArrayList<UserDataModel> userDataModels){
        this.userDataModels=userDataModels;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username, passCode;
        public ImageView user_image;

        public MyViewHolder(View view) {
            super(view);
            user_image=(ImageView)view.findViewById(R.id.user_image);
            username=(TextView)view.findViewById(R.id.userName);
            passCode=(TextView)view.findViewById(R.id.passCode);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_user_row, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try{
            UserDataModel userDataModel=userDataModels.get(position);
            holder.username.setText(userDataModel.getUsername());
            holder.passCode.setText("# "+userDataModel.getPasscode());
            setImage(userDataModel.getImagePath() ,holder.user_image);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setImage(String imagePath, ImageView user_image) {
        if ((imagePath!= null) && (!imagePath.isEmpty())) {
            byte[] imageByte = Base64.decode(imagePath, 0);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            user_image.setImageBitmap(decodedByte);
        }
    }

    @Override
    public int getItemCount() {
        return userDataModels.size();
    }


}
