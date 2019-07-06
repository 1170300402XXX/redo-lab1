package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


public class FriendshipGraph {
    
    
    List<Person> people = new ArrayList<>();
    Map<Person, HashSet<Person>> friendshipMap = new HashMap<>();
    
    
    
    /**
     * add a new person to the FriendshipGraph
     * 
     * @param person
     * @return true or false
     */
    public boolean addVertex(Person person) {
        if ( person != null ) {            
            people.add(person);
            return true;
        }  
        System.out.println("没有这个人，不能加入关系图");
        return false;
    }
    
    
    /**
     * add a new friendship to the FriendshipGraph
     * 
     * @param person1
     * @param person2
     * @return true or false
     */
    public boolean addEdge(Person person1, Person person2) {
        if ( people.contains(person1) && people.contains(person2) ) {
            if (friendshipMap.containsKey(person1) ) {
                HashSet<Person> personSet1 = friendshipMap.get(person1);
                personSet1.add(person2);                    //已经在person类中重写了equals和hashcode方法，所以set不会加入重复元素
                friendshipMap.replace(person1, personSet1);
            }else {
                HashSet<Person> ps2 = new HashSet<>();
                ps2.add(person2);
                friendshipMap.put(person1, ps2);
            }
            return true;
        }
        
        if ( !people.contains(person1) ) {
            System.out.println( person1.toString()+"不在关系图中，不能加入这条边" );
        }
        if ( !people.contains(person2) ) {
            System.out.println( person2.toString()+"不在关系图中，不能加入这条边" );
        }   
        return false;
    }
    
    /**
     * Calculating the Minimum Relational Distance between Two People.
     * 
     * 
     * @param person1
     * @param person2
     * @return distance
     */
    public int getDistance(Person person1, Person person2) {
        
        if (person1.equals(person2)) {
            return 0;
        }
        List<Person> friendList = new ArrayList<>();    
        List<Person> reachedFriendList =new ArrayList<>();
        List<Person> nextFriendList = new ArrayList<>();
               
        reachedFriendList.add(person1);  //已经被遍历到的人
        friendList.addAll( friendshipMap.get(person1) );
        //System.out.println("队列"+friendList.toString());
        int size = friendshipMap.get(person1).size();  //广度优先遍历树，获取每一层 的大小       
        int deepth = 1;             //广度优先遍历树正在遍历树层的深度
        
        while ( !friendList.isEmpty() ) {

            if(size !=0 ) {
                size--;               
            }else {
                deepth++;
                size = friendList.size();
                size--;
            }
            Person aPerson = friendList.remove(0);  //每次取队列的第一个元素
            //System.out.println("每次取出元素"+aPerson.toString()); 
            reachedFriendList.add(aPerson);
            
            //System.out.println("已到达名单表"+reachedFriendList.toString()); 
                        
            if ( aPerson.equals( person2 ) ) {
                return deepth;
            }
            
            nextFriendList.clear();
            nextFriendList.addAll(friendshipMap.get(aPerson));  //储存由该点找到的下一批朋友
            //System.out.println("未移除的下一批名单表"+nextFriendList.toString());
            nextFriendList.removeAll(reachedFriendList);
            //System.out.println("下一批名单表"+nextFriendList.toString());
            //friendList.addAll(friendshipMap.get(aPerson));    犯错误，原本用next..来搞一个干净的下一批名单，结果你又add旧的名单
            friendList.addAll(nextFriendList);
            //System.out.println("队列"+friendList.toString());
        }
        return -1;      
    }
    /*
     * 广度优先遍历求无向无权图最短距离
     * 思路：生成广度优先遍历生成树
     * 树的深度即代表该深度的这一层节点与根节点的距离
     * 在队列 friendship中，始终分成两部分，一个是正在遍历的树的一层，一个是正在形成的树的下一层。
     * 所以，用size记录正在遍历的树的这一层剩余的未被遍历的点，每遍历一个点，size-1。
     * 当size=0时，说明队列中正在遍历的这一层遍历完了，队列中只剩下正在形成的树的下一层的元素，且恰好是形成完整的树的下一层元素，
     * 而且他们即将变成正在遍历的树的层。
     * 所以此时，size变成队列的size，也就正好是树的这一层的size。
     *每次size变成0的时候，也就是遍历完树的一层的时候，这时候深度deepth应当+1 。
     *深度deepth表示正在遍历的树的这一层的深度
     *           
     * */
    
    
    public static void main(String[] args) {
        
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);
        System.out.println("应当是1，结果为："+graph.getDistance(rachel, ross)); 
        //should print 1
        System.out.println("应当是2，结果为："+graph.getDistance(rachel, ben)); 
        //should print 2
        System.out.println("应当是0，结果为："+graph.getDistance(rachel, rachel)); 
        //should print 0
        System.out.println("应当是-1，结果为："+graph.getDistance(rachel, kramer)); 
        //should print -1

        
        
        
        
    }

 

}






