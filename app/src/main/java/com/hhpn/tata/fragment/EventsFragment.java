package com.hhpn.tata.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import com.hhpn.tata.EventDetail;
import com.hhpn.tata.R;
import com.hhpn.tata.adapter.EventsAdapter;

/**
 * Created by hiantohendry on 8/7/15.
 */
public class EventsFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

    private static final String TAG = "EventsFragment";

        public static EventsFragment newInstance(int sectionNumber) {
            EventsFragment fragment = new EventsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public EventsFragment() {
        }

    private RecyclerView mRecyclerView;
    private EventsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        initDataset();
    }
    String myDataset[][];

    private void initDataset() {
         myDataset = new String[][]{{"Suropati Music Park","https://mikebm.files.wordpress.com/2014/09/klasikanan2a.jpg"}
                 ,{"Senam Pagi Taman Ismail Marzuki","http://www.kejari-jaksel.go.id/files/gallery/1350616419.jpg"}
         ,{"Kerja Bakti Taman Slipi" ,"http://desatrihanggo.com/admin/gambar/foto_gallery/kerjabakti.jpg"}};

    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            rootView.setTag(TAG);
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        final  FragmentActivity a = getActivity();
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(a);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new EventsAdapter(myDataset,getActivity());
        mAdapter.setListener(new EventsAdapter.OnMyListItemClick() {
            @Override
            public void onMyListItemClick(View itemView,int position) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getActivity().getWindow().setExitTransition(defineExitTransition(position));
                }
                Intent launchDetailActivityIntent = new Intent(getActivity(), EventDetail.class);

                startActivity(launchDetailActivityIntent);
                /*
                mRecyclerView.getChildAt(position);
                EventsAdapter.ViewHolder viewHolder = (EventsAdapter.ViewHolder) mRecyclerView.getTag();
                View  textView= viewHolder.textView;
                View iconView= viewHolder.imageView;
                Resources res = getResources();
                ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        Pair.create(iconView, res.getString(R.string.transition_image)),
                        Pair.create(textView, res.getString(R.string.transition_text))
                );
                ActivityCompat.startActivity(getActivity(), launchDetailActivityIntent, activityOptions.toBundle());
                */
            }
        });
        mRecyclerView.setAdapter(mAdapter);


            return rootView;
        }

    public View getActionBarView() {
        Window window = getActivity().getWindow();
        View v = window.getDecorView();
        int resId = getResources().getIdentifier("action_bar_container", "id", "android");
        return v.findViewById(resId);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    Transition defineExitTransition(int visiblePosition) {

        Transition upperTrans = new Slide(Gravity.TOP)
                .addTarget(getActionBarView())
                .setDuration(800);
        for (int i=0; i<visiblePosition; i++) {
            upperTrans.addTarget(mRecyclerView.getChildAt(i));
        }
        Transition lowerTrans = new Slide(Gravity.BOTTOM)
                .setDuration(800);
        for (int i=visiblePosition+1; i<mRecyclerView.getChildCount(); i++) {
            lowerTrans.addTarget(mRecyclerView.getChildAt(i));
        }
        Transition middleTrans = new Fade().setDuration(100)
                .addTarget(mRecyclerView.getChildAt(visiblePosition));
        TransitionSet ts = new TransitionSet();
        ts.addTransition(upperTrans)
                .addTransition(lowerTrans)
                .addTransition(middleTrans)
                .setOrdering(TransitionSet.ORDERING_TOGETHER)
                .excludeTarget(android.R.id.navigationBarBackground, true)
                .excludeTarget(android.R.id.statusBarBackground, true);
        return ts;
    }
    }

