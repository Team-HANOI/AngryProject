package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class stack_num_noTouch { // 기술 스택 + 고유 번호(프로그래머스 기준!) 컬럼 파싱 or DB저장

	public static void main(String ar[]) throws JSONException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		HashMap<String, String> stack = new HashMap<String, String>();

		for (int i = 1; i <= 64; i++) {

			String url = "https://programmers.co.kr/api/job_positions/?page=" + i + "&order=default";
//						  https://programmers.co.kr/api/job_positions/1496
			JSONObject jobJson = readJsonFromUrl(url);

			// 문자열을 JSON형태의 구조로 메모리에 올리는 작업
			JSONTokener tokener = new JSONTokener(jobJson.toString());
			JSONObject object = new JSONObject(tokener);
			JSONArray jobPosions = object.getJSONArray("jobPositions");

			// 가져온 jobPosions에서 title을 배열로 뽑아낸다.
			// company안에 들어가있는 id를 가져 와야됨 이건 ..
			for (int j = 0; j < jobPosions.length(); j++) {

				JSONObject title = (JSONObject) jobPosions.get(j);

				// 배열이 아니야 ..... 그냥 JSONObject에서 "name" : value로 뽑아내면 될듯?

				JSONArray technicalTags = (JSONArray) title.get("technicalTags");

				for (int k = 0; k < technicalTags.length(); k++) {

					JSONObject tags = (JSONObject) technicalTags.get(k);
					stack.put(tags.get("name").toString(), tags.get("id").toString());

				}

			}
//			for (Iterator iterator = key.iterator(); iterator.hasNext()) {
//				
//				String keyValue = (String) iterator.next();
//				String valueVar = (String) map.get(keyValue);
//	   
//				System.out.println(keyValue +" = " +valueVar);
//			}

//			HashMap에서 Entry로 가져오기

		}

		try {

			// 1. driver 설정
			Class.forName("oracle.jdbc.OracleDriver");

			// 2. connection 가져오기
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");

			String sql = "insert into stack(stack,stackNum) values( ? , ? )";
			pstmt = conn.prepareStatement(sql);

			for (Entry<String, String> entry : stack.entrySet()) {

//				System.out.println(entry.getKey() + entry.getValue());
				pstmt.setString(1, entry.getKey());
				pstmt.setString(2, entry.getValue());
				pstmt.executeUpdate();

			}

		} catch (Exception e) {

			e.printStackTrace();

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