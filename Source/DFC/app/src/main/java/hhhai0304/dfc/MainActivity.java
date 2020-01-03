package hhhai0304.dfc;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import adapter.DrawerListAdapter;
import fragments.BaseFragment;
import fragments.FragmentAbout;
import helper.DatabaseHelper;
import item.CategoryItem;
import item.Cookie;
import item.NavItem;

public class MainActivity extends AppCompatActivity
{
    Menu menu;
    Boolean isOpened = false;
    Cookie cookie;
    DatabaseHelper db;
    LinearLayout llMain;
    ActionBar actionBar;
    ListView mDrawerList;
    TextView userName, desc;
    DrawerListAdapter adapter;
    DrawerLayout mDrawerLayout;
    ArrayList<CategoryItem> ca;
    ArrayList<NavItem> mNavItems;
    ActionBarDrawerToggle mDrawerToggle;
    RelativeLayout mDrawerPane, rlprofileBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(MainActivity.this);
        cookie = db.layCookie();
        KhaiBao();

        rlprofileBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccountDialog();
            }
        });

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                selectItemFromDrawer(position);
            }
        });

        if(!cookie.UserID.equals("null")) {
            userName.setText("Xin chào, " + cookie.LastName + " " + cookie.FirstName);
            desc.setText("Quản lý Tài khoản");
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_launcher, R.string.space, R.string.space) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_cart);
        item.setIcon(R.drawable.ic_cart);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (isOpened)
                {
                    mDrawerLayout.closeDrawers();
                    isOpened = false;
                }
                else
                {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                    isOpened = true;
                }
            }
        }
        if (item.getItemId() == R.id.action_cart) {
            Intent i = new Intent(MainActivity.this, CartActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void selectItemFromDrawer(final int position)
    {
        Fragment fragment;
        FragmentManager fragmentManager = getFragmentManager();

        mDrawerList.setItemChecked(position, true);

        if (position == 0)
        {
            Intent i = new Intent(this, OrderManagerActivity.class);
            startActivity(i);
        }
        else if (position == 1)
        {
            setTitle("Trang Chủ");
            fragment = new BaseFragment() {
                @Override
                protected String setCategory() {
                    return "";
                }
            };
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }
        else if (position == (mNavItems.size() - 1))
        {
            setTitle("Giới thiệu");
            fragment = new FragmentAbout();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }
        else
        {
            setTitle(mNavItems.get(position).mTitle);
            fragment = new BaseFragment() {
                @Override
                protected String setCategory() {
                    return mNavItems.get(position).mCategoryID;
                }
            };
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

    private void KhaiBao()
    {
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        userName = (TextView) findViewById(R.id.userName);
        desc = (TextView) findViewById(R.id.desc);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        rlprofileBox = (RelativeLayout) findViewById(R.id.profileBox);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);

        mNavItems = new ArrayList<>();
        mNavItems.add(new NavItem("Quản lý Đơn hàng", "Xem thông tin các Đơn hàng", R.drawable.ic_order, ""));
        mNavItems.add(new NavItem("Tổng Hợp", "Tất cả món ăn của Dark Fast Food", R.drawable.ic_home, ""));

        ca = new ArrayList<>();
        ca = db.getAllCategory();
        for (int i = 0; i < ca.size(); i++)
            mNavItems.add(new NavItem(ca.get(i).getName(), ca.get(i).getDetail(), R.drawable.ic_dish, ca.get(i).getCategoryID()));

        mNavItems.add(new NavItem("Giới Thiệu", "Thông tin về Dark Fast Food", R.drawable.ic_about, ""));

        adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        Fragment fragment = new BaseFragment() {
            @Override
            protected String setCategory() {
                return "";
            }
        };
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }

    public void createAccountDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(cookie.LastName + " " + cookie.FirstName);
        builder.setMessage("Tài khoản: " + cookie.Username + "\nĐịa chỉ: " + cookie.Address + ", quận " + cookie.District
                + "\nThành phố: " + cookie.City + "\nĐiện thoại: " + cookie.Phone + "\nEmail: " + cookie.Email);

        builder.setNegativeButton("CHỈNH SỬA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                startActivity(i);
            }
        });
        builder.setPositiveButton("MẬT KHẨU", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(MainActivity.this, ChangePassActivity.class);
                startActivity(i);
            }
        });
        builder.setNeutralButton("ĐĂNG XUẤT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.xoaCookie();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}