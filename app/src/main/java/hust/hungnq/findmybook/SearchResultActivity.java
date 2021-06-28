package hust.hungnq.findmybook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.Random;

public class SearchResultActivity extends AppCompatActivity {
    private String dataJson;
    private final LinkedList<Book> mBookList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private BookListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        dataJson = intent.getStringExtra("data");
//        Log.d("myTag", dataJson);

        startRecyclerBook();

        if (savedInstanceState != null) {
            mBookList.clear();
        }
        else processJsonData(dataJson);
    }

    private void startRecyclerBook() {
//        for (int i = 0; i < 20; i++) {
//            mBookList.addLast(new Book("Title " + i, "Author " + i));
//        }

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview_book);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new BookListAdapter(this, mBookList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);

        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));
    }

    public void processJsonData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            int i = 0;
            String title = null;
            String authors = null;
            String description = null;

            TypedArray bookImages = getResources().obtainTypedArray(R.array.book_images);
//            int[] bookImages = getResources().getIntArray(R.array.book_images);

            mBookList.clear();
            Log.d("myTag", "number = " + itemsArray.length());
            while (i < itemsArray.length()) {
                // Get the current item information.
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    title = volumeInfo.getString("title");

                    JSONArray authorArray = volumeInfo.getJSONArray("authors");
                    authors = "";
                    int j = 0;
                    for (; j < authorArray.length()-1; j++) {
                        authors += authorArray.getString(j) + ", ";
                    }
                    authors += authorArray.getString(j);
                    Log.d("myTag", authors);

                    description = volumeInfo.getString("description");
                    if  (authors != null && title != null)
                        mBookList.addLast(new Book(title, authors, bookImages.getResourceId(new Random().nextInt(4), 0), description));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Move to the next item.
                i++;
            }

        } catch (JSONException e) {
            // If onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results.
            mBookList.addLast(new Book(getString(R.string.no_results), "", R.drawable.img_soccer, ""));
            e.printStackTrace();
        }


        Log.d("myTag", "number = " + mBookList.size());
        // update the RecyclerView to display the data.
        mAdapter.notifyDataSetChanged();
        // Notify the adapter, that the data has changed.
        mRecyclerView.getAdapter().notifyItemInserted(mBookList.size());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        for (Book book : mBookList){
            outState.putSerializable("book" + mBookList.indexOf(book), book);
        }
        outState.putInt("number", mBookList.size());
    }
}