package thread;

public class RunnableDownloaderDemo implements Runnable {

    private String url;
    private String name;

    public RunnableDownloaderDemo(String url, String name) {
        this.url = url;
        this.name = name;
    }

    @Override
    public void run() {
        WebDownloader wd = new WebDownloader();
        wd.download(url, name);
        System.out.println(name);
    }

    public static void main(String[] args) {
        String path = RunnableDownloaderDemo.class.getClassLoader().getResource("images/book").getPath();
        String prmlUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSZRcwGodXF3tYbd2sr0jOVANH0bE4LPRVA3qQ-yEQ5-SXBM-Gb";
        String mlappUrl = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQ0vd4m7QS9eTpg1AUMAzpvfPSF5xN42_EEiZnZ2oLYPQDBvAfX";
        String eslUrl = "https://weltbild.scene7.com/asset/vgwwb/vgw/the-elements-of-statistical-learning-105132013.jpg?$ads-min-zoom-v2$&wc80";
        RunnableDownloaderDemo rd1 = new RunnableDownloaderDemo(prmlUrl, path + "/prml.png");
        RunnableDownloaderDemo rd2 = new RunnableDownloaderDemo(mlappUrl, path + "/mlapp.png");
        RunnableDownloaderDemo rd3 = new RunnableDownloaderDemo(eslUrl, path + "/esl.png");
        new Thread(rd1).start();
        new Thread(rd2).start();
        new Thread(rd3).start();
    }

}
