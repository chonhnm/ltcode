# 构建自定义的同步工具

## 状态依赖性的管理

### 可阻塞的状态依赖操作的结构
```
acquire lock on object state
while(precondition does not hold) {
    release lock
    wait until precondition might hold
    optionally fail if interrupted or timeout expires
    reacquire lock
}
perfom action
release lock
```

### 条件列队
一个对象持有锁也持有条件列队。Object的wait，notify，notifyAll方法构成条件列队的API。
锁与条件列队是互相关联的。只有持有对象的锁才能调用对象中条件列队的方法。
内置的条件列队都是非公平的，显示Condition可以提供公平和非公平的列队。

## 使用条件列队

### 条件谓词 (The condition predicate)
- 条件谓词是由多个状态变量构成的表达式。
- 条件谓词是使某个操作成为状态依赖操作的前提条件。比如take的条件谓词是“buffer不为空”，put的条件谓词是“buffer不满”。
- 条件等待包含三元关系：锁，wait方法和条件谓词。条件谓词包含多个状态变量，状态变量由锁保护，所以在测试条件谓词之前需要先获取锁。
锁对象与条件列队对象（调用wait，notify的对象）必须是同一个。
- 调用wait前必须持有锁对象，调用后自动释放锁，唤醒时重新获得锁。

### 过早唤醒 
过早唤醒的原因：
- 由notifyAll唤醒时条件谓词不一定为真。
- 由notifyAll唤醒时条件谓词为真，但是其他线程先获取了锁，并将条件谓词设为否。
- 一个条件列队可以包含多个条件谓词（如：buffer不为空，buffer不满）。唤醒时wait相关的谓词不一定为真。

状态依赖方法的标准形式
```
void stateDependentMethod() throws InterruptedException {
    synchronized(lock) {
        while(!conditionPredicate()) {
            lock.wait();
        }
        // do something
    }
}
```

### 信号丢失
线程在调用wait之前没有检查条件谓词，如果此时条件谓词已经为真，那么就可能不会再有notify事件，导致线程一直无法被唤醒。
所以在调用wait之前，一定要先检查谓词。


### 通知
- 调用notify前必须获取条件列队相关联的锁，调用notify后释放锁，所有在该条件列队中等待的线程开始竞争锁（非公平列队）。
- notify唤醒条件列队中一个等待的线程，notifyAll唤醒条件列队中所有等待的线程。
- 因为一个条件列队可以包含多个条件谓词，所以使用notify很危险，它唤醒的可能不是需要的线程。
- 使用notify时必须同时满足以下两个条件：
    - 所有等待的线程类型都相同：一个条件列队只包含一个条件谓词。
    - 单进单出：条件变量上的每次通知，最多只能唤醒一个线程来执行。
- 单次通知和条件通知都是优化措施，优先保证程序正确。

### 子类安全问题
- 条件通知或者单通知所带来的限制条件会使子类复杂化。
- 父类要么完全暴露wait和notify的协议及文档说明，要么完全对子类隐藏。
- 隐藏可用final修饰类，或者对子类隐藏locks, condition queues and state variables.

### 进入和退出协议
- 进入协议指该操作的条件谓词。
- 退出协议指检查该操作改变了哪些状态变量，这些状态变量会使哪些条件谓词成真，以便通知对应的条件列队。
- AbstractQueuedSynchronizer使用了本协议。

### 外部条件对象
- Condition对象是内部条件列队的一般化。正如Lock之于内部锁。
- 内部锁绑定一个内部条件列队。外部Lock可以对应多个Condition
