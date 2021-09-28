package com.shaoqi.exercise.DesignMode.ChainOfResponsibility.servlet;

import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/9/28 15:08
 * 过 req res;  req请求先过一再过二  res的请求先过二在过1
 */
public class servletMain {
    public static void main(String[] args) {
  Request request=new Request();
  request.str="request";
  Response response=new Response();
  response.str="response";

  FilterChain fc=new FilterChain();
  fc.add(new HTMLFilter()).add(new SensitiveFilter());

  fc.doFilter(request,response,fc);
        System.out.println(response.str);


    }
}


class Request{
String str;
}
class Response{
String str;
}

interface  Filter{
    boolean doFilter(Request req,Response res,FilterChain chain);
}


class HTMLFilter implements  Filter{

    @Override
    public boolean doFilter(Request req, Response res,FilterChain chain) {
        req.str=req.str.replaceAll("<","[").replaceAll(">","]");
        chain.doFilter(req,res,chain);
        res.str=res.str+"-HTMLFilter";
        return true;
    }
}

class SensitiveFilter implements  Filter{

    @Override
    public boolean doFilter(Request req, Response res,FilterChain chain) {
        req.str=req.str.replaceAll("996","995");
        chain.doFilter(req,res,chain);
        res.str=res.str+"-SensitiveFilter";
        return true;
    }
}

class FaceFilter implements  Filter{

    @Override
    public boolean doFilter(Request req, Response res,FilterChain chain) {
        return false;
    }
}

class UrlFilter implements  Filter{

    @Override
    public boolean doFilter(Request req, Response res,FilterChain chain) {
        return false;
    }
}


class FilterChain implements  Filter{
    List<Filter> filters=new ArrayList<>();
    int index=0;
    public FilterChain add(Filter f){
        filters.add(f);
        return this;
    }
    public boolean doFilter(Request req, Response res,FilterChain chain){
        if (index==filters.size()) return false;
        Filter f=filters.get(index);
        index ++;
            return f.doFilter(req,res,chain);

    }
}