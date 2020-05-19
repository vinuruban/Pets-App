package com.example.android.pets;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.android.pets.data.PetContract.PetEntry;

import com.example.android.pets.data.PetContract;

public class PetCursorAdapter extends CursorAdapter {

    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView breedTextView = (TextView) view.findViewById(R.id.breed);

        //FIND THE COLUMNS OF THE PET ATTRIBUTES WE WANT
        int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_NAME);
        int breedColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_BREED);

        //GET DATA IN THE COLUMN (THIS AND THE CODE ABOVE WAS MOVED FROM CATALOG_ACTIVITY)
        String nameData = cursor.getString(nameColumnIndex);
        String breedData = cursor.getString(breedColumnIndex);

        //SET DATA
        nameTextView.setText(nameData);
        breedTextView.setText(breedData);
    }
}
