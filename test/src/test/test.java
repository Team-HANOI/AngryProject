package test;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

public class test { // 페이징 정보 가져오기

	public static void main(String ar[]) throws JSONException, IOException {

//		Connection conn = null;
//		PreparedStatement pstmt = null;
		
//	*	ArrayList<String[]> array = new ArrayList<String[]>();
		
		String[] arr0 = new String[] { "a1", "b2", "d4" };
		String[] arr1 = new String[] { "a1", "b2", "c3", "d4", "e5" };
		String[] arr2 = new String[] { "a1", "b2" };
		String[] arr3 = new String[] { "b2", "d4", "e5" };
		String[] arr4 = new String[] { "b2", "d4", };
		String[] arr5 = new String[] { "a1", "e5" };
		String[] arr6 = new String[] { "a1" };
		String[] arr7 = new String[] { "b2", "e5" };
		String[] arr8 = new String[] { "b2", "d4", "e5" };
		String[] arr9 = new String[] { "a1", "d4", "e5" };

		String arr = "arr";
		int test = 0;
		String page = "";
		
		
//		"arr0" -> arr0 이렇게 바꿀수는 없나 ... "arr0"이 arr0주소값을 참조하게
		System.out.println(arr+test); 
		System.out.println((arr+test).getClass().getSimpleName());

		System.out.println();

		System.out.println(arr0);	
		System.out.println(arr0.getClass().getSimpleName());
		
//		for (int i = 0; i < 10; i++) {
//
//			page = arr + i;
//
//			for (int j = 0; j < page.length(); j++) {
//				
//			}
//
//		}
		

//		try {
//
//			// 1. driver 설정
//			Class.forName("oracle.jdbc.OracleDriver");
//
//			// 2. connection 가져오기
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");
//			String sql = "insert into stackarry values( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )";
//			pstmt = conn.prepareStatement(sql);
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//
//		}

	}
}

//변수 타입 보는 방법
//public class MyClass {
//    public static void main(String args[]) {
//        String str = "Sample String";
//        System.out.println(str.getClass().getSimpleName());
//    }
//}
