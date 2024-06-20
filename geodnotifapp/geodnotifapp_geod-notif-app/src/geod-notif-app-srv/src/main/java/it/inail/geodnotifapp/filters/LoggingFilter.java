package it.inail.geodnotifapp.filters;

import ch.qos.logback.classic.helpers.MDCInsertingServletFilter;
import org.springframework.stereotype.Component;

/**
 * MDC filter che abilita la scrittura dei dati di tracciatura sul log del servizio.
 * Il filtro si provvede alla valorizzazione dei dati del Mapped Diagnostic Context necessario per il logging.
 */
@Component
public class LoggingFilter extends MDCInsertingServletFilter {
}
