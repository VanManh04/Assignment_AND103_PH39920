package com.example.assignment_and103_ph39920.ADAPTER;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_and103_ph39920.APIService;
import com.example.assignment_and103_ph39920.Home;
import com.example.assignment_and103_ph39920.Model.MotorModel;
import com.example.assignment_and103_ph39920.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MotorADAPTER extends RecyclerView.Adapter<MotorADAPTER.viewholder> {
    private final Context context;
    private final ArrayList<MotorModel> list;
    Home mainActivity;
    public MotorADAPTER(Context context, ArrayList<MotorModel> list) {
        this.context = context;
        this.list = list;
        mainActivity=(Home) context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.items_motor, null);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        MotorModel motor = list.get(position);
        holder.txtName.setText("Name: " + motor.getName());
        holder.txtBrand.setText("Brand: " + motor.getBrand());
        holder.txtPrice.setText("Price: " + formatPrice(motor.getPrice()));
        holder.txtQuantity.setText("Quantity: " + motor.getQuantity());
        holder.txtStatus.setText("Status: " + motor.isStatus());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idMotor = motor.get_id();
//                Toast.makeText(context, idMotor+"", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Warning !");
                builder.setMessage("Are you sure you want to delete ?");
                builder.setIcon(R.drawable.warning);

                builder.setNegativeButton("No",null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        APIService.apiService.deleteMotor(idMotor).enqueue(new Callback<MotorModel>() {
                            @Override
                            public void onResponse(Call<MotorModel> call, Response<MotorModel> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(context, "Delete Success!", Toast.LENGTH_SHORT).show();
                                    list.remove(position);
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<MotorModel> call, Throwable t) {
                                Toast.makeText(context, "Delete Fall.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Home home = new Home();
//                home.showDialogMT(context,list.get(position),0);
                mainActivity.showDialogMT(context,list.get(position),0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        TextView txtName, txtBrand, txtPrice, txtQuantity, txtStatus;
        ImageButton btnUpdate, btnDelete;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName_ITMT);
            txtBrand = itemView.findViewById(R.id.txtBrand_ITMT);
            txtPrice = itemView.findViewById(R.id.txtPrice_ITMT);
            txtQuantity = itemView.findViewById(R.id.txtQuantity_ITMT);
            txtStatus = itemView.findViewById(R.id.txtstatus_ITMT);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    // Phương thức để định dạng giá
    private String formatPrice(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(price);
    }
}
