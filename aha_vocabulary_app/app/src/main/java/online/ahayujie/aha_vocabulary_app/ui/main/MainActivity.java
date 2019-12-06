package online.ahayujie.aha_vocabulary_app.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import online.ahayujie.aha_vocabulary_app.BR;
import online.ahayujie.aha_vocabulary_app.R;
import online.ahayujie.aha_vocabulary_app.app.MyApplication;
import online.ahayujie.aha_vocabulary_app.app.MyViewModelFactory;
import online.ahayujie.aha_vocabulary_app.databinding.ActivityMainBinding;
import online.ahayujie.aha_vocabulary_app.ui.BaseAbstractActivity;
import online.ahayujie.aha_vocabulary_app.ui.adapter.MainViewPagerAdapter;
import online.ahayujie.aha_vocabulary_app.ui.clean.CleanFragment;
import online.ahayujie.aha_vocabulary_app.ui.login.LoginActivity;
import online.ahayujie.aha_vocabulary_app.ui.word.WordFragment;

/**
 * 主活动，包含单词本和回收站两个Fragment
 *
 * @author aha
 */
public class MainActivity extends BaseAbstractActivity<ActivityMainBinding, MainViewModel> {

    private AlertDialog.Builder logOutDialog;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    /**
     * 初始化根布局
     *
     * @param savedInstanceState
     * @return 布局layout的id
     */
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    @Override
    public int initVariableId() {
        return BR.mainViewModel;
    }

    @Override
    public MainViewModel initViewModel() {
        MyViewModelFactory viewModelFactory = MyViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        initViewPager();
        initBottomNav();
        initLogoutDialog();
        initDrawerLayout();
    }

    @Override
    public void initViewObservable() {
        // 监听退出状态
        viewModel.getLogoutStatusLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.finish();
                    MainActivity.this.viewModel.finish();
                }
            }
        });
    }

    private void initDrawerLayout() {
        TextView userNameTextView = binding.mainDrawerNaviView.getHeaderView(0)
                .findViewById(R.id.main_drawer_layout_username);
        userNameTextView.setText(viewModel.getDataRepository().getUserName());
        binding.mainDrawerNaviView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.main_drawer_logout:
                        logOutDialog.show();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void initLogoutDialog() {
        logOutDialog = new AlertDialog.Builder(this);
        logOutDialog.setTitle("提示");
        logOutDialog.setMessage("你确定要退出登录吗?");
        logOutDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        logOutDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                viewModel.logout();
            }
        });
    }

    /**
     * 初始化底部导航栏
     */
    private void initBottomNav() {
        binding.mainBottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.main_bottom_nav_word:
                                binding.mainViewPager.setCurrentItem(0);
                                return true;
                            case R.id.main_bottom_nav_clean:
                                binding.mainViewPager.setCurrentItem(1);
                                return true;
                            default:
                                return false;
                        }
                    }
                }
        );
    }

    /**
     * 初始化viewPager
     */
    private void initViewPager() {
        MainViewPagerAdapter viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.setFragmentList(initFragmentList());
        binding.mainViewPager.setAdapter(viewPagerAdapter);
        binding.mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.mainBottomNav.setSelectedItemId(binding.mainBottomNav.getMenu().
                        getItem(position).getItemId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化单词本Fragment和回收站Fragment
     * @return
     */
    private List<Fragment> initFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new WordFragment());
        fragmentList.add(new CleanFragment());
        return fragmentList;
    }

    public DrawerLayout getMainDrawerLayout() {
        return binding.mainDrawerLayout;
    }

}



















