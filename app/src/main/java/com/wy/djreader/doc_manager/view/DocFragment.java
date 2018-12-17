package com.wy.djreader.doc_manager.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wy.djreader.doc_manager.view.iview.IDocFragment;
import com.wy.djreader.model.FragmentCommListener;
import com.wy.djreader.model.MyViewVModel;
import com.wy.djreader.model.entity.HaveReadFilesSerializable;
import com.wy.djreader.R;
import com.wy.djreader.doc_manager.presenter.IDocPresenterImpl;
import com.wy.djreader.doc_manager.presenter.ipresenter.IDocPresenter;
import com.wy.djreader.showdoc.view.DisplayDocActivity;
import com.wy.djreader.utils.Constant;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DocFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DocFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocFragment extends ListFragment implements IDocFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Activity transfer parameters to Fragment
    private String mParam1;
    private String mParam2;

    private MyViewVModel myViewVModel;

    private View view;
    private Context context;
    private boolean mDualPune = false;//是否为双窗口
    private int mCurrSelectItem = 0;//当前选择项
    private IDocPresenter iDocPresenter = null;

    //定义接口
    private docFragmentInteractionListener mListener;
    private static FragmentCommListener fragmentCommListener;

    public DocFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DocFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DocFragment newInstance(String mParam1, String mParam2){
        DocFragment docFragment = new DocFragment();
        Bundle paramBundle = new Bundle();
        paramBundle.putString(ARG_PARAM1,mParam1);
        paramBundle.putString(ARG_PARAM2,mParam2);
        docFragment.setArguments(paramBundle);
        return docFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreatefragment","onCreate");
        if (getArguments() != null){
            //获取activity传递的参数
            //
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("onCreateViewFragment","onCreateView");
        context = getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doc, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        ViewModel
//        myViewVModel = ViewModelProviders.of(getActivity()).get(MyViewVModel.class);
//        myViewVModel.select(haveReadFiles);
//
//        //调用数据接收端的接口实现
//        fragmentCommListener.selectedFile(haveReadFiles);
        //为ListView配置适配器
        iDocPresenter = new IDocPresenterImpl(this);
        iDocPresenter.getFileListInfo(context);
        //判断当前是否支持双窗口
        View displayFrame = getActivity().findViewById(R.id.show_fragment_container);
        mDualPune = iDocPresenter.dualPuneJudge(context,displayFrame);
        if (mDualPune){
            if (savedInstanceState != null){
                //恢复数据
                mCurrSelectItem = savedInstanceState.getInt("currSelected");
            }
            //设置list中选中项突出显示
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            iDocPresenter.display(mCurrSelectItem);
        }
    }



    //创建注册回调接口的方法
    public static void fragmentCommListener(FragmentCommListener fragmentComm){
         fragmentCommListener = fragmentComm;
    }

    //保存数据，Fragment发生异常时调用
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //记录最后一次选中的选项
        outState.putInt("currSelected",mCurrSelectItem);
    }

    // item点击事件
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        iDocPresenter.display(position);
    }

    @Override
    public void setListAdapter(ArrayAdapter<HaveReadFilesSerializable> readFilesArrayAdapter) {
        this.setListAdapter(readFilesArrayAdapter);
    }

    @Override
    public void displayDoc(int index,String filePath) {
        DisplayDocFragment displayDocFragment = (DisplayDocFragment) getFragmentManager().findFragmentById(R.id.show_fragment_container);
        if (displayDocFragment == null || displayDocFragment.getShowDocIndex() != index){
            displayDocFragment = DisplayDocFragment.newInstance(filePath,index);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.show_fragment_container,displayDocFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }

    @Override
    public void toDisplayDocActivity(String filePath) {
        Intent showIntent = new Intent();
        showIntent.setClass(context,DisplayDocActivity.class);
        showIntent.putExtra(Constant.HAVEREADFILE_PATH_KEY,filePath);
        startActivity(showIntent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof docFragmentInteractionListener){
            mListener = (docFragmentInteractionListener) context;
        }else {
            throw new RuntimeException(context.toString()
                        + "must implement docFragmentInteractionListener!");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface docFragmentInteractionListener{
        //Activity需要实现的接口
        void docFragmentInteraction(Uri uri);
    }
}
