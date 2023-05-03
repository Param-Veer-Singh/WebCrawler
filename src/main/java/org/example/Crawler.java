package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashSet;

public class Crawler {

    HashSet<String> urlSet;
    int maxDepth = 2;
    Crawler(){
        urlSet = new HashSet<>();
    }
    public void getPageTextAndLinks(String url, int depth){
        if(urlSet.contains(url))return;
        if(depth > maxDepth)return;
        urlSet.add(url);
        depth++;
        try{
            Document document = Jsoup.connect(url).timeout(5000).get();
            Indexer indexer = new Indexer(document, url);
            System.out.println(document.title());
            Elements avlLinkOnPage = document.select("a[href]");
            for(Element currentLink : avlLinkOnPage){
                getPageTextAndLinks(currentLink.attr("abs:href"), depth);
            }
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }

    }
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        //crawler.getPageTextAndLinks("https://javatpoint.com",1);
        crawler.getPageTextAndLinks("https://practice.geeksforgeeks.org",1);
        crawler.getPageTextAndLinks("https://leetcode.com/problemset/all",1);
    }
}