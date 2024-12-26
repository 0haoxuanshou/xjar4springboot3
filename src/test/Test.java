

import io.xjar.XCryptos;


public class Test {

    /**
     * 加密密码
     *
     */
    private final String password = "xdkj.#pwd@io.xjar";


    /**
     * 使用方式
     *
     * 1. 修改jarpath  和  加密文件名
     * 2. 修改from参数
     * 3. 执行main方法生成启动器源码 xjar.go
     * 4. 执行go build 交叉变异 xjar.go ，将会生成 xjar 启动器
     * 5. 到目标机器执行 /path/to/xjar /path/to/java [OPTIONS] -jar /path/to/encrypted.jar ,即可启动项目
     *
     * 这里加密以下内容：
     * 1. classpath下的/static目录下的所有文件不加密
     * 2. classpath下的/META-INF/resources/的所有文件不加密
     * 3. 除1，2点外，其他文件全部加密
     *
     *
     */
    @org.junit.Test
    public void generationJar() {

        /**
         * 源码jars包全路径
         */
        String jarpath = "D:\\workspace\\boot3\\target";
        try {
            XCryptos.encryption()
                    .from(jarpath + "/boot3.jar")  // 加密的源文件
                    .use(password)
                    .exclude("/static/**/*")
                    .exclude("/META-INF/resources/**/*")
                    .to(jarpath + "/encrypted5.jar");  // 输出文件
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * 这里加密以下内容：
     *
     * 1. com.xd包下的所有class
     * 2. classes路径下的所有配置文件
     * 3. 指定了jdk執行文件的md5,也就是指定了必须要特定版本的jdk才能运行此程序
     *
     * 简单的说就是,如果有include配置,那么只会加密include的内容，如果没有include配置，则整个项目全部加密。
     * 如果include配置和exclude配置都存在，那么会将include里面配置排除掉exclude, 剩下的内容进行加密处理
     *
     * 关于配置指定jdk版本
     * 1. 首先下载并安装相应的jdk
     * 2. 下载java.exe， windows/linux可能不一样
     * 3. 使用项目目录的 md5计算工具 计算md5值，然后配置进去即可
     */
    @org.junit.Test
    public void generationJar2() {

        /**
         * 源码jars包全路径
         */
        String jarpath = "D:\\workspace\\demotestjvm\\target";
        try {
            XCryptos.encryption()
                    .from(jarpath + "/demotestjvm-0.0.1-SNAPSHOT.jar")  // 加密的源文件
                    .use(password)
                    // 这里是配置执行的java.exe文件的md5,也就是只有指定版本的jdk才能运行
                    // 可以指定多个，一个匹配即可运行；使用128bit md5值进行比较，项目跟目录有现成的md5计算工具
                    .jdkmd5(new String[]{
                            "683C5B2267B21EAC97A949BAB59CE46B",
                            "99695C06445AF934F4FC40DA7A39DD10"
                    })
                    .include("/com/xd/**")
                    .include("/*.*")
                    .to(jarpath + "/encrypted5.jar");  // 输出文件
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}