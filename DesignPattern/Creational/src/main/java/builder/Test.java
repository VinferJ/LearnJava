package builder;

import java.util.function.Predicate;

/**
 * @author vinfer
 * @date 2021-07-11 12:47
 */
public class Test {

    public static void main(String[] args) {
        Predicate<String> predicate = String::isEmpty;
        boolean b = PermissionAuthBuilder.builder().withPermission(predicate).thenAuthorize();
    }

}
