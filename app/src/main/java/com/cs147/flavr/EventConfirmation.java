    //NOTICE: THIS CODE CONTAINS MATERIAL THAT IS FREELY DISTRIBUTED BY GOOGLE.INC
    package com.cs147.flavr;



    import android.app.Activity;
    import android.app.FragmentManager;
    import android.content.IntentSender;
    import android.location.Location;
    import android.os.Bundle;

    import com.google.android.gms.common.GooglePlayServicesClient;
    import com.google.android.gms.common.GooglePlayServicesUtil;
    import com.google.android.gms.location.LocationClient;
    import com.google.android.gms.maps.GoogleMap;
    import com.google.android.gms.maps.MapFragment;
    import android.widget.TextView;
    import android.support.v4.app.FragmentActivity;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.content.Intent;
    import android.view.View;
    import android.app.FragmentTransaction;
    import android.location.LocationManager;
    import android.widget.Toast;
    import com.google.android.gms.common.ConnectionResult;
    import com.google.android.gms.maps.model.MarkerOptions;

    import android.app.DialogFragment;
    import android.app.Dialog;
    import android.util.Log;
    import android.content.Context;
    import com.google.android.gms.maps.model.LatLng;

    public class EventConfirmation extends FragmentActivity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    private final static int
            CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    public LocationClient myLocationClient;
        Location currentLocation;

    public static class ErrorDialogFragment extends DialogFragment {
        //Contains error dialog
        private Dialog myDialog;

        public ErrorDialogFragment() {
            super();
            myDialog = null;
        }
        public void setDialog(Dialog dialog) {
            myDialog = dialog;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return myDialog;
        }
    }

    /*
    * Handlepublic FragmentTransaction getSupportFragmentManager() {
        return supportFragmentManager;
    } results returned to the FragmentActivity
    * by Google Play services
    */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        // Decide what to do based on the original request code
        switch (requestCode) {
            case CONNECTION_FAILURE_RESOLUTION_REQUEST :
    /*
    * If the result code is Activity.RESULT_OK, try
    */
                switch (resultCode) {
                    case Activity.RESULT_OK :
    /*
     * Try the request again
     */
                        break;
                }
        }
    }
    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.
                        isGooglePlayServicesAvailable(this);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates",
                    "Google Play services is available.");
            // Continue
            return true;
            // Google Play services was not available for some reason.
            // resultCode holds the error code.
        } else {
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                    resultCode,
                    this,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment =
                        new ErrorDialogFragment();
                // Set the dialog in the DialogFragment
                errorFragment.setDialog(errorDialog);

            }
        }
        return false;
    }

    @Override
    //Location service calls this when successfully connected.
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
    }
    @Override
    //Location Service calls this if there is an error.
    public void onDisconnected() {
        Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if(connectionResult.hasResolution()) {
            try{
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            }
            catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLocation() {
        currentLocation = myLocationClient.getLastLocation();
        double latitude = currentLocation.getLatitude();
        double longitude = currentLocation.getLongitude();
        MapFragment temp = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        GoogleMap eventMap = temp.getMap();
        LatLng location = new LatLng(latitude,longitude);
        MarkerOptions newMarker = new MarkerOptions();
        newMarker.position(location);
        eventMap.addMarker(newMarker);

    }


//    protected void onStart() {
//        super.onStart();
//        // Connect the client.
//        myLocationClient.connect();
//    }
//
//    protected void onStop() {
//        // Disconnecting the client invalidates it.
//        myLocationClient.disconnect();
//        super.onStop();
//    }

        public void printEventInfo(String [] information) {
            TextView eventTitle = (TextView) findViewById(R.id.event_name);
            eventTitle.setText(information[0]);
            TextView food = (TextView) findViewById(R.id.food_info);
            food.setText("Food Available: "+information[1]);
            TextView description = (TextView) findViewById(R.id.description2);
            description.setText("Description: "+information[2]);
            TextView location = (TextView) findViewById(R.id.locationinfo);
            location.setText("Location"+information[3]);

//            TextView keywords = (TextView) findViewById(R.id.keywords);
//            keywords.setText("Keywords: "+information[4]);
        }

        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //myLocationClient = new LocationClient(this, this, this);
        //setLocation();
            Intent eventInfo = getIntent();
            setContentView(R.layout.activity_event_confirmation);
            String [] eventInformation = eventInfo.getStringArrayExtra(createEvent.TO_CREATE);
            printEventInfo(eventInformation);

    //       MapFragment temp = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
    //       GoogleMap eventMap = temp.getMap();
    //       if(mapIsAvailable(eventMap)) {
    //
    //       }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_confirmation, menu);
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
    //
    //    public void printConfirmation(View view) {
    //
    //    }
    }
