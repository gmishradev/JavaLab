package com.test;


// deadlock - thread dump to check

// command line tool -> jcmd  73234 Thread.print
//-> jstack 73234
// here 73234 is process id.




public class Deadlock {

    public static void main(String[] args) {


        Object gkm =  new Object();
        Object pkm =  new Object();

        Runnable  govind= new Runnable() {
            @Override
            public void run() {
              synchronized ( gkm)
              {
                  System.out.println("govind got the gkm lock");

                  synchronized ( pkm){
                      System.out.println(" govind got the pkm lock");
                  }
              }


            }
        };


        Runnable  puja= new Runnable() {
            @Override
            public void run() {
                synchronized ( pkm)
                {
                    System.out.println("puja got the pkm lock");

                    synchronized ( gkm){
                        System.out.println(" puja got the gkm lock");
                    }
                }


            }
        };


        Thread t1 = new Thread(govind);
        t1.start();

        Thread t2 = new Thread(puja);
        t2.start();




    }
}
