package com.arun.rajora.chat.chit.bingo.family.bingofamily;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arun.rajora.chat.chit.bingo.family.bingofamily.fragments.AccountsFragment;
import com.arun.rajora.chat.chit.bingo.family.bingofamily.fragments.ManageFragments;
import com.arun.rajora.chat.chit.bingo.family.bingofamily.fragments.PaymentFragment;
import com.arun.rajora.chat.chit.bingo.family.bingofamily.fragments.ServicesFragment;
import com.arun.rajora.chat.chit.bingo.family.bingofamily.fragments.SummaryFragment;
import com.arun.rajora.chat.chit.bingo.family.bingofamily.fragments.TransactionsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

	private SectionsPagerAdapter mSectionsPagerAdapter;
	int states[]={0,0,0,0,0,0,0};
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FirebaseAuth auth=FirebaseAuth.getInstance();
		FirebaseUser x=auth.getCurrentUser();
		if(x.getDisplayName()!=null){
			toolbar.setSubtitle(x.getDisplayName());
		}
		if(x.getPhotoUrl()!=null){
			Picasso.with(this)
					.load(x.getPhotoUrl())
					.resize(120, 120)
					.centerCrop()
					.into(new com.squareup.picasso.Target()
					{
						@Override
						public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
						{
							RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
							circularBitmapDrawable.setCircular(true);
							toolbar.setLogo(circularBitmapDrawable);
						}

						@Override
						public void onBitmapFailed(Drawable errorDrawable)
						{

						}

						@Override
						public void onPrepareLoad(Drawable placeHolderDrawable)
						{

						}
					});
		}
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(mViewPager);
		mViewPager.setCurrentItem(1);
		mViewPager.setOffscreenPageLimit(10);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
				case 0:
					return AccountsFragment.newInstance();
				case 1:
					return SummaryFragment.newInstance();
				case 2:
					return TransactionsFragment.newInstance();
				case 3:
					return ServicesFragment.newInstance();
				case 4:
					return PaymentFragment.newInstance();
				case 5:
					return ManageFragments.newInstance();
			}
			return null;
		}

		@Override
		public int getCount() {
			return 6;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return "Accounts";
				case 1:
					return "Summary";
				case 2:
					return "Transactions";
				case 3:
					return "Services";
				case 4:
					return "Payment";
				case 5:
					return "Alerts & Reminders";
			}
			return null;
		}
	}
	public void toggleDate1(View v){
		states[0]=1-states[0];
		CardView view=(CardView)v;
		view.setCardBackgroundColor(getResources().getColor(states[0]==0?R.color.cardview_light_background:R.color.colorAccent));
	}

	public void toggleDate2(View v){
		states[1]=1-states[1];
		CardView view=(CardView)v;
		view.setCardBackgroundColor(getResources().getColor(states[1]==0?R.color.cardview_light_background:R.color.colorAccent));
	}

	public void toggleDate3(View v){
		states[2]=1-states[2];
		CardView view=(CardView)v;
		view.setCardBackgroundColor(getResources().getColor(states[2]==0?R.color.cardview_light_background:R.color.colorAccent));
	}

	public void toggleDate4(View v){
		states[3]=1-states[3];
		CardView view=(CardView)v;
		view.setCardBackgroundColor(getResources().getColor(states[3]==0?R.color.cardview_light_background:R.color.colorAccent));
	}

	public void toggleDate5(View v){
		states[4]=1-states[4];
		CardView view=(CardView)v;
		view.setCardBackgroundColor(getResources().getColor(states[4]==0?R.color.cardview_light_background:R.color.colorAccent));
	}

	public void toggleDate6(View v){
		states[5]=1-states[5];
		CardView view=(CardView)v;
		view.setCardBackgroundColor(getResources().getColor(states[5]==0?R.color.cardview_light_background:R.color.colorAccent));
	}

	public void toggleDate7(View v){
		states[6]=1-states[6];
		CardView view=(CardView)v;
		view.setCardBackgroundColor(getResources().getColor(states[6]==0?R.color.cardview_light_background:R.color.colorAccent));
	}

}
