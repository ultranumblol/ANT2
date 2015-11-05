package com.ant.contact.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ant.contact.R;
import com.ant.contact.fragment.Fragment4;
import com.ant.contact.fragment.Fragment6;
import com.ant.contact.fragment.FragmentTest;

import java.util.ArrayList;

import static com.ant.contact.R.color.gray;
import static com.ant.contact.R.color.white;

public class MainActivity extends FragmentActivity {
	private ViewPager m_vp;
	private Fragment4 mFragment4;
	private Fragment6 mfragment6;
    private FragmentTest mfragmentTest;
	private ImageView imgbar1,imgbar2;
	private LinearLayout bar1,bar2;
	private TextView bartv1,bartv2;
	private int lcurmainMaskImageId = R.id.bar1;// 当前选中的导航栏图标 默认第一个
	FragmentManager fm;
	// 页面列表
	private ArrayList<Fragment> fragmentList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
	}
	public void initView() {
		bar1= (LinearLayout) findViewById(R.id.bar1);
		bar2= (LinearLayout) findViewById(R.id.bar2);
		bartv1= (TextView) findViewById(R.id.mainbar1);
		bartv2= (TextView) findViewById(R.id.mainbar2);
		imgbar1= (ImageView) findViewById(R.id.mainbar_img1);
		imgbar2= (ImageView) findViewById(R.id.mainbar_img2);
		m_vp = (ViewPager) findViewById(R.id.viewpager);
		mfragment6 = new Fragment6();
		mFragment4 = new Fragment4();
        mfragmentTest = new FragmentTest();
		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(mfragment6);
		//fragmentList.add(mFragment4);
        fragmentList.add(mfragmentTest);
		bar1.setOnClickListener(new onclickMainbar());
		bar2.setOnClickListener(new onclickMainbar());
		final MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentList);
		m_vp.setAdapter(adapter);
		m_vp.setCurrentItem(0);//初始化默认pager是第一页
		bartv1.setTextColor(getResources().getColor(white));//初始化导航栏字体颜色
		bartv2.setTextColor(getResources().getColor(gray));
		m_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				if (position==0){
					lcurmainMaskImageId=R.id.bar1;
					imgbar1.setImageResource(R.drawable.main_tab_item_home_focus);
					imgbar2.setImageResource(R.drawable.main_tab_item_user_normal);
					bartv1.setTextColor(getResources().getColor(white));
					bartv2.setTextColor(getResources().getColor(gray));

				}
				if (position==1){
					lcurmainMaskImageId=R.id.bar2;
					imgbar1.setImageResource(R.drawable.main_tab_item_home_normal);
					imgbar2.setImageResource(R.drawable.main_tab_item_user_focus);
					bartv1.setTextColor(getResources().getColor(gray));
					bartv2.setTextColor(getResources().getColor(white));
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

    /**
     * 获得联系人页面(contactsActivity)返回的 "该刷新了"信息，将该信息通过fragmentmanager的findfragmentbyid ，找到
     * 联系人分组页面(fragmentTest),传递到该fragment的onActivityResult方法中，执行刷新操作
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getExtras().getString("result");
        //Log.i("xml", result + "111111111111111111111");
        //Log.i("xml", fragmentList.get(1).getId() + "ididididididididi");
        Fragment f = getSupportFragmentManager().findFragmentById(fragmentList.get(1).getId());
        f.onActivityResult(requestCode, resultCode, data);
    }

	public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
		ArrayList<Fragment> list;
		public FragmentManager fm;
		public MyFragmentPagerAdapter(FragmentManager fm,ArrayList<Fragment> list) {
			super(fm);
			this.fm = fm;
			this.list = list;
		}


		@Override
		public int getCount() {
			return list.size();
		}


		@Override
		public Fragment getItem(int arg0) {
			return list.get(arg0);
		}



		@Override
		public Fragment instantiateItem(ViewGroup container, int position) {
			Fragment fragment = (Fragment) super.instantiateItem(container,
					position);
			fm.beginTransaction().show(fragment).commit();
			return fragment;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// super.destroyItem(container, position, object);
			Fragment fragment = list.get(position);
			fm.beginTransaction().hide(fragment).commit();
		}


	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public class onclickMainbar implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			int id = v.getId();
			if(lcurmainMaskImageId==id){// 判断当前点击的view id 是否与刚才点的 id 一致

				return;
			}
			switch (id){
				case R.id.bar1:
					lcurmainMaskImageId=R.id.bar1;
					imgbar1.setImageResource(R.drawable.main_tab_item_home_focus);
					imgbar2.setImageResource(R.drawable.main_tab_item_user_normal);
					m_vp.setCurrentItem(0);
					bartv1.setTextColor(getResources().getColor(white));
					bartv2.setTextColor(getResources().getColor(gray));
					break;
				case R.id.bar2:
					lcurmainMaskImageId=R.id.bar2;
					imgbar1.setImageResource(R.drawable.main_tab_item_home_normal);
					imgbar2.setImageResource(R.drawable.main_tab_item_user_focus);
					m_vp.setCurrentItem(1);
					bartv1.setTextColor(getResources().getColor(gray));
					bartv2.setTextColor(getResources().getColor(white));
					break;
				default:
					break;
			}

		}
	}
	//翻页动画1
	public class DepthPageTransformer implements PageTransformer {
		float MIN_SCALE = 0.75f;


		@Override
		public void transformPage(View view, float position) {
			int pageWidth = view.getWidth();
			if (position < -1) { // [-Infinity,-1)
				// This page is way off-screen to the left.
				view.setAlpha(0);
			} else if (position <= 0) { // [-1,0]
				// Use the default slide transition when
				// moving to the left page
				view.setAlpha(1);
				view.setTranslationX(0);
				view.setScaleX(1);
				view.setScaleY(1);
			} else if (position <= 1) { // (0,1]
				// Fade the page out.
				view.setAlpha(1 - position);
				// Counteract the default slide transition
				view.setTranslationX(pageWidth * -position);
				// Scale the page down (between MIN_SCALE and 1)
				float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
						* (1 - Math.abs(position));
				view.setScaleX(scaleFactor);
				view.setScaleY(scaleFactor);
			} else { // (1,+Infinity]
				// This page is way off-screen to the right.
				view.setAlpha(0);

			}
		}

	}
	//翻页动画2
	public class ZoomOutPageTransformer implements ViewPager.PageTransformer
	{
		private static final float MIN_SCALE = 0.85f;
		private static final float MIN_ALPHA = 0.5f;


		public void transformPage(View view, float position)
		{
			int pageWidth = view.getWidth();
			int pageHeight = view.getHeight();

			Log.e("TAG", view + " , " + position + "");

			if (position < -1)
			{ // [-Infinity,-1)
				// This page is way off-screen to the left.
				view.setAlpha(0);

			} else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
			{ // [-1,1]
				// Modify the default slide transition to shrink the page as well
				float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
				float vertMargin = pageHeight * (1 - scaleFactor) / 2;
				float horzMargin = pageWidth * (1 - scaleFactor) / 2;
				if (position < 0)
				{
					view.setTranslationX(horzMargin - vertMargin / 2);
				} else
				{
					view.setTranslationX(-horzMargin + vertMargin / 2);
				}

				// Scale the page down (between MIN_SCALE and 1)
				view.setScaleX(scaleFactor);
				view.setScaleY(scaleFactor);

				// Fade the page relative to its size.
				view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
						/ (1 - MIN_SCALE) * (1 - MIN_ALPHA));

			} else
			{ // (1,+Infinity]
				// This page is way off-screen to the right.
				view.setAlpha(0);
			}
		}
	}

}
