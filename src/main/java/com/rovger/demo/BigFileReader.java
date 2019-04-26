package com.rovger.demo;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description: https://github.com/yongzhidai/ToolClass/blob/master/src/main/java/cn/dyz/tools/file/BigFileReader.java
 * @Author weijlu
 * @Date 2019/4/25 10:30
 */
public class BigFileReader {
    private long fileLength;
    private IHandle handle;
    private String charset;
    private int bufferSize;
    private int threadSize;
    private RandomAccessFile accessFile;
    private CyclicBarrier cyclic;
    private Set<StartEndPair> startEndPairSet;
    private AtomicLong counter = new AtomicLong(0);
    private ExecutorService executorService;

    public BigFileReader(File file, IHandle handle, String charset, int bufferSize, int threadSize) {
        this.fileLength = file.length();
        this.handle = handle;
        this.charset = charset;
        this.bufferSize = bufferSize;
        this.threadSize = threadSize;
        this.executorService = Executors.newFixedThreadPool(threadSize);
        this.startEndPairSet = new HashSet<>();
        try {
            this.accessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        BigFileReader.Builder builder = new BigFileReader.Builder("C:\\Users\\weijlu\\Desktop\\test.txt", new IHandle() {
            @Override
            public void handle(String line) {
                System.out.println(line);
            }
        });
        builder.withThreadSize(10)
                .withCharset("gbk")
                .withBufferSize(1024 * 1024);
        BigFileReader reader = builder.build();
        reader.start();
    }

    public void start() throws IOException {
        long perSize = fileLength / threadSize;
        System.out.println("fileLength:" + fileLength + ", perSize:" + perSize);
        calculateStartEnd(0, perSize);

        long startTime = System.currentTimeMillis();
        cyclic = new CyclicBarrier(startEndPairSet.size(), new Runnable() {
            @Override
            public void run() {
                System.out.println("Use time: " + (System.currentTimeMillis() - startTime));
                System.out.println("All lines: " + counter.get());
            }
        });
        for (StartEndPair pair : startEndPairSet) {
            System.out.println("分配分片: " + pair);
            this.executorService.execute(new SliceReaderTask(pair));
        }
        this.executorService.shutdown();
        try {
            this.executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void calculateStartEnd(long start, long perSize) throws IOException {
        if (start > fileLength - 1) return;
        StartEndPair pair = new StartEndPair();
        pair.setStart(start);
        long endPosition = start + perSize - 1;
        if (endPosition >= fileLength - 1) {
            pair.setEnd(fileLength - 1);
            startEndPairSet.add(pair);
            return;
        }
        accessFile.seek(endPosition);
        /*byte tmp = (byte) accessFile.read();
        while (tmp != '\n' && tmp != '\r') {
            endPosition++;
            if (endPosition >= fileLength - 1) {
                endPosition = fileLength - 1;
                break;
            }
            accessFile.seek(endPosition);
            tmp = (byte) accessFile.read();
        }*/
        pair.setEnd(endPosition);
        startEndPairSet.add(pair);

        calculateStartEnd(endPosition + 1, perSize);
    }

    public void shutdown() {
        try {
            this.accessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.executorService.shutdown();
    }

    private void handle(byte[] bytes) throws UnsupportedEncodingException {
        String line = null;
        if (this.charset == null) {
            line = new String(bytes);
        } else {
            line = new String(bytes, charset);
        }
        if (line != null && !"".equals(line)) {
            this.handle.handle(line);
            counter.incrementAndGet();
        }
    }

    class SliceReaderTask implements Runnable {
        private long start;
        private long sliceSize;
        private byte[] readBuff;

        public SliceReaderTask(StartEndPair pair) {
            this.start = pair.getStart();
            this.sliceSize = pair.getEnd() - pair.getStart() + 1;
            this.readBuff = new byte[bufferSize];
        }

        @Override
        public void run() {
            try {
                MappedByteBuffer mapBuffer = accessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, start, this.sliceSize);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                for (int offset = 0; offset < sliceSize; offset += bufferSize) {
                    int readLength;
                    if (offset + bufferSize <= sliceSize) {
                        readLength = bufferSize;
                    } else {
                        readLength = (int) (sliceSize - offset);
                    }
                    mapBuffer.get(readBuff, 0, readLength);
                    for (int i = 0; i < readLength; i++) {
                        byte tmp = readBuff[i];
                        if (tmp == '\n' || tmp == '\r') {
                            handle(bos.toByteArray());
                            bos.reset();
                        } else {
                            bos.write(tmp);
                        }
                    }
                }
                if (bos.size() > 0) {
                    handle(bos.toByteArray());
                }
                cyclic.await();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    interface IHandle {
        public void handle(String line);
    }

    public static class Builder {
        private int threadSize = 1;
        private String charset = null;
        private int bufferSize = 1024 * 1024;
        private IHandle handle;
        private File file;

        public Builder(String file, IHandle handle) {
            this.file = new File(file);
            if (!this.file.exists())
                throw new IllegalArgumentException("文件不存在！");
            this.handle = handle;
        }

        public Builder withThreadSize(int size) {
            this.threadSize = size;
            return this;
        }

        public Builder withCharset(String charset) {
            this.charset = charset;
            return this;
        }

        public Builder withBufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
            return this;
        }

        public BigFileReader build() {
            return new BigFileReader(this.file, this.handle, this.charset, this.bufferSize, this.threadSize);
        }
    }

    class StartEndPair {
        private long start;
        private long end;

        @Override
        public String toString() {
            return "start=" + start + ", end=" + end;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (int) (end ^ (end >>> 32));
            result = prime * result + (int) (start ^ (start >>> 32));
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (this.getClass() != obj.getClass()) return false;
            StartEndPair other = (StartEndPair) obj;
            if (start != other.getStart()) return false;
            if (start != other.getEnd()) return false;
            return true;
        }

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public long getEnd() {
            return end;
        }

        public void setEnd(long end) {
            this.end = end;
        }
    }

}