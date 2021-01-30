package com.musafi.parent;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MyActivity extends AppCompatActivity {

    private Car_Adapter car_adapter;
    private RecyclerView main_rv_list;
    private TextView main_txt_log;
    private long startTimeStamp = 0;
    private ArrayList<String> cars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_rv_list = findViewById(R.id.main_rv_list);
        main_txt_log = findViewById(R.id.main_txt_log);
        cars = new ArrayList<>();
        getList();
        readLogs();


    }

    private void init(){
        car_adapter = new Car_Adapter(this,cars);
        main_rv_list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,(int)1,false);
        main_rv_list.setLayoutManager(layoutManager);
        main_rv_list.setAdapter(car_adapter);
        car_adapter.SetOnItemClickListener(new Car_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String car) {

            }
        });
    }

    private void getList(){
        new GarageControllerPro().fetchAllMovies(new GarageControllerPro.CallBack_Garages() {
            @Override
            public void garages(MyGarage garages) {
                cars = new ArrayList<>(Arrays.asList(garages.getCars()));
                init();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimeStamp = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();
        long duration = System.currentTimeMillis() - startTimeStamp;
        MyTimeLogger.getInstance().addTlogTime("activity_main_time", (int) duration);
    }

    private void readLogs() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyTimeLogger.getInstance().getAllLogs(new MyTimeLogger.CallBack_Logs() {
                    @Override
                    public void dataReady(List<TLog> tLogs) {
                        int sum = 0;
                        int count = 0;
                        String s = "";
                        for (TLog tLog : tLogs) {
                            sum += tLog.duration/1000;
                        }

                        try {
                            s =""+sum;
                        } catch (Exception e) {
                            s = "0 s";
                        }
                        String finalS = s;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                main_txt_log.setText(finalS+ " s");
                            }
                        });
                    }
                });
            }
        }).start();
    }
}
