package za.co.itechhub.sassaapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import za.co.itechhub.sassaapp.models.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public TextView last_name, idNumber;
    public User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (User) getIntent().getSerializableExtra("user");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Snackbar snackBar = Snackbar.make(findViewById(R.id.main_activity_layout),
                        "Replace with another menu for example edit profile", 10000);
                snackBar.setActionTextColor(Color.RED);
                // Changing action button text color
                View sbView = snackBar.getView();
                TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackBar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackBar.dismiss();
                    }
                });
                snackBar.show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initialize();
    }

    public void initialize() {
        last_name = findViewById(R.id.last_name);
        idNumber = findViewById(R.id.idnumber);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                /**
                 * We allow user to update contact number and address.
                 * NB~ They can only update their address and contact numbers after 3 months.
                 * Meaning we must keep record of the last updated profile and always query when
                 * a user want to change or update their contact*/
                break;
            case R.id.action_logout:
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingPermission")
    @Override
    public boolean onNavigationItemSelected(MenuItem itm) {
        int id = itm.getItemId();
        Intent intent;
        switch (id) {
            case R.id.nab_announcement:
                /**query the announcement board/data,
                 * which will be updated by Sassa admin*/
                break;
            case R.id.nav_transactions:
                /**
                 * Data is needed for this operation to work.
                 * User information or profile*/
                break;
            case R.id.nav_balances:
                /**
                 * Data is needed for this operation to work.
                 * User information or profile*/
                break;
            case R.id.nav_sassa_offices:
                /**
                 * Here i will use google location pointers on the map.
                 * It will display all the nearest sassa offices or we can use south african map'
                 * so instead of displaying using the users location we will display all the sassa head
                 * offices on each an every province.
                 * It will then allow users to navigate to the selected sassa offices of their choice.*/

                break;
            case R.id.nav_special_doctors:
                /**
                 * Here i will use google location pointers on the map.
                 * It will display all the nearest doctors on a specific radius.
                 * It will then allow users to navigate to the selected doctor of their choice.*/

                break;
            case R.id.nav_sassa_email:
                /**
                 * Here a user sends an email to the support services.
                 * So we call an email intent to complete our job.
                 * @param mailto is the parameter that tells the intent which application to choose
                 * then by seeing that it knows that it should allow users to choose the mail app
                 * of their choice*/
                intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "info@itechhub.co.za", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Assist me with something");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(intent, "Send email..."));
                }

                break;
            case R.id.nav_sassa_help:
                intent = new Intent(Intent.ACTION_CALL);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("tel:" + "071 633 4002"));
                startActivity(intent);
                break;
            default:
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
