/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.pets.data.PetContract.PetEntry;
import com.example.android.pets.data.PetDbHelper;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // Identifies a particular Loader being used in this component
    private static final int URL_LOADER = 0;

    //adapter for our list view
    PetCursorAdapter petCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() { //OPENS THE CREATE PET PAGE
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        //ListView attached
        ListView listView = (ListView) findViewById(R.id.pet_list_view);

        //TODO: add empty view

        //the second argument for Miwok app was "words" array. In our case, its the CURSOR!
        petCursorAdapter = new PetCursorAdapter(this, null);

        //ListView + CursorAdapter
        listView.setAdapter(petCursorAdapter);

        //Start the loader
        getLoaderManager().initLoader(URL_LOADER, null, this);

    }

//    onStart() with displayDatabaseInfo() method removed since adapter will automatically get updated with new cursor


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//THIS CREATES THE MENU BUTTON IN THE APP BAR
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //THIS HANDLES THE FUNCTIONALITY OF WHAT'S IN THE MENU
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                // displayDatabaseInfo() method that was previously called here has been removed since adapter will automatically get updated with new cursor
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


//    displayDatabaseInfo() REMOVED SINCE ITS MOVED INTO THE BG THREAD!
//onStart is triggered whenever the application is rotated, or if you navigate away for a second and then back. If you rotate the app or navigate away and back from the application - do you really need to get the data from the database again? Has anything in the database changed? The answer is no, nothing in the database has changed, so you should not need to keep reloading the data.

    private void insertPet() {

    // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_NAME, "dummy_name");
        values.put(PetEntry.COLUMN_BREED, "dummy_breed");
        values.put(PetEntry.COLUMN_WEIGHT, 6);

        // Insert a new row for Toto into the provider using the ContentResolver.
        // Use the {@link PetEntry#CONTENT_URI} to indicate that we want to insert
        // into the pets database table.
        // Receive the new content URI that will allow us to access Toto's data in the future.
        Uri newUri = getContentResolver().insert(PetEntry.CONTENT_URI, values);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                PetEntry.COLUMN_ID,
                PetEntry.COLUMN_NAME,
                PetEntry.COLUMN_BREED
        };

    // Returns a new CursorLoader
        return new CursorLoader(
                this,           // Parent activity context
                PetEntry.CONTENT_URI,   // Table to query
                projection,             // Projection to return
                null,          // No selection clause
                null,       // No selection arguments
                null           // Default sort order
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Update adapter with this new cursor containing updated pet data
        petCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
//        Callback called when the data needs to be deleted
        petCursorAdapter.swapCursor(null);
    }

}
