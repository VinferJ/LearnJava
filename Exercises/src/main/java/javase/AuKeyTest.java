package javase;

import java.util.ArrayList;
import java.util.List;

/**
 * 傲基科技2021应届春招笔试题（流程图题目）
 *
 * @author vinfer
 * @date 2021-03-07 16:00
 */
public class AuKeyTest {


    public static void main(String[] args) {
        int[] boxes1 = {0,3,1,3,7,9,5,3,12,7,12,5,0};
        System.out.println("=====Result of test1:\nWhat number is now in box 11: "+test1(boxes1));
        int[] boxes2 = {0,3,8,6,2,11,3,6,12,7,-2,4,-6,12};
        System.out.println("=====Result of test2:\nWhat number is now in box 12: "+test2(boxes2));;
        int[] boxes3 = {0,2,11,-9,3,-2,12,9,9,6,8,1,4};
        test3(boxes3);
        System.out.println("=====Result of test3:\nWhat number is now in box 3: "+boxes3[3]+" and box 10: "+boxes3[10]);
        int[] boxes4 = {0,2,8,1,9,3,5,7,-9,-1,6,0,5};
        System.out.println("=====Result of test4:\nWhat number must be in box 11: "+test4(boxes4));
        System.out.println("=====Result of test5:\nWhat number must be in box 3: "+test5());
        System.out.println("=====Result of test6:\nWhat is the smallest number which may be in box 15: "+test6());
        System.out.println("=====Result of test7:\nWhat number must be in box 2: "+test7());
        int[] result8 = test8();
        System.out.println("=====Result of test8:\nThe box["+result8[0]+"] contains the largest number, the box["+result8[1]+"]: " +
                "contains the smallest number");
    }


    static int test1(int[] boxes){
        // step1
        boxes[4] = boxes[2] + boxes[boxes[12]];
        // doing the loop
        while (true){
            // step2
            boxes[7] = boxes[7] * boxes[7];
            // step3
            if (boxes[4] == boxes[9]){
                // breaking the loop if meets the conditions
                break;
            }else {
                // step4
                boxes[4] = boxes[4] + boxes[1];
                // step5
                boxes[7] = boxes[7] - boxes[6];
            }
        }
        // step6
        boxes[10] = boxes[3] + boxes[9];
        // step 7
        boxes[11] = boxes[7] - boxes[10];
        return boxes[11];
    }

    static int test2(int[] boxes){
        // step1
        boxes[12] = boxes[7] - boxes[boxes[6]];
        // def a var for step4
        int secondBoxNumber = 13;
        // doing the loop
        while (true){
            // step2
            boxes[12] = boxes[12] + boxes[secondBoxNumber];
            // step3
            if (boxes[12] % 5 == 0){
                continue;
            }else {
                // step4: Change instruction 2: decrease the second box-number mentioned in it, by (number in the box whose number is in box 11)
                secondBoxNumber = secondBoxNumber - boxes[boxes[11]];
            }
            // step5
            if (boxes[2] < secondBoxNumber){
                // step6
                boxes[11] = boxes[11] - 1;
            }else {
                break;
            }
        }
        return boxes[12];
    }

    static void test3(int[] boxes){
        // step1
        boxes[10] -= 1;
        // def two var for step5
        int firstBoxNumber = 9;
        int secondBoxNumber = 1;
        // step2
        boxes[secondBoxNumber] = boxes[firstBoxNumber];
        // remove the step3, because 9 is not an even number
        // step4: Take as your next instruction, the one whose number is in the box whose number is in box 8.
        int stepNumber = boxes[boxes[8]];
        switch (stepNumber){
            // only exec the value changing operations
            case 1: boxes[10] -= 1;break;
            case 6: boxes[10] = boxes[10] + boxes[4];break;
            default:break;
        }
        // step5
        // exec step5 when the last step number is less than 5
        if (stepNumber < 5){
            firstBoxNumber -= 1;
        }
        // step7
        firstBoxNumber -= boxes[11];
        secondBoxNumber += boxes[11];
        // simplify the step from step8 to step9
        if (secondBoxNumber != boxes[12]){
            // step9: Take as your next Instruction, the one before Instruction 3
            // so exec the step2 again
            boxes[secondBoxNumber] = boxes[firstBoxNumber];
        }

    }

    static int test4(int[] boxes){
        // def a var for step2 and step3
        int lastBoxNumber = 4;
        while (!checkCondition(boxes)){
            // step1
            boxes[lastBoxNumber] = 0;
            // step2
            lastBoxNumber+=1;
            // step3: check the condition here
            // if last box-number is equals to boxes[11], the loop should be breaking here
            // so the value in boxes[11] should be the lastBoxNumber in loop's breaking
        }
        return lastBoxNumber;
    }

    static boolean checkCondition(int[] boxes){
        return boxes[4] == 0
                && boxes[5] == 0
                && boxes[6] == 0
                && boxes[7] == 0;
    }

    static int test5(){
        // this question does not require code to achieve
        // the number must be in box 3 is 4
        // because the box5 and box6 has no number def in their box
        // so the loop of flow-chart must be breaking in third round
        // so the condition of step3 must be satisfied
        // so the number in box3 is not greater than 4+1+1, which is 6
        return 6;
    }

    static int test6(){
        int numberInBox15 = 0;
        int secondBoxNumber = 13;

        // in order to find the target value that satisfy the condition
        // the numberInBox15 must be init as zero, then increasing by 1 in each loop until find the target value
        while (numberInBox15 <= secondBoxNumber){
            // the last double box-number must be box5'number
            if (secondBoxNumber == 5){
                break;
            }
            // step1: double the target box, this step in code can be ignored

            // step2
            secondBoxNumber -= 2;

            // if numberInBox15 is still less then secondBoxNumber, that means the target values is still not be found
            // so keep increasing the var by 1
            ++numberInBox15;
        }
        // the required values must be the smallest values that satisfy the condition
        // it's easy to be told from the code that the loop will break when the secondBoxNumber equals to 3,
        // but the target values must be greater then 3 and less then or equals to 5
        // so the smallest values that between above-mentioned values, only can be 4

        return numberInBox15;
    }

    static int test7(){
        //step1, ignore here...

        int firstNumber = 11;
        int secondBoxNumber = 3;
        // the breaking condition for the loop is that the list contains 5 following elements:
        // 3,4,5,10,11
        List<Integer> numberAddUpList = new ArrayList<>(5);
        while (!checkCondition(numberAddUpList)){
            // step2: this step is equivalent to the following operation
            numberAddUpList.add(secondBoxNumber);

            // step3
            // because we need to find the number in box2,
            // so the condition here should be removed

            // step4: this step is equivalent to the fallowing operation
            numberAddUpList.add(firstNumber);
            // step5
            secondBoxNumber++;
            // step6
            firstNumber--;
        }
        // the number in the box2 must be the last element that be added into the list,
        // which makes the last element be the breaking condition of the loop
        return numberAddUpList.get(4);
    }

    static boolean checkCondition(List<Integer> numberAddUpList){
        return numberAddUpList.contains(3)
                && numberAddUpList.contains(4)
                && numberAddUpList.contains(5)
                && numberAddUpList.contains(10)
                && numberAddUpList.contains(11);
    }

    static int[] test8(){
        // this question does not require code to achieve
        // it's easy to choose the a right flow from the flow-chart
        // obviously the flow should goes like: (3) > (1) && (2) > (1) && (3) < (2)
        // it's easy to know from the above-mentioned flow that,
        // box2 contains the largest number and box1 contains the smallest number
        return new int[]{2,1};
    }

}
