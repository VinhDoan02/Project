package doan.vinh.workit.ui.mainpage.exercisetab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import doan.vinh.workit.R;
import doan.vinh.workit.data.model.Exercise;

/**
 * Created by Doand on 3/23/2018.
 */

public class ExerciseTabAdapter extends BaseAdapter {
    ArrayList<Exercise> exerciseList;
    Context context;

    public ExerciseTabAdapter(Context context, ArrayList<Exercise> exerciseList)
    {
        this.exerciseList = exerciseList;
        this.context = context;
    }

    public void updateList(ArrayList<Exercise> exerciseList)
    {
        this.exerciseList = exerciseList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return exerciseList.size();
    }

    @Override
    public Object getItem(int i) {
        return exerciseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

       Exercise exercise = exerciseList.get(i);
       if(view == null){
           view = LayoutInflater.from(context).inflate(R.layout.item_exercise_view,viewGroup,false);
       }

       TextView title = (TextView)view.findViewById(R.id.txt_title);
       title.setText(exercise.getName());

       return view;
    }
}
