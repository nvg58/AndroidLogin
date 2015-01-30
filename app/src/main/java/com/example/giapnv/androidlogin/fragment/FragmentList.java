package com.example.giapnv.androidlogin.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giapnv.androidlogin.R;
import com.example.giapnv.androidlogin.Reasons;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

public class FragmentList extends ListFragment {

    ArrayList<String> reasonList = new ArrayList<>();
    ArrayAdapter<String> mAdapter;
    private int index = -1;
    private int top = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_single_choice, Reasons.REASONS);
        setListAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "Item clicked: " + id + position);

        CheckedTextView check = (CheckedTextView) v;
        check.toggle();

        if (check.isChecked()) {
            check.setTextColor(Color.parseColor("#9c27b0"));
//            reasonList.add(Reasons.REASONS[(int)id]);
        } else {
            check.setTextColor(Color.parseColor("#111111"));
//            reasonList.remove((int)id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.the_layout, container, false);
        view.findViewById(R.id.footer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("getListView count", "" + getListView().getCheckedItemCount());
                if (reasonList.size() > 0) {
                    new DownloadFilesTask().execute();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please choose a reason!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setListAdapter(mAdapter);
        if (index != -1) {
            this.getListView().setSelectionFromTop(index, top);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            index = this.getListView().getFirstVisiblePosition();
            View v = this.getListView().getChildAt(0);
            top = (v == null) ? 0 : v.getTop();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private class DownloadFilesTask extends AsyncTask<Void, Void, Void> {
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

            String res = "";

            int len = getListView().getCount();
            SparseBooleanArray checked = getListView().getCheckedItemPositions();

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
            Toast.makeText(getActivity().getApplicationContext(), "Submit successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}
