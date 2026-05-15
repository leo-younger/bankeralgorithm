import java.util.Arrays;
import java.util.Scanner;

public class BankerAlgorithm {
    public static final int PROCESS_NUM = 5;
    public static final int RESOURCE_NUM = 3;

    public static int[][] max = new int[PROCESS_NUM][RESOURCE_NUM];
    public static int[][] allocation = new int[PROCESS_NUM][RESOURCE_NUM];
    public static int[][] need = new int[PROCESS_NUM][RESOURCE_NUM];
    public static int[] available = new int[RESOURCE_NUM];
    public static boolean[] finish = new boolean[PROCESS_NUM];

    public static void init()
        {
        max = new int[][]{
                {7, 5, 3},
                {3, 2, 2},
                {9, 0, 2},
                {2, 2, 2},
                {4, 3, 3}
        };
        allocation = new int[][]{
                {0, 1, 0},
                {2, 0, 0},
                {3, 0, 2},
                {2, 1, 1},
                {0, 0, 2}
        };
        need = new int[][]{
                {7, 4, 3},
                {1, 2, 2},
                {6, 0, 0},
                {0, 1, 1},
                {4, 3, 1}
        };
        available = new int[]{3, 3, 2};
        }

    public static void show()
        {
        System.out.println("进程\\资源情况    Max     Allocation      Need        Available");
        for (int i = 0; i < PROCESS_NUM; i++) {
            System.out.print("P" + (i + 1) + ":");
            System.out.print("           ");
            print(max, i);
            System.out.print("      ");
            print(allocation, i);
            System.out.print("       ");
            print(need, i);

            if (i == 0) {
                System.out.print("       ");
                for (int j = 0; j < RESOURCE_NUM; j++) {
                    System.out.print(available[j] + " ");
                }

            }
            System.out.println();
        }
        }

    public static void print(int[][] arr, int i)
        {
        for (int j = 0; j < RESOURCE_NUM; j++) {
            System.out.print(arr[i][j] + " ");
        }
        }

    public static void calNeed()
        {
        for (int i = 0; i < PROCESS_NUM; i++) {
            for (int j = 0; j < RESOURCE_NUM; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
        }

    public static boolean isSafe()
        {
        Arrays.fill(finish, false);
        int count = 0;
        int[] safeSeq = new int[PROCESS_NUM];

        int[] work = copyToWork();

        while (count < PROCESS_NUM) {
            int process = -1;
            boolean isFindProcess = false;
            //找满足条件的进程
            for (int i = 0; i < PROCESS_NUM; i++) {
                if (!finish[i] && isProcessCanRun(i, work)) {
                    process = i;
                    isFindProcess = true;
                    break;
                }
            }

            if (isFindProcess) {
                //用找到的进程进行操作
                for (int i = 0; i < RESOURCE_NUM; i++) {
                    work[i] += allocation[process][i];
                }
                safeSeq[count] = process;
                count++;
                finish[process] = true;
            } else {
                System.out.println("系统不安全");
                return false;
            }

        }
        System.out.println("系统安全，安全序列为：");
        for (int num : safeSeq) {
            System.out.print("P" + num + " ");
        }
        System.out.println();
        return true;
        }

    //拷贝available数组到work
    public static int[] copyToWork()
        {
        int[] work = new int[RESOURCE_NUM];
        for (int i = 0; i < RESOURCE_NUM; i++) {
            work[i] = available[i];
        }
        return work;
        }

    //判断i进程是否满足资源分配
    public static boolean isProcessCanRun(int i, int[] work)
        {
        return arrayLessOrEqual(need[i], work);
        }

    //判断数组a所有元素<=b
    public static boolean arrayLessOrEqual(int[] a, int[] b)
        {
        for (int i = 0; i < RESOURCE_NUM; i++) {
            if (a[i] > b[i]) {
                return false;
            }
        }
        return true;
        }

    public static boolean request(int[] pid, int process)
        {
        if (!preRequest(pid, process)) {
            System.out.println("请求资源超出，出现死锁");
            return false;
        }
        //修改资源数available，allocation，need
        updateResource(pid, process);
        return isSafe();
        }

    public static boolean preRequest(int[] pid, int process)
        {
        if (arrayLessOrEqual(pid, need[process]) && arrayLessOrEqual(pid, available)) {
            return true;
        }
        return false;
        }

    public static void updateResource(int[] pid, int process)
        {
        for (int i = 0; i < RESOURCE_NUM; i++) {
            available[i] -= pid[i];
            allocation[process][i] += pid[i];
        }
        calNeed();
        }

    public static void main(String[] args)
        {
        init();
        show();
        if (isSafe()) {
            int[] pid = new int[RESOURCE_NUM];
            int process;
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入为哪个进程请求资源：");
            process = sc.nextInt() - 1;
            System.out.println("请输入请求资源数：");
            for (int i = 0; i < RESOURCE_NUM; i++) {
                pid[i] = sc.nextInt();
            }
            request(pid, process);

        }
        }

}
