/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        
        Instant start = tweets.get(0).getTimestamp();
        Instant end   = tweets.get(0).getTimestamp();
        
        for ( int i = 1; i < tweets.size(); i++ ) {
           if ( tweets.get(i).getTimestamp().isBefore(start)) {
             start = tweets.get(i).getTimestamp();
           }
           if ( tweets.get(i).getTimestamp().isAfter(end) ) {
             end = tweets.get(i).getTimestamp();
           }
        }        
        Timespan ret = new Timespan(start, end);        
        return ret;      
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        
        Set<String> mentionedUsers = new HashSet<>();
        
        for (Tweet oneTweet : tweets) {                              //遍历每一个推特                    
           char[] textChar = oneTweet.getText().toCharArray();
           int length = textChar.length; 
           int currentPoint = 0;                                     //cp是读到#号时数组所在的位置， p是#号后面读走一段字母后，那时cp应当处在的位置
           int point = 0;
           
           while(currentPoint <length) {                             //遍历一条推特的每一个字母
               StringBuffer nameBuffer = new StringBuffer(); 
               
               if( (textChar[currentPoint] == '@' && currentPoint == 0) 
                       || ( (currentPoint>0) && (textChar[currentPoint] == '@') && (textChar[currentPoint-1] == ' ') )  ) {  //出现@
                   
                   point = currentPoint;                            //记录出现@的位置                   
                   for(int i = currentPoint+1; i<length; i++) {     //遍历当前@后面的字母

                       point++;                                     //记录从此@后面读字母时，currentpoint偏移的位置，到时候用来更新cp                       
                                                                    //读到合法字符，记录；  读到空格，截止  ； 读到非法字符，清空之前的buffer
                       if( (textChar[i] >= 'A' && textChar[i] <= 'Z') || (textChar[i] >= 'a' && textChar[i] <= 'z') 
                               || (textChar[i] >= '0' && textChar[i] <= '9') || (textChar[i] == '-') || (textChar[i] == '_') ) {
                           
                           nameBuffer.append(textChar[i]);
                       } 
                       else if (textChar[i] == ' ') {
                          break;
                       }
                       else {
                           nameBuffer = new StringBuffer();         //delete all characters
                           break;
                       }
                   }
                   if(nameBuffer.length() > 0) {                     //如果读到他@了某个人，就把这一对加入到图中
                       mentionedUsers.add(nameBuffer.toString());    //set不会加入重复字符串   
                   }
                   currentPoint = point;                             //更新cp的指针位置
               }
               currentPoint++;
           }          
                            
        }                                                 
        return mentionedUsers;            
    }
    
}
