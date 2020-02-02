package thread;

public class ProducerConsumerDemoI {

    public static void main(String[] args) {
        SynchronizedContainer container = new SynchronizedContainer();
        new Producer(container).start();
        new Consumer(container).start();
    }

}

class Producer extends Thread {

    SynchronizedContainer container;

    public Producer(SynchronizedContainer container) {
        this.container = container;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("producer --> " + i + " --> product");
            container.push(new Product(i));
        }
    }

}

class Consumer extends Thread {

    SynchronizedContainer container;

    public Consumer(SynchronizedContainer container) {
        this.container = container;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("consumer --> " + container.pop().id + " --> product");
        }
    }

}

class SynchronizedContainer {

    Product[] ps = new Product[10];
    int count = 0;

    public synchronized void push(Product p) {
        if (count == ps.length) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ps[count] = p;
        count++;
        this.notifyAll();
    }

    public synchronized Product pop() {
        if (count == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Product p = ps[--count];
        this.notifyAll();
        return p;
    }

}

class Product {

    int id;

    public Product(int id) {
        this.id = id;
    }

}
