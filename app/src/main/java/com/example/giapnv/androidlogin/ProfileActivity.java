package com.example.giapnv.androidlogin;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;


public class ProfileActivity extends ListActivity {

    private TextView titleTextView;
    private TextView emailTextView;
    private TextView nameTextView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        ParseLoginBuilder builder = new ParseLoginBuilder(ProfileActivity.this);
//        startActivityForResult(builder.build(), 0);

        titleTextView = (TextView) findViewById(R.id.profile_title);
        emailTextView = (TextView) findViewById(R.id.profile_email);
        nameTextView = (TextView) findViewById(R.id.profile_name);
        titleTextView.setText(R.string.profile_title_logged_in);
        listView = (ListView) findViewById(android.R.id.list);

        setListAdapter(new MyAdapter());

        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("%d", String.valueOf(listView.getCheckedItemCount()));
                if (listView.getCheckedItemCount() > 0) {
                    new DownloadFilesTask().execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Please choose a reson!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.logout_button).setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                ParseUser.logOut();

                // FLAG_ACTIVITY_CLEAR_TASK only works on API 11, so if the user
                // logs out on older devices, we'll just exit.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    Intent intent = new Intent(ProfileActivity.this,
                            LoginDispatchActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    finish();
                }
            }
        });
    }

    private class DownloadFilesTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog = new ProgressDialog(ProfileActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait...");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParseUser user = ParseUser.getCurrentUser();

            ParseObject post = new ParseObject("AbsentReports");

            String res = "";

            int len = listView.getCount();
            SparseBooleanArray checked = listView.getCheckedItemPositions();

            for (int i = 0; i < len; i++)
                if (checked.get(i)) {
                    res += "[" + Reasons.REASONS[i] + "]";
                }

            Log.d("%s", res);

            post.put("reasons", res);
            post.put("user", user);
            post.put("userName", user.get("name"));
            post.saveInBackground();

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... progress) {
        }

        @Override
        protected void onPostExecute(Void result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(getApplicationContext(), "Submit successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * A simple array adapter that creates a list of reasons.
     */
    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return Reasons.REASONS.length;
        }

        @Override
        public String getItem(int position) {
            return Reasons.REASONS[position];
        }

        @Override
        public long getItemId(int position) {
            return Reasons.REASONS[position].hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
            }

            ((TextView) convertView.findViewById(android.R.id.text1))
                    .setText(getItem(position));
            return convertView;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Set up the profile page based on the current user.
        ParseUser user = ParseUser.getCurrentUser();
        showProfile(user);
    }

    /**
     * Shows the profile of the given user.
     *
     * @param user given user
     */
    private void showProfile(ParseUser user) {
        if (user != null) {
            emailTextView.setText(user.getEmail());
            String fullName = user.getString("name");
            if (fullName != null) {
                nameTextView.setText(fullName);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
