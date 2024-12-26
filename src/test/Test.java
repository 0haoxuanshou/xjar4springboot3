

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
     *
     * 简单的说就是,如果有include配置,那么只会加密include的内容，如果没有include配置，则整个项目全部加密。
     * 如果include配置和exclude配置都存在，那么会将include里面配置排除掉exclude, 剩下的内容进行加密处理
     */
    @org.junit.Test
    public void generationJar2() {

        /**
         * 源码jars包全路径
         */
        String jarpath = "D:\\workspace\\boot3\\target";
        try {
            XCryptos.encryption()
                    .from(jarpath + "/xd-battery-web-console-5.0.3.20240613.jar")  // 加密的源文件
                    .use(password)
                    .include("/com/xd/**")
                    .include("/*.*")
                    .to(jarpath + "/encrypted5.jar");  // 输出文件
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}