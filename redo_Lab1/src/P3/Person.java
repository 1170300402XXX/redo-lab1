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
    
    
    /*ΪʲôҪ��дequals������hashcode��������Ϊ�ڹ�ϵͼ�����õ� ���Զ�������ȵıȽϣ�
       * ����ʱΪ�˲���set�м���Ԫ��ʱ���������ظ����뷨�����Ƚ������Զ����ࡣ
    * 
       * ���ԱȽ������Զ������Ƿ���ȣ���Ҫ��д����������������ο���һ��������վ��
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
