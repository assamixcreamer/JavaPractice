package assam.tool.regex;

/**
 * @Description 	正則表達式測試
 * @author 			charlie
 * @CreateDate 		2016年1月11日 下午4:41:07
 * @LastModifier 	charlie
 * @LastModifyDate 	2016年1月11日 下午4:41:07
 * @Copyright 		Smart Catch International Co., Ltd
 */
public class HtmlUrlNameTest {

	public static void main(String[] args) {
		
		String url = "<a href=\"" + "http://localhost:8080/test/index.html\"" +">JPG123456</a>, <a href=\"" + "http://localhost:8080/test2/index.html\"" +">JPF14567.jpg</a>";
		String info = "<a href=\"" + "http://localhost:8080/test/index.html\"" +">JPG123456</a>, <a href=\"" + "http://localhost:8080/test/index.html\"" +">JPF14567</a>";
		
		url = url.replaceAll("<a href=\"", "").replaceAll("\">[a-zA-Z0-9.]*</a>", "");
		info = info.replaceAll("</?[a-z][a-z0-9]*[^<>]*>", "");
		
		System.out.println("url = " + url);
		System.out.println("info = " + info);
		
	}

}
