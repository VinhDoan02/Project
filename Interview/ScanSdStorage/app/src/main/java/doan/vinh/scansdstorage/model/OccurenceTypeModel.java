package doan.vinh.scansdstorage.model;

import android.support.annotation.NonNull;

/**
 * Created by Doand on 4/12/2018.
 */

public class OccurenceTypeModel implements Comparable<OccurenceTypeModel>{

    int occurence = 0;
    String type;


    public OccurenceTypeModel(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        OccurenceTypeModel model = (OccurenceTypeModel)obj;

        return type.equalsIgnoreCase(model.getType());
    }


    public int getOccurence() {
        return occurence;
    }

    public String getType() {
        return type;
    }

    public void incrementOccurence()
    {
        occurence++;
    }
    @Override
    public int compareTo(@NonNull OccurenceTypeModel o) {
        return o.getOccurence()-occurence;
    }


    public String toString()
    {
        return type + " - " + occurence;
    }
}
