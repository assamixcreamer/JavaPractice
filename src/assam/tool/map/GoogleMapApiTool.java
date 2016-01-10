package assam.tool.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleMapApiTool {
	
	public static final String GoogleDirectionAPI = "http://maps.googleapis.com/maps/api/directions/json?origin=%s&destination=%s&sensor=false&language=zh-tw";
	public static final String GoogleGeocodingAPI = "http://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=false&language=zh-tw";
	public static final String GoogleAddressAPI = "http://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&sensor=false&language=zh-tw";
	
	public static String getDistanceInfo(String origin, String destination) throws Exception{
		
		String distanceInfo = null;
		
		try {
			URL url = new URL(String.format(GoogleDirectionAPI, origin, destination));
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			int responseCode = con.getResponseCode();
			
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine + "\n");
			}
			in.close();
			
			distanceInfo = response.toString();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw e;
		}
		
		return distanceInfo;
	}
	
	
	public static String getGeocodingInfo(String address) throws IOException{
		String addressInfo = null;
		
		try {
			URL url = new URL(String.format(GoogleGeocodingAPI, address));
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			int responseCode = con.getResponseCode();
			
//			System.out.println("\nSending 'GET' request to URL : " + url);
//			System.out.println("Response Code : " + responseCode);
 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine+ "\n");
			}
			in.close();
			
			addressInfo = response.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		
		return addressInfo;
	}
	
	public static String getAddressInfomation(String lat, String lng) throws Exception{
		String addressInfo = null;
		
		try {
			URL url = new URL(String.format(GoogleAddressAPI, lat, lng));
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			int responseCode = con.getResponseCode();
			
//			System.out.println("\nSending 'GET' request to URL : " + url);
//			System.out.println("Response Code : " + responseCode);
 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine+ "\n");
			}
			in.close();
			
			addressInfo = response.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		
		return addressInfo;
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.sci.citygo.v3.service.IStoreService#getAddressCoordinates(java.lang.String)
	 */
	public static String getAddressByLatAndLng(String lat, String lng) throws Exception{
		String address = null;
		try {
			if(lat != null && lng != null){
				
				String addressInfo = GoogleMapApiTool.getAddressInfomation(lat, lng);
		 
//				System.out.println(addressInfo);
				
				if(addressInfo == null){
					return null;
				}
				
				JSONObject json = new JSONObject(addressInfo);
				
				JSONArray results = json.getJSONArray("results");
				if (results == null || results.length() == 0) {
					return null;
				}
				JSONObject result = results.getJSONObject(0);
				if (result == null) {
	                return null;
	            }
				address = result.getString("formatted_address");
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return address;
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.sci.citygo.v3.service.IStoreService#getAddressCoordinates(java.lang.String)
	 */
	public static double[] getLatAndLngByAddress(String address) throws Exception{
		double[] area = new double[2];
		try {
			if(address != null){
				
				String addressInfo = GoogleMapApiTool.getGeocodingInfo(address);
		 
				if(addressInfo == null){
					return null;
				}
				
				JSONObject json = new JSONObject(addressInfo);
				
				JSONArray results = json.getJSONArray("results");
				if (results == null || results.length() == 0) {
					return null;
				}
				JSONObject result = results.getJSONObject(0);
				if (result == null) {
	                return null;
	            }
				JSONObject geometry = result.getJSONObject("geometry");
				if(geometry == null){
					return null;
				}
				JSONObject location = geometry.getJSONObject("location");
				if(location == null){
					return null;
				}
				double lat = location.getDouble("lat");
				double lng = location.getDouble("lng");
				
				area[0] = lat;
				area[1] = lng;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return area;
	}
	
	public static void main(String[] args) {
		try {
//			String test = getDistanceInfo("台北車站", "台北101");
//			String test = getGeocodingInfo("台北車站");
			
			double[] test = getLatAndLngByAddress("台北車站");
			
//			String test = getAddressInfomation("25.047908", "121.517315");
//			String test2 = getAddressByLatAndLng("25.047908", "121.517315");
//			System.out.println(test);
			System.out.println(test[0] + ":" + test[1]);
//			System.out.println(test2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
