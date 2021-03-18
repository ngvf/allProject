package com.imooc.ms.mytool.QRcode;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
 
import javax.imageio.ImageIO;
 
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
 
import com.swetake.util.Qrcode;
//import com.youge.util.JdbcDao;
//import com.youge.util.WebUtil;
 
/**
 * 批量二维码
 */
public class test {
//  private JdbcTemplate jt = JdbcDao.getJdbcTemplate();
  public static void main(String[] args) throws IOException {
	 test t=new test();
    for(int i=2018700001;i<2018700003;i++){
      Map<String, Object> map=new HashMap<>();
      String q_qrcode=i+"";//http://www.injiaxing.com:8080/youge/mApply/home.shtml?p_qrcode=
      String p_code=i+"";
      map.put("q_qrcode", q_qrcode);
      map.put("p_code", p_code);
//      t.insertQrcode(map);
      t.createQrcode(i+"");
    }
  }
 
//  public int insertQrcode(Map<String, Object> params){
//    String sql=" insert into y_qrcode (q_qrcode,p_code) values(?,?)";
//    return jt.update(sql,params.get("q_qrcode"),params.get("p_code"));
//  }
 
  public void createQrcode(String str) throws IOException{
    //计算二维码图片的高宽比
    // API文档规定计算图片宽高的方式 ，v是本次测试的版本号
    int v =6;
    int width = 67 + 12 * (v - 1);
    int height = 67 + 12 * (v - 1);
 
 
    Qrcode x = new Qrcode();
    /**
     * 纠错等级分为
     * level L : 最大 7% 的错误能够被纠正；
     * level M : 最大 15% 的错误能够被纠正；
     * level Q : 最大 25% 的错误能够被纠正；
     * level H : 最大 30% 的错误能够被纠正；
     */
    x.setQrcodeErrorCorrect('L');
    x.setQrcodeEncodeMode('B');//注意版本信息 N代表数字 、A代表 a-z,A-Z、B代表 其他)
    x.setQrcodeVersion(v);//版本号 1-40
    String qrData = "http://localhost:8081/qrcode?p_qrcode="+str;//内容信息
 
    byte[] d = qrData.getBytes("utf-8");//汉字转格式需要抛出异常
 
    //缓冲区
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
 
    //绘图
    Graphics2D gs = bufferedImage.createGraphics();
 
    gs.setBackground(Color.WHITE);
    gs.setColor(Color.BLACK);
    gs.clearRect(0, 0, width, height);
 
    //偏移量
    int pixoff = 2;
 
 
    /**
     * 容易踩坑的地方
     * 1.注意for循环里面的i，j的顺序，
     *  s[j][i]二维数组的j，i的顺序要与这个方法中的 gs.fillRect(j*3+pixoff,i*3+pixoff, 3, 3);
     *  顺序匹配，否则会出现解析图片是一串数字
     * 2.注意此判断if (d.length > 0 && d.length < 120)
     *  是否会引起字符串长度大于120导致生成代码不执行，二维码空白
     *  根据自己的字符串大小来设置此配置
     */
    if (d.length > 0 && d.length < 120) {
      boolean[][] s = x.calQrcode(d);
 
      for (int i = 0; i < s.length; i++) {
        for (int j = 0; j < s.length; j++) {
          if (s[j][i]) {
            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
          }
        }
      }
    }
    gs.dispose();
    bufferedImage.flush();
    //设置图片格式，与输出的路径
    ImageIO.write(bufferedImage, "png", new File("D:/qrcode/"+str+".png"));
    System.out.println("二维码生成完毕");
  }
 
 
}