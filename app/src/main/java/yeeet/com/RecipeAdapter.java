package yeeet.com;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private Context mContext;
    private ArrayList<Recipe> mRecipeList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RecipeAdapter(Context context, ArrayList<Recipe> recipeList){
        mContext = context;
        mRecipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recipe, parent, false );
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int position) {
        Recipe currentRecipe = mRecipeList.get(position);

        String imageUrl = currentRecipe.getImageUrl();
        String recipeTitle = currentRecipe.getTitle();
        int minutes = currentRecipe.getMinutes();

        recipeViewHolder.mTextViewTitle.setText(recipeTitle);;
        recipeViewHolder.mTextViewReady.setText("Ready in: " + minutes + "minutes");
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(recipeViewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewTitle;
        public TextView mTextViewReady;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewTitle = itemView.findViewById(R.id.text_view_title);
            mTextViewReady = itemView.findViewById(R.id.text_view_ready);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public  void onClick(View v){
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
