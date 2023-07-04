package com.example.texttoemotion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.texttoemotion.Home.HomeFragment;
import com.example.texttoemotion.Profile.ProfileFragment;
import com.example.texttoemotion.Settings.SettingsFragment;
import com.example.texttoemotion.authintication.LoginActivity;
import com.example.texttoemotion.controller.Callback;
import com.example.texttoemotion.controller.Emotion_server;
import com.example.texttoemotion.controller.UserAccountdata;
import com.example.texttoemotion.databinding.ActivityMainBinding;
import com.example.texttoemotion.databinding.CustomBarDialogBinding;
import com.example.texttoemotion.uplaodcomplaint.AddComplaintFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private String tag="MainActivity";
    private UserAccountdata insUserAccountdata;
    private int prevoisselction=R.id.home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this,android.R.color.transparent));
        getWindow().setBackgroundDrawable(getDrawable(R.drawable.actionbar));
        getSupportActionBar().hide();
        new Thread(() -> {
            insUserAccountdata=UserAccountdata.getInstance(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance(), obj -> {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this,obj.getMessage(),Toast.LENGTH_LONG).show();
                    Log.e(tag,obj.getMessage());
                });
            });

        }).start();
        binding.bottomnav.setSelectedItemId(R.id.home);
            binding.bottomnav.setOnItemSelectedListener(item -> {
                        Fragment F = null;
                        Fragment originalFragment=(Fragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view_tag);
                        switch(item.getItemId()){
                            case R.id.home:
                                F = new HomeFragment();
                                break;
                            case R.id.logout:
                                showLogoutDialog();
                                break;
                            case R.id.profile:
                                F = new ProfileFragment();
                                break;
                            case R.id.addcomplaint:
                                F = new AddComplaintFragment();
                                break;

                        }
                 if(F!=null&&originalFragment!=null&&!originalFragment.getClass().equals(F.getClass())) {
                     getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, F).commit();
                 }
                 if(F!=null){
                     prevoisselction=item.getItemId();

                 }
                return true;
        });
    }

    private void showLogoutDialog() {
        CustomBarDialogBinding barDialogBinding= CustomBarDialogBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(barDialogBinding.getRoot());
        barDialogBinding.yes.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            dialog.dismiss();
        });
        barDialogBinding.no.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.setOnDismissListener(dialog1 -> {
            binding.bottomnav.setSelectedItemId(prevoisselction);
        });
        dialog.show();
    }

    public void change_to_home(){
       getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag,new HomeFragment()).commit();
       binding.bottomnav.setSelectedItemId(R.id.home);
   }


}