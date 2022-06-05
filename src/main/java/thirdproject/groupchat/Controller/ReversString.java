package thirdproject.groupchat.Controller;

public class ReversString {
    public static String reverseString(String str){
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        return sb.toString();
    }
}
