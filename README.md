java web项目总结的公共包
=====

#包含组件
## 1. SpringUtil
    spring 工具类
## 2. ConditionalOnPrefix
    **说明**:
    spring bean创建条件;当 prefix在配置文件中存在时进行配置
    **用法**:
    1.类或方法增加该注解，并设置prefix
    当prefix在配置文件存在时，加载配置，不存在不加载配置，无论是否有值
## 3. Chain
    **说明**:
    基于Spring的自动装配责任链，责任链节点继承Handle类并且是Spring Bean，并在配置文件中配置责任链信息
    **用法**:
    1. 继承Handle类
    2. 配置文件配置责任链
    如：
    chain:
      chains:
      - id: testChain  # bean Name
        handles:
        - test1Handle
        - test2Handle
        
# 4.PushTime
    **说明**:
    当时间在某段时间内时， 获取一个延时的时间， 比如需求为早上8点到12点的数据， 14点统一进行推送
    **用法**:
    1. 配置文件进行配置
    如：
    pushtime:
      templates:
        # 推送时间配置
        pushKey: # 配置的key,用于表示那个配置列表
          # 周一9:30之前、63小时时间范围，即周五18:30 至 周一9:30 ， 全部延迟到周一10:30
          - deadlineCron: 0 30 9 * * MON  # 截止日期， cron表达式
            timeRange: 226800 #63*60*60  # 时间长度，单位 秒
            pushTimeNo: 1 # 获取第几次的pushTimeCron时间， 如为2 就是获取下下次的延迟时间
            pushTimeCron: 0 30 10 * * MON  # 延迟时间，cron表达式
