package hust.hungnq.findmybook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.LinkedList;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder>{
    private final LinkedList<Book> mBookList;
    private LayoutInflater mInflater;
    private Context mContext;

    public BookListAdapter(Context context, LinkedList<Book> mBookList) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mBookList = mBookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        //get current book
        Book currentBook = mBookList.get(position);
        holder.bindTo(currentBook);
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView bookTitleView;
        public final TextView bookAuthorsView;
        private ImageView bookImageView;
        final BookListAdapter mAdapter;

        public BookViewHolder(@NonNull View itemView, BookListAdapter bookListAdapter) {
            super(itemView);
            bookTitleView = itemView.findViewById(R.id.titleText);
            bookAuthorsView = itemView.findViewById(R.id.authorText);
            bookImageView = itemView.findViewById(R.id.bookImage);
            this.mAdapter = bookListAdapter;

            itemView.setOnClickListener(this);
        }

        void bindTo(Book currentBook) {
            // Populate the textviews with data.
            bookTitleView.setText(currentBook.getTitle());
            bookAuthorsView.setText(currentBook.getAuthors());
            Glide.with(mContext).load(currentBook.getImageResource()).into(bookImageView);
        }


        @Override
        public void onClick(View v) {
//            Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();
            Book currentBook = mBookList.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, BookDetailActivity.class);
            detailIntent.putExtra("book", currentBook);
            mContext.startActivity(detailIntent);
        }
    }
}
