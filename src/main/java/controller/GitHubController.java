package controller;

import com.google.gson.Gson;
import entity.Account;
import io.ebean.Ebean;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.github.GHPerson;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.web.bind.annotation.*;
import tmp.DataSingleton;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class GitHubController {

    @GetMapping
    public JSONArray info() throws IOException {
        JSONArray res = new JSONArray();
        
        GitHub github = GitHub.connectUsingOAuth(token);
        System.err.println("3");
        Map<String, GHRepository> map = github.getMyself().getAllRepositories();
        Iterator<Map.Entry<String, GHRepository>> iterable = map.entrySet().iterator();
        for (; iterable.hasNext(); ) {
            GHRepository ghRepository = iterable.next().getValue();
            JSONObject tmpJson = new JSONObject();
            tmpJson.put("name", ghRepository.getName());
            tmpJson.put("readme", IOUtils.toString(ghRepository.getReadme().read()));
            tmpJson.put("files", new JSONObject(new Gson().toJson(ghRepository.listRefs())));
            res.put(tmpJson);
        }
        return res;
    }
}