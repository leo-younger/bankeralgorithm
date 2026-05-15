# 银行家算法

操作系统死锁避免经典算法，判断系统是否处于安全状态，并模拟进程资源请求。

## 运行

```bash
mvn compile exec:java -Dexec.mainClass="BankerAlgorithm"
```

或直接在 IDE 中运行 `BankerAlgorithm.main()`。

## 示例数据

5 个进程，3 类资源，初始 Available = [3, 3, 2]。

输入进程编号（1~5）和三类资源的请求量，程序会判断该请求是否安全并给出安全序列。

## 结构

```
src/main/java/
└── BankerAlgorithm.java    # 单文件实现
```
