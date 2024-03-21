package com.example.assignment_and103_ph39920;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.core.graphics.Insets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_and103_ph39920.ADAPTER.MotorADAPTER;
import com.example.assignment_and103_ph39920.Model.MotorModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {
    RecyclerView rcv_Motor;
    FloatingActionButton fab_addMotor;
    MotorADAPTER motorADAPTER;
    private ArrayList<MotorModel> listMotor = new ArrayList<>();
    ImageView image_DL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.home);

        rcv_Motor = findViewById(R.id.rcv_Motor);
        fab_addMotor = findViewById(R.id.fab_addMotor);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Home,(v,insets)->{
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        }));
        loadData();

        fab_addMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Home.this, listMotor.size()+"", Toast.LENGTH_SHORT).show();
                showDialogMT(Home.this,new MotorModel(),1);
            }
        });
    }

    public void showDialogMT(Context context, MotorModel motorModel, int type) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater= ((Activity) context).getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_motor,null);
        builder.setView(view);
        Dialog dialog=builder.create();
        dialog.show();

        EditText edtName_DL = view.findViewById(R.id.edtName_DL);
        EditText edtBrand_DL = view.findViewById(R.id.edtBrand_DL);
        EditText edtQuantity_DL = view.findViewById(R.id.edtQuantity_DL);
        EditText edtPrice_DL = view.findViewById(R.id.edtPrice_DL);
        RadioButton rdoStatusTrue_DL = view.findViewById(R.id.rdoStatusTrue_DL);
        RadioButton rdoStatusFalse_DL = view.findViewById(R.id.rdoStatusFalse_DL);
        Button btnSave_DL = view.findViewById(R.id.btnSave_DL);
        Button btnCancel_DL = view.findViewById(R.id.btnCancel_DL);
        image_DL = view.findViewById(R.id.image_DL);



        if(type==0){
            edtName_DL.setText(motorModel.getName());
            edtBrand_DL.setText(motorModel.getBrand());
            edtQuantity_DL.setText(String.valueOf(motorModel.getQuantity()));
            edtPrice_DL.setText(formatPrice(motorModel.getPrice()));
            if(motorModel.isStatus()==true){
                rdoStatusTrue_DL.setChecked(true);
            }else {
                rdoStatusFalse_DL.setChecked(true);
            }
        }

        btnSave_DL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName_DL.getText().toString();
                String brand = edtBrand_DL.getText().toString();
                String quantity = edtQuantity_DL.getText().toString();
                String price = edtPrice_DL.getText().toString();
                boolean status;
                if (rdoStatusTrue_DL.isChecked() == true) {
                    // RadioButton True đã được chọn
                    status = true;
                } else if (rdoStatusFalse_DL.isChecked() == true) {
                    // RadioButton False đã được chọn
                    status = false;
                } else {
                    // Không có RadioButton nào được chọn
                    Toast.makeText(Home.this, "Bạn phải chọn Capital !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(name.trim().isEmpty()||brand.trim().isEmpty()||quantity.trim().isEmpty()||price.trim().isEmpty()){
                    Toast.makeText(context, "Không bỏ trống thông tin !", Toast.LENGTH_SHORT).show();
                }else if (Integer.parseInt(quantity)<0){
                    Toast.makeText(context, "Quantity phải >= 0", Toast.LENGTH_SHORT).show();
                }else if (Double.parseDouble(price)<0){
                    Toast.makeText(context, "Price phải >=0", Toast.LENGTH_SHORT).show();
                }else{
                    MotorModel motor = new MotorModel(name,brand,Double.parseDouble(price),Integer.parseInt(quantity),status);
                    Call<MotorModel> call = APIService.apiService.addMotor(motor);

                    if(type==0){
                        call=APIService.apiService.updateMotor(motorModel.get_id(),motor);
                    }

                    call.enqueue(new Callback<MotorModel>() {
                        @Override
                        public void onResponse(Call<MotorModel> call, Response<MotorModel> response) {
                            if (response.isSuccessful()){
                                String msg = "Add success";
                                if (type == 0){
                                    msg = "Update fall";
                                }
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                loadData();
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<MotorModel> call, Throwable t) {
                            String msg = "Add fail";
                            if (type == 0){
                                msg = "Update success";
                            }
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            loadData();
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
        btnCancel_DL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private String formatPrice(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("###.##"); // Sử dụng DecimalFormat để định dạng số thập phân
        return decimalFormat.format(price); // Trả về chuỗi số được định dạng
    }

    public  void  loadData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<ArrayList<MotorModel>> call = apiService.getMotor();

        call.enqueue(new Callback<ArrayList<MotorModel>>() {
            @Override
            public void onResponse(Call<ArrayList<MotorModel>> call, Response<ArrayList<MotorModel>> response) {
                if (response.isSuccessful()) {
                    listMotor = response.body();
//                    Toast.makeText(Home.this, "Success: " + listMotor.size(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(Home.this, listMotor.get(0).getName()+listMotor.get(0).getQuantity()+"", Toast.LENGTH_SHORT).show();

                    motorADAPTER = new MotorADAPTER(Home.this, listMotor);
                    LinearLayoutManager layout = new LinearLayoutManager(Home.this);
                    rcv_Motor.setLayoutManager(layout);

                    rcv_Motor.setAdapter(motorADAPTER);
                } else {
                    Toast.makeText(Home.this, "Response not successful: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e("Main", "Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MotorModel>> call, Throwable t) {
                Log.e("Main",t.getMessage());
            }
        });
    }

}