package com.promote;

import java.util.Date;
import java.util.Scanner;

public class TestMain {

    public static void main(String[] args) {

        long time1 = System.currentTimeMillis();


        int index = 0;
        while (true){
            index++;
            long time2 = System.currentTimeMillis();
            if (time2-time1==1000)
                break;
        }
        System.out.println(index+ " index ");

        String a = "你好 啊  忠诚";
        String b = "你好 啊  忠诚";
        String aa = new String("1");
        String bb = new String("1");

        System.out.println(System.identityHashCode(aa));
        System.out.println(System.identityHashCode(bb));
        System.out.println(a == b);
        System.out.println(a.equals(b));
        System.out.println(aa == bb);
        System.out.println(aa.equals(bb));
        try {
            System.out.println("try");
            //return;
            throw new Exception("try");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally");
        }
        //定义数组
        int[] src;
        src = new int[10];
        //创建scanner对象
        Scanner s = new Scanner(System.in);
        //定义和
        int k = 0;
        //循环输入
        for (int i = 0; i < src.length; i++) {
            int j = i + 1;
            System.out.print("请输入第" + j + "个数字：");
            src[i] = s.nextInt();
        }
        //对比值
        int max = src[0];
        int min = src[0];
        //循环找到最大值
        for (int z = 0; z < src.length; z++) {
            if (src[z] > max) {
                max = src[z];
            }
            //循环找到最小值
            if (src[z] < min) {
                min = src[z];
            }
        }
        //输出
        System.out.println("最大值为：" + max);
        System.out.println("最小值为：" + min);

        //冒泡排序
        for(int i=0;i<src.length-1;i++){
            for(int j=0;j<src.length-1-i;j++){
                if(src[j]<src[j+1]){
                    int temp=src[j];
                    src[j]=src[j+1];
                    src[j+1]=temp;
                }
            }
        }
        //循环输出冒泡排序之后的数组
        System.out.println("排序之后的数据是：");
        for(int i=0;i<src.length;i++){
            System.out.print(src[i]+"  ");
        }
    }
}
