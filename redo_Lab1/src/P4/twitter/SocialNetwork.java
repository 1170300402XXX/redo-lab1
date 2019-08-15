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
 * what i have know: �����Ǵ�Сд�޹صģ���ϵͼ��map���洢����  a@b,��a follow b ��b����aΪkey��map��
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
     * *û��follow���ˣ��ǲ��������map�е�key�е�*
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
            Set<String> mentionedUser = Extract.getMentionedUsers( Arrays.asList(oneTweet) );  //��������ÿһƪ���أ���ÿһƪ����@�����ҳ���
            
                                                                                              //System.out.println(mentionedUser.size());  // p1.2���������԰�������һ������@��4���ˣ�����ȴ��ʾһ���ˣ�˵������������������⣬����λ�ö�λ��p1.3           
            //û��follow���˲���key��
            if(  ! mentionedUser.isEmpty() ) {
                if( networkMap.containsKey( oneTweet.getAuthor() ) ) {
                    mentionedUser.addAll( networkMap.get(oneTweet.getAuthor()) );
                    networkMap.put(oneTweet.getAuthor(), mentionedUser );
                }else {
                    networkMap.put(oneTweet.getAuthor(), mentionedUser );                   
                }               
            }                     
        } 
        
        //��#��ͬ�����follow����ȥ
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
        
        /*ð������*/
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
            int currentPoint = 0;                                       //cp�Ƕ���#��ʱ�������ڵ�λ�ã� p��#�ź������һ����ĸ����ʱcpӦ�����ڵ�λ��
            int point = 0;
            
            //����һ�仰��ÿһ����ĸ
            while(currentPoint <length) {
                StringBuffer topicBuffer = new StringBuffer();
                              
                if( (textArray[currentPoint] == '#' && currentPoint == 0) 
                        || ((currentPoint > 0) && (textArray[currentPoint] == '#') && (textArray[currentPoint-1] == ' ')) ) {    //����#�ţ��ͱ����������ĸ
                  
                    point = currentPoint;                              //��¼����#�ŵ�λ��
                    
                    for(int i = currentPoint +1; i < length; i++) {   //����#�ź������ĸ
                        
                        point++;                                      //��¼�˴γ���#�ź����ƶ�����λ��
                        
                        //�����Ϸ��ַ�����¼��  �����ո񣬽�ֹ  �� ���������ַ������buffer
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
                    if(topicBuffer.length() > 0 ) {                    //������仰���ҵ����Ļ���Ͷ�Ӧ���ߵ����ּ�¼��ͼ��
                        String topicString = topicBuffer.toString();  //��¼�»�������
                        
                        System.out.println(topicString);
                        if( commonTopicUsers.containsKey(topicString) ) {
                            commonTopicUsers.get(topicString).add(oneTweet.getAuthor());
                        }else {
                            commonTopicUsers.put(topicString, new HashSet<>(Arrays.asList(oneTweet.getAuthor())));
                        }                       
                    }
                    currentPoint = point;                           //���µ�ǰ�����ָ��λ��                       
                }
                currentPoint++;                                     //ָ������                      
            }                                                            
        }     
        //�����������غ󣬵õ�һ��keyΪ���⣬valueΪ���۸û����һȺ�˵�map�����洦���map
        for(String key : commonTopicUsers.keySet()) {               //����ÿһ������
            
            for(String person : commonTopicUsers.get(key)) {        //����һ�������Ӧ��һ�����е�ÿһ����
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
