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
	 //构造方法，解释dir
	 protected LexReader(String dir){
		 try {
			fr = new FileInputStream(dir);
			isr = new InputStreamReader(fr, "UTF-8");
			br = new BufferedReader(isr);
		} catch (Exception e) {
			System.out.println(dir + " File Not Found!");
		}
	 }
	 //默认构造方法，解释当前目录下code.txt
	 protected LexReader(){
		 try {
			fr = new FileInputStream("E://code.txt");
			isr = new InputStreamReader(fr, "UTF-8");
			br = new BufferedReader(isr);
		} catch (Exception e) {
			System.out.println("code.txt File Not Found!");
		}
	 }
	 //是否数字
	 private boolean isDigit(char c){
		 if(c >= '0' && c <= '9') return true;
		 else return false;
	 }
	 //是否字母
	 private boolean isLetter(char c){
		 if(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' ) return true;
		 else return false;
	 }
	 //是否为数字或字母
	 private boolean isDigitOrLetter(char c){
		 if(isDigit(c) || isLetter(c)) return true;
		 else return false;
	 }
	 //取单词
	 public String[] getLex(){
		 String[] re=new String[2];
		 char ch;
		 StringBuilder sb;
		 try {
			ch = (char) br.read();
			//若首字符为字母，则该单词为标识符
			if(isLetter(ch)){
				sb = new StringBuilder();
				//拼接标识符，读到非字母数字为止
				while(isDigitOrLetter(ch)){
					br.mark(50);						//标记前一位
					sb.append(ch);
					ch = (char) br.read();
				}											//标识符结束
				br.reset();							//回溯至上一位(退回1字符)
				re[0] ="WORD";
				re[1] = new String(sb);
				return re;
			}
			//若首字符为数字，则该单词为数值
			else if(isDigit(ch)){
				sb = new StringBuilder();
				while(isDigit(ch) || ch == '.'){
					br.mark(50);						//标记前一位
					sb.append(ch);
					ch = (char) br.read();
				}
				br.reset();							//回溯至上一位(退回1字符)
				re[0] = "NUM";
				re[1] = new String(sb);
				return re;
			}
			//空白字符，跳过
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
			//单双字节运算符
			else if(ch == '=' || ch =='&' || ch == '|'){
				char ch2;
				br.mark(2);	
				ch2 = (char) br.read();
				
				//是否双字运算符
				if(ch2 == ch){
					re[0] = "" + ch + ch2;
				}else{
					re[0] = ch + "";
					br.reset();
				}
				return re;
			}
			//"开头，字符串
			else if(ch == '\"'){
				sb = new StringBuilder();
				ch = (char) br.read();
				//拼接标识符，读到另一个"为止
				while(ch !='\"'){
					sb.append(ch);
					ch = (char) br.read();
				}										
				re[0] ="STRING";
				re[1] = new String(sb);
				return re;
			}
			//‘开头，字符
			else if(ch == '\''){
				ch = (char) br.read();
				re[1] = ch + "";
				while(ch !='\''){
					ch = (char) br.read();
				}
				re[0] = "CHAR";
				return re;
			}
			//文件结束
			else if(ch == 65535){
				re[0] = "END";
				return re;
			}
			//未识别单词
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
