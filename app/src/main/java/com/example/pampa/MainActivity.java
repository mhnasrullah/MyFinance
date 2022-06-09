package com.example.pampa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.pampa.api.ApiConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.*;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> lb = new ArrayList<>();
    ArrayList<String> lt = new ArrayList<>();

    private FirebaseAuth auth;
    private FirebaseUser user;
    private RecyclerView rv,rvd;
    private BalanceAdapter adap;
    private DetailAdapter adapd;
    private EditText ettitle,etamount,etdes;
    private ImageButton nav_add, nav_recap, nav_acc, btndel;
    private ConstraintLayout clacc, boxbalto;
    private Button btnout,btnin,btnsp,btnsave;
    private TextView tnrecap,tnacc,tnadd,displayname,etbalto,accdisname,accemail;
    private RelativeLayout bgform;
    private DatabaseReference mDatabase;
    private ScrollView sv;
    private boolean StAmount;
    private ArrayList<TodayModel> todaymodel = new ArrayList<>();
    private ArrayList<DaysModel> daymodel = new ArrayList<>();
    private NumberFormat currformat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currformat = NumberFormat.getCurrencyInstance(new Locale("in","ID"));

        mDatabase = FirebaseDatabase.getInstance().getReference(ModelDetail.class.getSimpleName());

        accdisname = findViewById(R.id.accdisname);
        accemail = findViewById(R.id.accemail);
        etdes = findViewById(R.id.etdescrib);
        displayname = findViewById(R.id.textView2);
        nav_add = findViewById(R.id.btn_plus);
        nav_recap = findViewById(R.id.btn_recap);
        nav_acc = findViewById(R.id.btn_acc);
        clacc = findViewById(R.id.clacc);
        btnout = findViewById(R.id.btn_out);
        tnrecap = findViewById(R.id.tnrecap);
        tnacc = findViewById(R.id.tnacc);
        tnadd = findViewById(R.id.tnadd);
        btnin = findViewById(R.id.btn_income);
        btnsp = findViewById(R.id.btn_spend);
        bgform = findViewById(R.id.bgform);
        btndel = findViewById(R.id.btn_dell);
        btnsave = findViewById(R.id.btn_save);
        sv = findViewById(R.id.scrollview);
        ettitle = findViewById(R.id.etf_title);
        etamount = findViewById(R.id.etf_amount);
        rvd = findViewById(R.id.rvd);
        rv = findViewById(R.id.rv);
        boxbalto = findViewById(R.id.constraintLayout2);
        etbalto = findViewById(R.id.etbalto);

        StAmount = true;

        displayname.setText(user.getDisplayName());
        accdisname.setText(user.getDisplayName());
        accemail.setText(user.getEmail());

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String t = ettitle.getText().toString();
                int am = Integer.parseInt(etamount.getText().toString());

                if (ettitle != null && etamount.getText() != null){
                    Call<AddTodayResponse> addtoday = ApiConfig.getApiService().addToday(user.getUid(),t,am,StAmount);
                    addtoday.enqueue(new Callback() {
                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) {
                            Log.d("APIDEBUG", "onResponse: Success");
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Log.d("APIDEBUG", "ADD ERROR");
                        }
                    });
                }
                ettitle.setText("");
                etamount.setText(null);
                etdes.setText("");

                reload();
            }
        });

        btnin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnin.setBackgroundResource(R.drawable.btn_purple);
                btnin.setTextColor(getColor(R.color.white));
                bgform.setBackgroundResource(R.drawable.bgformpurple);
                btnsp.setBackgroundResource(R.drawable.btn_outred);
                btnsp.setTextColor(getColor(R.color.pink));
                btndel.setBackgroundResource(R.drawable.bgformpink);
                btnsave.setBackgroundResource(R.drawable.btn_purple);
                StAmount = true;
            }
        });

        btnsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StAmount = false;
                btnin.setBackgroundResource(R.drawable.btn_outpurple);
                btnin.setTextColor(getColor(R.color.purple));
                bgform.setBackgroundResource(R.drawable.bgformpink);
                btnsp.setBackgroundResource(R.drawable.btnpink);
                btnsp.setTextColor(getColor(R.color.white));
                btndel.setBackgroundResource(R.drawable.bgformpurple);
                btnsave.setBackgroundResource(R.drawable.btnpink);
            }
        });

        nav_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RECAP SECTION
                rv.setVisibility(View.GONE);
                nav_recap.setImageResource(R.drawable.ic_recap);
                tnrecap.setVisibility(View.VISIBLE);
//                ACC SECTION
                clacc.setVisibility(View.GONE);
                btnout.setVisibility(View.GONE);
                nav_acc.setImageResource(R.drawable.ic_vacc);
                tnacc.setVisibility(View.VISIBLE);
//                ADD SECTION
                sv.setVisibility(View.VISIBLE);
                nav_add.setImageResource(R.drawable.ic_plus_a);
                tnadd.setVisibility(View.GONE);
            }
        });

        nav_recap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RECAP SECTION
                rv.setVisibility(View.VISIBLE);
                nav_recap.setImageResource(R.drawable.ic_recap_a);
                tnrecap.setVisibility(View.GONE);
//                ACC SECTION
                clacc.setVisibility(View.GONE);
                btnout.setVisibility(View.GONE);
                nav_acc.setImageResource(R.drawable.ic_vacc);
                tnacc.setVisibility(View.VISIBLE);
//                ADD SECTION
                sv.setVisibility(View.GONE);
                nav_add.setImageResource(R.drawable.ic_plus);
                tnadd.setVisibility(View.VISIBLE);
            }
        });
        nav_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RECAP SECTION
                rv.setVisibility(View.GONE);
                nav_recap.setImageResource(R.drawable.ic_recap);
                tnrecap.setVisibility(View.VISIBLE);
//                ACC SECTION
                clacc.setVisibility(View.VISIBLE);
                btnout.setVisibility(View.VISIBLE);
                nav_acc.setImageResource(R.drawable.ic_vacc_a);
                tnacc.setVisibility(View.GONE);
//                ADD SECTION
                sv.setVisibility(View.GONE);
                nav_add.setImageResource(R.drawable.ic_plus);
                tnadd.setVisibility(View.VISIBLE);
            }
        });
        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etdes.setText("");
                etamount.setText(null);
                ettitle.setText("");
            }
        });

        btnout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(MainActivity.this,LandingActivity.class));
                finish();
            }
        });

        getresdays();
        getrestoday();

        Log.d("APIDEBUG", user.getUid());

    }

    private void getresdays(){
        Call<DayResponse> resDay = ApiConfig.getApiService().getDays(user.getUid());
        resDay.enqueue(new Callback<DayResponse>() {
            @Override
            public void onResponse(Call<DayResponse> call, Response<DayResponse> response) {
//                Log.d("APIDEBUG", response.body().getData().toString());
                for (int i = response.body().getData().size()-1 ; i >= 0 ; i--){
                    daymodel.add(response.body().getData().get(i));
                }

                adap = new BalanceAdapter(daymodel);
                RecyclerView.LayoutManager lm = new LinearLayoutManager(MainActivity.this);
                rv.setLayoutManager(lm);
                rv.setAdapter(adap);

            }

            @Override
            public void onFailure(Call<DayResponse> call, Throwable t) {
                Log.d("APIDEBUG", "onFailure: GETERROR");
            }
        });
    }
    private void getrestoday(){
        Call<TodayResponse> res = ApiConfig.getApiService().getToday(user.getUid());
        res.enqueue(new Callback<TodayResponse>() {
            @Override
            public void onResponse(Call<TodayResponse> call, Response<TodayResponse> response) {
                Log.d("APIDEBUGB", response.body().getData().toString());
                todaymodel.addAll(response.body().getData());

                adapd = new DetailAdapter(todaymodel);
                RecyclerView.LayoutManager lmd = new LinearLayoutManager(MainActivity.this);
                rvd.setLayoutManager(lmd);
                rvd.setAdapter(adapd);

                int spd = 0;
                int inc = 0;
                for (int i = 0 ; i < todaymodel.size(); i ++){
                    if (todaymodel.get(i).isStatus()){
                        inc += todaymodel.get(i).getAmount();
                    }else{
                        spd += todaymodel.get(i).getAmount();
                    }
                }

                int bal = inc-spd;


                if (bal < 0){
//                    etbalto.setText("-Rp"+String.valueOf(bal*(-1))+",00");
                    etbalto.setText("-"+currformat.format(bal*(-1)));
                    boxbalto.setBackgroundResource(R.drawable.bg_item_r);
                }else{
//                    etbalto.setText("+Rp"+String.valueOf(bal)+",00");
                    etbalto.setText("+"+currformat.format(bal));
                    boxbalto.setBackgroundResource(R.drawable.bg_item_p);
                }

//                Log.d("APIDEBUG", todaymodel.toString());
            }

            @Override
            public void onFailure(Call<TodayResponse> call, Throwable t) {
                Log.d("APIDEBUG", "onFailure: GETERROR");
            }
        });
    }

    public void reload(){
        Intent i = new Intent(MainActivity.this, MainActivity.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);
    }


    class ModelDetail{
        public String Title;
        public String Des;
        public boolean St;

        public ModelDetail(String t, String a, boolean s){
            this.Title = t;
            this.Des = a;
            this.St = s;
        }

    }

    public void writeNewDetail(String t, String a, boolean st) {
        ModelDetail detail = new ModelDetail(t,a,st);
        mDatabase.push().setValue(detail);
    }

//    public void addata(){
//        this.list.add(new Model("24","+Rp50.000,00", "Rp150.000,00", "Rp50.000,00","plus"));
//        this.list.add(new Model("23","+Rp50.000,00", "Rp150.000,00", "Rp50.000,00","min"));
//        this.list.add(new Model("22","+Rp50.000,00", "Rp150.000,00", "Rp50.000,00","plus"));
//        this.list.add(new Model("21","+Rp50.000,00", "Rp150.000,00", "Rp50.000,00","min"));
//        this.list.add(new Model("20","+Rp50.000,00", "Rp150.000,00", "Rp50.000,00","plus"));
//        this.list.add(new Model("19","+Rp50.000,00", "Rp150.000,00", "Rp50.000,00","plus"));
//    }

//    public void addetail(){
//        this.lb.add("+2000");
//        this.lt.add("Makan");
//
//        this.lb.add("+5000");
//        this.lt.add("Makanan");
//    }

}