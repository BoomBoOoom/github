package entity;

import com.google.gson.annotations.Expose;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author sheol on 10/31/17 at 2:46 PM
 * @project SpringRestDemo
 */
@Entity
@Table(name = "token")
public class Token extends BaseEntity {
    @Expose
    private String token;
    @Expose
    private Long user_id;
    @Expose
    private String token_type = "Bearer";
    @Expose
    private int ttl = 3600;
    private Long id_token;

    public Token() {

    }

    public Long getId_token() {
        return id_token;
    }

    public Long getUser_id() {
        return user_id;
    }

    public int getTtl() {
        return ttl;
    }

    public String getToken() {
        return token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setId_token(Long id_token) {
        this.id_token = id_token;
    }
}
