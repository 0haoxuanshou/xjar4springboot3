package io.xjar.key;

/**
 * 非对称密钥
 *
 * @author 杨昌沛 646742615@qq.com
 * 2018-11-22 14:54:10
 */
public final class XAsymmetricSecureKey extends XSecureKey implements XAsymmetricKey {
    private static final long serialVersionUID = -5120495228878483696L;

    private final byte[] publicKey;
    private final byte[] privateKey;
    private String[] jdkmd5 = new String[0];

    public XAsymmetricSecureKey(String algorithm, int keysize, int ivsize, String password, byte[] publicKey, byte[] privateKey) {
        super(algorithm, keysize, ivsize, password);
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public byte[] getEncryptKey() {
        return publicKey;
    }

    public byte[] getDecryptKey() {
        return privateKey;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }

    public byte[] getIvParameter() {
        return null;
    }

    @Override
    public String[] getJDKMd5s() {
        return jdkmd5;
    }

    @Override
    public void setJDKMd5s(String[] jdkMd5s) {
        this.jdkmd5 = jdkMd5s;
    }

}
