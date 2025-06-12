package com.lx.framework.demo1.thread.controller;

import java.util.*;

public class MyRunable implements Runnable {

    private int count = 0;


    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (count < 100) {
                    System.out.println(Thread.currentThread().getName() + "-->" + count);
                    count++;
                } else {
                    break;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int mySqrt(int x) {
        int l = 0, r = x, ans = -1;
        int i = 1;
        while (l <= r) {
            System.out.println("第" + i + "次");
            int mid = l + (r - l) / 2;
            if ((long) mid * mid <= x) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
            i++;
        }
        return ans;
    }

    public static void main(String[] args) {
//        System.out.println(mySqrt(81));
//        char[][] board = {
//                {'a', 'a', 'a', 'a','a', 'a', 'a', 'a','a', 'a', 'a', 'a','a', 'a', 'a', 'a'},
//                {'a', 'a', 'a', 'a','a', 'a', 'a', 'a','a', 'a', 'a', 'a','a', 'a', 'a', 'a'},
//                {'a', 'a', 'a', 'a','a', 'a', 'a', 'a','a', 'a', 'a', 'a','a', 'a', 'a', 'a'},
//                {'a', 'a', 'a', 'a','a', 'a', 'a', 'a','a', 'a', 'a', 'a','a', 'a', 'a', 'a'}
//        };
//        String[] words = {"a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa", "aaaaaaa", "aaaaaaaa", "aaaaaaaaa", "aaaaaaaaaa"};
//        char[][] board = {
//                {'o', 'a', 'b', 'n'},
//                {'o', 't', 'a', 'e'},
//                {'a', 'h', 'k', 'r'},
//                {'a', 'f', 'l', 'v'}
//        };
//        String[] words = {"oa", "oaa"};
//        char[][] board = {
//                {'a', 'b'},
//                {'a', 'a'}
//        };
//        String[] words = {"aaaa"};
//        char[][] board = {
//                {'o','a','a','n'},
//                {'e','t','a','e'},
//                {'i','h','k','r'},
//                {'i','f','l','v'}
//        };
//        String[] words = {"oath","pea","eat","rain"};

//        char[][] board = {
//                {'a','b','c'},
//                {'a','e','d'},
//                {'a','f','g'}
//        };
//        String[] words = {"eaabcdgfa"};
//        System.out.println(findWords(board, words));

        Integer[] prices  = {7,1,5,3,4};
        Integer minPrice = Integer.MAX_VALUE;
        Integer maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            if(prices[i] < minPrice){
                minPrice = prices[i];
            }else if(prices[i] - minPrice > maxProfit){
                maxProfit = prices[i] - minPrice;
            }
        }

    }
    //输入：
    //输出：["eat","oath"]

    public static List<String> findWords(char[][] board, String[] words) {
        ArrayList<String> strings = new ArrayList<>();
        for (String word : words) {

            char c = word.charAt(0);
            List<HashMap<Integer, Integer>> list = new ArrayList<>();

            //获取所有和单词首字母相同的下标
            int yLength = board.length;
            int xLength = board[0].length;
            if (yLength * xLength < word.length()) {
                continue;
            }
            for (int y = 0; y < yLength; y++) {
                for (int x = 0; x < xLength; x++) {
                    //如果首字母相等
                    if (board[y][x] == c) {
                        HashMap<Integer, Integer> integerIntegerHashMap = new HashMap<>();
                        integerIntegerHashMap.put(x, y);
                        list.add(integerIntegerHashMap);
                    }
                }
            }
            if (list.isEmpty()) {
                continue;
            }
            boolean exist = exist(board, word, list, null, null, 1);
            if (exist) {
                strings.add(word);
            }
        }
        return strings;
    }

    public static boolean exist(char[][] board, String word, List<HashMap<Integer, Integer>> list, Integer prex, Integer prey, Integer wordIndex) {
        int length = word.length();
        if (wordIndex == length) {
            System.out.println("==========================到达最大长度!=========================");
            return true;
        }
        System.out.println("当前单词：" + word + "比较字母" +word.charAt(wordIndex)+",索引位置"+ wordIndex);
        int yLength = board.length;
        int xLength = board[0].length;

        List<HashMap<Integer, Integer>> list1 = new ArrayList<>();
        for (HashMap<Integer, Integer> integerIntegerHashMap : list) {
            Set<Map.Entry<Integer, Integer>> entries = integerIntegerHashMap.entrySet();
            Integer x = 0;
            Integer y = 0;
            System.out.println("上一个坐标：" + prex+","+prey);
            for (Map.Entry<Integer, Integer> integerIntegerEntry : entries) {
                x = integerIntegerEntry.getKey();
                y = integerIntegerEntry.getValue();
            }
            //左
            if (x - 1 >= 0) {
                if ((prex == null && prey == null) || (prex != null && prey != null && x - 1 != prex || y != prey)) {
                    char c1 = board[y][x - 1];
                    if (c1 == word.charAt(wordIndex)) {
                        System.out.println("下一个坐标："+(x - 1)+","+y);
                        HashMap<Integer, Integer> integerIntegerHashMap1 = new HashMap<>();
                        integerIntegerHashMap1.put(x - 1, y);
                        list1.add(integerIntegerHashMap1);
                        Integer newIndex = wordIndex + 1;
                        if (exist(board, word, list1, x, y, newIndex)) {
                            return true;
                        }
                    }
                }
            }
            //上
            if (y - 1 >= 0) {
                if ((prex == null && prey == null) || (prex != null && prey != null &&  x != prex || y - 1 != prey)) {
                    char c1 = board[y - 1][x];
                    if (c1 == word.charAt(wordIndex)) {
                        System.out.println("下一个坐标："+x+","+(y - 1));
                        HashMap<Integer, Integer> integerIntegerHashMap1 = new HashMap<>();
                        integerIntegerHashMap1.put(x, y - 1);
                        list1.add(integerIntegerHashMap1);
                        Integer newIndex = wordIndex + 1;
                        if (exist(board, word, list1, x, y, newIndex)) {
                            return true;
                        }
                    }
                }
            }
            //右
            if (x + 1 < xLength) {
                if ((prex == null && prey == null) || (prex != null && prey != null && x + 1 != prex || y != prey)) {
                    char c1 = board[y][x + 1];
                    if (c1 == word.charAt(wordIndex)) {
                        System.out.println("下一个坐标："+(x+1)+","+y);
                        HashMap<Integer, Integer> integerIntegerHashMap1 = new HashMap<>();
                        integerIntegerHashMap1.put(x + 1, y);
                        list1.add(integerIntegerHashMap1);
                        Integer newIndex = wordIndex + 1;
                        if (exist(board, word, list1, x, y, newIndex)) {
                            return true;
                        }
                    }
                }
            }
            //下
            if (y + 1 < yLength) {
                if ((prex == null && prey == null) || (prex != null && prey != null && x!= prex || y + 1  != prey)) {
                    char c1 = board[y + 1][x];
                    if (c1 == word.charAt(wordIndex)) {
                        System.out.println("下一个坐标："+x+","+(y+1));
                        HashMap<Integer, Integer> integerIntegerHashMap1 = new HashMap<>();
                        integerIntegerHashMap1.put(x, y + 1);
                        list1.add(integerIntegerHashMap1);
                        Integer newIndex = wordIndex + 1;
                        if (exist(board, word, list1, x, y, newIndex)) {
                            return true;
                        }
                    }
                }
            }
        }
        System.out.println("本次链路匹配失败！");
        return false;
    }
}
