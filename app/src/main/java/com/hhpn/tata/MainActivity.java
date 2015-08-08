package com.hhpn.tata;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.PagerTitleStrip;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.transition.Fade;

import android.view.Menu;
import android.view.MenuItem;


import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.hhpn.tata.fragment.EventsFragment;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    String name="";
    PagerTitleStrip pager_title_strip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);
        Intent a = getIntent();
        name = a.getStringExtra("name");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        pager_title_strip = (PagerTitleStrip)findViewById(R.id.pager_title_strip);

        mViewPager.setAdapter(mSectionsPagerAdapter);


        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(true);
        ab.setTitle("Hi, " + name + " !");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setAllowReturnTransitionOverlap(true);
            getWindow().setReenterTransition(new Fade()
                    .excludeTarget(android.R.id.navigationBarBackground, true));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            LoginManager.getInstance().logOut();
            Intent a = new Intent(MainActivity.this,SplashActivity.class);
            startActivity(a);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==0)
                return EventsFragment.newInstance(position + 1);
            else
                return EventsFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            SpannableStringBuilder sb = new SpannableStringBuilder("  Acara"); // space added before text for convenience

            Drawable drawable = getResources().getDrawable(R.mipmap.ic_park);
            drawable.setBounds(0, 0, 70, 70);
            ImageSpan span = new ImageSpan(drawable,0);
            sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return sb;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */


}
