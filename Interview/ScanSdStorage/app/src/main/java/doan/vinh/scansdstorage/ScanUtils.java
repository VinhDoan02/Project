package doan.vinh.scansdstorage;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.util.ArrayList;
import java.util.HashMap;

import doan.vinh.scansdstorage.model.FileModel;
import doan.vinh.scansdstorage.model.OccurenceTypeModel;

/**
 * Created by Doand on 4/12/2018.
 */

public class ScanUtils {
   // Context context;
    String TAG = "scan utility";

    

   HashMap<String,OccurenceTypeModel> occurenceTypeModelMap;
    boolean keepScanning = true;

    final double megaBytesSize = 1048576;
    double average = 0;

    public void setKeepScanning(boolean keepScanning) {
        this.keepScanning = keepScanning;
    }

    public void startScan(ContentResolver cr)
    {
        keepScanning = true;
        occurenceTypeModelMap = new HashMap<String,OccurenceTypeModel>();
        ArrayList<FileModel> fileModelArrayList = new ArrayList<>();
        // ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Files.getContentUri("external");

        // every column, although that is huge waste, you probably need
        // BaseColumns.DATA (the path) only.
        String[] projection = null;

        // exclude media files, they would be here also.
        String selection = null;
        String[] selectionArgs = null; // there is no ? in selection so null here

        String sortOrder = null; // unordered
        Cursor allFilesCursor = cr.query(uri, projection, selection, selectionArgs, sortOrder);

        double cursorsize = allFilesCursor.getCount();
        int previousPercent = -1;

        Log.d(TAG,"count " + cursorsize);
        double counter = 0;
        if (allFilesCursor != null) {
            if (allFilesCursor.moveToFirst()) {
                do {
                    int MediaType = allFilesCursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
                    int fileSize = allFilesCursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE);
                    int titleInt = allFilesCursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE);
                    counter++;

                    String title = allFilesCursor.getString(titleInt);
                    String filePath = allFilesCursor.getString(MediaType);
                    if(filePath != null && title != null)
                    {
                        int fileNameIndex = filePath.lastIndexOf('/');
                        String name = filePath.substring(fileNameIndex+1);
                        int fileExtIndex = name.lastIndexOf('.');

                        if(fileNameIndex != -1 && fileExtIndex != -1)
                        {
                            String extension = name.substring(fileExtIndex+1);
                            int size = allFilesCursor.getInt(fileSize);

                            double sizeInMega = size/megaBytesSize;

                            FileModel tempFile = new FileModel(name,extension,sizeInMega);
                            fileModelArrayList.add(tempFile);

                            if(occurenceTypeModelMap.containsKey(extension))
                            {
                                occurenceTypeModelMap.get(extension).incrementOccurence();
                            }else
                            {
                                OccurenceTypeModel tempModel = new OccurenceTypeModel(extension);
                                tempModel.incrementOccurence();
                                occurenceTypeModelMap.put(extension,tempModel);
                            }

                        }



                    }

                    int percentage = (int)((counter / cursorsize)*100);
                    if(percentage > previousPercent)
                    {
                        previousPercent = percentage;
                        progressListener.onProgress(percentage);
                    }

                } while (allFilesCursor.moveToNext() && keepScanning);
            }
        }
        assert allFilesCursor != null;
        allFilesCursor.close();

        if(keepScanning)
        {
            progressListener.onCompleted(fileModelArrayList,occurenceTypeModelMap);
            Log.d(TAG,cursorsize+ "count " + counter);
            Log.d(TAG,"list " + fileModelArrayList.size());
        }

        keepScanning = false;

    }

    public interface OnProgressListener
    {
        void onProgress(int percentage);

        void onCompleted(ArrayList<FileModel> fileModels,HashMap<String,OccurenceTypeModel> occurenceTypeModelMap);
    }

    private OnProgressListener progressListener;

    public void setProgressListener(OnProgressListener progressListener)
    {
        this.progressListener = progressListener;
    }

}
