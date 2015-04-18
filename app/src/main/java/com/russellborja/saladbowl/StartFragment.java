package com.russellborja.saladbowl;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.russellborja.saladbowl.data.Contract;
import com.russellborja.saladbowl.data.DbHelper;

/**
 * Created by russellborja on 15-04-10.
 */

public class StartFragment extends Fragment {

        static final String LOG_TAG = "StartFragment";
        private TeamOneAdapter teamOneAdapter;
        private TeamTwoAdapter teamTwoAdapter;
        private final String selectLetter = "SelectLetter";
        private final String selectWordCount = "WordCount";

        public StartFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            DbHelper dbHelper = new DbHelper(getActivity());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            db.delete(Contract.NameEntry.TABLE_NAME, null, null);

            Cursor cursor = db.rawQuery("select rowid _id,* from " + Contract.NameEntry.TABLE_NAME, null);
            teamOneAdapter = new TeamOneAdapter(getActivity(), cursor, 0);
            teamTwoAdapter = new TeamTwoAdapter(getActivity(), cursor, 0);

            ListView listView1 = (ListView) rootView.findViewById(R.id.listview_team_one);
            listView1.setAdapter(teamOneAdapter);

            ListView listView2 = (ListView) rootView.findViewById(R.id.listview_team_two);
            listView2.setAdapter(teamTwoAdapter);

            //initialize spinner to choose starting letter
//            Spinner spinner =(Spinner) getActivity().findViewById(R.id.letter_spinner);
//            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(),
//                    android.R.layout.simple_spinner_item, R.array.letters_array);
//
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinner.setAdapter(adapter);

            db.close();
            return rootView;




        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState){
            //add buttons
            Button teamOneAddButton = (Button) getActivity().findViewById(R.id.team_one_add);
            Button teamTwoAddButton = (Button) getActivity().findViewById(R.id.team_two_add);

            //reset buttons
            Button teamOneReset = (Button) getActivity().findViewById(R.id.team_one_reset);
            Button teamTwoReset = (Button) getActivity().findViewById(R.id.team_two_reset);

            //submit button
            Button submitButton = (Button) getActivity().findViewById(R.id.submit_button);

            //spinners
            final Spinner letterSpinner = (Spinner) getActivity().findViewById(R.id.letter_spinner);
            final Spinner wordCountSpinner = (Spinner) getActivity().findViewById(R.id.word_count_spinner);

            //text edit fields
            final EditText teamOneEditText = (EditText) getActivity().findViewById(R.id.team_one_edittext);
            final EditText teamTwoEditText = (EditText) getActivity().findViewById(R.id.team_two_edittext);


            teamOneAddButton.setOnClickListener(
                    new View.OnClickListener(){
                        public void onClick(View v){
                            String playerOneName = teamOneEditText.getText().toString();
                            Log.v(LOG_TAG, playerOneName);
                            DbHelper dbHelper = new DbHelper(getActivity());
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            //add player name
                            values.put(Contract.NameEntry.COLUMN_NAME, playerOneName);
                            values.put(Contract.NameEntry.COLUMN_TEAM, "TEAM ONE");
                            values.put(Contract.NameEntry.COLUMN_WORDS, "");

                            long newRowId = db.insert(Contract.NameEntry.TABLE_NAME, null, values);
                            Cursor cursor = db.rawQuery("select rowid _id,* from " + Contract.NameEntry.TABLE_NAME + " where "
                                    + Contract.NameEntry.COLUMN_TEAM + "= 'TEAM ONE'", null);
                            Log.v(LOG_TAG, "Row: " + newRowId);

                            updateListView(cursor, true);

                            db.close();

                        }
                    }
            );

            teamTwoAddButton.setOnClickListener(
                    new View.OnClickListener(){
                        public void onClick(View v){
                            String playerTwoName = teamTwoEditText.getText().toString();
                            Log.v(LOG_TAG, playerTwoName);
                            DbHelper dbHelper = new DbHelper(getActivity());
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put(Contract.NameEntry.COLUMN_NAME, playerTwoName);
                            values.put(Contract.NameEntry.COLUMN_TEAM, "TEAM TWO");
                            values.put(Contract.NameEntry.COLUMN_WORDS, "");

                            long newRowId = db.insert(Contract.NameEntry.TABLE_NAME, null, values);
                            Cursor cursor = db.rawQuery("select rowid _id,* from " + Contract.NameEntry.TABLE_NAME + " where "
                                    + Contract.NameEntry.COLUMN_TEAM + "= 'TEAM TWO'", null);
                            Log.v(LOG_TAG, "Row: " + newRowId);

                            updateListView(cursor, false);

                            db.close();

                        }
                    }
            );

            teamOneReset.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    DbHelper dbHelper = new DbHelper(getActivity());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    String deleteCommand = "DELETE from " + Contract.NameEntry.TABLE_NAME + " where "
                            + Contract.NameEntry.COLUMN_TEAM + "= 'TEAM ONE'";
                    db.execSQL(deleteCommand);
                    Cursor cursor = db.rawQuery("select rowid _id,* from " + Contract.NameEntry.TABLE_NAME + " where "
                            + Contract.NameEntry.COLUMN_TEAM + "= 'TEAM ONE'", null);
                    updateListView(cursor, true);
                }
            });

            teamTwoReset.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    DbHelper dbHelper = new DbHelper(getActivity());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    String deleteCommand = "DELETE from " + Contract.NameEntry.TABLE_NAME + " where "
                            + Contract.NameEntry.COLUMN_TEAM + "= 'TEAM TWO'";
                    db.execSQL(deleteCommand);
                    Cursor cursor = db.rawQuery("select rowid _id,* from " + Contract.NameEntry.TABLE_NAME + " where "
                            + Contract.NameEntry.COLUMN_TEAM + "= 'TEAM TWO'", null);
                    updateListView(cursor, false);
                }
            });

            //on submit, go to next activity
            submitButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    String letter = letterSpinner.getSelectedItem().toString();
                    int wordCount = Integer.parseInt(wordCountSpinner.getSelectedItem().toString());
                    Intent intent = new Intent(getActivity(), InputWordsActivity.class);
                    intent.putExtra(selectLetter, letter);
                    intent.putExtra(selectWordCount, wordCount);
                    startActivity(intent);

                }
            });


        }

        public void updateListView(Cursor cursor, boolean isTeamOne){
            if(cursor!= null) {
                if(isTeamOne) {
                    Log.v(LOG_TAG, "in updatelistview");
                    teamOneAdapter.changeCursor(cursor);
                }
                else{
                    teamTwoAdapter.changeCursor(cursor);
                }
            }
            else{
                Log.e(LOG_TAG, "Null cursor, cannot update listview");
            }
        }
    }

