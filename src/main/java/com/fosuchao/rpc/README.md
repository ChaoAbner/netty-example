# 实现RPC服务
## 什么是RPC
RPC，即远程过程调用。在现在的分布式、微服务架构中，RPC发挥着很重要的作用。
在我们的个人的小项目中基本都是使用单体架构，写了很多服务模块之后，模块之间相互调用。如果只是自己联系或者用户量很少的话基本没什么问题，但是当用户量躲起来的时候，难免会遇到很多性能瓶颈，需要将我们的单体架构换成分布式架构，甚至将服务拆分成各个模块分散在不同的服务器上。在这种场景下。RPC就诞生了。

## RPC的实现过程
![](http://img.fosuchao.com/20200224171407.png)





参考链接
https://pjmike.github.io/2019/10/05/%E5%9F%BA%E4%BA%8ENetty%E5%AE%9E%E7%8E%B0%E7%AE%80%E6%98%93RPC%E6%A1%86%E6%9E%B6/?utm_source=tuicool&utm_medium=referral
https://xilidou.com/2018/09/26/dourpc-remoting/
