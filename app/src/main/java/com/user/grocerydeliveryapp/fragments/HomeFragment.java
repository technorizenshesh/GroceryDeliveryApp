package com.user.grocerydeliveryapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.user.grocerydeliveryapp.R;
import com.user.grocerydeliveryapp.activities.HomeAct;
import com.user.grocerydeliveryapp.activities.NotificationAct;
import com.user.grocerydeliveryapp.adapters.HomeAdapter;
import com.user.grocerydeliveryapp.databinding.FragmentHomeBinding;
import com.user.grocerydeliveryapp.model.SuccessResGetOrders;
import com.user.grocerydeliveryapp.retrofit.ApiClient;
import com.user.grocerydeliveryapp.retrofit.Constant;
import com.user.grocerydeliveryapp.retrofit.GroceryDeliveryInterface;
import com.user.grocerydeliveryapp.util.DataManager;
import com.user.grocerydeliveryapp.util.SharedPreferenceUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;

    private GroceryDeliveryInterface apiInterface;

    private ArrayList<SuccessResGetOrders.Result>  requestList= new ArrayList<>();

    private HomeAdapter homeAdapter;

    int selectedPosition = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);

        apiInterface = ApiClient.getClient().create(GroceryDeliveryInterface.class);

        homeAdapter = new HomeAdapter(getActivity(),requestList);

        binding.rvHome.setHasFixedSize(true);
        binding.rvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvHome.setAdapter(homeAdapter);

        getAllRequests();

        binding.tabLay.addTab(binding.tabLay.newTab().setText(R.string.all));
        binding.tabLay.addTab(binding.tabLay.newTab().setText(R.string.new1));
        binding.tabLay.addTab(binding.tabLay.newTab().setText(R.string.in_progress));
        binding.tabLay.addTab(binding.tabLay.newTab().setText(R.string.completed));
        binding.tabLay.setTabGravity(TabLayout.GRAVITY_FILL);
        binding.tabLay.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int currentTabSelected= tab.getPosition();
                if(currentTabSelected==0)
                {
                    getAllRequests();
                    selectedPosition = 0;
                }else if(currentTabSelected==1)
                {
                    getRequests();
                    selectedPosition = 1;
                }else if(currentTabSelected==2)
                {
                    getProgressRequests();
                    selectedPosition = 2;
                }else if(currentTabSelected==3)
                {
                    selectedPosition = 3;
                    getCompletedRequests();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        if(selectedPosition==0)
        {
            getAllRequests();
        }else if(selectedPosition==1)
        {
            getRequests();
        }else if(selectedPosition==2)
        {
            getProgressRequests();
        }else if(selectedPosition==3)
        {
            getCompletedRequests();
        }
        super.onResume();
    }

    public void getRequests()
    {
        String userId = SharedPreferenceUtility.getInstance(getActivity()).getString(Constant.USER_ID);
        DataManager.getInstance().showProgressMessage(getActivity(), getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("driver_id",userId);
        Call<SuccessResGetOrders> call = apiInterface.getRequest(map);
        call.enqueue(new Callback<SuccessResGetOrders>() {
            @Override
            public void onResponse(Call<SuccessResGetOrders> call, Response<SuccessResGetOrders> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResGetOrders data = response.body();
                    Log.e("data",data.success+"");
                    if (data.success==1) {
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                        requestList.clear();
                        requestList.addAll(data.getResult());
                        homeAdapter.notifyDataSetChanged();
                    } else if (data.success==0) {
                        Constant.showToast(getActivity(), data.message);
                        requestList.clear();
                        homeAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<SuccessResGetOrders> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }

    public void getProgressRequests()
    {
        String userId = SharedPreferenceUtility.getInstance(getActivity()).getString(Constant.USER_ID);
        DataManager.getInstance().showProgressMessage(getActivity(), getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("driver_id",userId);
        Call<SuccessResGetOrders> call = apiInterface.getProgressRequest(map);
        call.enqueue(new Callback<SuccessResGetOrders>() {
            @Override
            public void onResponse(Call<SuccessResGetOrders> call, Response<SuccessResGetOrders> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResGetOrders data = response.body();
                    Log.e("data",data.success+"");
                    if (data.success==1) {
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                        requestList.clear();
                        requestList.addAll(data.getResult());
                        homeAdapter.notifyDataSetChanged();
                    } else if (data.success==0) {
                        Constant.showToast(getActivity(), data.message);
                        requestList.clear();
                        homeAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessResGetOrders> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }

    public void getCompletedRequests()
    {
        String userId = SharedPreferenceUtility.getInstance(getActivity()).getString(Constant.USER_ID);
        DataManager.getInstance().showProgressMessage(getActivity(), getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("driver_id",userId);
        Call<SuccessResGetOrders> call = apiInterface.getCompletedRequest(map);
        call.enqueue(new Callback<SuccessResGetOrders>() {
            @Override
            public void onResponse(Call<SuccessResGetOrders> call, Response<SuccessResGetOrders> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResGetOrders data = response.body();
                    Log.e("data",data.success+"");
                    if (data.success==1) {
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                        requestList.clear();
                        requestList.addAll(data.getResult());
                        homeAdapter.notifyDataSetChanged();
                    } else if (data.success==0) {
                        Constant.showToast(getActivity(), data.message);
                        requestList.clear();
                        homeAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessResGetOrders> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }

    public void getAllRequests()
    {
        String userId = SharedPreferenceUtility.getInstance(getActivity()).getString(Constant.USER_ID);
        DataManager.getInstance().showProgressMessage(getActivity(), getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("driver_id",userId);
        Call<SuccessResGetOrders> call = apiInterface.getAllRequest(map);
        call.enqueue(new Callback<SuccessResGetOrders>() {
            @Override
            public void onResponse(Call<SuccessResGetOrders> call, Response<SuccessResGetOrders> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResGetOrders data = response.body();
                    Log.e("data",data.success+"");
                    if (data.success==1) {
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                        requestList.clear();
                        requestList.addAll(data.getResult());
                        homeAdapter.notifyDataSetChanged();
                    } else if (data.success==0) {
                        Constant.showToast(getActivity(), data.message);
                        requestList.clear();
                        homeAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<SuccessResGetOrders> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }
}