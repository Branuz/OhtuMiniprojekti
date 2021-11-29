package project.main;
import java.util.List;
import project.db.SQLUserDAO;
import static spark.Spark.*;

import project.logic.AuthenticationService;

import project.domain.BlogRecommendation;

import spark.template.thymeleaf.ThymeleafTemplateEngine;
import spark.ModelAndView;

// /list-sivun proof of concept. Poistetaan, kun blogien hakeminen tietokannasta onnistuu.
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String args[]){
        AuthenticationService auth = new AuthenticationService(new SQLUserDAO());

        port(5000);
        get("/", (req,res) -> new ModelAndView(new HashMap<>(), "index"), new ThymeleafTemplateEngine());
        get("/list", (req,res) -> {
            // Proof of concept. Poistetaan, kun blogien hakeminen tietokannasta onnistuu.
            BlogRecommendation testBlog1 = new BlogRecommendation("Blog 1", "Blog", "https://test.blog.com");
            BlogRecommendation testBlog2 = new BlogRecommendation("Blog 2", "Blog", "https://test.blog2.org");
            List tempBlogList = new ArrayList<>();
            tempBlogList.add(testBlog1);
            tempBlogList.add(testBlog2);
            
            HashMap map = new HashMap<>();
            map.put("recommendations", tempBlogList);
            return new ModelAndView(map, "list");
        }, new ThymeleafTemplateEngine());

        get("/login", (req,res) -> new ModelAndView(new HashMap<>(), "login"), new ThymeleafTemplateEngine());
        post("/login", (req, res) -> {
            if (auth.login(
                req.queryParamOrDefault("username", null),
                req.queryParamOrDefault("password", null)
            ) == null) {
                return "{\"message\":\"Wrong username or password\"}";
            }
            return "{\"message\":\"Success\"}";
            }
        );

        get("/signup", (req,res) -> new ModelAndView(new HashMap<>(), "signup"), new ThymeleafTemplateEngine());
        post("/signup", (req, res) -> {
           if (auth.createUser(
               req.queryParamOrDefault("username", null),
               req.queryParamOrDefault("password", null)
           )) {
               return "{\"message\":\"Success\"}";
           }
            return "{\"message\":\"Failure\"}";
        });

        get("/post", (req,res) -> "Post a new recommendation");
    }
}
