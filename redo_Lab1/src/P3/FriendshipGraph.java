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
        System.out.println("û������ˣ����ܼ����ϵͼ");
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
                personSet1.add(person2);                    //�Ѿ���person������д��equals��hashcode����������set��������ظ�Ԫ��
                friendshipMap.replace(person1, personSet1);
            }else {
                HashSet<Person> ps2 = new HashSet<>();
                ps2.add(person2);
                friendshipMap.put(person1, ps2);
            }
            return true;
        }
        
        if ( !people.contains(person1) ) {
            System.out.println( person1.toString()+"���ڹ�ϵͼ�У����ܼ���������" );
        }
        if ( !people.contains(person2) ) {
            System.out.println( person2.toString()+"���ڹ�ϵͼ�У����ܼ���������" );
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
               
        reachedFriendList.add(person1);  //�Ѿ�������������
        friendList.addAll( friendshipMap.get(person1) );
        //System.out.println("����"+friendList.toString());
        int size = friendshipMap.get(person1).size();  //������ȱ���������ȡÿһ�� �Ĵ�С       
        int deepth = 1;             //������ȱ��������ڱ�����������
        
        while ( !friendList.isEmpty() ) {

            if(size !=0 ) {
                size--;               
            }else {
                deepth++;
                size = friendList.size();
                size--;
            }
            Person aPerson = friendList.remove(0);  //ÿ��ȡ���еĵ�һ��Ԫ��
            //System.out.println("ÿ��ȡ��Ԫ��"+aPerson.toString()); 
            reachedFriendList.add(aPerson);
            
            //System.out.println("�ѵ���������"+reachedFriendList.toString()); 
                        
            if ( aPerson.equals( person2 ) ) {
                return deepth;
            }
            
            nextFriendList.clear();
            nextFriendList.addAll(friendshipMap.get(aPerson));  //�����ɸõ��ҵ�����һ������
            //System.out.println("δ�Ƴ�����һ��������"+nextFriendList.toString());
            nextFriendList.removeAll(reachedFriendList);
            //System.out.println("��һ��������"+nextFriendList.toString());
            //friendList.addAll(friendshipMap.get(aPerson));    ������ԭ����next..����һ���ɾ�����һ���������������add�ɵ�����
            friendList.addAll(nextFriendList);
            //System.out.println("����"+friendList.toString());
        }
        return -1;      
    }
    /*
     * ������ȱ�����������Ȩͼ��̾���
     * ˼·�����ɹ�����ȱ���������
     * ������ȼ��������ȵ���һ��ڵ�����ڵ�ľ���
     * �ڶ��� friendship�У�ʼ�շֳ������֣�һ�������ڱ���������һ�㣬һ���������γɵ�������һ�㡣
     * ���ԣ���size��¼���ڱ�����������һ��ʣ���δ�������ĵ㣬ÿ����һ���㣬size-1��
     * ��size=0ʱ��˵�����������ڱ�������һ��������ˣ�������ֻʣ�������γɵ�������һ���Ԫ�أ���ǡ�����γ�������������һ��Ԫ�أ�
     * �������Ǽ���������ڱ��������Ĳ㡣
     * ���Դ�ʱ��size��ɶ��е�size��Ҳ��������������һ���size��
     *ÿ��size���0��ʱ��Ҳ���Ǳ���������һ���ʱ����ʱ�����deepthӦ��+1 ��
     *���deepth��ʾ���ڱ�����������һ������
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
        System.out.println("Ӧ����1�����Ϊ��"+graph.getDistance(rachel, ross)); 
        //should print 1
        System.out.println("Ӧ����2�����Ϊ��"+graph.getDistance(rachel, ben)); 
        //should print 2
        System.out.println("Ӧ����0�����Ϊ��"+graph.getDistance(rachel, rachel)); 
        //should print 0
        System.out.println("Ӧ����-1�����Ϊ��"+graph.getDistance(rachel, kramer)); 
        //should print -1

        
        
        
        
    }

 

}






