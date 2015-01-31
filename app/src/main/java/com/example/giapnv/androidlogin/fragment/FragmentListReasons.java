package com.example.giapnv.androidlogin.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giapnv.androidlogin.R;
import com.example.giapnv.androidlogin.data.Reasons;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class FragmentListReasons extends ListFragment {

    private String mReason = null;
    private MyAdapter mAdapter;
    private int mSelectedItem;    

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new MyAdapter();
        setListAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        ListView lv = getListView();
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setItemChecked(0, true);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentListReasons", "Item clicked: " + position);

        mSelectedItem = position;
        mAdapter.notifyDataSetChanged();

        mReason = l.getAdapter().getItem(mSelectedItem).toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);
        view.findViewById(R.id.footer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mReason != null) {
                    new SubmitTask().execute();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please choose a reason!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private class SubmitTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait...");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParseUser user = ParseUser.getCurrentUser();

            ParseObject post = new ParseObject("AbsentReports");

            Log.d("%s", mReason);

            post.put("reasons", mReason);
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
            Toast.makeText(getActivity().getApplicationContext(), "Submit successfully!", Toast.LENGTH_SHORT).show();
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
                convertView = getActivity().getLayoutInflater().inflate(android.R.layout.simple_list_item_checked, container, false);
            }

            TextView tv = ((TextView) convertView.findViewById(android.R.id.text1));
            tv.setText(getItem(position));

            if (position == mSelectedItem) {
                tv.setTextColor(Color.parseColor("#9c27b0"));
            } else {
                tv.setTextColor(Color.parseColor("#111111"));
            }

            return convertView;
        }
    }
}
