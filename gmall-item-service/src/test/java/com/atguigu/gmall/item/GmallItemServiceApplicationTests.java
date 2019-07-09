package com.atguigu.gmall.item;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.Random;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class GmallItemServiceApplicationTests {
 /*   @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    Random random = new Random();*/
    @Test
    public void contextLoads() throws SQLException {
        String url="jdbc:mysql://localhost:3306/bigdata?characterEncoding=UTF-8&rewriteBatchedStatements=true";
        Connection connection = DriverManager.getConnection(url,"root","123");
        //String sql="insert into dept(deptno,dname,loc) values(?,?,?)";
        String sql = "insert into emp(empno,ename,job,mgr,hiredate,sal,comm,deptno) values(?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 1; i <= 1000000; i++) {
            preparedStatement.setObject(1, i);
            preparedStatement.setObject(2, "汤姆" + i);
            preparedStatement.setObject(3, i + "SALESMAN");
            preparedStatement.setObject(4, i + 0001);
            preparedStatement.setObject(5, new Date());
            preparedStatement.setObject(6, i +10);
            preparedStatement.setObject(7, i + 200);
            Integer deptno = (int)(Math.random()*10+1);
            preparedStatement.setObject(8, deptno);

            preparedStatement.addBatch();
            if(i%1000==0){
                preparedStatement.executeBatch();
                preparedStatement.clearBatch();
            }
        }

    }
    @Test
    public void queryEmp() throws SQLException {
        String url="jdbc:mysql://localhost:3306/bigdata?characterEncoding=UTF-8&rewriteBatchedStatements=true";
        Connection connection = DriverManager.getConnection(url,"root","123");
        //String sql="insert into dept(deptno,dname,loc) values(?,?,?)";
        String sql = "select * from emp where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,1);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            System.out.println(resultSet.getString("ename"));
        }



    }

    }


