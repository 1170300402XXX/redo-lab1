/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import P4.twitter.SocialNetwork;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    //private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    
    /*added by zhh*/
    private static final Instant d3 = Instant.parse("2016-02-17T10:05:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T10:10:00Z");
    private static final Instant d5 = Instant.parse("2016-02-17T10:15:00Z");
    private static final Instant d6 = Instant.parse("2016-02-17T10:20:00Z");
    private static final Instant d7 = Instant.parse("2016-02-17T10:25:00Z");     
    private static final Instant d8 = Instant.parse("2016-02-17T10:30:00Z"); 
    private static final Instant d9 = Instant.parse("2016-02-17T10:35:00Z"); 
    
    //private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    //private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    
    /*added by zhh*/
    private static final Tweet tweet3 = new Tweet(3, "zhh", "is @xdc it @lzh is @jkm is @gzy and over", d3);//*******4@
    private static final Tweet tweet4 = new Tweet(4, "xdc", "is it @lzh is @jkm is @gzy maybe over", d4);//***3@
    private static final Tweet tweet5 = new Tweet(5, "lzh", "is it @jkm is @gzy maybe over", d5);//****2@
    private static final Tweet tweet6 = new Tweet(6, "jkm", "is it @gzy is over", d6);  //***1@
    private static final Tweet tweet7 = new Tweet(7, "gzy", "is it #lab1 and #lab2 over", d7);  //***0@
    private static final Tweet tweet8 = new Tweet(8, "zzz", "is it and @xdc and @lzh and @jkm and #lab1 over", d8);  //***0@
    private static final Tweet tweet9 = new Tweet(9, "lqd", "is it and @xdc and @lzh and @jkm and #lab2 and @zzz over", d9);  //***0@
    
    //private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void getCommonTopicTest() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
        
        /*test by zhh*/
        Map<String, Set<String>> followsGraph2 = SocialNetwork.getCommonTopic( Arrays.asList(tweet7,tweet8,tweet9));
        
        assertTrue("expected a not empty graph", !followsGraph2.isEmpty());
        
        assertEquals("expected 3 key", 3, followsGraph2.keySet().size()); 
        assertTrue("expected list to contain tweet",  new ArrayList<>(followsGraph2.get("gzy")).containsAll(Arrays.asList("lqd","zzz")) );
        assertTrue("expected list to contain tweet",  new ArrayList<>(followsGraph2.get("zzz")).containsAll(Arrays.asList("gzy")) );
        assertTrue("expected list to contain tweet",  new ArrayList<>(followsGraph2.get("lqd")).containsAll(Arrays.asList("gzy")) );

        
        
        
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
        
        /*test by zhh*/
        Map<String, Set<String>> followsGraph2 = SocialNetwork.guessFollowsGraph( Arrays.asList(tweet3,tweet4,tweet5,tweet6,tweet7,tweet8,tweet9));
        
        assertFalse("expected empty graph", followsGraph2.isEmpty());
        
        System.out.println(followsGraph2.keySet());
        
        assertEquals("expected 7 key", 7, followsGraph2.keySet().size());    //加入tweet8,9  改了#号后由4变7                       
        assertTrue("expected list to contain tweet",  new ArrayList<>(followsGraph2.get("zhh")).containsAll(Arrays.asList("xdc","lzh","jkm","gzy")) );
        assertTrue("expected list to contain tweet",  new ArrayList<>(followsGraph2.get("xdc")).containsAll(Arrays.asList("lzh","jkm","gzy")) );
        assertTrue("expected list to contain tweet",  new ArrayList<>(followsGraph2.get("lzh")).containsAll(Arrays.asList("jkm","gzy")) );
        assertTrue("expected list to contain tweet",  new ArrayList<>(followsGraph2.get("jkm")).containsAll(Arrays.asList("gzy")) );        
        assertTrue("expected list to contain tweet",  new ArrayList<>(followsGraph2.get("gzy")).containsAll(Arrays.asList("zzz","lqd")) ); 
        assertTrue("expected list to contain tweet",  new ArrayList<>(followsGraph2.get("zzz")).containsAll(Arrays.asList("gzy","xdc","lzh","jkm")) ); 
        assertTrue("expected list to contain tweet",  new ArrayList<>(followsGraph2.get("lqd")).containsAll(Arrays.asList("gzy","xdc","lzh","jkm","zzz")) );
        
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
        
        /*test by zhh*/
        Map<String, Set<String>> followsGraph2 = SocialNetwork.guessFollowsGraph( Arrays.asList(tweet3,tweet4,tweet5,tweet6,tweet7,tweet8,tweet9));
        List<String> influencers2 = SocialNetwork.influencers(followsGraph2);
        assertFalse("expected empty list", influencers2.isEmpty());
        assertEquals("expected 7 users", 7, influencers2.size());
        assertEquals("expected 7 users", Arrays.asList("gzy","jkm", "lzh", "xdc","zzz","lqd","zhh"), influencers2);
           
    }
    


    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
