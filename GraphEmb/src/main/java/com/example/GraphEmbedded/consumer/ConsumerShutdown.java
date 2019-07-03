package com.example.GraphEmbedded.consumer;

public class ConsumerShutdown {
    private static boolean shutdown;

    public ConsumerShutdown() {
    }

    public static boolean isShutdown() {
        return shutdown;
    }

    public static void setShutdown() {
        shutdown = true;
    }

    public static void main(String[] args) {
        ConsumerShutdown.setShutdown();

    }
}


