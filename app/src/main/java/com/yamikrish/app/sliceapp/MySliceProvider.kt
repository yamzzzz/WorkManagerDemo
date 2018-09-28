package com.yamikrish.app.sliceapp

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.slice.Slice
import androidx.slice.SliceProvider
import androidx.slice.builders.ListBuilder
import androidx.slice.builders.SliceAction
import android.R.attr.path
import androidx.core.graphics.drawable.IconCompat
import android.app.PendingIntent





class MySliceProvider : SliceProvider() {
    /**
     * Instantiate any required objects. Return true if the provider was successfully created,
     * false otherwise.
     */
    override fun onCreateSliceProvider(): Boolean {
        return true
    }

    /**
     * Converts URL to content URI (i.e. content://com.yamikrish.app.sliceapp...)
     */
    override fun onMapIntentToUri(intent: Intent?): Uri {
        // Note: implementing this is only required if you plan on catching URL requests.
        // This is an example solution.
        var uriBuilder: Uri.Builder = Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT)
        if (intent == null) return uriBuilder.build()
        val data = intent.data
        if (data != null && data.path != null) {
            val path = data.path.replace("/", "")
            uriBuilder = uriBuilder.path(path)
        }
        val context = context
        if (context != null) {
            uriBuilder = uriBuilder.authority(context.packageName)
        }
        return uriBuilder.build()
    }

    /**
     * Construct the Slice and bind data if available.
     */
    override fun onBindSlice(sliceUri: Uri): Slice? {
        // Note: you should switch your build.gradle dependency to
        // slice-builders-ktx for a nicer interface in Kotlin.
        val path = sliceUri.getPath();
        when (path) {

            //Define the slice’s URI; I’m using ‘mainActivity’//

            "/mainActivity" -> return createSlice(sliceUri)
        }
        return null
    }

    fun createSlice(sliceUri: Uri): Slice {
        val activityAction = createActivityAction()

        //Create the ListBuilder//
        val listBuilder = ListBuilder(context!!, sliceUri, ListBuilder.INFINITY)

        //Create the RowBuilder//
        val rowBuilder = ListBuilder.RowBuilder(listBuilder)
                .setTitle("Test Launch!")
                .setSubtitle("Let's see how it works.. Just Click on it to proceed!!")
                .setPrimaryAction(activityAction)


        listBuilder.addRow(rowBuilder)

        return listBuilder.build()
    }

    fun createActivityAction(): SliceAction {
        return SliceAction.create(
                PendingIntent.getActivity(
                        context, 0, Intent(context, MainActivity::class.java), 0
                ),
                IconCompat.createWithResource(context, R.drawable.ic_launcher_foreground),
                ListBuilder.ICON_IMAGE,
                "Open App"
        )
    }


    /**
     * Slice has been pinned to external process. Subscribe to data source if necessary.
     */
    override fun onSlicePinned(sliceUri: Uri?) {
        // When data is received, call context.contentResolver.notifyChange(sliceUri, null) to
        // trigger MySliceProvider#onBindSlice(Uri) again.
    }

    /**
     * Unsubscribe from data source if necessary.
     */
    override fun onSliceUnpinned(sliceUri: Uri?) {
        // Remove any observers if necessary to avoid memory leaks.
    }
}
