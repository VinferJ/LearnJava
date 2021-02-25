package string;

/**
 * @author Vinfer
 * @date 2020-12-24    00:57
 **/
public class FindDifference {

    public static void main(String[] args) {
        String s = "suoihsadhi";
        String t = "usotihsadhi";
        System.out.println(solution1(s,t));
    }

    public static char solution1(String s,String t){
        if (s.equals("")){
            return t.charAt(0);
        }else {
            String sub1 = t.substring(0, s.length());
            String sub2 = t.substring(1);
            if (sub1.equals(s)){
                return t.charAt(t.length()-1);
            }
            if (sub2.equals(s)){
                return t.charAt(0);
            }
            for (int i = 0; i < s.length(); i++) {
                String sub = s.substring(0, i + 1);
                if (!t.contains(sub)){
                    return t.charAt(i);
                }
            }
            return '#';
        }
    }

}
