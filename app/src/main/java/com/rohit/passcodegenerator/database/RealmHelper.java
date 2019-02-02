package com.rohit.passcodegenerator.database;

import android.content.Context;

import com.rohit.passcodegenerator.models.PassCode;
import com.rohit.passcodegenerator.models.UserDataModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by oust on 2/2/19.
 */

public class RealmHelper {


    public static void setDefaultConfig(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("pcgenerator.realm")
                .schemaVersion(7)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public static void addorUpdateUserData(final UserDataModel userDataModel) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(userDataModel);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (realm != null)
                realm.close();
        }
    }

    public static void addorUpdatePassCode(final PassCode passCode) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(passCode);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (realm != null)
                realm.close();
        }
    }


    public static ArrayList<UserDataModel> getUserDatas() {
        ArrayList<UserDataModel> userDataModels = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<UserDataModel> query = realm.where(UserDataModel.class);
        RealmResults<UserDataModel> results = query.findAll();
        if (results != null && results.size() > 0)
            for (UserDataModel userDataModel : results) {
                userDataModels.add(userDataModel);
            }

        return userDataModels;
    }

    public static int getUsersCount() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<UserDataModel> query = realm.where(UserDataModel.class);
        int count = query.findAll().size();


        return count;
    }
    public static boolean isPassCodeFound(int passC){
        boolean isFound=false;
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<PassCode> query = realm.where(PassCode.class).equalTo("passC", passC);
        RealmResults<PassCode> results = query.findAll();
        if (results.size() > 0) {
            isFound=true;
        } else
            isFound= false;

        return isFound;
    }

}
