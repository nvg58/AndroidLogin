package com.example.giapnv.androidlogin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.giapnv.androidlogin.fragment.FragmentListReasons;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialSectionListener;

public class MyNavigationDrawer extends MaterialNavigationDrawer implements MaterialAccountListener {
    MaterialAccount account;
    String userName;
    String userMail;

    @Override
    public void init(Bundle savedInstanceState) {
        ParseUser user = ParseUser.getCurrentUser();

        userName = user.get("name").toString();
        userMail = user.getEmail();

        account = new MaterialAccount(this.getResources(), userName, userMail, R.drawable.photo, R.drawable.bamboo);
        this.addAccount(account);

        Bundle params = new Bundle();
        params.putString("fields", "cover");
        new Request(ParseFacebookUtils.getSession(),
                "me",
                params,
                HttpMethod.GET,
                new Request.Callback() {
                    @Override
                    public void onCompleted(Response response) {
                        Log.wtf("wtf", response.getGraphObject().getProperty("cover").toString());
                        GraphObject graphObject = response.getGraphObject();
//                        String s = textViewResults.getText().toString();
                        if (graphObject != null) {
                            JSONObject jsonObject = graphObject.getInnerJSONObject();
                            try {
                                JSONArray array = jsonObject.getJSONArray("cover");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = (JSONObject) array.get(i);
                                    Log.wtf("id", "id = " + object.get("id"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
//                        textViewResults.setText(s);
//                    }
                    }
                }).executeAsync();

        // set listener
        this.setAccountListener(this);

        // create sections
        this.addSection(newSection("Choose reasons", R.drawable.ic_hotel_grey600_24dp, new FragmentListReasons()).setSectionColor(Color.parseColor("#9c27b0")));

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