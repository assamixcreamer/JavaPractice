package assam.tool.regex;

/**
 * @Description 	正則表達式測試
 * @author 			charlie
 * @CreateDate 		2016年1月11日 下午4:41:07
 * @LastModifier 	charlie
 * @LastModifyDate 	2016年1月11日 下午4:41:07
 * @Copyright 		Smart Catch International Co., Ltd
 */
public class MposFunctionNameFilterTest {

	public static void main(String[] args) {
		
		// m[0-9]+ 表示m開頭後面接任意數字 ex: m901
		// /{1} 表示"/"這個符號必須要有1個
		// "." 表示任一字元， "*" 表示1次或是多次
		// \\.{1} 表示 "."這個符號必須出現1次
		//zul 表示必須符合zul這三個英文字
		System.out.println("m11/M101.zul".matches("m[0-9]+/{1}.*\\.{1}zul"));
	}

}
