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
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.giapnv.androidlogin.R;
import com.example.giapnv.androidlogin.data.Reasons;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

public class FragmentListReasons extends ListFragment {

    ArrayList<String> reasonList = new ArrayList<>();
    ArrayAdapter<String> mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_selectable_list_item, Reasons.REASONS);
        setListAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentListReasons", "Item clicked: " + id + Reasons.REASONS[(int) id]);

        CheckedTextView check = (CheckedTextView) v;
        check.toggle();

        if (check.isChecked()) {
            check.setTextColor(Color.parseColor("#9c27b0"));
            reasonList.add(Reasons.REASONS[(int) id]);
        } else {
            check.setTextColor(Color.parseColor("#111111"));
            reasonList.remove(Reasons.REASONS[(int) id]);
        }
    }

    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);
        view.findViewById(R.id.footer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("reasonList", reasonList.toString());
                if (reasonList.size() > 0) {
                    new SubmitTask().execute();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please choose a reason!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

//        return super.onCreateView(inflater, container, savedInstanceState);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        this.getListView().setAdapter(new ArrayAdapter<>(this.getActivity(),
//                android.R.layout.simple_selectable_list_item,
//                Reasons.REASONS));
//    }

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

            String res = reasonList.toString();

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
