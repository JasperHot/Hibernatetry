package com.java.maven.test.hibernatetry;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateTest2 {
    private static Object LOCK = new Object();
    public static void main( String[] args ) throws InterruptedException {
        final CrUser crUser = new CrUser();
        class IntLock {int i=0;void setI(int i){this.i=i;} int getI(){return i;}}
        final IntLock lock=new IntLock();
        System.out.println("House:"+crUser.getHouses().size());
        Thread threadA=new Thread(new Runnable() {
            //@Override
            public void run() {
                synchronized (LOCK) {

                    System.out.println("Thread-1");
                    Session session = HibernateUtil.getSession();
                    Transaction tx = session.beginTransaction();
                    try {
                        //Write
                        crUser.setCode("316");
                        crUser.setName("hibernate 2");
                        crUser.setCreatedTime(HibernateTest.getCurrentTime());
                        House myHouse = new House();
                        myHouse.setAddress("Jin Tian Ni Hao");
                        myHouse.setAera("39");
                        myHouse.setUser(crUser);
                        crUser.getHouses().add(myHouse);
                        session.save(crUser);//这里操作的是java对象
                        tx.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                        tx.rollback();
                        System.out.println("save failed!");
                    } finally {
                        HibernateUtil.closeSession();
                        System.out.println("Thread-1 end");
                        lock.setI(1);
                    }
                }
            }
        },"Thread-1");


        Thread threadB=new Thread(new Runnable() {
            //@Override
            public void run() {
                synchronized (LOCK) {
                    System.out.println("Thread-2");
                    Session session = HibernateUtil.getSession();
                    Transaction tx = session.beginTransaction();
                    try {
                        //Read
                        CrUser crUser2 = (CrUser) session.get(CrUser.class, crUser.getId());
                        System.out.println("Thread-2 userId: " + crUser2.getId());
                        if (crUser2 != null) {
                            System.out.println("Thread-2 house: " + crUser2.getHouses().size());
                            for (House house : crUser2.getHouses()) {
                                System.out.println(" " + house.getId());
                                System.out.println(" " + house.getAddress());
                                System.out.println(" " + house.getAera());
                                System.out.println(" " + house.getUser().getCreatedTime());
                                System.out.println("  ");
                                House house2 = (House) session.get(House.class, house.getId());
                                if(house2!=null) {
                                    System.out.println(" " + house2.getId());
                                    System.out.println(" " + house2.getAddress());
                                    System.out.println(" " + house2.getAera());
                                    System.out.println(" " + house2.getUser().getCreatedTime());
                                    //house2.setAddress("xxxx");
                                }
                                session.save(house2);
                                tx.commit();
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        tx.rollback();
                        System.out.println("read failed!");
                    } finally {
                        HibernateUtil.closeSession();
                        System.out.println("Thread-2 end");
                        lock.setI(2);
                    }
                }
            }
        },"Thread-2");

        threadA.start();
        threadB.start();
        while(true){
            Thread.sleep(1000);
            if(lock.getI()==2) {
                System.out.println("Main userId: " + crUser.getId());
                System.out.println("Main House:" + crUser.getHouses().size());
                System.exit(-1);
            }
        }


    }

}
