package com.java.maven.test.hibernatetry;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateTest {
    public static void main( String[] args )
    {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        try{
            CrUser crUser = new CrUser();
            crUser.setCode("123");
            crUser.setName("hibernate demo");
            session.save(crUser);//这里操作的是java对象
            tx.commit();
            System.out.println("保存成功!");
        }catch(Exception e){
            e.printStackTrace();
            tx.rollback();
            System.out.println("保存失败!");
        }finally{
            HibernateUtil.closeSession();
        }


    }
}
