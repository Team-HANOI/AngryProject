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
import java.util.HashSet;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Tag_stack_noTouch { // HashSet 으로 기술 스택가져와서 db에 담기 2021-08-11 13:50분 업데이트, 고유번호 = i++를 pk로 처리

	public static void main(String[] args) throws JSONException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		HashSet<String> stack = new HashSet<String>();

		for (int i = 1; i <= 64; i++) { // 긁어올 공고 페이지수 설정 (필수)

			String url = "https://programmers.co.kr/api/job_positions/?page=" + i + "&order=default";

			JSONObject jobJson = readJsonFromUrl(url);
			JSONTokener tokener = new JSONTokener(jobJson.toString());
			JSONObject object = new JSONObject(tokener);
			JSONArray jobPosions = object.getJSONArray("jobPositions");

			for (int j = 0; j < jobPosions.length(); j++) {

				JSONObject title = (JSONObject) jobPosions.get(j);
				JSONArray technicalTags = (JSONArray) title.get("technicalTags");

				for (int k = 0; k < technicalTags.length(); k++) {

					JSONObject tags = (JSONObject) technicalTags.get(k);
					stack.add(tags.get("name").toString());

				}
			}
		}

//		HashSet에 담겨있는 배열을 하나씩 나열 여기서 dao호출해서 넣어버릴까
//		System.out.println(stack);

		try {

			// 1. driver 설정
			Class.forName("oracle.jdbc.OracleDriver");

			// 2. connection 가져오기
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");

			String sql = "insert into stack(stack,stackNum) values( ? , ? )";
			pstmt = conn.prepareStatement(sql);
			Iterator<String> stacks = (Iterator<String>) stack.iterator();
			for (int i = 1; i <= stack.size(); i++) {

				pstmt.setString(1, stacks.next());
				pstmt.setInt(2, i);
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
