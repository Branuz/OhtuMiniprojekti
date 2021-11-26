// Lukuvinkki-olioiden käyttölogiikasta vastaava luokka
package project.logic;

import java.util.ArrayList;
import java.util.HashMap;
import project.DAO.ReadingRecommendationDAO;
import project.domain.BlogRecommendation;
import project.domain.BookRecommendation;

public class ReadingRecommendationService {

    private ArrayList<ReadingRecommendationDAO> recommendations;

    public ReadingRecommendationService(ArrayList<ReadingRecommendationDAO> recommendations) {
        this.recommendations = recommendations;
    }

    public void addRecommendation(ReadingRecommendationDAO recommendation) {
        this.recommendations.add(recommendation);
    }

    public void createRecommendation(HashMap<String, String> info) {
        String headline = info.get("headline");
        String type = info.get("type");
        if (type.equals("blog")) {
            BlogRecommendation r = new BlogRecommendation(headline, type, info.get("url"));
            if (info.containsKey("writer")) {
                r.setWriter(info.get("writer"));
            }
            this.recommendations.add(r);
            
        } else if (type.equals("book")) {
            BookRecommendation r = new BookRecommendation(headline, type, info.get("writer"));
            if (info.containsKey("ISBN")) {
                r.setISBN(info.get("ISBN"));
            }
            this.recommendations.add(r);
            
        }

    }

    public void removeRecommendation(String headline) {
        int index = findIndex(headline);
        if (index >= 0) {
            this.recommendations.remove(index);
        }
    }

    // korvattavissa alla olevalla metodilla findIndex?
    public ReadingRecommendationDAO findRecommendation(String headline) {
        for (ReadingRecommendationDAO r : this.recommendations) {
            if (r.getHeadline().equals(headline)) {
                return r;
            }
        }
        return null;
    }

    // palauttaa halutun lukuvinkki-olion indeksin listassa, negatiivinen jos oliota ei löydy
    public int findIndex(String headline) {
        int index = -1;
        for (int i = 0; i < this.recommendations.size(); i++) {
            if (this.recommendations.get(i).getHeadline().equals(headline)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public ArrayList<ReadingRecommendationDAO> getAllRecommendations() {
        return this.recommendations;
    }
}
