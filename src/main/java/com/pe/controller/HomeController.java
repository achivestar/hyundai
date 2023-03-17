package com.pe.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.pe.domain.Memo;
import com.pe.repository.MemoRepository;


import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor 
public class HomeController {
	
	/*
	 * @Autowired private MemoService memoService;
	 */
	 private final MemoRepository memoRepository;

	@GetMapping("/")
	public String jsp(Model model) {
		System.out.println("jsp 파일이 요청됨");
		model.addAttribute("username","쌤즈");
		return "index";
	}
	
	
	@GetMapping("/api/hello")
	public @ResponseBody List<String> Hello() {
		return Arrays.asList("서버","뷰","오징어");
	}
	
	
	@GetMapping("/memo")
    public   String findAllMemo(Model model) {
		model.addAttribute("memoList",memoRepository.findAll());
        return "memo";
    }

	@GetMapping("/api/memo")
    public @ResponseBody  List<Memo> findAllMemoAPI() {
        return memoRepository.findAll();
    }
	
	
	@GetMapping("/naver")
	public @ResponseBody String naver(@RequestParam String query) {
	      	String clientId = "GY1ZxazaqsKf4BwnUARo"; //애플리케이션 클라이언트 아이디
	        String clientSecret = "jUjpAHJdVO"; //애플리케이션 클라이언트 시크릿


	        String text = null;
	        try {
	            text = URLEncoder.encode(query, "UTF-8");
	        } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException("검색어 인코딩 실패",e);
	        }


	        String apiURL = "https://openapi.naver.com/v1/search/blog?display=20&query=" + text;    // JSON 결과
	        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // XML 결과


	        Map<String, String> requestHeaders = new HashMap<>();
	        requestHeaders.put("X-Naver-Client-Id", clientId);
	        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
	        String responseBody = get(apiURL,requestHeaders);


	        System.out.println(responseBody);
	        return responseBody;
	    }
	
	
	
	 	@GetMapping("/kakao")
	    public @ResponseBody Map kakao(@RequestParam String query) {
	        // 카카오 Open API 요청 URL
	 		String key = "12a1801ee470636d250e8a8195f39432";
	 	    String url = "https://dapi.kakao.com/v2/search/blog";
	 		System.out.println("kakao : "+query);
		        
	 		RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders httpHeaders = new HttpHeaders();
	        httpHeaders.set("Authorization", "KakaoAK " + key); //Authorization 설정
	        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders); //엔티티로 만들기
	        URI targetUrl = UriComponentsBuilder
	                .fromUriString(url) //기본 url
	                .queryParam("query", query) //인자
	                .queryParam("size", 20) //인자
	                .build()
	                .encode(StandardCharsets.UTF_8) //인코딩
	                .toUri();

	        //GetForObject는 헤더를 정의할 수 없음
	        ResponseEntity<Map> result = restTemplate.exchange(targetUrl, HttpMethod.GET, httpEntity, Map.class);
	        return result.getBody(); //내용 반환
	    }

	 	
	 	
	 	
	 	@GetMapping("/visitjeju")
	    public  @ResponseBody ResponseEntity<String> visitjeju(@RequestParam String query) {
	        // visitjeju 
	 		String key = "fuj7r18lgumeamjf";
	 	    String url = "http://api.visitjeju.net/vsjApi/contents/searchList?apiKey=fuj7r18lgumeamjf&locale=kr&title="+query;
	 		System.out.println("visitjeju : "+query);
		        
	 		
	 		return new ResponseEntity<String>(url,HttpStatus.OK);
	    }
	 	
	 	
	 	
	 	@GetMapping("/map")
	    public String map() {
	 		return "map";
	 	}

	    private static String get(String apiUrl, Map<String, String> requestHeaders){
	        HttpURLConnection con = connect(apiUrl);
	        try {
	            con.setRequestMethod("GET");
	            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
	                con.setRequestProperty(header.getKey(), header.getValue());
	            }


	            int responseCode = con.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
	                return readBody(con.getInputStream());
	            } else { // 오류 발생
	                return readBody(con.getErrorStream());
	            }
	        } catch (IOException e) {
	            throw new RuntimeException("API 요청과 응답 실패", e);
	        } finally {
	            con.disconnect();
	        }
	    }


	    private static HttpURLConnection connect(String apiUrl){
	        try {
	            URL url = new URL(apiUrl);
	            return (HttpURLConnection)url.openConnection();
	        } catch (MalformedURLException e) {
	            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
	        } catch (IOException e) {
	            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
	        }
	    }


	    private static String readBody(InputStream body){
	        InputStreamReader streamReader = new InputStreamReader(body);


	        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
	            StringBuilder responseBody = new StringBuilder();


	            String line;
	            while ((line = lineReader.readLine()) != null) {
	                responseBody.append(line);
	            }


	            return responseBody.toString();
	        } catch (IOException e) {
	            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
	        }
	    }
	

}
