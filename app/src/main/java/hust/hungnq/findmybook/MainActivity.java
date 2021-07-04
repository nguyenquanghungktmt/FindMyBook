package hust.hungnq.findmybook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    private TextView mWarning;
    private ImageView mWarningImage;
    private EditText mBookInput;
    private String bookTitle;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWarning = findViewById(R.id.warningText);
        mWarningImage = findViewById(R.id.warningImage);
        mBookInput = findViewById(R.id.bookInput);

        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().initLoader(0,null,this);
        }
    }

    public void searchBooks(View view) {
        //get text input
        bookTitle = mBookInput.getText().toString();

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //hide the keyboard
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        //check internet connection
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected() && bookTitle.length() != 0) {
            // have internet connection
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", bookTitle);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);

            mWarning.setText(R.string.loading);
            mWarning.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.img_loading).into(mWarningImage);
            //Glide.with(getApplicationContext()).load(R.drawable.warning_no_title).into(mWarningImage);

        } else {
            if (bookTitle.length() == 0) {
                mWarning.setText(R.string.no_search_term);
                mWarning.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(R.drawable.warning_no_title).into(mWarningImage);
            } else {
                mWarning.setText(R.string.no_network);
                mWarning.setVisibility(View.VISIBLE);
                Glide.with(this).load(R.drawable.warning_no_internet).into(mWarningImage);
            }
        }

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = bookTitle;

        return new BookLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        //create intent
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);

        //Destroy Loader
        if (getSupportLoaderManager().hasRunningLoaders() == true) getSupportLoaderManager().destroyLoader(0);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mBookInput.setText("");
        bookTitle = "";
        mWarning.setText("");
        mWarning.setVisibility(View.INVISIBLE);
        Glide.with(this).load(R.drawable.img_cute).into(mWarningImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                showAboutDialog();
                Toast.makeText(getApplicationContext(), "Created by Kircpp", Toast.LENGTH_SHORT).show();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    public void showAboutDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("About");
        dialog.setMessage("Created by kiracpp. All right reserved.");
        dialog.setIcon(R.drawable.ic_dialog);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }) ;

        dialog.show();
    }

}