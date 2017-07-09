package app.mobile.examwarrior.multidrawer;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import app.mobile.examwarrior.R;

public class MultiDrawer extends AppCompatActivity {

    Toolbar t;
    ImageView imgLeft,imgRight;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    FrameLayout drawerLeft,drawerRight,drawerMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multidrawer);

        t= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.activity_main);
        imgLeft= (ImageView) findViewById(R.id.imgLeft);
        imgRight= (ImageView) findViewById(R.id.imgRight);

        drawerLeft= (FrameLayout) findViewById(R.id.frmLeft);
        drawerRight= (FrameLayout) findViewById(R.id.frmRight);
        drawerMain= (FrameLayout) findViewById(R.id.frmMain);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDrawerLayout.isDrawerOpen(drawerLeft))
                {
                    mDrawerLayout.closeDrawer(drawerLeft);
                }
                else if(!mDrawerLayout.isDrawerOpen(drawerLeft))
                {
                    mDrawerLayout.openDrawer(drawerLeft);
                }
                if(mDrawerLayout.isDrawerOpen(drawerRight))
                {
                    mDrawerLayout.closeDrawer(drawerRight);
                }
            }
        });

        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDrawerLayout.isDrawerOpen(drawerRight))
                {
                    mDrawerLayout.closeDrawer(drawerRight);
                }
                else if(!mDrawerLayout.isDrawerOpen(drawerRight))
                {
                    mDrawerLayout.openDrawer(drawerRight);
                }
                if(mDrawerLayout.isDrawerOpen(drawerLeft))
                {
                    mDrawerLayout.closeDrawer(drawerLeft);
                }
            }
        });
    }
}
