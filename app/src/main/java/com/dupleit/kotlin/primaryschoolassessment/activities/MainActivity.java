package com.dupleit.kotlin.primaryschoolassessment.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dupleit.kotlin.primaryschoolassessment.Evidence.AddEvidence;
import com.dupleit.kotlin.primaryschoolassessment.FullImageView.FullImage;
import com.dupleit.kotlin.primaryschoolassessment.R;
import com.dupleit.kotlin.primaryschoolassessment.activities.Login.UI.LoginActivity;
import com.dupleit.kotlin.primaryschoolassessment.addSubframeMarksDetails.SubFramworkMarksDetails;
import com.dupleit.kotlin.primaryschoolassessment.fragments.AssessmentRecord.AssessmentRecordFragment;
import com.dupleit.kotlin.primaryschoolassessment.fragments.evidences.AllEvidences;
import com.dupleit.kotlin.primaryschoolassessment.fragments.framework.FrameworkFragment;
import com.dupleit.kotlin.primaryschoolassessment.fragments.HomeFragment;
import com.dupleit.kotlin.primaryschoolassessment.fragments.allStudents.Ui.StudentListFragment;
import com.dupleit.kotlin.primaryschoolassessment.fragments.Profile.ProfileFragment;

import com.dupleit.kotlin.primaryschoolassessment.otherHelper.PreferenceManager;
import com.dupleit.kotlin.primaryschoolassessment.otherHelper.Utils;
import com.dupleit.kotlin.primaryschoolassessment.teacherClasss.getTeacherCurrentClass;
import com.dupleit.kotlin.primaryschoolassessment.teacherClasss.selectTeacherClass.selectStudentClass;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg;
    CircleImageView imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";
    private static final int REQUEST= 112;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_STUDENT_LIST = "student_list";
    private static final String TAG_FRAMEWORK = "framework";
    private static final String TAG_EVIDENCES = "evidence";
    private static final String TAG_ASSESSMENT_RECORDS = "assessment_record";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();
        if ((new PreferenceManager(this).getUserEmail()).equals("")){
            Intent i = new Intent(this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }

        if ((new PreferenceManager(this).getTeacherClassId()).equals("")){
            Intent i = new Intent(this, selectStudentClass.class);
            i.putExtra("activityType","login");
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName =  navHeader.findViewById(R.id.name);
        txtWebsite = navHeader.findViewById(R.id.website);
        imgNavHeaderBg = navHeader.findViewById(R.id.img_header_bg);
        imgProfile = navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);



        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    public void updateNav(int index,String fragmentTag){
        navItemIndex = index;
        CURRENT_TAG = fragmentTag;
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website

        if (getTeacherName().equals("")){
            txtName.setText("Welcome User");
        }else{
            txtName.setText("Welcome "+getTeacherName());
        }
        txtWebsite.setText("www.dupleit.com");

        // loading header background image
        Glide.with(this).load(R.drawable.nav_header)
                .into(imgNavHeaderBg);

        // Loading profile image

        if (getTeacherImage().equals("")){
            Glide.with(this).load(R.drawable.ic_account_circle_black_36dp).into(imgProfile);
        }else{
            Glide.with(this).load(Utils.webUrlHome+getTeacherImage())
                    .into(imgProfile);
        }


        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getTeacherImage().equals("")){
                    Intent i = new Intent(MainActivity.this, FullImage.class);
                    i.putExtra("ImagePath",Utils.webUrlHome+getTeacherImage());
                    startActivity(i);
                }
            }
        });
        // showing dot next to notifications label
       // navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }
    private String getTeacherName() {
        return  new PreferenceManager(this).getUsername();
    }

    private String getTeacherImage() {
        return  new PreferenceManager(this).getUserImage();
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    public void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }



        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // photos
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            case 2:
                // movies fragment
                StudentListFragment studentListFragment = new StudentListFragment();
                return studentListFragment;
            case 3:
                // notifications fragment
                FrameworkFragment frameworkFragment = new FrameworkFragment();
                return frameworkFragment;
            case 4:
                // notifications fragment
                AllEvidences AllEvidences= new AllEvidences();
                return AllEvidences;

            case 5:
                // settings fragment
                AssessmentRecordFragment assessmentRecordFragment = new AssessmentRecordFragment();
                return assessmentRecordFragment;
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_profile:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PROFILE;
                        break;
                    case R.id.nav_student_list:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_STUDENT_LIST;
                        break;
                    case R.id.nav_framework:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_FRAMEWORK;
                        break;
                    case R.id.nav_evidence:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_EVIDENCES;
                        break;
                    case R.id.nav_assessment_record:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_ASSESSMENT_RECORDS;
                        break;
                    case R.id.nav_change_class:
                        // launch new intent instead of loading fragment
                        //startActivity(new Intent(MainActivity.this, getTeacherCurrentClass.class));
                        Intent i = new Intent(MainActivity.this, selectStudentClass.class);
                        i.putExtra("activityType","currentClass");
                        startActivity(i);drawer.closeDrawers();
                        return true;
                    case R.id.nav_add_subframe_marks_detail:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, SubFramworkMarksDetails.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_add_evidence:
                        // launch new intent instead of loading fragment
                        //startActivity(new Intent(MainActivity.this, SubFramworkMarksDetails.class));
                        checkPermissionUser();
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_faq:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, FaqActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_logout:
                        // launch new intent instead of loading fragment
                        new PreferenceManager(getApplicationContext()).saveUserEmail("");
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }


    private void checkPermissionUser() {
        if (Build.VERSION.SDK_INT >= 23) {
            Log.d("TAG","@@@ IN IF Build.VERSION.SDK_INT >= 23");
            String[] PERMISSIONS = {
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            };
            if (!hasPermissions(MainActivity.this, PERMISSIONS)) {
                Log.d("TAG","@@@ IN IF hasPermissions");
                ActivityCompat.requestPermissions((Activity) MainActivity.this, PERMISSIONS, REQUEST );
            } else {
                Log.d("TAG","@@@ IN ELSE hasPermissions");
                goToEvidenceActivity();
            }
        } else {
            Log.d("TAG","@@@ IN ELSE  Build.VERSION.SDK_INT >= 23");
            goToEvidenceActivity();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAG","@@@ PERMISSIONS grant");
                    goToEvidenceActivity();

                } else {
                    Log.d("TAG","@@@ PERMISSIONS Denied");
                    Toast.makeText(MainActivity.this, "PERMISSIONS Denied", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    private void goToEvidenceActivity() {
        startActivity(new Intent(MainActivity.this, AddEvidence.class));
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }else
                {
                    goToEvidenceActivity();
                    return true;
                }
            }
        }
        return true;
    }
}
