package doan.vinh.scansdstorage.model;

import android.support.annotation.NonNull;

/**
 * Created by Doand on 4/12/2018.
 */

public class FileModel implements Comparable<FileModel> {
    String name;
    String type;
    double size;


    public FileModel(String name, String type, double size)
    {
        this.name = name;
        this.type = type;
        this.size = size;
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getSize() {
        return size;
    }

    @Override
    public int compareTo(@NonNull FileModel o) {


        double result = o.getSize()-size;

        if(result == 0 )
        {
            return 0;
        }else if(result > 0)
        {
            return 1;
        }else
        {
            return -1;
        }
    }
}
