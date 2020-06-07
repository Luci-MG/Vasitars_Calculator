package com.example.vasitarscalculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class nonLeakingCalculate extends Fragment {
    private int menu_Type_Of_Loss = 10;
    private int select_wrapping = 10;
    leakingCalculate leaking = new leakingCalculate();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_non_leaking_calculate, container, false);
        String[] Menu_Type_of_Loss = new String[] {"Wall loss (in degrees)", "Localized Wall loss"};
        String[] Menu_wrapper_types = new String[] {"Helical", "Straight"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        Objects.requireNonNull(getContext()),
                        R.layout.support_simple_spinner_dropdown_item,
                        Menu_Type_of_Loss);

        AutoCompleteTextView menuTypeOfLoss = view.findViewById(R.id.select_wall_Loss_NL_14);
        menuTypeOfLoss.setAdapter(adapter);

        ArrayAdapter<String> adapterr =
                new ArrayAdapter<>(
                        getContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        Menu_wrapper_types);

        AutoCompleteTextView menuWrapper = view.findViewById(R.id.select_wrapping_NL_11);
        menuWrapper.setAdapter(adapterr);
        final LinearLayout linearLayout = view.findViewById(R.id.first_NL);
        final LinearLayout linearLayout2 = view.findViewById(R.id.third_NL);

        menuTypeOfLoss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View root, int position, long id) {
                if(position == 0){
                    linearLayout.setVisibility(VISIBLE);
                    linearLayout2.setVisibility(GONE);
                    menu_Type_Of_Loss = 0;
                }else if(position == 1){
                    linearLayout.setVisibility(GONE);
                    linearLayout2.setVisibility(VISIBLE);
                    menu_Type_Of_Loss = 1;
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
        Button button = view.findViewById(R.id.calculateButton_NL);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menu_Type_Of_Loss == 10){
                    Toast.makeText(getContext(),"Please Select Types Of Loss",Toast.LENGTH_LONG).show();
                    return;
                }else if(select_wrapping == 10){
                    Toast.makeText(getContext(),"Please Select Wrapping Method",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    calculate_NL(view);
                }
            }
        });
        /////////////
        return view;
    }

    private void calculate_NL(View view){



        double L = 0d;
        double Mass = 0d;


        TextInputLayout L_1 = view.findViewById(R.id.NL_1);//Pipe Diameter (in mm)
        TextInputLayout L_2 = view.findViewById(R.id.NL_2);//Pipe Original Wall Thickness (in mm)
        TextInputLayout L_3 = view.findViewById(R.id.NL_3);//Pipe Material Strength (in psi)
        TextInputLayout L_4 = view.findViewById(R.id.NL_4);//Pipe Working Pressure (in bars)
        TextInputLayout L_5 = view.findViewById(R.id.NL_5);//Total Longitudinal extent of damage (in mm)
        TextInputLayout L_7 = view.findViewById(R.id.NL_7);//Width of CSM Fiber (in mm)
        TextInputLayout L_8 = view.findViewById(R.id.NL_8);//Width of WRM Fiber (in mm)
        TextInputLayout L_9 = view.findViewById(R.id.NL_9);//GSM of CSM Fiber (in g/m2)
        TextInputLayout L_10 = view.findViewById(R.id.NL_10);//GSM of WRM Fiber (in g/m2)
        AutoCompleteTextView menuWrapper = view.findViewById(R.id.select_wrapping_NL_11);//select_wrapping_L_11
        TextInputLayout L_12 = view.findViewById(R.id.NL_12);//Minimum Wall Thickness (in mm)
        AutoCompleteTextView menuTypeOfLoss = view.findViewById(R.id.select_wall_Loss_NL_14);//Select Type of Wall loss
        TextInputLayout L_20 = view.findViewById(R.id.NL_20);//Average Interface Adhesive Thickness (in mm)
        TextInputLayout L_21 = view.findViewById(R.id.NL_21);//Circumferential defect width (in mm) _3
        TextInputLayout L_50 = view.findViewById(R.id.NL_50);//Average Wall Loss Extent (in degrees) _1

        try {
            double Interface_Adhesive_thickness;
            double D = Double.parseDouble(Objects.requireNonNull(L_1.getEditText()).getText().toString());
            double POWT = Double.parseDouble(Objects.requireNonNull(L_2.getEditText()).getText().toString());
            //double PMS = Double.parseDouble((L_3.getEditText()).getText().toString());
            //double PWP = Double.parseDouble(Objects.requireNonNull(L_4.getEditText()).getText().toString());
            double L_defect = Double.parseDouble(Objects.requireNonNull(L_5.getEditText()).getText().toString());
            double W_CSM = Double.parseDouble(Objects.requireNonNull(L_7.getEditText()).getText().toString());
            double W_WRM = Double.parseDouble(Objects.requireNonNull(L_8.getEditText()).getText().toString());
            double G_CSM = Double.parseDouble(Objects.requireNonNull(L_9.getEditText()).getText().toString());
            double G_WRM = Double.parseDouble(Objects.requireNonNull(L_10.getEditText()).getText().toString());
            double MWT = Double.parseDouble(Objects.requireNonNull(L_12.getEditText()).getText().toString());
            try {
                Interface_Adhesive_thickness = Double.parseDouble(Objects.requireNonNull(L_20.getEditText()).getText().toString());
            } catch (NumberFormatException e) {
                Interface_Adhesive_thickness = Double.MAX_VALUE;
            }
            L = leaking.getRepairLength(L_defect, D, POWT);
            double N_WRM = leaking.numberOfTurns(L, W_WRM, select_wrapping);
            double N_CSM = leaking.numberOfTurns(L, W_CSM, select_wrapping);

            double P = Math.PI * D;
            double L_WRM = leaking.getLengthWRMFiber(N_WRM, P, W_WRM, select_wrapping);
            double L_CSM = leaking.getLengthCSMFiber(N_CSM, P, W_CSM, select_wrapping);

            double Weight_WRM_Fiber = leaking.getWeightWRMFiber(L_WRM, W_WRM, G_WRM);
            double Weight_CSM_Fiber = leaking.getWeightCSMFiber(L_CSM, W_CSM, G_CSM);

            double Weight_Of_Resin = leaking.getResignWeight(Weight_WRM_Fiber, Weight_CSM_Fiber);
            double Weight_Hardener = leaking.getWeightHardner(Weight_Of_Resin);

            L_CSM = L_CSM / 1e3;
            L_WRM = L_WRM / 1e3;

            if (menu_Type_Of_Loss == 0) {
                double Volume;
                double theta = Double.parseDouble(Objects.requireNonNull(L_50.getEditText()).getText().toString());
                double D_dash = D - (POWT - MWT);
                double arc_length = (((Math.PI) * D) / 360) * theta;
                if (Interface_Adhesive_thickness == Double.MAX_VALUE) {
                    Volume = ((Math.PI)) * (theta / 360) * (Math.pow(D, 2) - Math.pow(D_dash, 2)) * L_defect;
                } else {
                    Volume = arc_length * Interface_Adhesive_thickness * L_defect;
                }
                Mass = (Volume * 1.4) * (1e-6);
            } else if (menu_Type_Of_Loss == 1) {
                double Volume;
                double w = Double.parseDouble(Objects.requireNonNull(L_21.getEditText()).getText().toString());
                if (Interface_Adhesive_thickness == Double.MAX_VALUE) {
                    Volume = (POWT - MWT) * w * L_defect;
                } else {
                    Volume = w * Interface_Adhesive_thickness * L_defect;
                }
                Mass = (Volume * 1.4) * (1e-6);
            }
            double interfacePartA = Mass / 1.1;
            double interfacePartB = Mass - interfacePartA;
            //Toast.makeText(getContext(), Double.toString(interfacePartB),Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(), "SWIPE UP FOR RESULTS", Toast.LENGTH_LONG).show();
            TextView L_01 = view.findViewById(R.id.NL_01);
            TextView L_02 = view.findViewById(R.id.NL_02);
            TextView L_03 = view.findViewById(R.id.NL_03);
            TextView L_04 = view.findViewById(R.id.NL_04);
            TextView L_05 = view.findViewById(R.id.NL_05);
            TextView L_06 = view.findViewById(R.id.NL_06);
            TextView L_07 = view.findViewById(R.id.NL_07);
            TextView L_08 = view.findViewById(R.id.NL_08);
            TextView L_09 = view.findViewById(R.id.NL_09);
            TextView L_015 = view.findViewById(R.id.NL_015);

            TextView L_019 = view.findViewById(R.id.NL_019);
            LinearLayout output_0 = view.findViewById(R.id.output_0_NL);
            LinearLayout output_1 = view.findViewById(R.id.output_1_NL);
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
            temp = new BigDecimal(Mass).setScale(3, RoundingMode.HALF_EVEN);
            L_05.setText(temp.toString());
            L_06.setText("2");
            L_07.setText("4");
            temp = new BigDecimal(interfacePartA).setScale(3, RoundingMode.HALF_EVEN);
            L_08.setText(temp.toString());
            temp = new BigDecimal(interfacePartB).setScale(3, RoundingMode.HALF_EVEN);
            L_09.setText(temp.toString());
            temp = new BigDecimal(L).setScale(1, RoundingMode.HALF_EVEN);
            L_015.setText(temp.toString());

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(),"Please Fill All",Toast.LENGTH_LONG).show();
            LinearLayout output_0 = view.findViewById(R.id.output_0_NL);
            LinearLayout output_1 = view.findViewById(R.id.output_1_NL);
            output_0.setVisibility(GONE);
            output_1.setVisibility(GONE);
        }
    }
}
