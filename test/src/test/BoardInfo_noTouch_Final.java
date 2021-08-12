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
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class BoardInfo_noTouch_Final { // 페이징 정보 가져오기 채용 공고 모드에 들어가는 컬럼(회사이름, url, logourl) 파싱 or DB저장
									// 개선방향 : 기술스택 하나 하나를(기술스택 고유 번호 사용) 컬럼으로 옆으로 늘여놓아서 질문 매칭?

	public static void main(String ar[]) throws JSONException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		String proGrammersURL = "https://programmers.co.kr"; // title.get("url")앞에 붙이는 url, 채용공고 모드 링크 매칭용
//		
		for (int i = 1; i <= 5; i++) { // 긁어올 공고 페이지수 설정 (필수)

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
				JSONObject name = (JSONObject) title.get("company"); // company JSONObject에서 name(회사 이름)항목 가져오기

				String result = proGrammersURL + title.get("url");
				JSONArray technicalTags = (JSONArray) title.get("technicalTags");

				ArrayList<String> tag = new ArrayList<String>();
				for (int k = 0; k < technicalTags.length(); k++) {

					JSONObject tags = (JSONObject) technicalTags.get(k);
					tag.add(tags.get("name").toString());

				}
//				System.out.println(tag.toString());
				try {
					// 1. driver 설정
					Class.forName("oracle.jdbc.OracleDriver");

					// 2. connection 가져오기
					conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");

					String sql = "insert into allpage(company,url,logourl,stacks) values( ? , ? , ? , ?)";
					pstmt = conn.prepareStatement(sql);

					pstmt.setString(1, name.get("name").toString());
					pstmt.setString(2, result);
					pstmt.setString(3, name.get("logoUrl").toString());
					pstmt.setString(4, tag.toString());
					pstmt.executeUpdate();
					pstmt.close();
					conn.close();

				}

				catch (Exception e) {

					e.printStackTrace();

				}

			}
		}

	}

//	다수의 페이지를 한번에 가져오려고 하면 이런 오류가 생김
//	java.sql.SQLException: Listener refused the connection with the following error:
//		ORA-12519, TNS:no appropriate service handler found

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