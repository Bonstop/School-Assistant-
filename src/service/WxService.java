package service;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class WxService {
	//΢�ŷ������֤ǩ��:
	private static final String TOKEN = "bonstop";//�����Token����ֵ�����΢�ſ��������÷�ʽһ��//
	private static String sha1(String s){ // ���ܷ���//
		try{
			//��ȡһ�����ܵĶ���//
			MessageDigest md = MessageDigest.getInstance("sha1");
			//����//
			byte[] digest = md.digest(s.getBytes());
			char[] chars = {'0' , '1' , '2' , '3' , '4' , '5' , '6' , '7' , '8' , '9' , 'a' , 'b' , 'c' , 'd' , 'e' , 'f'};
			StringBuilder sb = new StringBuilder();
			//�������ܵĽ��//
			for(byte b : digest)
			{
				sb.append(chars[(b >> 4) & 15]);//ȡ�����λ//
				sb.append(chars[b & 15]);//ȡ�����λ//
			}
			return sb.toString();
		}
		catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		return null;
	}
	public static boolean check(String signature , String timestamp , String nonce){
		 //1����token��timestamp��nonce�������������ֵ������� 
			String[] str = new String[] {TOKEN , timestamp , nonce};
			Arrays.sort(str);
		 //2�������������ַ���ƴ�ӳ�һ���ַ�������sha1���� 
			String s = str[0] + str[1] + str[2];
			String mysig = sha1(s);
			System.out.println(mysig);
			System.out.println(signature);
		 //3�������߻�ü��ܺ���ַ�������signature�Աȣ���ʶ��������Դ��΢��
		return mysig.equalsIgnoreCase(signature);
	}
	
	public static Map<String, String> parseRequest(InputStream is) {
		Map<String , String> map = new HashMap();
		SAXReader reader = new SAXReader();
		try{
			//��ȡ������,��ȡ�ĵ�����//
			Document document = reader.read(is);
			//�����ĵ������ȡ���ڵ�//
			Element root = document.getRootElement();
			//��ȡ���ڵ��µ������ӽڵ�//
			List<Element> elements = root.elements();
			for(Element e : elements){
				map.put(e.getName(), e.getStringValue());
			}
		}
		catch(DocumentException e){
			e.printStackTrace();
		}
		return map;
	}
}