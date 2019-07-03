package com.example.GraphEmbedded.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GraphMap {
    private String fromnode;
    private String tonode;
    private long tnt;
    private long version;
    private String type;

    //dates are in strings , convert them into neo4j date format while using them for comparision
    private String start_date;
    private String end_date;


    public String getFromnode() {
        return fromnode;
    }

    public void setFromnode(String fromnode) {
        this.fromnode = fromnode;
    }

    public String getTonode() {
        return tonode;
    }

    public void setTonode(String tonode) {
        this.tonode = tonode;
    }

    public long getTnt() {
        return tnt;
    }

    public void setTnt(long tnt) {
        this.tnt = tnt;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GraphMap() {
    }

    public GraphMap(String fromnode, String tonode, long tnt, long version, String start_date, String end_date, String type) {
        this.fromnode = fromnode;
        this.tonode = tonode;
        this.tnt = tnt;
        this.version = version;
        this.start_date = start_date;
        this.end_date = end_date;
        this.type = type;
    }

    //to json string


    public String toJson() {
        //String str="GraphMap[fromNode="+map.getFromNode()+","+"toNode="+map.getToNode()+",tnt="+map.getTnt()+",version="+map.getVersion()+",start_date="+map.getStart_date()+",end_date="+map.getEnd_date()+",type="+map.getType()+"]";
        ObjectMapper obj=new ObjectMapper();
        String jsonStr =null;
        try{
            jsonStr=obj.writeValueAsString(this);
        }catch (IOException e){
            e.printStackTrace();
        }
        return jsonStr;
    }
    //check if it is map type or not;
    public boolean check(){
        if((this.getType()!=null)&(this.getEnd_date()!=null)&(this.getStart_date()!=null)&(this.getTonode()!=null)&(this.getFromnode()!=null))
            return true;
        else
             return false;
    }

}
