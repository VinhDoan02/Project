package doan.vinh.workit.ui.mainpage.profiletab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.android.support.AndroidSupportInjection;
import doan.vinh.workit.R;
import doan.vinh.workit.ui.base.BaseFragment;


public class ProfileTabFragment extends BaseFragment {

   // private OnFragmentInteractionListener mListener;


/*    @Inject
    ProfileTabViewModel mProfileViewModel;*/

    public static ProfileTabFragment newInstance()
    {
        Bundle args = new Bundle();
        ProfileTabFragment fragment = new ProfileTabFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public ProfileTabFragment() {
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
        return inflater.inflate(R.layout.fragment_profile_tab, container, false);
    }



}
