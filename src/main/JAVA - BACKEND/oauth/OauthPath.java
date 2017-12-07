package oauth;

import java.util.ArrayList;


public interface OauthPath {
    void protectResource(ArrayList<String> path);

    void setOauthPath(ArrayList<String> loginPath);

    void excludeResource(ArrayList<String> resource);
}
