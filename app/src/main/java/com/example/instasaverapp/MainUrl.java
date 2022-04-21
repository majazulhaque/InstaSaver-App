package com.example.instasaverapp;

public class MainUrl {
    public MainUrl(MainReel graphql) {
        this.graphql = graphql;
    }

    private MainReel graphql;


    public MainReel getGraphql() {
        return graphql;
    }

    public void setGraphql(MainReel graphql) {
        this.graphql = graphql;
    }
}
