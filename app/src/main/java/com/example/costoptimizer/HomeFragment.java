package com.example.costoptimizer;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment  implements View.OnClickListener{
    private Button BnSave,BnView,BnDelete,BnUpdate,BnDiagram,BnGraph,BnAdvice;
    OnDbOpListener dbOpListener;



    public HomeFragment() {
        // Required empty public constructor
    }

    public interface OnDbOpListener
    {
        public void dbOpPerformed(int method);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        BnSave = view.findViewById(R.id.bn_add_purchase);
        BnSave.setOnClickListener(this);
        BnView = view.findViewById(R.id.bn_view_list);
        BnView.setOnClickListener(this);

        BnUpdate = view.findViewById(R.id.bn_update_purchase);
        BnUpdate.setOnClickListener(this);

        BnDelete = view.findViewById(R.id.bn_delete_purchase);
        BnDelete.setOnClickListener(this);


        BnDiagram = view.findViewById(R.id.bn_see_diagram);
        BnDiagram.setOnClickListener(this);

        BnGraph = view.findViewById(R.id.bn_see_graph);
        BnGraph.setOnClickListener(this);

        BnAdvice = view.findViewById(R.id.bn_advice);
        BnAdvice.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.bn_add_purchase:
                dbOpListener.dbOpPerformed(0);
                break;
            case R.id.bn_view_list:
                dbOpListener.dbOpPerformed(1);
                break;
            case R.id.bn_update_purchase:
                dbOpListener.dbOpPerformed(2);
                break;
            case R.id.bn_delete_purchase:
                dbOpListener.dbOpPerformed(3);
                break;
            case R.id.bn_see_diagram:
                dbOpListener.dbOpPerformed(4);
                break;
            case R.id.bn_see_graph:
                dbOpListener.dbOpPerformed(5);
                break;
            case R.id.bn_advice:
                dbOpListener.dbOpPerformed(6);
                break;

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;
         try {
             dbOpListener = (OnDbOpListener) activity;
         }
         catch (ClassCastException e)
         {
             throw new ClassCastException(activity.toString()+" must implement the interface method...");
         }

         }
}
