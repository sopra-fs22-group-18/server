package ch.uzh.ifi.hase.soprafs22.service;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class MessageEncoder implements Encoder.Text<String> {
    private static Gson gson = new Gson();

    @Override
    public String encode(String message) throws EncodeException {
        System.out.println(gson.toJson(message));
        return message;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
        // Close resources (if any used)
    }
}