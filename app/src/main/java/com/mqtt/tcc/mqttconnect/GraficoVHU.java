package com.mqtt.tcc.mqttconnect;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.Date;

/**
 * Created by gabri on 15/05/2018.
 */

public class GraficoVHU implements OnChartValueSelectedListener{



    private LineChart grafVHU;


    public GraficoVHU(LineChart grafico){
       this.grafVHU= grafico;

    }
    public void iniciaGrafico(){

        grafVHU.setOnChartValueSelectedListener(this);
        grafVHU.setNoDataText("You need to provide data for the chart.");

        // enable touch gestures
        grafVHU.setTouchEnabled(true);

        // enable scaling and dragging
        grafVHU.setDragEnabled(true);
        grafVHU.setScaleEnabled(true);
        grafVHU.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        grafVHU.setPinchZoom(true);

        // set an alternative background color
        grafVHU.setBackgroundColor(Color.WHITE);
        grafVHU.setBorderColor(Color.rgb(67,164,34));


        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        grafVHU.setData(data);

        // get the legend (only possible after setting data)
        Legend l = grafVHU.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(Typeface.MONOSPACE);
        l.setTextColor(Color.rgb(67, 164, 34));

        XAxis xl = grafVHU.getXAxis();
        xl.setTypeface(Typeface.MONOSPACE);
        xl.setTextColor(Color.rgb(67, 164, 34));
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = grafVHU.getAxisLeft();
        leftAxis.setTypeface(Typeface.MONOSPACE);
        leftAxis.setTextColor(Color.rgb(67, 164, 34));

        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = grafVHU.getAxisRight();
        rightAxis.setEnabled(false);

    }
    public void addEntry(float value) {
        Date min= new Date();
        LineData data = grafVHU.getData();

        if (data != null){

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }
            //set.getEntryCount()
            data.addEntry(new Entry(set.getEntryCount(), value),0);
            Log.w("chart", set.getEntryForIndex(set.getEntryCount()-1).toString());

            data.notifyDataChanged();

            // let the chart know it's data has changed
            grafVHU.notifyDataSetChanged();

            // limit the number of visible entries
            grafVHU.setVisibleXRangeMaximum(10);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            grafVHU.moveViewTo(set.getEntryCount()-1, data.getYMax(), YAxis.AxisDependency.LEFT);

            // this automatically refreshes the chart (calls invalidate())
            // mChart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }
    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.rgb(67, 164, 34));
        //set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        //set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(Color.rgb(67, 164, 34));
        set.setHighLightColor(Color.rgb(67, 164, 34));
        set.setValueTextColor(Color.rgb(67, 164, 34));
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
