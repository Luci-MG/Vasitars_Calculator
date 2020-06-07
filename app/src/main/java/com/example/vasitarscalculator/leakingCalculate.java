package com.example.vasitarscalculator;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import java.lang.*;

public class leakingCalculate extends Fragment {

    private int type_of_leaking = 10;
    private int select_wrapping = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_leaking_calculate, container, false);
        /////////////
        String[] Menu_leaking_type = new String[] {"Circumferential (in degrees)", "Through Hole (Hole diameter)", "Through Hole (Hole dimensions)"};
        String[] Menu_wrapper_types = new String[] {"Helical", "Straight"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        Objects.requireNonNull(getContext()),
                        R.layout.support_simple_spinner_dropdown_item,
                        Menu_leaking_type);

        AutoCompleteTextView menuLeaking = view.findViewById(R.id.type_of_leaking_L_14);
        menuLeaking.setAdapter(adapter);

        ArrayAdapter<String> adapterr =
                new ArrayAdapter<>(
                        getContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        Menu_wrapper_types);

        AutoCompleteTextView menuWrapper = view.findViewById(R.id.select_wrapping_L_11);
        menuWrapper.setAdapter(adapterr);
        final LinearLayout linearLayout = view.findViewById(R.id.first);
        final LinearLayout linearLayout1 = view.findViewById(R.id.second);
        final LinearLayout linearLayout2 = view.findViewById(R.id.third);

        menuLeaking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View root, int position, long id) {
                if(position == 0){
                    linearLayout.setVisibility(VISIBLE);
                    linearLayout1.setVisibility(GONE);
                    linearLayout2.setVisibility(GONE);
                    type_of_leaking = 0;
                }else if(position == 1){
                    linearLayout.setVisibility(GONE);
                    linearLayout1.setVisibility(VISIBLE);
                    linearLayout2.setVisibility(GONE);
                    type_of_leaking = 1;
                }else if(position == 2){
                    linearLayout.setVisibility(GONE);
                    linearLayout1.setVisibility(GONE);
                    linearLayout2.setVisibility(VISIBLE);
                    type_of_leaking = 2;
                }
            }
        });

        menuWrapper.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    select_wrapping = 0;
                }else if(position == 1){
                    select_wrapping = 1;
                }
            }


        });
        /////////////
        Button button = view.findViewById(R.id.calculateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type_of_leaking == 10){
                    Toast.makeText(getContext(),"Please Select Types Of Leaking",Toast.LENGTH_LONG).show();
                    return;
                }else if(select_wrapping == 10){
                    Toast.makeText(getContext(),"Please Select Wrapping Method",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    calculate(view);
                }
            }
        });
        /////////////
        return view;
    }



    public void calculate(View view){



        double L = 0d;
        double Mass = 0d;

        TextInputLayout L_1 = view.findViewById(R.id.L_1);//Pipe Diameter (in mm)
        TextInputLayout L_2 = view.findViewById(R.id.L_2);//Pipe Original Wall Thickness (in mm)
        TextInputLayout L_3 = view.findViewById(R.id.L_3);//Pipe Material Strength (in psi)
        TextInputLayout L_4 = view.findViewById(R.id.L_4);//Pipe Working Pressure (in bars)
        TextInputLayout L_7 = view.findViewById(R.id.L_7);//Width of CSM Fiber (in mm)
        TextInputLayout L_8 = view.findViewById(R.id.L_8);//Width of WRM Fiber (in mm)
        TextInputLayout L_9 = view.findViewById(R.id.L_9);//GSM of CSM Fiber (in g/m2)
        TextInputLayout L_10 = view.findViewById(R.id.L_10);//GSM of WRM Fiber (in g/m2)
        AutoCompleteTextView menuWrapper = view.findViewById(R.id.select_wrapping_L_11);//select_wrapping_L_11
        TextInputLayout L_12 = view.findViewById(R.id.L_12);//Pipe Live Pressure (in bars)
        AutoCompleteTextView menuLeaking = view.findViewById(R.id.type_of_leaking_L_14);//type_of_leaking_L_14
        TextInputLayout L_20 = view.findViewById(R.id.L_20);//Axial Extent of Hole (in mm) _3
        TextInputLayout L_21 = view.findViewById(R.id.L_21);//Circumferential Extent of Hole (in mm) _3
        TextInputLayout L_22 = view.findViewById(R.id.L_22);//Hole diameter (in mm) _2
        TextInputLayout L_50 = view.findViewById(R.id.L_50);//Longitudinal Length to be considered _1
        TextInputLayout L_90 = view.findViewById(R.id.L_90);//Leaking Extent (in degrees) _1


        try {
            double D = Double.parseDouble(Objects.requireNonNull(L_1.getEditText()).getText().toString());
            double POWT = Double.parseDouble(Objects.requireNonNull(L_2.getEditText()).getText().toString());
            //double PMS = Double.parseDouble((L_3.getEditText()).getText().toString());
            //double PWP = Double.parseDouble((L_4.getEditText()).getText().toString());
            double W_CSM = Double.parseDouble(Objects.requireNonNull(L_7.getEditText()).getText().toString());
            double W_WRM = Double.parseDouble(Objects.requireNonNull(L_8.getEditText()).getText().toString());
            double G_CSM = Double.parseDouble(Objects.requireNonNull(L_9.getEditText()).getText().toString());
            double G_WRM = Double.parseDouble(Objects.requireNonNull(L_10.getEditText()).getText().toString());
            //double MWT = Double.parseDouble((L_12.getEditText()).getText().toString());
            if (type_of_leaking == 0) {
                double theta = Double.parseDouble(Objects.requireNonNull(L_50.getEditText()).getText().toString());
                double L_defect = Double.parseDouble(Objects.requireNonNull(L_90.getEditText()).getText().toString());
                double w = ((2 * (Math.PI) * (D / 2)) / 360) * theta;
                double t = 3d;
                double Volume = L_defect * w * 3 * 1.4;
                Mass = (Volume * 1.5) * (1e-3);
                L = getRepairLength(L_defect, D, POWT);
            } else if (type_of_leaking == 1) {
                double w = Double.parseDouble(Objects.requireNonNull(L_22.getEditText()).getText().toString());
                double L_defect = 10 * w;
                double t = 3d;
                double Volume = (((Math.PI) * 100 * w * w) / 4) * 3 * (1.4);
                Mass = (Volume * 1.5) * (1e-3);
                L = getRepairLength(L_defect, D, POWT);
            } else if (type_of_leaking == 2) {
                double x = Double.parseDouble(Objects.requireNonNull(L_20.getEditText()).getText().toString());
                double y = Double.parseDouble(Objects.requireNonNull(L_21.getEditText()).getText().toString());
                double L_defect = 10 * x;
                double t = 3d;
                double Volume = 100 * x * y * 3;
                Mass = (Volume * 1.5) * (1e-3) * (1.4);
                L = getRepairLength(L_defect, D, POWT);
            }
            double RapidPartA = Mass / 1.67;
            double RapidPartB = Mass - RapidPartA;
            double N_WRM = numberOfTurns(L, W_WRM, select_wrapping);
            double N_CSM = numberOfTurns(L, W_CSM, select_wrapping);

            double P = Math.PI * D;
            double L_WRM = getLengthWRMFiber(N_WRM, P, W_WRM, select_wrapping);
            double L_CSM = getLengthCSMFiber(N_CSM, P, W_CSM, select_wrapping);

            double Weight_WRM_Fiber = getWeightWRMFiber(L_WRM, W_WRM, G_WRM);
            double Weight_CSM_Fiber = getWeightCSMFiber(L_CSM, W_CSM, G_CSM);

            double Weight_Of_Resin = getResignWeight(Weight_WRM_Fiber, Weight_CSM_Fiber);
            double Weight_Hardener = getWeightHardner(Weight_Of_Resin);

            L_CSM = L_CSM / 1e3;
            L_WRM = L_WRM / 1e3;

            //Toast.makeText(getContext(), Double.toString(Weight_Hardener), Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(), "SWIPE UP FOR RESULTS", Toast.LENGTH_LONG).show();
            TextView L_01 = view.findViewById(R.id.L_01);
            TextView L_02 = view.findViewById(R.id.L_02);
            TextView L_03 = view.findViewById(R.id.L_03);
            TextView L_04 = view.findViewById(R.id.L_04);
            TextView L_06 = view.findViewById(R.id.L_06);
            TextView L_07 = view.findViewById(R.id.L_07);
            TextView L_011 = view.findViewById(R.id.L_011);
            TextView L_012 = view.findViewById(R.id.L_012);
            TextView L_015 = view.findViewById(R.id.L_015);
            TextView L_016 = view.findViewById(R.id.L_016);
            TextView L_017 = view.findViewById(R.id.L_017);
            TextView L_018 = view.findViewById(R.id.L_018);
            TextView L_019 = view.findViewById(R.id.L_019);

            LinearLayout output_0 = view.findViewById(R.id.output_0);
            LinearLayout output_1 = view.findViewById(R.id.output_1);
            LinearLayout output_2 = view.findViewById(R.id.output_2);

            output_0.setVisibility(VISIBLE);
            output_1.setVisibility(VISIBLE);
            BigDecimal temp = new BigDecimal(Weight_Of_Resin).setScale(2, RoundingMode.HALF_EVEN);
            L_01.setText(temp.toString());
            temp = new BigDecimal(Weight_Hardener).setScale(2, RoundingMode.HALF_EVEN);
            L_02.setText(temp.toString());
            if(L%5 != 0)
            {
                L = L + 5 - (L%5);
            }
            if(select_wrapping == 0){
                L_019.setText("50%");
            }else if(select_wrapping == 1){
                L_019.setText("100%");
            }
            temp = new BigDecimal(L_CSM).setScale(1, RoundingMode.HALF_EVEN);
            L_03.setText(temp.toString());
            temp = new BigDecimal(L_WRM).setScale(1, RoundingMode.HALF_EVEN);
            L_04.setText(temp.toString());
            L_06.setText("2");
            L_07.setText("4");
            temp = new BigDecimal(L).setScale(1, RoundingMode.HALF_EVEN);
            L_015.setText(temp.toString());
            temp = new BigDecimal(Mass).setScale(1, RoundingMode.HALF_EVEN);
            L_016.setText(temp.toString());
            temp = new BigDecimal(RapidPartA).setScale(1, RoundingMode.HALF_EVEN);
            L_011.setText(temp.toString());
            temp = new BigDecimal(RapidPartB).setScale(1, RoundingMode.HALF_EVEN);
            L_012.setText(temp.toString());
            if(type_of_leaking==2){
                output_2.setVisibility(VISIBLE);
                double x = Double.parseDouble(Objects.requireNonNull(L_20.getEditText()).getText().toString());
                double y = Double.parseDouble(Objects.requireNonNull(L_21.getEditText()).getText().toString());
                x = x*10;
                temp = new BigDecimal(x).setScale(4, RoundingMode.HALF_EVEN);
                L_017.setText(temp.toString());
                y=y*10;
                temp = new BigDecimal(y).setScale(4, RoundingMode.HALF_EVEN);
                L_018.setText(temp.toString());
            }else{
                output_2.setVisibility(GONE);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(),"Please Fill All",Toast.LENGTH_LONG).show();
            LinearLayout output_0 = view.findViewById(R.id.output_0);
            LinearLayout output_1 = view.findViewById(R.id.output_1);
            LinearLayout output_2 = view.findViewById(R.id.output_2);
            output_0.setVisibility(GONE);
            output_1.setVisibility(GONE);
            output_2.setVisibility(GONE);
        }
    }

    public double getRepairLength(double L_defect, double D, double POWT){
        double L_over = 0d;
        double Temp = Math.sqrt(D*POWT);
        if(L_defect < (0.5*Temp)){
            L_over = 4*L_defect;
        }else {
            L_over = 2*Temp;
        }
        double L_taper = 2*(L_defect/5);
        return (2*L_over+L_defect+L_taper);
    }

    public double numberOfTurns(double L, double W, int wrap_method){
        if (wrap_method == 0){
            return (L/(W/2));
        }else if (wrap_method == 1){
            return (L/W);
        }
        return 0d;
    }

    public double getLengthWRMFiber(double N, double P, double W, int wrap_method){
        if (wrap_method == 0){
            return ((2*N*(Math.sqrt(Math.pow(P, 2) + Math.pow(0.5*W,2)))) + (4*P));
        }else if (wrap_method == 1){
            return (4*P*N);
        }
        return 0d;
    }

    public double getLengthCSMFiber(double N, double P, double W, int wrap_method){
        if (wrap_method == 0){
            return (N*(Math.sqrt(Math.pow(P, 2) + Math.pow(0.5*W,2))) + (2*P));
        }else if (wrap_method == 1){
            return (2*P*N);
        }
        return 0d;
    }

    public double getWeightWRMFiber(double L_WRM, double W_WRM, double G_WRM){
        return (((L_WRM*W_WRM)/1e6)*(G_WRM/1e3));
    }

    public double getWeightCSMFiber(double L_CSM, double W_CSM, double G_CSM){
        return (((L_CSM*W_CSM)/1e6)*(G_CSM/1e3));
    }

    public double getResignWeight(double Weight_WRM, double Weight_CSM){
        return ((2*Weight_CSM) + Weight_WRM);
    }

    public double getWeightHardner(double resin){
        return (0.1*resin);
    }

    private double CheckIfEntered(String s){
        if(s.isEmpty()){
            Toast.makeText(getContext(),"Enter",Toast.LENGTH_LONG).show();
            return 0d;
        }else {
            return Double.parseDouble(s);
        }
    }

}
