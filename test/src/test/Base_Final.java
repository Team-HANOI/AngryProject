package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Base_Final { // 페이징 정보 가져오기

	public static void main(String ar[]) throws JSONException, IOException {

		String proGrammersURL = "https://programmers.co.kr"; // title.get("url")앞에 붙이는 url, 채용공고 모드 링크 매칭용

		for (int i = 1; i <= 1; i++) { // 긁어올 공고 페이지수 설정 (필수)

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

				JSONArray technicalTags = (JSONArray) title.get("technicalTags");

				// technicalTags JSONArray에서 name(기술 스택)모두 가져오기
				System.out.print("기술 스택 : "); // 출력부

				for (int k = 0; k < technicalTags.length(); k++) {

					JSONObject tags = (JSONObject) technicalTags.get(k);

					System.out.print(tags.get("name") + ","); // 출력부

				}
				
				String result = proGrammersURL + title.get("url");
				System.out.println("\t회사 이름 :" + name.get("name") + "  \t  공고 페이지 :" + result ); // 출력부

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