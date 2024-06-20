package it.inail.geodnotifapp.security.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Entità utilizzata per salvare i dati di profilazione nella cache redis.
 */
@RedisHash("profile")
public class Profile {

    @Id
    private String key;

    private Boolean value;

    private Date expirationDate;

    public Profile(String key, Boolean value, Date expirationDate) {
        this.key = key;
        this.value = value;
        this.expirationDate = expirationDate;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Boolean getValue() {
        return value;
    }

    public boolean isExpired() {
        return Calendar.getInstance().getTime().after(expirationDate);
    }

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    public long getTimeToLive() {
        long ttl = this.expirationDate.getTime() - Calendar.getInstance().getTime().getTime();
        return ttl >= 0 ? ttl : 0L;
    }

}
