package xyz.ivyxjc.dubbo.demo;

public class ZkServer {
    public static void main(String[] args) {
        new EmbeddedZooKeeper(2183, false).start();
    }
}
