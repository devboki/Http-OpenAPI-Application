package com.mycom;

import java.io.IOException;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SpringBootTest
@Transactional
public class buisnessServiceTest {

	@Autowired BuisnessService buisnessService;
	
	@Test
	public void returnStringTest() throws IOException {
		Bno bno = new Bno();
		bno.setBNo("5298600830");
		
		//ResultDTO dtoTest = buisnessService.check(bno);
		//System.out.println(dtoTest);
		//ResultDTO(bNo=null, statusCode=null, taxType=null)

		OkHttpClient client = new OkHttpClient()
				.newBuilder()
				.build();
		
		MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
		
		String json = bno.getBNo();
		String url = "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=qiZ1LHi4vs9UHAPKDnqSXmpbF20WwAPnZd2RpfnCqRhbT06uvUepuDGUgTqdgb6cPGJB2OVnzHbzlH8EJpImng%3D%3D";
		String jsonStart = "{\r\n  \"b_no\": [\r\n    \"";
		String jsonEnd = "\"\r\n  ]\r\n}";
		
		RequestBody body = RequestBody.create(mediaType, jsonStart + json + jsonEnd);
		
		Request request = new Request.Builder()
				  .url(url)
				  .method("POST", body)
				  .addHeader("Content-Type", "application/json")
				  .build();
		
		Response response = client.newCall(request).execute();
		
		String result = response.body().string();
		
		try {
			JSONObject jsonResult = new JSONObject(result);
			System.out.println("jsonResult = " + jsonResult);
			//jsonResult = {"match_cnt":1,"status_code":"OK","data":[{"end_dt":"","b_no":"5298600830","b_stt_cd":"01","tax_type":"부가가치세 일반과세자","b_stt":"계속사업자","utcc_yn":"N","invoice_apply_dt":"","tax_type_change_dt":"","tax_type_cd":"01"}],"request_cnt":1}
			System.out.println("status_code = " + jsonResult.get("status_code"));
			//status_code = OK
			
			JSONArray dataArray = jsonResult.getJSONArray("data");
			System.out.println("dataArray = " + dataArray.get(0));
			//dataArray = {"end_dt":"","b_no":"5298600830","b_stt_cd":"01","tax_type":"부가가치세 일반과세자","b_stt":"계속사업자","utcc_yn":"N","invoice_apply_dt":"","tax_type_change_dt":"","tax_type_cd":"01"}
			
			for(int i=0; i<dataArray.length(); i++) {
				JSONObject dataObj = dataArray.getJSONObject(i);
				String bNo = dataObj.getString("b_no");
				String taxType = dataObj.getString("tax_type");
				ResultDTO resultDTO = new ResultDTO(bNo, taxType);
				System.out.println("resultDTO = " + resultDTO);
				//resultDTO = ResultDTO(bNo=5298600830, taxType=부가가치세 일반과세자)
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void returnDtoTest() throws IOException {
		Bno bno = new Bno();
		bno.setBNo("5298600830");
		ResultDTO dto = buisnessService.check(bno);
		System.out.println(dto);
	}
}
