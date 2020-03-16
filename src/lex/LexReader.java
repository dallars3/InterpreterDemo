package lex;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import token.*;

public class LexReader {
	 FileInputStream fr;
	 InputStreamReader isr;
	 BufferedReader br;
	 //���췽��������dir
	 protected LexReader(String dir){
		 try {
			fr = new FileInputStream(dir);
			isr = new InputStreamReader(fr, "UTF-8");
			br = new BufferedReader(isr);
		} catch (Exception e) {
			System.out.println(dir + " File Not Found!");
		}
	 }
	 //Ĭ�Ϲ��췽�������͵�ǰĿ¼��code.txt
	 protected LexReader(){
		 try {
			fr = new FileInputStream("E://code.txt");
			isr = new InputStreamReader(fr, "UTF-8");
			br = new BufferedReader(isr);
		} catch (Exception e) {
			System.out.println("code.txt File Not Found!");
		}
	 }
	 //�Ƿ�����
	 private boolean isDigit(char c){
		 if(c >= '0' && c <= '9') return true;
		 else return false;
	 }
	 //�Ƿ���ĸ
	 private boolean isLetter(char c){
		 if(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' ) return true;
		 else return false;
	 }
	 //�Ƿ�Ϊ���ֻ���ĸ
	 private boolean isDigitOrLetter(char c){
		 if(isDigit(c) || isLetter(c)) return true;
		 else return false;
	 }
	 //ȡ����
	 public String[] getLex(){
		 String[] re=new String[2];
		 char ch;
		 StringBuilder sb;
		 try {
			ch = (char) br.read();
			//�����ַ�Ϊ��ĸ����õ���Ϊ��ʶ��
			if(isLetter(ch)){
				sb = new StringBuilder();
				//ƴ�ӱ�ʶ������������ĸ����Ϊֹ
				while(isDigitOrLetter(ch)){
					br.mark(50);						//���ǰһλ
					sb.append(ch);
					ch = (char) br.read();
				}											//��ʶ������
				br.reset();							//��������һλ(�˻�1�ַ�)
				re[0] ="WORD";
				re[1] = new String(sb);
				return re;
			}
			//�����ַ�Ϊ���֣���õ���Ϊ��ֵ
			else if(isDigit(ch)){
				sb = new StringBuilder();
				while(isDigit(ch) || ch == '.'){
					br.mark(50);						//���ǰһλ
					sb.append(ch);
					ch = (char) br.read();
				}
				br.reset();							//��������һλ(�˻�1�ַ�)
				re[0] = "NUM";
				re[1] = new String(sb);
				return re;
			}
			//�հ��ַ�������
			else if(ch == '\t' || ch == '\n' || ch == '\r' || ch == ' '){
				re[0] = "NULL";
				return re;
			}
			else if(ch == ';' || ch == '+' || ch == '-' || ch == '*' || ch == '/' 
					|| ch == '.' || ch == '(' || ch == ')' || ch == '[' || ch == ']' 
					|| ch == '{' || ch == '}' || ch == ',' || ch == '>' || ch == '<'
					|| ch == '!' || ch == '~'){
				re[0] = ch + "";
				return re;
			}
			//��˫�ֽ������
			else if(ch == '=' || ch =='&' || ch == '|'){
				char ch2;
				br.mark(2);	
				ch2 = (char) br.read();
				
				//�Ƿ�˫�������
				if(ch2 == ch){
					re[0] = "" + ch + ch2;
				}else{
					re[0] = ch + "";
					br.reset();
				}
				return re;
			}
			//"��ͷ���ַ���
			else if(ch == '\"'){
				sb = new StringBuilder();
				ch = (char) br.read();
				//ƴ�ӱ�ʶ����������һ��"Ϊֹ
				while(ch !='\"'){
					sb.append(ch);
					ch = (char) br.read();
				}										
				re[0] ="STRING";
				re[1] = new String(sb);
				return re;
			}
			//����ͷ���ַ�
			else if(ch == '\''){
				ch = (char) br.read();
				re[1] = ch + "";
				while(ch !='\''){
					ch = (char) br.read();
				}
				re[0] = "CHAR";
				return re;
			}
			//�ļ�����
			else if(ch == 65535){
				re[0] = "END";
				return re;
			}
			//δʶ�𵥴�
			else{
				re[0] = "ERR";
				return re;
			}
			
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return re;
		 
	 }
	 

}
