package com.rovger.http;

import java.io.*;

/**
 * @Description: 通过构造curl命令，实现批量执行操作
 *
 * 在linux下，文件权限分为三种：可读权限(read)、可写权限(write)以及可执行权限(execute)，三者分别对应的字符为r、w和x；
 * 除此之外，文件权限身份也分三种：文件所有者（user）、文件所有者所在组（group）以及其他（others），这里所说的“其他”，就是指的非文件所有者及其所在组的用户。在上述查询结果中的第3个字段和第4个字段分别反映了该文件的所有者以及文件所有者所在组。
 * 现在再来说说如何通过每个文件第一个字段中的字符串来了解文件的权限。
 *
 * 整个10个字符分为以下4个部分：
 * ①第1个字符。描述文件\目录 类型，如果为‘-’则表示这是一个文件，如果为'd'表示这是一个目录；
 * ②第2~4个字符。这3个字符为一组，按照顺序描述了文件所有者对该文件的可读、可写和可执行权限；
 * ②第5~7个字符。这3个字符为一组，按照顺序描述了文件所有者所在组对该文件的可读、可写和可执行权限；
 * ②第8~10个字符。这3个字符为一组，按照顺序描述了其他身份对该文件的可读、可写和可执行权限；
 *
 * 修改文件权限使用chmod指令。该指令常用的有两种使用方式：
 * 1.chmod abc filename
 * 指令中的a、b、c分别表示一个数字，其中a对应文件所有者权限，b对应文件所有者所在组权限，c对应其他身份权限。
 * 对于a、b、c各自来讲，它们都是0~7的数字，对应r、w、x三个二进制位按序组成的二进制数，举个例子，如果是只可读，对应的二进制数就是“100”，也就是4；如果是可读可写不可执行，那么对应二进制数为“110”，也就是6……
 * 再举个最常见的chmod 777 xxxx指令，这里有3个7，但是每个7的含义是不同的。7的二进制形式为111，表示可读可写可执行，第1个7表示文件对于文件所有者来说可读可写可执行；第2个7表示文件对于文件所有者所在组来说可读可写可执行；第3个7表示文件对于其他身份的用户来说可读可写可执行。也就是说，通过chmod 777，文件就没有了读写执行权限限制了。
 * 如果我要将上述client.cpp文件权限改为“文件所有者可读可写可执行，其余身份只可读”，那么就可以使用如下指令：
 * chmod 744 client.cpp
 *
 * 2.chmod u/g/o/a    +/-    r/w/x   filename
 * 该指令除了chmod和filename之外，还有三个部分：
 * ①描述文件权限身份。u表示文件所有者、g表示文件所有者所在组、o表示其他用户、a表示三者全部。可以搭配使用，如ug表示文件所有者及其所在组；
 * ②指定权限配置行为。‘+’表示添加权限，‘-’表示删除权限；
 * ③权限类型。分别对于可读可写可执行。
 * 举个例子，通过chmod的第一种方式，我已经将client.cpp的权限改为“文件所有者可读可写可执行，其余身份只可读”，如果我现在想删除文件所有者的可执行权限(u -x)，增加文件所有者所在组和其他身份的可写和可执行权限(go +wx)，就可以使用如下指令：
 * chmod u-x,go+wx client.cpp
 *
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年10月27日 14:26
 */
public class BatchCurlWithCookie {

    static String path = "src/main/java/com/rovger/http/";

    // %s 为 app_id
    static String cookie = "curl 'http://cms.test.9nali.com/openapi-admin-site/inner/apps/open/%s' \\\n" +
            "  -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9' \\\n" +
            "  -H 'Accept-Language: zh-CN,zh;q=0.9,en;q=0.8' \\\n" +
            "  -H 'Connection: keep-alive' \\\n" +
            "  -H 'Cookie: JSESSIONID=679EFB3F0B755737D12A3A5C6BF73A82; _xmLog=h5&06ebe5f5-7497-4b56-817f-1cea6da576ba&2.3.8' \\\n" +
            "  -H 'Referer: http://cms.test.9nali.com/openapi-admin-site/inner/appsV2/show/3183' \\\n" +
            "  -H 'Upgrade-Insecure-Requests: 1' \\\n" +
            "  -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36' \\\n" +
            "  --compressed \\\n" +
            "  --insecure";

    public static void main(String[] args) throws IOException {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(path + "src_file"));
            writer = new BufferedWriter(new FileWriter(path + "des_file.sh"));
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String tmp = String.format(cookie, line);
                writer.write(tmp);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                reader.close();
            if (writer != null)
                writer.close();
        }

    }

}
