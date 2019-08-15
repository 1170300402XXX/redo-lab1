/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * what i have know: 名字是大小写无关的，关系图用map来存储名字  a@b,则a follow b ，b在以a为key的map中
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     *  
     * *没有follow的人，是不会出现在map中的key中的*
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        
        Map<String, Set<String>> networkMap = new HashMap<>();
        int size = tweets.size();
        
        for(int i = 0; i < size; i++) {
            Tweet oneTweet = tweets.get(i);            
            Set<String> mentionedUser = Extract.getMentionedUsers( Arrays.asList(oneTweet) );  //单独分析每一篇推特，把每一篇推特@的人找出来
            
                                                                                              //System.out.println(mentionedUser.size());  // p1.2：分析测试案例，第一条推特@了4个人，这里却显示一个人，说明上面这个函数有问题，问题位置定位到p1.3           
            //没有follow的人不在key中
            if(  ! mentionedUser.isEmpty() ) {
                if( networkMap.containsKey( oneTweet.getAuthor() ) ) {
                    mentionedUser.addAll( networkMap.get(oneTweet.getAuthor()) );
                    networkMap.put(oneTweet.getAuthor(), mentionedUser );
                }else {
                    networkMap.put(oneTweet.getAuthor(), mentionedUser );                   
                }               
            }                     
        } 
        
        //把#共同话题的follow加上去
        Map<String, Set<String>>  topicMap = SocialNetwork.getCommonTopic(tweets);
        for(String keyString : topicMap.keySet()) {            
            if(networkMap.containsKey(keyString)) {
                networkMap.get(keyString).addAll(topicMap.get(keyString));
                                
            }else {
                networkMap.put(keyString, topicMap.get(keyString));
            }
        }
        System.out.println(networkMap);
        return networkMap;
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        
        Map<String , Integer > rangeMap = new HashMap<>();       
        
        for(String key : followsGraph.keySet() ) {
            if(!rangeMap.containsKey(key)) {
                rangeMap.put(key, 0);
            }
    
            for( String follower : followsGraph.get(key) ) {
                if( rangeMap.containsKey(follower) ) {
                    rangeMap.put(follower, rangeMap.get(follower)+1 );                   
                }else {
                    rangeMap.put(follower, 1);
                }
            }
        }
        //System.out.println(rangeMap);
        
        String[] userNameStrings = new String[rangeMap.size()];
        rangeMap.keySet().toArray(userNameStrings);
        int size = userNameStrings.length;
        
        /*冒泡排序*/
        for(int i = 0; i < size-1; i++) {
            for(int j = i+1; j < size; j++ ) {
                if( rangeMap.get(userNameStrings[i]) < rangeMap.get(userNameStrings[j]) ) {
                    String tempString = userNameStrings[i];
                    userNameStrings[i] = userNameStrings[j];
                    userNameStrings[j] = tempString;
                }               
            }
        }
        
        List<String> influenceList = Arrays.asList(userNameStrings);
        
        return influenceList;
                    
    }
    
    
    
    /**
     * To get the follow graph which is get by the common topic that tweets talk about
     *  
     * @param tweets  a list of tweet
     * 
     * @return a map that tell all the following relationships
     */
    public static Map<String, Set<String>> getCommonTopic(List<Tweet> tweets){
        
        Map<String, Set<String>> commonTopicFollow = new HashMap<>();  //key: person  value: a group of people that person follows
        
        Map<String, Set<String>> commonTopicUsers = new HashMap<>();  // key: topic   value:people
        
        for(Tweet oneTweet : tweets) {
            char[] textArray = oneTweet.getText().toCharArray();
            int length = textArray.length;
            int currentPoint = 0;                                       //cp是读到#号时数组所在的位置， p是#号后面读走一段字母后，那时cp应当处在的位置
            int point = 0;
            
            //遍历一句话的每一个字母
            while(currentPoint <length) {
                StringBuffer topicBuffer = new StringBuffer();
                              
                if( (textArray[currentPoint] == '#' && currentPoint == 0) 
                        || ((currentPoint > 0) && (textArray[currentPoint] == '#') && (textArray[currentPoint-1] == ' ')) ) {    //出现#号，就遍历后面的字母
                  
                    point = currentPoint;                              //记录发现#号的位置
                    
                    for(int i = currentPoint +1; i < length; i++) {   //遍历#号后面的字母
                        
                        point++;                                      //记录此次出现#号后面移动到的位置
                        
                        //读到合法字符，记录；  读到空格，截止  ； 读到其他字符，清空buffer
                        if( (textArray[i] >= 'A' && textArray[i] <= 'Z') || (textArray[i] >= 'a' && textArray[i] <= 'z') 
                                || (textArray[i] >= '0' && textArray[i] <= '9') || (textArray[i] == '-') || (textArray[i] == '_') ) {
                          
                            topicBuffer.append(textArray[i]);
                        }else if(textArray[i] == ' ' ){
                            break;
                        }else {
                            topicBuffer = new StringBuffer();
                            break;
                        }                       
                    }                      
                    if(topicBuffer.length() > 0 ) {                    //把在这句话中找到到的话题和对应作者的名字记录到图中
                        String topicString = topicBuffer.toString();  //记录下话题名字
                        
                        System.out.println(topicString);
                        if( commonTopicUsers.containsKey(topicString) ) {
                            commonTopicUsers.get(topicString).add(oneTweet.getAuthor());
                        }else {
                            commonTopicUsers.put(topicString, new HashSet<>(Arrays.asList(oneTweet.getAuthor())));
                        }                       
                    }
                    currentPoint = point;                           //更新当前数组的指针位置                       
                }
                currentPoint++;                                     //指针右移                      
            }                                                            
        }     
        //读完所有推特后，得到一个key为话题，value为讨论该话题的一群人的map，下面处理此map
        for(String key : commonTopicUsers.keySet()) {               //遍历每一个话题
            
            for(String person : commonTopicUsers.get(key)) {        //遍历一个话题对应的一组人中的每一个人
                Set<String> followSet = new HashSet<>(commonTopicUsers.get(key));
                followSet.remove(person);
                
                if( !followSet.isEmpty()) {
                    if(commonTopicFollow.containsKey(person)) {
                        commonTopicFollow.get(person).addAll(followSet);
                    }else {
                        commonTopicFollow.put(person, followSet);
                    }
                }
            }
        }
        return commonTopicFollow;
    }

}
