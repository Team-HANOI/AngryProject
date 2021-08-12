package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Board_info_StackArray_noTouch_SizeTest { // 페이징 정보 가져오기

	public static void main(String ar[]) throws JSONException, IOException {

//		Connection conn = null;
//		PreparedStatement pstmt = null;

//		String proGrammersURL = "https://programmers.co.kr"; // title.get("url")앞에 붙이는 url, 채용공고 모드 링크 매칭용
//		ArrayList<String> test = new ArrayList<String>();
		for (int i = 1; i <= 1; i++) { // 긁어올 공고 페이지수 설정 (필수) // page >

//			ArrayList<String> stackArray = new ArrayList<String>();

			String url = "https://programmers.co.kr/api/job_positions/?page=" + i + "&order=default";
//						  https://programmers.co.kr/api/job_positions/1496
			JSONObject jobJson = readJsonFromUrl(url);

			// 문자열을 JSON형태의 구조로 메모리에 올리는 작업
			JSONTokener tokener = new JSONTokener(jobJson.toString());
			JSONObject object = new JSONObject(tokener);
			JSONArray jobPosions = object.getJSONArray("jobPositions");

//			System.out.println("페이지");

			for (int j = 0; j < jobPosions.length(); j++) {

//				System.out.println("-----시작------");
//				System.out.println("n번째 공고" + jobPosions.length());

				JSONObject jobPosion = (JSONObject) jobPosions.get(j); // n번째 공고
				JSONArray stackArray = jobPosion.getJSONArray("technicalTags");

//				JSONObject tags = (JSONObject) technicalTags.get(k);
//				stack.put(tags.get("name")

				try {

					// 1. driver 설정
//					Class.forName("oracle.jdbc.OracleDriver");
//					// 2. connection 가져오기
//					conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");
//					String sql = "insert into stackarry values( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )";
//					pstmt = conn.prepareStatement(sql);
					ArrayList<String> test = new ArrayList<String>();

					for (int k = 0; k < stackArray.length(); k++) {

						JSONObject tags = (JSONObject) stackArray.get(k);
						test.add(tags.get("id").toString());
						System.out.print(tags.get("id") + " ");

					}

					System.out.println("||size : " + test.size());

				} catch (Exception e) {

					e.printStackTrace();

				}
			}
		}
	}

// JSON데이터 URL에서 가져오기
	private static String jsonReadAll(Reader reader) throws IOException {

		StringBuilder sb = new StringBuilder();

		int cp;

		while ((cp = reader.read()) != -1) {

			sb.append((char) cp);

		}

		return sb.toString();

	}

	private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {

		InputStream is = new URL(url).openStream();

		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = jsonReadAll(br);
			JSONObject json = new JSONObject(jsonText);
			return json;

		} finally {

			is.close();

		}
	}
}