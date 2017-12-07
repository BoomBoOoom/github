package controller;

import com.google.gson.Gson;
import entity.Account;
import io.ebean.Ebean;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
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
        JSONArray res = new JSONArray();
        Map<String, GHRepository> map = github.getMyself().getAllRepositories();
        String avatar_url = github.getMyself().getAvatarUrl();
        Iterator<Map.Entry<String, GHRepository>> iterable = map.entrySet().iterator();
        for (; iterable.hasNext(); ) {
            GHRepository ghRepository = iterable.next().getValue();
            JSONObject tmpJson = new JSONObject();
            tmpJson.put("name", ghRepository.getName());
            tmpJson.put("avatar_url", avatar_url);
            tmpJson.put("readme", IOUtils.toString(ghRepository.getReadme().read()));
            tmpJson.put("files", new Gson().toJson(ghRepository.listRefs().asList()));
            res.put(tmpJson);
        }
        return res.toString();
    }


    @PostMapping
    public JSONObject postGitHub(@RequestBody Account account, JSONObject req) throws IOException {
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