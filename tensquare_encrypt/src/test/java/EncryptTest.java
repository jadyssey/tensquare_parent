import com.Tony.encrypt.EncryptApplication;
import com.Tony.encrypt.rsa.RsaKeys;
import com.Tony.encrypt.service.RsaService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EncryptApplication.class)
public class EncryptTest {

    @Autowired
    private RsaService rsaService;

    @Before
    public void before() throws Exception{
    }

    @After
    public void after() throws Exception {
    }

//    测试加密方法
    @Test
    public void genEncryptDataByPubKey() {
        String data = "{\"title\":\"java\"}"; //被加密的Json数据

        try {
            //RSAEncryptDataPEM是使用Rsa加密方法，getServerPubKey为获取公钥
            String encData = rsaService.RSAEncryptDataPEM(data, RsaKeys.getServerPubKey());

            System.out.println("data: " + data);
            System.out.println("encData: " + encData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    测试解密方法
    @Test
    public void test() throws Exception {
//        在控制台获取的被加密后的数据作为输入
        String requestData = "XQ30MH5XHmycFUCJNKixuoDXMBgI7J/TYKYHXCql8mEO/WnhYGS/altTdcIGkE2rd/7sJptk5uIBso0PKjPEjXZ0CLetXjiCCsrhOg2xuJSSoqnQdNPasWu+Y+IVxJw2kK52hpTH/f/rsWQK0EQKNx0DK/UO/hStTJMlactB3TclFGmjtpXX9/UELInEWhNmFS39eCuUpLp0VvLOJeNRadaIhiIO3Qu4g9j4+Cqzk9Qi+cHw2bDzrWCBGTaKOHURDpeaVA7RX5/VkeOsRB03mKYwv49xUN/fIafsmHO82WzOA4Cb1tzpb6Urrjx8vVOCq97aRUHaT4/fpCMFWfb2Dw==";
//        RSADecryptDataPEM是解密方法，getServerPrvKeyPkcs8获取私钥
        String decryptData = rsaService.RSADecryptDataPEM(requestData, RsaKeys.getServerPrvKeyPkcs8());
//        输出解密数据
        System.out.println(decryptData);

    }

}
