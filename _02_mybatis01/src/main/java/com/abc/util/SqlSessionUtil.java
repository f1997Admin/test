package com.abc.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.annotation.Resource;
import java.io.InputStream;

public class SqlSessionUtil {
    private  static  SqlSessionFactory factory=null;
    String name;
    private SqlSessionUtil(){}
    static {
        String config="/mybatis.xml";
        InputStream in= Resource.class.getResourceAsStream(config);
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        factory=builder.build(in);


    }
    private static  ThreadLocal<SqlSession> t=new ThreadLocal<>();
    public static SqlSession getSession(){
        SqlSession session=t.get();
        if (session == null) {
            session=factory.openSession();
            t.set(session);
        }
        return session;
    }
    public static void myClose(SqlSession session){
        if (session !=null) {
            session.close();
            t.remove();
        }
    }

}
