package com.example.giapnv.androidlogin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.example.giapnv.androidlogin.fragment.FragmentListReasons;
import com.parse.ParseUser;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialSectionListener;

public class MyNavigationDrawer extends MaterialNavigationDrawer implements MaterialAccountListener {
    MaterialAccount account;

    @Override
    public void init(Bundle savedInstanceState) {
        ParseUser user = ParseUser.getCurrentUser();

//        if (ParseFacebookUtils.isLinked(user)) {
        String userName = user.get("name").toString();
        String userMail = user.getEmail();
//            String userAvatar = ParseFacebookUtils.isLinked(user) ?

        // add accounts
        account = new MaterialAccount(this.getResources(), userName, userMail, R.drawable.photo, R.drawable.bamboo);
        this.addAccount(account);
//        }

        // set listener
        this.setAccountListener(this);

        // create sections
        this.addSection(newSection("Section 1", new FragmentListReasons()));
        this.addSection(newSection("Section 2", new Intent(this, ProfileActivity.class)));
        this.addSection(newSection("Section 3", R.drawable.ic_mic_white_24dp, new FragmentListReasons()).setSectionColor(Color.parseColor("#9c27b0")));
        this.addSection(newSection("Section", R.drawable.ic_hotel_grey600_24dp, new FragmentListReasons()).setSectionColor(Color.parseColor("#03a9f4")));

        // create bottom section
        this.addBottomSection(newSection("Logout", R.drawable.ic_settings_black_24dp, new MaterialSectionListener() {
            @Override
            public void onClick(MaterialSection materialSection) {
                ParseUser.logOut();

                // FLAG_ACTIVITY_CLEAR_TASK only works on API 11, so if the user
                // logs out on older devices, we'll just exit.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    Intent intent = new Intent(MyNavigationDrawer.this,
                            LoginDispatchActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    finish();
                }
            }
        }));
    }

    @Override
    public void onAccountOpening(MaterialAccount account) {

    }

    @Override
    public void onChangeAccount(MaterialAccount newAccount) {

    }


}