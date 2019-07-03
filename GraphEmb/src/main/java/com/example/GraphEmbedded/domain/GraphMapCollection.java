package com.example.GraphEmbedded.domain;

import java.util.ArrayList;

public class GraphMapCollection {
    ArrayList<GraphMap> items;

    public ArrayList<GraphMap> getItems() {
        return items;
    }

    public void setItems(ArrayList<GraphMap> items) {
        this.items = items;
    }

    public GraphMapCollection() {
    }

    public GraphMapCollection(ArrayList<GraphMap> items) {
        this.items = items;
    }
}
