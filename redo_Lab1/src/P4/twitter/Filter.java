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
     * spec�������������������Ҫ���������ǲ���Ҫ�࿼��
     * return����Ϊ��list�ṹ������Ҫ��˳��һ��
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
     * returnʱҪ��˳��������˳��һ��
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
        
        for( int i = 0; i < size; i++ ) {                                   //after �� before �������ǲ������߽�ģ�����Ȳ������ڣ�����������ӽ�ȥ�˵��ڣ�ʱ�����Ҳ������
            if( ((tweets.get(i).getTimestamp().isAfter(timespan.getStart())) && (tweets.get(i).getTimestamp().isBefore(timespan.getEnd()))) || tweets.get(i).getTimestamp().equals(timespan.getStart()) || tweets.get(i).getTimestamp().equals(timespan.getEnd()) ) {
                timespanTweets.add(tweets.get(i));
            }
        }
        return timespanTweets;        
    }

    /**
     * Find tweets that contain certain words.
     * 
     * words��û�п��ַ�����û�пո��ַ�
     * 
     * words�Ƚ�ʱʱ��Сд�����еģ�����һ����һ�����ϵĹؼ��ʵ����ض�Ҫ���������
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
