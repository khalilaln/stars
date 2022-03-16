package com.example.my_star;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ShareCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.my_star.adapter.StarAdapter;
import com.example.my_star.beans.Star;
import com.example.my_star.service.StarService;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StarAdapter starAdapter = null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        stars = new ArrayList<>();
        StarService service = StarService.getInstance();
        init();
        recyclerView = findViewById(R.id.recycle_view);
        //insérer le code
        starAdapter = new StarAdapter(this, service.findAll());
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void init(){
        StarService service = StarService.getInstance();
        service.create(new Star("Pedri", "https://www.sofoot.com/IMG/joueurs/pedri-391247.jpg", 3.5f));
        service.create(new Star("Messi", "https://www.vhv.rs/dpng/d/528-5287147_barcelona-messi-png-fc-barcelona-transparent-png.png", 3));
        service.create(new Star("ibrahimovic", "https://upload.wikimedia.org/wikipedia/commons/0/09/Zlatan_Ibrahimovi%C4%87_June_2018.jpg", 5));
        service.create(new Star("Bellerin", "https://1vs1-7f65.kxcdn.com/img/players/Hector-Bellerin-Moruno-111890_548-ub-800.png", 1));
        service.create(new Star("Ronaldo", "https://upload.wikimedia.org/wikipedia/commons/8/8c/Cristiano_Ronaldo_2018.jpg", 5));
        service.create(new Star("Mbappe", "https://imgresizer.eurosport.com/unsafe/1200x0/filters:format(jpeg):focal(1447x668:1449x666)/origin-imgresizer.eurosport.com/2021/08/26/3207251-65700288-2560-1440.jpg", 1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (starAdapter != null){
                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share){
            String txt = "Stars";
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Stars")
                    .setText(txt)
                    .startChooser();
        }
        return super.onOptionsItemSelected(item);
    }

}
