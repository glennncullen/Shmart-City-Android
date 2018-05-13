package afinal.project.glennncullen.shmartcity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // set log tag to underlying class name for debug
    private static final String LOG_TAG = MainActivity.class.getCanonicalName();

    TextView instructionTxt;
    TextView nextRoadTxt;
    ImageView directionImg;
    ImageView alarmImg;

    JSONArray route;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initiate handler
        Handler.instance(this);

        instructionTxt = (TextView) findViewById(R.id.instructionTxt);
        nextRoadTxt = (TextView) findViewById(R.id.nextRoadTxt);
        directionImg = (ImageView) findViewById(R.id.directionImg);
        alarmImg = (ImageView) findViewById(R.id.alarmImg);

        route = new JSONArray();

        noFire();


    }


    // when instruction is given, update to the next road
    public void updateRoad(){
        try {
            String currentRoad = (String) route.get(0);
            String nextRoad = (String) route.get(1);
            String nextDirection = getNextDirection(currentRoad.charAt(currentRoad.length()-2), nextRoad.charAt(nextRoad.length()-2));
            route.remove(0);
            updateDisplay(nextRoad, nextDirection);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // set the route
    public void setRoute(JSONObject message){
        try {
            route = (JSONArray) message.get("path");
            String currentRoad = (String) route.get(0);
            String nextRoad = (String) route.get(1);
            String nextDirection = getNextDirection(currentRoad.charAt(currentRoad.length()-2),
                                                    nextRoad.charAt(nextRoad.length()-2));
            alarmImg.setImageDrawable(getApplicationContext().getDrawable(R.drawable.alarm_green));
            updateDisplay(nextRoad, nextDirection);
            route.remove(0);
            Log.i(LOG_TAG, route.get(0).getClass().getSimpleName());
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to decode path json object");
        }
    }


    // when crisis is averted, return to initial state
    public void noFire(){
        instructionTxt.setText("Waiting for something to go on fire");
        nextRoadTxt.setText("");
        directionImg.setImageDrawable(null);
        alarmImg.setImageDrawable(getApplicationContext().getDrawable(R.drawable.alarm_red));
    }


    // update the cardview with the most recent info
    public void updateDisplay(String road, String direction){
        switch (direction){
            case "straight":
                instructionTxt.setText("At The Next Junction\nContinue Straight On");
                nextRoadTxt.setText(road.substring(0, road.length()-3));
                directionImg.setImageDrawable(getApplicationContext().getDrawable(R.drawable.straight_arrow));
                break;
            case "left":
                instructionTxt.setText("At The Next Junction\nTurn Left On To");
                nextRoadTxt.setText(road.substring(0, road.length()-3));
                directionImg.setImageDrawable(getApplicationContext().getDrawable(R.drawable.left_arrow));
                break;
            case "right":
                instructionTxt.setText("At The Next Junction\nTurn Right On To");
                nextRoadTxt.setText(road.substring(0, road.length()-3));
                directionImg.setImageDrawable(getApplicationContext().getDrawable(R.drawable.right_arrow));
                break;
        }
    }


    // get the next direction
    public String getNextDirection(char from, char to){
        switch (from){
            case 'N':
                if(to == 'N') return "straight";
                if(to == 'E') return "right";
                if(to == 'W') return "left";
                break;
            case 'S':
                if(to == 'S') return "straight";
                if(to == 'W') return "right";
                if(to == 'E') return "left";
                break;
            case 'E':
                if(to == 'E') return "straight";
                if(to == 'N') return "left";
                if(to == 'S') return "right";
                break;
            case 'W':
                if(to == 'W') return "straight";
                if(to == 'S') return "left";
                if(to == 'N') return "right";
                break;
        }
        return "";
    }

    // on back button pressed, do nothing
    @Override
    public void onBackPressed(){
    }
}
