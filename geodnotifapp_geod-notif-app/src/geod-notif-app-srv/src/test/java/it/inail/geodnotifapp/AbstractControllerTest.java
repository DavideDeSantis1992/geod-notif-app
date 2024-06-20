package it.inail.geodnotifapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

/**
 * Utility class per l'implementazione di test unitari di controller utilizzando il MockMVC di spring
 */
public abstract class AbstractControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public void setUp() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    protected RequestBuilder buildGetRequestBuilder(String path) {
        return buildGetRequestBuilder(path, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
    }

    protected RequestBuilder buildGetRequestBuilder(String path,
                                                    MediaType requestMediaType,
                                                    MediaType responseMediaType) {
        Assert.notNull(path, "path is required");
        return MockMvcRequestBuilders.get(path)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
    }

    protected RequestBuilder buildPostRequestBuilder(String path,
                                                     Object content) throws JsonProcessingException {
        return buildPostRequestBuilder(path,
                content,
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_JSON);
    }

    protected RequestBuilder buildPostRequestBuilder(String path,
                                                     Object content,
                                                     MediaType requestMediaType,
                                                     MediaType contentMediaType) throws JsonProcessingException {
        Assert.notNull(path, "path is required");
        Assert.notNull(content, "content is required");

        Object requestContent = content;
        if (requestMediaType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
            requestContent = objectMapper.writeValueAsBytes(content);
        }

        return MockMvcRequestBuilders.post(path)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .characterEncoding("utf-8")
                .accept(requestMediaType)
                .contentType(contentMediaType)
                .content(objectMapper.writeValueAsBytes(content));
    }

    protected RequestBuilder buildPutRequestBuilder(String path,
                                                    Object content) throws JsonProcessingException {
        return buildPutRequestBuilder(path,content, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
    }

    protected  RequestBuilder buildMultiPartRequetBuilder(String path, MockMultipartFile... files) {
        Assert.notNull(path, "path is required");
        Assert.notNull(files, "file is required");
        Assert.notEmpty(files, "files must not be empty");
        MockMultipartHttpServletRequestBuilder request = MockMvcRequestBuilders.multipart("/upload");
        for (MockMultipartFile file : files) {
            request.file(file);
        }
        request.with(SecurityMockMvcRequestPostProcessors.csrf());
        request.characterEncoding("UTF-8");
        return request;
    }

    protected RequestBuilder buildPutRequestBuilder(String path,
                                                    Object content,
                                                    MediaType requestMediaType,
                                                    MediaType contentMediaType) throws JsonProcessingException {
        Assert.notNull(path, "path is required");
        Assert.notNull(content, "content is required");
        return MockMvcRequestBuilders.put(path)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .characterEncoding("utf-8")
                .accept(requestMediaType)
                .contentType(contentMediaType)
                .content(objectMapper.writeValueAsBytes(content));
    }

    protected RequestBuilder buildPutRequestBuilder(String path,
                                                    MediaType requestMediaType,
                                                    MediaType contentMediaType) {
        Assert.notNull(path, "path is required");
        return MockMvcRequestBuilders.put(path)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .characterEncoding("utf-8")
                .contentType(contentMediaType)
                .accept(requestMediaType);
    }

    protected RequestBuilder buildDeleteRequestBuilder(String path) {
        Assert.notNull(path, "path is required");
        return MockMvcRequestBuilders.delete(path)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .characterEncoding("utf-8");

    }
}
