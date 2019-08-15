/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {
    

    /**
     * Find tweets written by a particular user.
     * 
     * spec中输入的名字满足名字要求，所以我们不需要多考虑
     * return中因为是list结构，所以要求顺序一样
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        
        List<Tweet> userTweets = new ArrayList<>();
        int size = tweets.size();
        
        for (int i = 0; i < size; i++) {
            if( tweets.get(i).getAuthor() == username ) {
                userTweets.add( tweets.get(i) );              
            }
        }
        return userTweets;              
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * return时要求顺序和输入的顺序一样
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        
        List<Tweet> timespanTweets = new ArrayList<>();
        int size = tweets.size();
        
        for( int i = 0; i < size; i++ ) {                                   //after 和 before 函数都是不包括边界的，即相等不算在内，所以我特意加进去了等于，时间相等也算在内
            if( ((tweets.get(i).getTimestamp().isAfter(timespan.getStart())) && (tweets.get(i).getTimestamp().isBefore(timespan.getEnd()))) || tweets.get(i).getTimestamp().equals(timespan.getStart()) || tweets.get(i).getTimestamp().equals(timespan.getEnd()) ) {
                timespanTweets.add(tweets.get(i));
            }
        }
        return timespanTweets;        
    }

    /**
     * Find tweets that contain certain words.
     * 
     * words中没有空字符串，没有空格字符
     * 
     * words比较时时大小写不敏感的，包含一个或一个以上的关键词的推特都要被纳入进来
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. 
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when 
     *         represented as a sequence of nonempty words bounded by space characters 
     *         and the ends of the string) includes *at least one* of the words 
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        
        List<Tweet> keywordTweets = new ArrayList<>();
        int size = tweets.size();
        int n = words.size();
        String textString;
        String word;
              
        for(int i = 0; i < size; i++ ) {          
            textString = tweets.get(i).getText().toLowerCase();
            for(int j = 0; j < n; j++ ) {
                word = words.get(j).toLowerCase();
                if( textString.indexOf(word) != -1 ) {
                    keywordTweets.add(tweets.get(i) );
                    break;
                }              
            }
        }
        return keywordTweets;                          
    }      
    
}
