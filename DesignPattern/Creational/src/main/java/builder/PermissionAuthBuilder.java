package builder;

import java.util.function.Predicate;

/**
 * @author vinfer
 * @date 2021-07-10 10:45
 */
public class PermissionAuthBuilder {


    public static AuthBuilder builder(){
        return new AuthBuilder();
    }

    static class AuthBuilder{

        static class PermissionBuilder<T>{

            static PermissionBuilder permissionBuilder;

            public PermissionBuilder(Predicate<T> predicate){
                head = tail = new Node<>();
                head.predicate = predicate;
            }

            static PermissionBuilder build(Predicate predicate){
                if (permissionBuilder == null){
                    permissionBuilder = new PermissionBuilder(predicate);
                }else {
                    Node tail = permissionBuilder.tail;
                    Node newNode = new Node<>();
                    newNode.predicate = predicate;
                    tail.next = newNode;
                    tail = tail.next;
                }
                return permissionBuilder;
            }

            static class Node<T>{
                Node<T> next;
                Predicate<T> predicate;
                LogicOperator operator;
                AuthBuilder builder;
            }

            private Node<T> head;

            private Node<T> tail;

            enum LogicOperator{
                AND,
                OR,
                THEN_FALSE,
                THEN_TURE,
            }

            public AuthBuilder and(){
                tail.operator = LogicOperator.AND;
                return tail.builder;
            }

            public AuthBuilder or(){
                tail.operator = LogicOperator.OR;
                return tail.builder;
            }

            public AuthBuilder thenPredicateFalse(){
                tail.operator = LogicOperator.THEN_FALSE;
                return tail.builder;
            }

            public AuthBuilder thenPredicateTure(){
                tail.operator = LogicOperator.THEN_TURE;
                return tail.builder;
            }

            public boolean thenAuthorize(){
                return false;
            }



        }

        public PermissionBuilder withPermission(Predicate predicate ){
            return PermissionBuilder.build(predicate);
        }

        public PermissionBuilder withoutPermission(Predicate predicate){
            return PermissionBuilder.build(predicate);
        }


    }

}
