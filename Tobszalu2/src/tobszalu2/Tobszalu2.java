package tobszalu2;

//Runable overrider
class Tasker implements Runnable {

    int szama;
    long alszik;

    Tasker(int hanyadik, long sleep) {

        szama = hanyadik;
        alszik = sleep;

    }

    @Override
    public void run() {
        System.out.println(szama + ". mellékszál indul-------------");
        for (int i = 0; i < 10; i++) {
            System.out.println(szama + ". mellékszál fut");
            byte[] p = new byte[100000000];
            for (int s = 0; s < 100000000; s++) {

                p[s] = (byte) s;
                p[s] = (byte) (p[s] ^ (byte) s + 1);

            }

            try {
                System.out.println(szama + ". mellékszál alszik ms: " + alszik);
                Thread.sleep(alszik);
            } catch (InterruptedException ex) {
                System.out.println(ex);
                System.out.println(szama + ". mellékszál kipurcant");
                Thread.interrupted();
            }
        }
        System.out.println(szama + ". mellékszál végzett***************");
    }
}

public class Tobszalu2 {

    public static void main(String[] args) {

        Thread[] run1 = new Thread[100];
        for (int i = 0; i < 100; i++) {
            long a = (long) (Math.random() * 1000) + 500;

            try {
                run1[i] = new Thread(new Tasker(i, a)); //osztályt adtam át
                run1[i].start();
            } catch (Exception e) {
                System.out.println(i + ". mellékszál indulási hiba");
            }
        }
    }

}
