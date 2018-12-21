package com.example.misu;

import android.app.Application;

import com.bean.Conserve;
import com.bean.MyOrder;

import java.util.ArrayList;

public class MyApplication extends Application {

	private static MyApplication context;
	private static ArrayList<MyOrder> orderList = new ArrayList<MyOrder>();
	private static ArrayList<Conserve> conserveList = new ArrayList<Conserve>();

	@Override
	public void onCreate() {
		super.onCreate();
		context=this;
	}

	public static MyApplication getContext(){
		return context;
	}

	public void addOrder(MyOrder order) {
		orderList.add(order);
	}

	public ArrayList<MyOrder> getOrder() {
		return orderList;
	}

	public void addConserve(Conserve conserve) {
		conserveList.add(conserve);
	}

	public ArrayList<Conserve> getConserve() {
		return conserveList;
	}


}



