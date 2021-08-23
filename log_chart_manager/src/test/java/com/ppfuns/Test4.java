package com.ppfuns;

import com.ppfuns.report.entity.AppCountDay;
import org.junit.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Stack;


public class Test4 {
    private static Integer i=128;
    private static final Long l=2l;
    private double d=1000;
    private static final double dc=10000;
    private static final int a=100;
    private static final String str="hell";
    @Test
    public void t(){
        System.out.println(Double.MAX_VALUE);
        System.out.println(Double.MIN_VALUE);
        System.out.println(Long.MAX_VALUE);
        System.out.println(Long.MIN_VALUE);
    }
    @Test
    public void tt(){
      String s="12+1";
      if(1 <= s.length() && s.length() <= 3 * 105){
         char[] arr=s.trim().toCharArray();
          Stack stack = new Stack<String>();
          int i=0;
          int len = arr.length;
          char singal;
          String digit="";
          while(i<arr.length){
             if(!Character.isDigit(arr[i])){
                 if(arr[i]=='('){
                     stack.push(arr[i]);
                 }else if(arr[i]=='+' || arr[i]=='-'){
                     singal=arr[i];
                 }
                 if(!digit.equals(")")){
                     stack.push(digit);
                 }
                 digit="";
                 stack.push(arr[i]);
             }else{
                 digit=digit+arr[i];
             }
             i++;
          }
          if(!digit.equals("")){
              stack.push(digit);
          }
          System.out.println(stack);
          System.out.println(stack.pop());
      }
    }

    public void cal(char[] arr){
        Stack stack = new Stack<String>();
        int i=0;
        int res=0;
        int signal=0;
        while(i<arr.length){
            char cur=arr[i];
            if(Character.isDigit(cur)){
                String val = "";
                while(i<arr.length){
                    if(Character.isDigit(arr[i])){
                        val+=arr[i];
                        i++;
                    }else{
                        cur=arr[i];
                        break;
                    }
                }
                stack.push(val);
            }
            if(cur=='+'){
                signal=1;
            }
        }

    }
    @Test
    public void t1() throws NoSuchFieldException {
        Field tDate = AppCountDay.class.getField("tDate");
    }
}
