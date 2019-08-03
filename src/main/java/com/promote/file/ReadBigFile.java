package com.promote.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ReadBigFile {

    private static final Logger logger = LoggerFactory.getLogger(ReadBigFile.class);


    private void readFile() {
        //定义文件操作目录
        File read = new File("E:" + File.separator + "pay" + File.separator + "read.txt");
        try(FileInputStream in = new FileInputStream(read)) {
            //定义读写流，通道
            FileChannel fin = in.getChannel();
            //通过通道获取内存映射 声明内存映射
            MappedByteBuffer map = fin.map(FileChannel.MapMode.READ_ONLY, 0, read.length());
            //将存映射放入到变量中进行输出
            byte[] data = new byte[(int) read.length()];
            int foot = 0;
            while (map.hasRemaining()) {
                data[foot++] = map.get();
            }
            logger.info(new String(data));
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }
}
