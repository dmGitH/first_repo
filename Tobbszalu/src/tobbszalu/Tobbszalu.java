
package tobbszalu;

//Treader osztály kiterjesztése



class Tasker extends Thread{

    int szama;
    long alszik;
    
    
    Tasker(int hanyadik,long sleep){
        
        szama=hanyadik;
        alszik=sleep;
        
    }

    public int getSzama() {
        return szama;
    }

    public void setSzama(int szama) {
        this.szama = szama;
    }

    public long getAlszik() {
        return alszik;
    }

    public void setAlszik(long alszik) {
        this.alszik = alszik;
    }
    
    @Override
    public void run(){
    
        for(int i=0;i<10;i++){
            System.out.println("mellékszál fut: "+szama+" "+i);
            try {
                System.out.println("mellékszál alszik"+szama+" ms "+alszik);
                Thread.sleep(alszik);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
        
    }
}

public class Tobbszalu {

    
    public static void main(String[] args) {
        
        Tasker[] run1=new Tasker[10];
        for(int i=0;i<10;i++){
            long a=(long)(Math.random()*1000)+500;
            
            run1[i]=new Tasker(i,a);
//            run1[i].setAlszik(a);
//            run1[i].setSzama(i);
            run1[i].start();
        }    
    }   
}
