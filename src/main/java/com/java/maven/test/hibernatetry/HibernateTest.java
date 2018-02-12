package com.java.maven.test.hibernatetry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HibernateTest {
    public static void main( String[] args )
    {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        try{
            //Write
            CrUser crUser = new CrUser();
            crUser.setCode("123");
            crUser.setName("hibernate demo");
            crUser.setCreatedTime(getCurrentTime());
            session.saveOrUpdate(crUser);//这里操作的是java对象

            Set<House> myHouses= new HashSet<House>();
            House myHouse=new House();
            myHouse.setAddress("Dong bu ling dong");
            myHouse.setUser(crUser);
            myHouse.setAera("80");
            session.saveOrUpdate(myHouse);//save first one
            myHouses.add(myHouse);

            House myHouse2=new House();
            myHouse2.setAddress("Po Bo Po Bo dong");
            myHouse2.setUser(crUser);
            myHouse2.setAera("90");
            session.saveOrUpdate(myHouse2);//save second one
            myHouses.add(myHouse2);

            crUser.setHouses(myHouses);
            session.saveOrUpdate(crUser);//这里操作的是java对象
            tx.commit();
            System.out.println("保存成功!");


            //read
            Query query =session.createQuery("from CrUser");
            List<CrUser> list = query.list();
            //使用forEach遍历集合
            for (CrUser user : list) {
                System.out.println(user.getId());
                System.out.println("code="+user.getCode());
                System.out.println("name="+user.getName());
                Set<House> houses = user.getHouses();
                for(House house:houses)
                System.out.println("houseId="+house.getId());
            }
        }catch(Exception e){
            e.printStackTrace();
            tx.rollback();
            System.out.println("保存失败!");
        }finally{
            HibernateUtil.closeSession();
        }
        System.exit(-1);

    }

    static String getCurrentTime(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddE HH:mm:ss");
        return sdf.format(d);
    }
}
