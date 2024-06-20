package it.inail.geodnotifapp.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.inail.geodnotifapp.security.dto.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;

/**
 * La classe gestisce gli errori di autenticazione costruendo un messaggio opportuno e generico di risposta.
 */
@Component
public class UnauthorizedAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7858869558953243875L;

    private static final Logger log = LoggerFactory.getLogger(UnauthorizedAuthenticationEntryPoint.class);

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException exception) throws IOException, ServletException {

        log.error("Responding with unauthorized error. Message - {}", exception.getMessage(), exception);

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                exception.getMessage(),
                request.getRequestURI(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase()
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        PrintWriter writer = response.getWriter();
        writer.println(jacksonObjectMapper.writeValueAsString(exceptionResponse));
    }
}
