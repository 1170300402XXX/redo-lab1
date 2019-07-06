package P3;


public class Person {
    
    String name;
    
 

    /**
     * @param name
     */
    public Person( String name) {
        this.name = new String(name);
    }
    
    
    /**
     * get person name in string type
     * 
     * @return name
     */
    public String getName() {
        return new String(this.name);
    }
    
    
    /*为什么要重写equals函数和hashcode函数？因为在关系图类中用到 了自定义类相等的比较，
       * （当时为了产生set中加入元素时，不可以重复的想法）而比较两个自定义类。
    * 
       * 所以比较两个自定义类是否相等，需要重写这两个方法。具体参考了一下两个网站：
    * 
    *    https://www.cnblogs.com/yuxiaole/p/9570850.html
    *    https://blog.csdn.net/monkeyduck/article/details/11633061
    * */
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Person) {
            if ( ((Person) obj).name.equals(this.name) ) {
                return true;
            }
        }

        return false;
    }
    
    @Override
    public int hashCode() {
        int result = 17;       
        result = result * 31 + ( name == null ? 0 : name.hashCode() );
        return result;
        
    }
    
    
    @Override
    public String toString() {
        return new String(this.name);
    }
    
    
    

 


}
