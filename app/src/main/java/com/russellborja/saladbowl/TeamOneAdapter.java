package com.russellborja.saladbowl;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.russellborja.saladbowl.data.Contract;

/**
 * Created by russellborja on 15-04-10.
 */
public class TeamOneAdapter extends CursorAdapter {

    public TeamOneAdapter(Context context, Cursor c, int flags){
        super(context, c, flags);
    }

    public static class ViewHolder{
        public final TextView teamOneTextView;

        public ViewHolder(View v){
            teamOneTextView = (TextView) v.findViewById(R.id.team_one_textview);
        }
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_names_one, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String teamOneName = cursor.getString(Contract.COL_NAME);
        Log.v("CursorAdapter", teamOneName);

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.teamOneTextView.setText(teamOneName);
    }
}
