package ch.uzh.ifi.hase.soprafs22.service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;

public class TextApi {
    static String checkComment(String comment_text) {
        String url="https://api.sightengine.com/1.0/text/check.json";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("text",comment_text);
        map.add("lang","en");
        map.add("opt_countries","us,gb,fr");
        map.add("mode","standard");
        map.add("api_user","1783195191");
        map.add("api_secret","rnoKAxkPU8bCMjyF4cDC");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        if (response.getBody().indexOf("type")!=-1){
            int start_fluchwort=response.getBody().indexOf("type")+8;
            int laenge=response.getBody().length();
            int ende_fluchwort=response.getBody().substring(start_fluchwort,laenge).indexOf(",")+start_fluchwort-1;
            String fluchwort=response.getBody().substring(start_fluchwort,ende_fluchwort);
            return fluchwort;}
        else{
            return "";
        }

        
}

}