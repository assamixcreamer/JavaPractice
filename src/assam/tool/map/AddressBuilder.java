package assam.tool.map;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Random;

/**
 * @Description 	地址產生器
 * @author 			charlie
 * @CreateDate 		2016年1月11日 下午4:39:50
 * @LastModifier 	charlie
 * @LastModifyDate 	2016年1月11日 下午4:39:50
 * @Copyright 		Smart Catch International Co., Ltd
 */
public class AddressBuilder {
	
	private static Random random = new Random();
	
	/**
	 * 一公尺 = 0.00000900900901度
	 */
	private static final double oneMeterToCoordinates = 0.00000900900901;
	
	/**
	 * 區域是否隨機
	 */
	private boolean isAreaRandom = false;
	
	/**
	 * 產生範圍(設定初始範圍(公尺))
	 */
	private int initRange = 5000;//設定初始範圍(公尺)
	
	/**
	 * 產出數量(預設產出幾組地址)
	 */
	private int initAddressNum = 3;//預設產出幾組地址
	
	/**
	 * @Description 
	 * @author charlie
	 * @param designationAddress 指定地點
	 * @param area 指定地區
	 * @param range 範圍(半徑;公尺)
	 * @param addressNum 產生幾組
	 * @CreateDate 2015年8月25日 上午10:32:05
	 */
	public void getAddressList(String designationAddress, Integer area, Integer range, Integer addressNum){
		
		try {
//			boolean isAreaRandom = false;
//			int initRange = 5000;//設定初始範圍(公尺)
//			int initAddressNum = 5;//預設產出幾組地址
			double[] chooseArea = new double[2]; //選擇的地區or地址經緯度
			
			if(designationAddress == null){
				isAreaRandom = true; //開啟地區亂數開關
			}
			else{
				//查出地址經緯度，如果無法查出則跳出
				double[] designationLngAndLat = GoogleMapApiTool.getLatAndLngByAddress(designationAddress);
				if(designationLngAndLat == null || designationLngAndLat.length == 0){
					System.out.println("address error.");
					return;
				}
				chooseArea[0] = designationLngAndLat[0];
				chooseArea[1] = designationLngAndLat[1];
			}
			
			if(range != null){
				if(range <= 0){//範圍不可小於0
					return;
				}
				initRange = range; 
			}
			
			if(addressNum != null){
				if(addressNum <= 0){//組數不可小於0
					return;
				}
				initAddressNum = addressNum; //產生組數設定
			}
			
			//開始產生地址
			for(int i = 0; i < initAddressNum; i++){
				
				if(isAreaRandom){
					chooseArea = getRandomArea(area);
				}
				
				double lat = chooseArea[0];//緯度
				double lng = chooseArea[1];//經度
				double[] randomPoint = getRandomIncreaseDistance(initRange, lng, lat);
				
				String address = GoogleMapApiTool.getAddressByLatAndLng(String.valueOf(randomPoint[0]), String.valueOf(randomPoint[1]));
				System.out.println(address);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public class Area implements Serializable{
		
		public final double[] 台北市 = {25.032964,121.565427}; // 0
		public final double[] 基隆市 = {25.127603,121.739183}; // 1
		public final double[] 新北市 = {25.016983,121.462787}; // 2
		public final double[] 桃園市 = {24.993628,121.300980}; // 3
		public final double[] 台中市 = {24.147736,120.673648}; // 4
		public final double[] 高雄市 = {22.627278,120.301435}; // 5
		public final double[] 新竹市 = {24.806649,120.966926}; // 6
		public final double[] 嘉義市 = {23.480075,120.449111}; // 7
		public final double[] 新竹縣 = {24.838723,121.017725}; // 8
		public final double[] 苗栗縣 = {24.560159,120.821427}; // 9
		public final double[] 彰化縣 = {24.051796,120.516135}; // 10
		public final double[] 南投縣 = {23.960998,120.971864}; // 11
		public final double[] 雲林縣 = {23.709203,120.431337}; // 12
		public final double[] 嘉義縣 = {23.451843,120.255462}; // 13
		public final double[] 屏東縣 = {22.551976,120.548760}; // 14
		public final double[] 宜蘭縣 = {24.702107,121.737750}; // 15
		public final double[] 花蓮縣 = {23.949989,121.551891}; // 16
		public final double[] 台東縣 = {22.797245,121.071370}; // 17
		public final double[] 澎湖縣 = {23.571190,119.579316}; // 18
		public final double[] 金門縣 = {24.449373,118.376635}; // 19
	}
	
	public class AREA_TYPE {
		public static final int 台北市 = 0;
		public static final int 基隆市 = 1;
		public static final int 新北市 = 2;
		public static final int 桃園市 = 3;
		public static final int 台中市 = 4; 
		public static final int 高雄市 = 5;
		public static final int 新竹市 = 6;
		public static final int 嘉義市 = 7;
		public static final int 新竹縣 = 8;
		public static final int 苗栗縣 = 9;
		public static final int 彰化縣 = 10;
		public static final int 南投縣 = 11;
		public static final int 雲林縣 = 12;
		public static final int 嘉義縣 = 13;
		public static final int 屏東縣 = 14;
		public static final int 宜蘭縣 = 15;
		public static final int 花蓮縣 = 16;
		public static final int 台東縣 = 17;
		public static final int 澎湖縣 = 18;
		public static final int 金門縣 = 19;
	}
	
	private double[] getRandomIncreaseDistance(int initRange, double lng, double lat) throws Exception{//lng 經度 東西  //lat 緯度 南北
		
		double end = random.nextDouble() * (initRange - (- initRange)) + (- initRange); //三角形底
		double high = Math.sqrt(initRange * initRange - end * end); //三角形高
		double horizontal = end; //經度增加距離
		double vertical = random.nextDouble() * (high - (- high)) + (- high); //緯度增加距離
		
		vertical = vertical + lat * oneMeterToCoordinates;
		horizontal = horizontal + lng * oneMeterToCoordinates;
		
		lng = lng + horizontal * oneMeterToCoordinates;
		lat = lat + vertical * oneMeterToCoordinates;
		
		double[] randomPoint = {lat, lng};//緯度,經度
		
		return randomPoint;
	}
	
	private double[] getRandomArea(Integer chooseArea) throws Exception{
		int randomArea = -1;
		
		Field[] field = Area.class.getDeclaredFields();
		AddressBuilder addressBuilder = new AddressBuilder();
		Area area = addressBuilder.new Area();//inner class
		
		if(chooseArea != null){
			if(chooseArea < 0 || chooseArea > field.length){
				return null;
			}
			randomArea = chooseArea;
		}
		else{
			int areaSize = field.length;//總共有的地區數 + 1 (不知道為何field中會多抓一個數)
			randomArea = random.nextInt(areaSize - 1);//所以取出時要減1
		}
		
		double[] areaCoordinates = ((double[])field[randomArea].get(area));//取出該地區的經緯度
		
		return areaCoordinates;
	}
	
	/**
	 * 區域是否隨機
	 */
	public void setAreaRandom(boolean isAreaRandom) {
		this.isAreaRandom = isAreaRandom;
	}

	/**
	 * 產生範圍
	 */
	public void setInitRange(int initRange) {
		this.initRange = initRange;
	}

	/**
	 * 產出數量
	 */
	public void setInitAddressNum(int initAddressNum) {
		this.initAddressNum = initAddressNum;
	}

	public static void main(String[] args) throws Exception{
//		Random random = new Random();
//		int k = 2;
//		double l = 50;
//		for(int i = 0; i< 50; i++){
//			System.out.println(random.nextInt(2*k + 1) - k);
//			System.out.println(random.nextInt(21));
//		}
//		
//		int initRange = 50;
//		int end = 20;
//		double endDouble = 50;
//		double radius = 100;
//		
//		double high = Math.sqrt((initRange * initRange - end * end));
//		System.out.println(high);
		
//		System.out.println(random.nextDouble() * (l - (-l)) + (-l));
		
		
		/*
		 * Area test
		 */
//		Field[] field = Area.class.getDeclaredFields();
//		AddressBuilder addressBuilder = new AddressBuilder();
//		Area area = addressBuilder.new Area();
//		for(Field f : field){
//			if(f.get(area) instanceof double[]){
//				System.out.println(((double[])f.get(area))[0] + "," + ((double[])f.get(area))[1]);
//			}
//		}
		
//		for(int i = 0;i < field.length;i++){
//			if(field[i].get(area) instanceof double[]){
//				System.out.println(((double[])field[i].get(area))[0] + "," + ((double[])field[i].get(area))[1]);
//			}
//		}
		
		/*
		 * 測試開始
		 */
		AddressBuilder addressBuilder = new AddressBuilder();
		addressBuilder.setInitAddressNum(1);
//		addressBuilder.getAddressList("台北車站", null, null, null);
		addressBuilder.getAddressList(null, AddressBuilder.AREA_TYPE.嘉義市, null, null);
	}

	
}
