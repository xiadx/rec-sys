package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableDownloaderDemo implements Callable<Boolean> {

    private String url;
    private String name;

    public CallableDownloaderDemo(String url, String name) {
        this.url = url;
        this.name = name;
    }

    @Override
    public Boolean call() throws Exception {
        WebDownloader wd = new WebDownloader();
        wd.download(url, name);
        System.out.println(name);
        return true;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String path = CallableDownloaderDemo.class.getClassLoader().getResource("images/book").getPath();
        String prmlUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSZRcwGodXF3tYbd2sr0jOVANH0bE4LPRVA3qQ-yEQ5-SXBM-Gb";
        String mlappUrl = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQ0vd4m7QS9eTpg1AUMAzpvfPSF5xN42_EEiZnZ2oLYPQDBvAfX";
        String eslUrl = "https://weltbild.scene7.com/asset/vgwwb/vgw/the-elements-of-statistical-learning-105132013.jpg?$ads-min-zoom-v2$&wc80";
        CallableDownloaderDemo cd1 = new CallableDownloaderDemo(prmlUrl, path + "/prml.png");
        CallableDownloaderDemo cd2 = new CallableDownloaderDemo(mlappUrl, path + "/mlapp.png");
        CallableDownloaderDemo cd3 = new CallableDownloaderDemo(eslUrl, path + "/esl.png");
        ExecutorService es = Executors.newFixedThreadPool(3);
        Future<Boolean> f1 = es.submit(cd1);
        Future<Boolean> f2 = es.submit(cd2);
        Future<Boolean> f3 = es.submit(cd3);
        boolean r1 = f1.get();
        boolean r2 = f2.get();
        boolean r3 = f3.get();
        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);
        es.shutdownNow();
    }

}
