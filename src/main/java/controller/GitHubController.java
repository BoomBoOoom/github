package controller;

import com.google.gson.Gson;
import entity.Account;
import entity.Token;
import io.ebean.Ebean;
import oauth.OauthAccount;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.github.GHPerson;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/account")
@SessionAttributes("oauth_account")
public class GitHubController {

    @GetMapping
    public String getGitHub(@ModelAttribute("oauth_account") GitHub github) throws IOException {
        System.err.println("4");
        JSONArray res = new JSONArray();
        Map<String, GHRepository> map = github.getMyself().getAllRepositories();
        Iterator<Map.Entry<String, GHRepository>> iterable = map.entrySet().iterator();
        for (; iterable.hasNext(); ) {
            GHRepository ghRepository = iterable.next().getValue();
            JSONObject tmpJson = new JSONObject();
            tmpJson.put("name", ghRepository.getName());
            tmpJson.put("readme", IOUtils.toString(ghRepository.getReadme().read()));
            System.err.println("5");
            tmpJson.put("files", new Gson().toJson(ghRepository.listRefs()));
            System.err.println("6");
            res.put(tmpJson);
        }
        return res.toString();
    }

    /* @GetMapping
    public String getGitHub(@PathVariable("username")String username, JSONObject req) throws IOException {
        GitHub github = GitHub.connectUsingPassword(req.getString("username"), req.getString("password"));
        GHPerson me = github.getUser(username);
        String avatar = me.getAvatarUrl();
        String url = me.getHtmlUrl().toString();
        JSONArray arr = new JSONArray();
        HashMap<String, JSONObject> infos = new HashMap<>();
        JSONObject rep = new JSONObject();

        if (req.has("username") && req.has("password")) {
            if (github.isCredentialValid()) {
                rep.put("status", "connected");
                rep.put("avatar", avatar);
                rep.put("url", url);
                infos.put("rep", rep);
                arr.put(infos.get("rep"));
                return arr.toString();
            } else {
                rep.put("status", "offline");
            }
        }
        return "username or password invalid";
    }*/


    @PostMapping
    public JSONObject loginGitHub(@RequestBody Account account, JSONObject req) throws IOException {
        JSONObject rep = new JSONObject();
        if (req.has("username") && req.has("password")) {
            GitHub github = GitHub.connectUsingPassword(req.getString("username"), req.getString("password"));
            if (github.isCredentialValid()) {
                rep.put("status", "connected");
                Ebean.save(account);
            } else {
                rep.put("status", "offline");
            }
        }
        return rep;
    }
}