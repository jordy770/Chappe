package yeeet.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static yeeet.com.MainActivity.EXTRA_MINUTES;
import static yeeet.com.MainActivity.EXTRA_TITLE;
import static yeeet.com.MainActivity.EXTRA_URL;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String imageURL = intent.getStringExtra(EXTRA_URL);
        String title = intent.getStringExtra(EXTRA_TITLE);
        int minutes = intent.getIntExtra(EXTRA_MINUTES, 0);

        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewTitle = findViewById(R.id.text_view_creator_detail);
        TextView textViewMinutes = findViewById(R.id.text_view_like_detail);

        Picasso.with(this).load(imageURL).fit().centerInside().into(imageView);
        textViewTitle.setText(title);
        textViewMinutes.setText("Likes: " + minutes);
    }
}
