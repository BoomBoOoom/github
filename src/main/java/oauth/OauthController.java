package oauth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Account;
import entity.Token;
import io.ebean.Ebean;
import io.ebean.EbeanServer;
import org.json.JSONObject;
import org.kohsuke.github.GHAuthorization;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tmp.DataSingleton;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author sheol on 10/29/17 at 1:00 PM
 * @project SpringRestDemo
 */
@RestController
@RequestMapping("/oauth")
public class OauthController {
    @PostMapping("/auth")
    public String auth(@RequestHeader(value = "authorization") String authorization,
                       HttpServletResponse response) throws IOException {
        String base64Tmp = authorization.split(" ")[1];
        String[] base64DecodeTmp = new String(Base64.getDecoder().decode(base64Tmp)).split(":");
        if (base64DecodeTmp.length == 2) {
            String username = base64DecodeTmp[0];
            String password = base64DecodeTmp[1];

           /* List<Account> accounts = Ebean.createQuery(Account.class).findList();
            //List<Account> accounts = DataSingleton.getInstance().getAccounts();
            for (Account account : accounts) {
                if (account.getUsername().equals(username)
                        && account.getPassword().equals(password)) {
                    Token token = new Token();
                    token.setToken(UUID.randomUUID().toString());
                    token.setUser_id(account.getId());
                    token.setTtl(3600);
                    //DataSingleton.getInstance().getTokens().add(token);
                    Ebean.save(token);
                    return DataSingleton.gson().toJson(token);
                }
            }*/
            GitHub github = GitHub.connectUsingPassword(username, password);
            if (github.isCredentialValid()) {
                JSONObject obj = new JSONObject();
                try {
                    GHAuthorization auth = createAuthToken(github);
                    obj.put("token", auth.getToken());
                } catch (Exception e) {
                    Token token = Ebean.createQuery(Token.class).where().eq("user_id", github.getMyself().getId()).findOne();
                    if (token != null) {
                        github.deleteAuth(token.getId_token());
                        Ebean.delete(token);
                        GHAuthorization auth = createAuthToken(github);
                        obj.put("token", auth.getToken());
                    }
                }
                return obj.toString();
            }
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return "";
    }

    private GHAuthorization createAuthToken(GitHub github) throws IOException {
        List<String> scopes = new ArrayList<>();
        scopes.add("repo");
        scopes.add("gist");
        scopes.add("notifications");
        scopes.add("user");
        GHAuthorization auth = github.createToken(scopes, "test", "http://test.fr");
        auth.getId();
        Token token = new Token();
        token.setToken(auth.getToken());
        token.setUser_id(github.getMyself().getId());
        token.setId_token(auth.getId());
        Ebean.save(token);
        return auth;
    }

    @PostMapping("/token_auth")
    public String tokenAuth() {
        // TODO: 10/29/17 validate Bearer auth
        // TODO: 10/29/17 GrantType.client_credentials
        // TODO: 10/29/17 GrantType.refresh_token
        // TOKEN_TYPE, ACCESS_TOKEN, EXPIRES_IN
        return "";
    }


}
