package com.zerobase.zbcharger.util;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

public class MockMvcUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ResultActions performPost(MockMvc mockMvc, String url, Object requestBody)
        throws Exception {
        return mockMvc.perform(
                post(url)
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestBody)))
            .andDo(print());
    }

    public static ResultActions performGet(MockMvc mockMvc, String url)
        throws Exception {
        return mockMvc.perform(
                get(url)
                    .contentType(APPLICATION_JSON))
            .andDo(print());
    }
}
