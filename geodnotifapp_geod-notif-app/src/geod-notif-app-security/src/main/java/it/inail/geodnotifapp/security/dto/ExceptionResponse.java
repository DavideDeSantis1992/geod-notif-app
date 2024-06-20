package it.inail.geodnotifapp.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class ExceptionResponse.
 */
public class ExceptionResponse implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 6508922410184158095L;

    /**
     * The timestamp.
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private final Date timestamp;

    /**
     * The message.
     */
    private final String message;

    /**
     * The detail.
     */
    private final String detail;

    /**
     * The http code message.
     */
    private final String httpCodeMessage;

    /**
     * Instantiates a new exception response.
     *
     * @param timestamp the timestamp
     * @param message the message
     * @param detail the detail
     * @param httpCodeMessage the http code message
     */
    public ExceptionResponse(Date timestamp, String message, String detail, String httpCodeMessage) {
        this.timestamp = timestamp;
        this.message = message;
        this.detail = detail;
        this.httpCodeMessage = httpCodeMessage;
    }

    /**
     * Gets the serial version UID.
     *
     * @return the serial version UID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * Gets the timestamp.
     *
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the detail.
     *
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Gets the http code message.
     *
     * @return the http code message
     */
    public String getHttpCodeMessage() {
        return httpCodeMessage;
    }
}
