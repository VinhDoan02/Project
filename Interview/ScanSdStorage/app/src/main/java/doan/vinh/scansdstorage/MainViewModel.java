package doan.vinh.scansdstorage;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.ContentResolver;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import doan.vinh.scansdstorage.model.FileModel;
import doan.vinh.scansdstorage.model.OccurenceTypeModel;

/**
 * Created by Doand on 4/12/2018.
 */

public class MainViewModel extends ViewModel {

String TAG = "MainViewModel";
Thread backgroundThread;
ScanUtils scan;
MutableLiveData<String> top_10 = new MutableLiveData<>();
MutableLiveData<String> most_frequent_5 = new MutableLiveData<>();
MutableLiveData<String> average_file_size = new MutableLiveData<>();
MutableLiveData<Integer> scan_progress = new MutableLiveData<>();
MutableLiveData<Boolean> onCompleted = new MutableLiveData<>();


    public void startScan(final ContentResolver cr)
    {

        backgroundThread = new Thread(new Runnable() {
            public void run() {
                // a potentially  time consuming task

                scan = new ScanUtils();
                scan.setProgressListener(new ScanUtils.OnProgressListener() {
                    @Override
                    public void onProgress(int percentage) {

                        update(percentage);
                    }

                    @Override
                    public void onCompleted(ArrayList<FileModel> fileModels, HashMap<String, OccurenceTypeModel> occurenceTypeModelMap) {


                        computeBiggestTen(fileModels);
                        computeMostFrequent(occurenceTypeModelMap);
                        computeAverage(fileModels);

                        onCompleted.postValue(true);
                    }




                });
                scan.startScan(cr);


            }
        });

        backgroundThread.start();

    }

    public MutableLiveData<Boolean> getOnCompleted() {
        return onCompleted;
    }

    public void computeMostFrequent(HashMap<String, OccurenceTypeModel> occurenceTypeModelMap)
    {
        StringBuilder result = new StringBuilder("Top 5 most frequent file extensions\n");
        ArrayList<OccurenceTypeModel> testOccurence = new ArrayList<>(occurenceTypeModelMap.values());
        Collections.sort(testOccurence);

        int size = Math.min(5,testOccurence.size());
        for(int l = 0 ; l < size ; l++)
        {
            result.append("ext : ");
            result.append(testOccurence.get(l).getType());
            result.append(", count : ");
            result.append(testOccurence.get(l).getOccurence());
            result.append("\n");
        }

        most_frequent_5.postValue(result.toString());
    }

    public void computeBiggestTen(ArrayList<FileModel> fileModels)
    {
        StringBuilder result = new StringBuilder("Top 10 biggest files \n");
        Collections.sort(fileModels);
        int size = Math.min(10,fileModels.size());
        for(int j = 0; j < size;j++)
        {
            result.append(fileModels.get(j).getName());
            result .append(" size :");
            result.append(new DecimalFormat("#.##").format(fileModels.get(j).getSize()));
            result.append("Mb");
            result.append("\n");
        }

        top_10.postValue(result.toString());
    }

    public void computeAverage(ArrayList<FileModel> fileModels)
    {
        int size = fileModels.size();
        double average = 0;
        for (int i =0 ; i < size ; i ++)
        {
            double tempAverage = fileModels.get(i).getSize();

            average = average + (tempAverage/size);
        }
        StringBuilder result = new StringBuilder();
        result.append("Average file size is : ");
        result.append(new DecimalFormat("#.##").format(average));
        result.append("Mb");
        average_file_size.postValue(result.toString());
    }


    public void update(int percentage)
    {
        Log.d(TAG,"percent " + percentage);
        scan_progress.postValue(percentage);
    }

    public void stopScan()
    {
        Log.d(TAG,"stop");

        if(backgroundThread != null)
        {
            Log.d(TAG,"tried to interupt");
            backgroundThread.interrupt();
        }

        if(scan != null)
        {
            scan.setKeepScanning(false);
        }

    }

    public MutableLiveData<String> getTop_10() {
        return top_10;
    }

    public MutableLiveData<String> getMost_frequent_5() {
        return most_frequent_5;
    }

    public MutableLiveData<String> getAverage_file_size() {
        return average_file_size;
    }

    public MutableLiveData<Integer> getScan_progress() {
        return scan_progress;
    }
}
