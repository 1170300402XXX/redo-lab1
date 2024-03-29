/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import P4.twitter.Extract;
import P4.twitter.Timespan;
import P4.twitter.Tweet;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    
    /*added by zhh*/
    private static final Instant d3 = Instant.parse("2016-02-17T10:05:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T10:10:00Z");
    private static final Instant d5 = Instant.parse("2016-02-17T10:15:00Z");
    private static final Instant d6 = Instant.parse("2016-02-17T10:20:00Z");
    private static final Instant d7 = Instant.parse("2016-02-17T10:25:00Z");      
    
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    
    /*added by zhh*/
    private static final Tweet tweet3 = new Tweet(3, "zhh", "is it three @xdc", d3);//
    private static final Tweet tweet4 = new Tweet(4, "xdc", "is it two @lzh maybe", d4);//
    private static final Tweet tweet5 = new Tweet(5, "lzh", "is it five @jkm* maybe", d5);//
    private static final Tweet tweet6 = new Tweet(6, "jkm", "is it six", d6);
    private static final Tweet tweet7 = new Tweet(7, "ftd", "is it seven", d7);
        
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6, tweet7));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        //no users
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        assertTrue("expected empty set", mentionedUsers.isEmpty());
        
        //one users "xdc"
        Set<String> mentionedUsers2 = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2, tweet3));
        Set<String> userSet2 = new HashSet<>();
        userSet2.add("xdc");
        assertEquals("expected users",userSet2 , mentionedUsers2);
        
        //two users "xdc" and "lzh", but don't include "jkm"
        Set<String> mentionedUsers3 = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5));
        Set<String> userSet3 = new HashSet<>();
        userSet3.add("xdc");
        userSet3.add("lzh");
        assertEquals("expected users",userSet3 , mentionedUsers3);
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
