package assam.tool.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

public class GetJsonObjectValueSample {
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	public enum DistanceEnum {

		_300M(300d);
		
		private Double distance;
		
		DistanceEnum(Double distance){
			this.distance = distance;
		}
		
		public Double getDistance() {
			return distance;
		}
	}
	
	public class MapClass {
		
		Double clongitude;
		Double clatitude;
		DistanceEnum distance;
		
		public Double getClongitude() {
			return clongitude;
		}
		public void setClongitude(Double clongitude) {
			this.clongitude = clongitude;
		}
		public Double getClatitude() {
			return clatitude;
		}
		public void setClatitude(Double clatitude) {
			this.clatitude = clatitude;
		}
		public DistanceEnum getDistance() {
			return distance;
		}
		public void setDistance(DistanceEnum distance) {
			this.distance = distance;
		}
	}
	
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		
//		String json = "{'clongitude':'25.028527423060638','clatitude':'121.47391225766101','distance':'_300M'}";
		
		GetJsonObjectValueSample sample = new GetJsonObjectValueSample();
		MapClass mapClass = sample.new MapClass();
		mapClass.setClongitude(25.028527423060638d);
		mapClass.setClatitude(121.47391225766101d);
		mapClass.setDistance(DistanceEnum._300M);
		
		String json = mapper.writeValueAsString(mapClass);
		
		System.out.println(json);
		
		try {
			JSONObject jsonObject= new JSONObject(json);
			
			Double latitude = jsonObject.getDouble("clatitude");
			Double longitude = jsonObject.getDouble("clongitude");
			String distance = jsonObject.getString("distance");
			System.out.println("latitude = " + latitude);
			System.out.println("longitude = " + longitude);
			System.out.println("distance = " + distance);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
