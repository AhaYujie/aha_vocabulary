package online.ahayujie.aha_vocabulary_app.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import online.ahayujie.aha_vocabulary_app.BR;
import online.ahayujie.aha_vocabulary_app.R;
import online.ahayujie.aha_vocabulary_app.databinding.ActivityMainBinding;
import online.ahayujie.aha_vocabulary_app.ui.adapter.MainViewPagerAdapter;
import online.ahayujie.aha_vocabulary_app.ui.clean.CleanFragment;
import online.ahayujie.aha_vocabulary_app.ui.word.WordFragment;

/**
 * 主活动，包含单词本和回收站两个Fragment
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> {

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
    public void initData() {
        super.initData();
        initViewPager();
        initBottomNav();
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
                        }
                        return false;
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


}



















