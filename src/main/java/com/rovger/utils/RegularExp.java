package com.rovger.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author weijlu
 * @version 1.0
 * @description 正则表达式测试
 * @date 2022/12/20 18:48:01
 */
public class RegularExp {

    private static List<String> regExs = new ArrayList<>();
    static {
        // xxxx【内容简介】/内容简介?/简介/——内容简介——/【内容介绍】/尽在喜马拉雅APP】/【作品简介】/专辑简介：/【剧情简介】/....
        regExs.add(".+(?>(【内容简介】)|(【内容介绍】)|(【故事简介】：*)|(简介\\?)|(【作品简介】)|(作品简介\\?)|(节目简介\\?)|(专辑简介：)|(尽在喜马拉雅APP】)|(【剧情简介】)|((—|-)+内容简介(—|-)+))");
        // ——（下载喜马拉雅APP，收听更多有声小说）
        regExs.add("(—|-)+（下载喜马拉雅APP，收听更多有声小说）");
    }

    public static void main(String[] args) {

        String intro = "【强烈推荐】长篇都市言情多人小说剧，等你来听！【作者简介】原著：花木长篇都市言情人气作者，代表作（99亿闪婚：豪门总裁忙）【主播简介】主要播音：排名不分先后女播：往昔-旁白、宋倾城、陆悠悠十六-辛悦牡丹-季母火柴-宋母柒天小呆-陆晴波比-季玲夏柳君-宋小妹、陆玉俇俇-应雪儿肜受-余笑沁沁-林欣、顾水簌簌轻扬-纳兰雨三吃-宋麻烦男播：君颜讲故事-季正庭、陆枭百里屠屠-陆佑霖、苏宇、陆亭川芸飞-周少景裂神-前期高鑫故城-后期高鑫、陆白九攻子-白敬亭、顾嘉、顾泽司徒-苏白离【后期简介】后期：片花后期：慕歌 正剧后期：千雪———【内容介绍 专辑简介：【剧情内容简介?【故事简介】她二十年一次脚下失足，【内容简介】撞了他的豪车，留下签名和号码，走的不带一片云彩。不仅如此，还顺便牵走他‘未婚妻’的名义…… 隔日，全球惊爆季氏总裁惊现未婚妻…… 所以，她宋倾城这次是闯大祸了？！ 不但如此，某男还给她直接甩了一份闪婚协议书。 一年后，宋倾城将离婚协议拍在某男面前！ “季先生，请签字！” “我有没有告诉过你我不会写三个字。” “哪三个字？” “季正霆。”——（下载喜马拉雅APP，收听更多有声小说）";
        System.out.println(contentIntro(intro));


        /*String url = "https://imgopen.xmcdn.com/group58/M06/08/9B/wKgLglzTyTjiOy0oAAcOTv16ohg815.jpg!op_type=3&columns=640&rows=640";
        String url2 = "https://imgopen.xmcdn.com/group58/M06/08/9B/wKgLglzTyTjiOy0oAAcOTv16ohg815.jpg";
        System.out.println(toGreyUrl(url));
        System.out.println(toGreyUrl(url2));*/
    }

    public static String contentIntro(String intro) {
        if (!StringUtils.isBlank(intro)) {
//            regExs.stream().forEach(reg -> intro.replaceFirst(reg, ""));
            Iterator<String> it = regExs.iterator();
            while (it.hasNext()) {
                intro = intro.replaceFirst(it.next(), "");
            }
        }
        return intro;
    }

    public static String toGreyUrl(String url) {
        if (url.indexOf("!") > 0) {
            return url.replaceAll("op_type=\\d+", "op_type=12") + "&monochrome=1";
        } else {
            return url + "!op_type=12&monochrome=1";
        }
    }

}
