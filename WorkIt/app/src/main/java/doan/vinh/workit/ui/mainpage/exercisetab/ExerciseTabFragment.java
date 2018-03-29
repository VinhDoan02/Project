package doan.vinh.workit.ui.mainpage.exercisetab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import javax.inject.Inject;

import doan.vinh.workit.R;
import doan.vinh.workit.data.model.Exercise;
import doan.vinh.workit.ui.base.BaseFragment;
import doan.vinh.workit.ui.exercise.ExerciseActivity;


public class ExerciseTabFragment extends BaseFragment {
    View view;

    @Inject
    ExerciseTabViewModel mExerciseViewModel;

    //@BindView(R.id.lv_exercise) exerciseList;

    @Inject
    ExerciseTabAdapter eAdapter;

    public static ExerciseTabFragment newInstance()
    {

        Bundle args = new Bundle();
        ExerciseTabFragment fragment = new ExerciseTabFragment();
        fragment.setArguments(args);
        return fragment;
    }




    public ExerciseTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_exercise_tab, container, false);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeList();
    }

    public void initializeList()
    {

        ListView exerciseListView = (ListView) view.findViewById(R.id.lv_exercise);
        exerciseListView.setDivider(null);
        exerciseListView.setDividerHeight(5);
        exerciseListView.setAdapter(eAdapter);
        exerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Exercise eWrapper = (Exercise) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(getActivity(), ExerciseActivity.class);
                intent.putExtra("eType",eWrapper.getExerciseID());
                getActivity().startActivity(intent);
            }
        });

        mExerciseViewModel.loadExerciseList();
        mExerciseViewModel.response().observe(this,response -> processResponse(response));

    }



    public void processResponse(ArrayList<Exercise> response)
    {
        eAdapter.updateList(response);
    }

}
