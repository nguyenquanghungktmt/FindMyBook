package hust.hungnq.findmybook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class BookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        //get  book from intent
        Intent receiveIntent = getIntent();
        Book currentBook = (Book) receiveIntent.getSerializableExtra("book");

        TextView mTitleTextView = findViewById(R.id.titleDetail);
        TextView mAuthorsTextView = findViewById(R.id.authorsDetail);
        TextView mDescriptionTextView = findViewById(R.id.description);
        ImageView bookImage = findViewById(R.id.bookImageDetail);

        mTitleTextView.setText(currentBook.getTitle());
        mAuthorsTextView.setText(currentBook.getAuthors());
        mDescriptionTextView.setText(currentBook.getDescription());
        Glide.with(this).load(currentBook.getImageResource()).into(bookImage);
    }
}