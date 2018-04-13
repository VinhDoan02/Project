package doan.vinh.scansdstorage.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.ContentResolver;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import doan.vinh.scansdstorage.Utils.ScanUtils;
import doan.vinh.scansdstorage.model.FileModel;
import doan.vinh.scansdstorage.model.OccurenceTypeModel;

/**
 * Created by Doand on 4/12/2018.
 */

public class MainViewModel extends ViewModel {

private final String TAG = "MainViewModel";
private Thread backgroundThread;
private ScanUtils scan;
private MutableLiveData<String> top_10 = new MutableLiveData<>();
private MutableLiveData<String> most_frequent_5 = new MutableLiveData<>();
private MutableLiveData<String> average_file_size = new MutableLiveData<>();
private MutableLiveData<Integer> scan_progress = new MutableLiveData<>();
private MutableLiveData<Boolean> onCompleted = new MutableLiveData<>();

public MainViewModel(ScanUtils scan)
{
    this.scan = scan;
}

    public void startScan(final ContentResolver cr)
    {

        backgroundThread = new Thread(new Runnable() {
            public void run() {
                // a potentially  time consuming task
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                scan.setProgressListener(new ScanUtils.OnProgressListener() {
                    @Override
                    public void onProgress(int percentage) {

                        update(percentage);
                      //  Log.d(TAG,"percentage " + percentage);
                    }

                    @Override
                    public void onCompleted(ArrayList<FileModel> fileModels, HashMap<String, OccurenceTypeModel> occurenceTypeModelMap) {

                      //  Log.d(TAG,"on complete");
                        if(fileModels.isEmpty() || fileModels == null)
                        {
                            top_10.setValue("No files on external Storage");
                        }else
                        {
                            computeBiggestTen(fileModels);
                            computeMostFrequent(occurenceTypeModelMap);
                            computeAverage(fileModels);
                        }


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
        result.append("    ------  \n");
        ArrayList<OccurenceTypeModel> testOccurence = new ArrayList<>(occurenceTypeModelMap.values());
        Collections.sort(testOccurence);

        int size = Math.min(5,testOccurence.size());
        for(int l = 0 ; l < size ; l++)
        {
            result.append("Extensions : ");
            result.append(testOccurence.get(l).getType());
            result.append("\nFrequencies : ");
            result.append(testOccurence.get(l).getOccurence());
            result.append("\n");
            result.append("  ----  \n");
        }

        most_frequent_5.postValue(result.toString());
    }

    public void computeBiggestTen(ArrayList<FileModel> fileModels)
    {
        StringBuilder result = new StringBuilder("Top 10 biggest files \n");
        result.append("    ------  \n");
        Collections.sort(fileModels);
        int size = Math.min(10,fileModels.size());
        for(int j = 0; j < size;j++)
        {
            result.append("Filename : ");
            result.append(fileModels.get(j).getName());
            result .append("\nSize : ");
            result.append(new DecimalFormat("#.##").format(fileModels.get(j).getSize()));
            result.append("MB");
            result.append("\n");
            result.append("  ----  \n");
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
            Log.d(TAG,"size" + tempAverage);
            average = average + (tempAverage/size);

        }
        StringBuilder result = new StringBuilder();
        result.append("Average file size is : ");
        result.append(new DecimalFormat("#.##").format(average));
        result.append("MB");
        average_file_size.postValue(result.toString());
    }


    public void update(int percentage)
    {
        scan_progress.postValue(percentage);
    }

    public void stopScan()
    {

        if(scan != null)
        {
            scan.setKeepScanning(false);
        }

        if(backgroundThread != null)
        {
            backgroundThread.interrupt();
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

    @Override
    protected void onCleared() {

        stopScan();
        super.onCleared();
    }

}
