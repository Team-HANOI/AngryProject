package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Title_noTouch_Dummy { // 모집 포지션 긁어오기, 문제 : 모집 포지션이 기업맘대로 작성되어서 포지션값이 말도 안되게 많음 약 900+개

	public static void main(String ar[]) throws JSONException, IOException {

		HashSet<String> stack = new HashSet<String>();
		for (int i = 1; i <= 1; i++) {

			String url = "https://programmers.co.kr/api/job_positions/?page=" + i + "&order=default";
			JSONObject jobJson = readJsonFromUrl(url);

			JSONTokener tokener = new JSONTokener(jobJson.toString());
			JSONObject object = new JSONObject(tokener);
			JSONArray jobPosions = object.getJSONArray("jobPositions");

			for (int j = 0; j < jobPosions.length(); j++) {

				JSONObject title = (JSONObject) jobPosions.get(j);
				stack.add(title.get("title").toString());

			}
		}

		Iterator<String> title = (Iterator<String>) stack.iterator();
		for (int i = 1; i <= stack.size(); i++) {

			System.out.println(title.next() + " " + i);

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