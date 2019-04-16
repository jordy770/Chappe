package yeeet.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static yeeet.com.SettingsActivity.SHARED_PREFS;
import static yeeet.com.SettingsActivity.SWITCH1;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener {
    public static final String EXTRA_URL = "imageURL";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_MINUTES = "ready";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private ArrayList<Recipe> mRecipeList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecipeList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
        settings();
    }

    private void parseJSON(){
        String url = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=dog&image_type=photo&pretty=true";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");

                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String title = hit.getString("user");
                                String imageUrl = hit.getString("webformatURL");
                                int minutes = hit.getInt("likes");

                                mRecipeList.add(new Recipe(imageUrl, title, minutes));
                            }
                            mRecipeAdapter = new RecipeAdapter( MainActivity.this, mRecipeList);
                            mRecyclerView.setAdapter(mRecipeAdapter);
                            mRecipeAdapter.setOnItemClickListener(MainActivity.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        Recipe clickedItem = mRecipeList.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_MINUTES, clickedItem.getMinutes());

        startActivity(detailIntent);
    }

    public void sendMessage(View view)
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void settings(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        boolean switch1 = sharedPreferences.getBoolean(SWITCH1, false);

        if(switch1){
            RecyclerView main = findViewById(R.id.recycler_view);

            main.setBackgroundColor(Color.BLUE);
//            TextView textView = findViewById(R.id.textView);
//            textView.setTextColor(Color.WHITE);
            Log.d("blackmode", "zwart");
        } else {
            RecyclerView main = findViewById(R.id.recycler_view);
            main.setBackgroundColor(Color.WHITE);
//            TextView textView = findViewById(R.id.textView);
//            textView.setTextColor(Color.BLACK);
            Log.d("blackmode", "wit");
        }
    }
}
